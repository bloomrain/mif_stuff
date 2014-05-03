class Text < ActiveRecord::Base
  validates :native, presence: true
  validates :foreign, presence: true
  structure do
    native :text
    foreign :text

    timestamps
  end

  def paragraph_comparison(strategy = :blind)
    comparator = "AlignmentStrategy::#{strategy.to_s.camelcase}".constantize.new(self)
    comparator.alignment
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

