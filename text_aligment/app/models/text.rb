class Text < ActiveRecord::Base
  validates :native, presence: true
  validates :foreign, presence: true
  structure do
    native :text
    foreign :text

    timestamps
  end

  def paragraph_comparison
    comparison = {}
    native_paragraphs.length.times do |native_index|
      foreign_paragraphs.length.times do |foreign_index|
        native_key = "N#{native_index}"
        foreign_key = "F#{foreign_index}"
        ratio = 0.0
        if native_index == foreign_index
          ratio = min_paragraphs.to_f / max_paragraphs
        elsif (native_index - foreign_index).abs == 1
          ratio = (1.0 - min_paragraphs.to_f / max_paragraphs) / 2
        end
        comparison[native_key] ||= {}
        comparison[foreign_key] ||= {}

        comparison[native_key][foreign_key] = ratio
        comparison[foreign_key][native_key] = ratio
      end
    end
    comparison
  end

  def native_paragraphs
    @native_paragraphs ||= native.split("\n").select(&:present?)
  end

  def foreign_paragraphs
    @foreign_paragraphs ||= foreign.split("\n").select(&:present?)
  end

  def max_paragraphs
    [native_paragraphs.length, foreign_paragraphs.length].max
  end

  def min_paragraphs
    [native_paragraphs.length, foreign_paragraphs.length].min
  end
end

