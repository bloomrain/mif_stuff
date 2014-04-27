class Brawler
  attr_accessor :signals

  def initialize(signals)
    self.signals = signals
  end

  def h
    1.0 / signals.length
  end

  def max_distance
    (max_signal - min_signal).abs
  end

  def max_signal
    @max_signal ||= signals.values.max
  end

  def min_signal
    @min_signal ||= signals.values.min
  end

  def brownian # Increasing normal distribution noise
    gen = Rubystats::NormalDistribution.new
    deltas = gen.rng(signals.length)
    deltas[0] = 0
    deltas.length.times.each do |i|
      next if i == 0
      deltas[i] = (deltas[i] + deltas[i - 1])
    end
    deltas
  end

  def random(min_random, max_random)
    distance = (max_random - min_random).abs
    (0...signals.length).to_a.map{ rand(distance) + min_random }
  end
end