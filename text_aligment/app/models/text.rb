class Text < ActiveRecord::Base
  validates :native, presence: true
  validates :foreign, presence: true

  has_many :correct_paragraph_alignments

  structure do
    native :text
    foreign :text

    timestamps
  end

  def paragraph_alignment_index_for(paragraph)
    alignment = if paragraph.class == Alignment::NativeParagraph
      correct_paragraph_alignments.detect{ |al| al.native_paragraph_positions.include?(paragraph.position.to_s) }
    else
      correct_paragraph_alignments.detect{ |al| al.foreign_paragraph_positions.include?(paragraph.position.to_s) }
    end
    correct_paragraph_alignments.pluck(:id).sort.index alignment.try(:id)
  end

  def aligned_paragraphs(strategy = :blind)
    alignment_runner(strategy).aligned_paragraphs
  end

  def native_paragraphs
    @native_paragraphs ||= native.split("\n").select(&:present?).map.with_index do |content, index|
      Alignment::NativeParagraph.new({ content: content, position: index + 1 })
    end
  end

  def foreign_paragraphs
    @foreign_paragraphs ||= foreign.split("\n").select(&:present?).map.with_index do |content, i|
      Alignment::ForeignParagraph.new({ content: content, position: i + 1})
    end
  end

  def max_paragraphs
    [native_paragraphs.length, foreign_paragraphs.length].max
  end

  def min_paragraphs
    [native_paragraphs.length, foreign_paragraphs.length].min
  end

  def alignment_runner(strategy = :blind)
    "Alignment::Strategy::#{strategy.to_s.camelcase}".constantize.new(self)
  end
end
