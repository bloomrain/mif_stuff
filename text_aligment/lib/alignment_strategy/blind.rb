class AlignmentStrategy::Blind < AlignmentStrategy::Base
  attr_reader :curr_n_index, :curr_f_index
  def refresh_alignment!
    @alignment = {}
    native_paragraphs.length.times do |native_index|
      @curr_n_index = native_index
      foreign_paragraphs.length.times do |foreign_index|
        @curr_f_index = foreign_index
        update_alignment!(native_index, foreign_index)
      end
    end
    @alignment
  end

  def update_alignment!(native_index, foreign_index)
    native_key = "N#{native_index}"
    foreign_key = "F#{foreign_index}"

    @alignment[native_key] ||= {}
    @alignment[foreign_key] ||= {}
    ratio = paragraphs_probability(native_index, foreign_index)

    @alignment[native_key][foreign_key] = ratio
    @alignment[foreign_key][native_key] = ratio
  end

  def paragraphs_probability(native_index, foreign_index)
    percentage_n_index = native_index.to_f / native_paragraphs.length
    percentage_f_index = foreign_index.to_f / foreign_paragraphs.length

    ratio = 0.0

    before_offset = native_index.to_f / native_paragraphs.length - (-1 + foreign_index.to_f) / foreign_paragraphs.length
    current_offset = native_index.to_f / native_paragraphs.length - foreign_index.to_f / foreign_paragraphs.length
    next_offset = native_index.to_f / native_paragraphs.length - (1 + foreign_index.to_f) / foreign_paragraphs.length


    if native_index == foreign_index
      ratio = min_paragraphs.to_f / max_paragraphs
    elsif (native_index - foreign_index).abs == 1
      ratio = (1.0 - min_paragraphs.to_f / max_paragraphs) / 2
    end

    ratio
  end
end