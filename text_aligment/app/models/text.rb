class Text < ActiveRecord::Base
  structure do
    lt :text
    en :text

    timestamps
  end

  def paragraph_comparison
    comparison = {}
    max_paragraphs.times do |lt_index|
      max_paragraphs.times do |en_index|
        lt_key = "LT#{lt_index}"
        en_key = "EN#{en_index}"
        ratio = min_paragraphs.to_f / max_paragraphs
        comparison[lt_key] ||= {}
        comparison[en_key] ||= {}

        comparison[lt_key][en_key] = ratio
        comparison[en_key][lt_key] = ratio
      end
    end
    comparison
  end

  def lt_paragraphs
    lt.split("\n").select(&:present?)
  end

  def en_paragraphs
    lt.split("\n").select(&:present?)
  end

  def max_paragraphs
    [lt_paragraphs.length, en_paragraphs.length].max
  end

  def min_paragraphs
    [lt_paragraphs.length, en_paragraphs.length].min
  end
end

