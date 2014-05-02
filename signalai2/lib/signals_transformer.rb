class SignalsTransformer
  attr_reader :original_signals, :noise, :from_ck_number, :till_ck_number, :options

  def initialize(signals, options = {})
    @original_signals = signals
    @options = options
    @noise = options[:noise] || []
    @from_ck_number = options[:from_ck_number]
    @till_ck_number = options[:till_ck_number]
  end

  def fourier(options = {})
    @fourier ||= {}
    @fourier[options.to_json] ||= begin
      fourier = {}

      signals.keys.each.with_index do |key, i|
        fourier[key] = f_fourier(i)
      end
      fourier
    end
  end

  def c(k)
    @c ||= {}
    @c[k] ||= n_ck(k) / n.to_f
  end

  def n_ck(k)
    k2 = k - 1 - n / 2
    p "(from_ck_number < k and k < n - 1 - from_ck_number) :: (#{from_ck_number} < #{k} and #{k} < #{n - 1 - from_ck_number}) :: #{(from_ck_number < k and k < n - 1 - from_ck_number)}" if from_ck_number.present?
    if from_ck_number.present? and (from_ck_number < k or k < n - 1 - from_ck_number)
      return Complex(0.0, 0.0)
    end

    #return Complex(0.0, 0.0)  and k > from_ck_number
    #return Complex(0.0, 0.0) if till_ck_number.present? and k > till_ck_number
    @n_ck ||= {}
    @n_ck[k] ||= if n == 1
      (0...n).to_a.map do |j|
        f(j).to_c * w(j*k)
      end.sum
    elsif k < n / 2
      a(k) + w(k) * b(k)
    else
      k2 = k - n / 2
      a(k2) - w(k2) * b(k2)
    end
  end

  def n
    signals.length
  end

  def original_signal_keys
    @original_signal_keys = original_signals.keys.map do |k|
      if k.is_a?(String)
        year, month, day = k.split('-').map{|v| v.sub(/^0/, '').to_i}
        day ||= 1
        month ||= 1

        Date.new(year, month, day)
      else
        k
      end
    end
  end

  def fake_signals_keys
    return [] if fake_signals_count == 0
    before_last_key, last_key = original_signals.keys.last(2)
    delta_params = nil
    if before_last_key.split('-').size == 1
      delta_params = {years: 1}
    elsif before_last_key.split('-').size == 2
      delta_params = {months: 1}
    elsif before_last_key.split('-').size == 3
      delta_params = {days: 1}
    else
      delta = last_key - before_last_key
    end

    keys = original_signal_keys
    fake_keys = [keys.last.to_time.advance(delta_params).to_date]
    (fake_signals_count - 1).times do |i|
      fake_keys << fake_keys.last.to_time.advance(delta_params).to_date
    end
    fake_keys
  end

  def original_signal_values
    original_signals.values
  end

  def signals
    @signals ||= begin
      signals = {}
      original_signal_values.each.with_index do |val, i|
        signals[original_signal_keys[i]] = val
      end

      fake_signals_keys.each do |key|
        signals[key] = 0.0
      end
      noisefy_signals!(signals)
      signals.with_indifferent_access
    end
  end

  def power_spectrum
    (0...n).inject({}) do |result, k|
      result[k] = c(k).abs.real.to_f**2
      result
    end
  end

  def formated
    signals
  end

  def noisefy_signals!(signals)
    signals.keys.sort.each.with_index do |key, i|
      signals[key] += noise[i].to_f
    end
  end

  def f(j)
    key = signals.keys.sort[j]
    signals[key]
  end

  def f_fourier(j)
    (0...n).to_a.map do |k|
      c(k) * w(-j * k)
    end.sum
  end

  def w(pow = 1, n = self.n)
    Math::E.to_c ** (-Complex::I * 2 * Math::PI * pow / n.to_f)
  end

  def fake_signals_count
    @fake_signals_count ||= begin
      n = 1
      while(original_signals.keys.length > n)
        n *= 2
      end
      blank_signals_count = n - original_signals.keys.length
    end
  end

  def a(k)
    a_k_fourier.n_ck(k)
  end

  def b(k)
    b_k_fourier.n_ck(k)
  end

  private

  [:a_k_fourier, :b_k_fourier].each do |fourier_method|
    define_method fourier_method do
      @fourier_method ||= {}
      @fourier_method[fourier_method] ||= begin
        splited_signals = {}
        signal_keys = signals.keys.sort
        (n / 2).times do |i|
          key = if fourier_method == :a_k_fourier
            signal_keys[i*2]
          else
            signal_keys[i*2 + 1]
          end

          splited_signals[key] = signals[key]
        end
        SignalsTransformer.new(splited_signals, options.slice(:noise))
      end
    end
  end
end