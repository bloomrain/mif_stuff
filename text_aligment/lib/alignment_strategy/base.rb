class AlignmentStrategy::Base
  delegate :native_paragraphs, :foreign_paragraphs, :max_paragraphs, :min_paragraphs, to: :text
  attr_reader :text

  def initialize(text)
    @text = text
  end

  def refresh_alignment!
  end

  def alignment
    @alignment ||= refresh_alignment!
  end
end