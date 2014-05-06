module ApplicationHelper
  def hz_from_k(k, signals = signals_collection.signals)
    delta = (signals.formated.keys.last - signals.formated.keys.first).to_i
    k.to_f / delta
  end

  def human_hz_from_k(k, signals = signals_collection.signals)
    hz = hz_from_k(k, signals)
    mesures = %w(m µ n)
    mesure_index = 0
    hz *= 1000
    while hz < 1 and mesures[mesure_index].present?
      hz *= 1000
      mesure_index += 1
    end
    mesure = mesures[mesure_index]  
    "#{hz.round(2)} #{mesure}Hz"
  end
end
