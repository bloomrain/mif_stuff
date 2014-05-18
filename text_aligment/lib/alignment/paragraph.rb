class Alignment::Paragraph
  TYPES = [:foreign, :native]

  attr_reader :position, :content

  def initialize(params = {})
    set_content(params[:content])
    set_position(params[:position])
    self.aligned_paragraph = params[:aligned_paragraph]
  end

  def foreign?
    type == :foreign
  end

  def native?
    type == :native
  end

  def unset_aligned_paragraph(strategy)
    @aligned_paragraphs.delete(strategy)
  end

  def aligned_paragraph=(paragraph)
    return if paragraph.nil?

    paragraph_before = aligned_paragraph(paragraph.strategy)

    if paragraph_before != paragraph
      reasign_paragraphs(paragraph_before, paragraph)
      @aligned_paragraphs[paragraph.strategy] = paragraph
    end
    paragraph
  end

  def update_aligned_paragraph(aligned_paragraph)
    raise NotImplementedError.new('Paragraph subclass must implement update_aligned_paragraph method')
  end

  def aligned_paragraph(strategy)
    @aligned_paragraphs ||= {}
    @aligned_paragraphs[strategy.to_sym]
  end

  def as_json(options = {})
    {
      position: position
    }
  end

  def aligned_paragraphs
    @aligned_paragraphs ||= {}
    @aligned_paragraphs.values
  end

  private
  def set_content(content)
    @content = content
  end

  def set_position(new_position)
    if new_position > 0
      @position = new_position
    else
      raise Exception.new("Position must be greather then 0")
    end
  end
end
