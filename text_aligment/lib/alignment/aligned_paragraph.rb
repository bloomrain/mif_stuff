module Alignment
  class AlignedParagraph
    UNCHANGABLE_ATTRIBUTES = %i[probability position strategy]
    attr_reader *(UNCHANGABLE_ATTRIBUTES + [:native_paragraphs, :foreign_paragraphs])

    def initialize(params = {})
      UNCHANGABLE_ATTRIBUTES.each do |attribute|
        send("set_#{attribute}", params)
      end

      params.except(*UNCHANGABLE_ATTRIBUTES).each_pair do |field, value|
        self.public_send("#{field}=", value)
      end
    end

    def native_paragraphs
      @native_paragraphs ||= []
    end

    def foreign_paragraphs
      @foreign_paragraphs ||= []
    end

    def native_paragraphs=(paragraphs)
      @native_paragraphs = paragraphs # this part is needed in order to avoid recursion
      @native_paragraphs = with_current_aligned_paragraph(paragraphs)
    end

    def foreign_paragraphs=(paragraphs)
      @foreign_paragraphs = paragraphs
      @foreign_paragraphs = with_current_aligned_paragraph(paragraphs)
    end

    def max_paragraphs
      @max_paragraphs ||= [native_paragraphs.size, foreign_paragraphs.size].max
    end

    def type=(new_type)
      raise Exception.new('type can not be nil!') if new_type.nil?
      @type = new_type
    end

    def as_json(options = {})
      %i(native_paragraphs foreign_paragraphs
      probability position).inject({}) do |result, field|
        result[field] = public_send(field)
        result
      end
    end

    private

    def set_probability(params)
      @probability = params[:probability] || 0.0
    end

    def set_position(params)
      position = params[:position]
      if position.present? and position < 1
        raise Exception.new('Position must be greather then 0')
      end
      @position = position
    end

    def set_strategy(params)
      new_stategy = params[:strategy]
      raise Exception.new('Strategy must be defined') unless new_stategy.present?
      @strategy = new_stategy
    end

    def without_other_aligned_paragraph(paragraphs)
      paragraphs.reject{ |paragraph| paragraph.aligned_paragraph(strategy) != self }
    end

    def with_current_aligned_paragraph(paragraphs)
      paragraphs.each do |paragraph|
        paragraph.aligned_paragraph = self if paragraph.aligned_paragraph(strategy) != self
      end
      paragraphs
    end
  end
end
