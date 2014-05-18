module Alignment
  class AlignedParagraph
    UNCHANGABLE_ATTRIBUTES = %i[probability position strategy]
    attr_reader *(UNCHANGABLE_ATTRIBUTES)

    def initialize(params = {})
      UNCHANGABLE_ATTRIBUTES.each do |attribute|
        send("set_#{attribute}", params)
      end

      params.except(*UNCHANGABLE_ATTRIBUTES).each_pair do |field, value|
        self.public_send("#{field}=", value)
      end
    end

    [:native, :foreign].each do |p_type|
      define_method "#{p_type}_paragraphs=" do |paragraphs|
        @paragraphs ||= {}
        @paragraphs[p_type] ||= []

        paragraphs_before = @paragraphs[p_type]
        @paragraphs[p_type] = paragraphs || []

        reset_aligned_paragraph(paragraphs_before, nil) if paragraphs_before.present?
        reset_aligned_paragraph(@paragraphs[p_type])

        @paragraphs[p_type]
      end

      define_method "#{p_type}_paragraphs" do
        @paragraphs ||= {}
        @paragraphs[p_type] ||= []
      end
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

    def reset_aligned_paragraph(paragraphs, aligned_paragraph = self)
      paragraphs.each do |paragraph|
        next if paragraph.aligned_paragraph(strategy) == aligned_paragraph
        if aligned_paragraph.present?
          paragraph.aligned_paragraph = aligned_paragraph
        else
          paragraph.unset_aligned_paragraph(strategy)
        end
      end
      paragraphs
    end
  end
end
