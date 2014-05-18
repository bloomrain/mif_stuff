module Alignment
  module Strategy
    class Base
      delegate :foreign_paragraphs, :native_paragraphs, :max_paragraphs, :min_paragraphs, to: :text
      attr_reader :text

      def initialize(strategy)
        @text = strategy
      end

      def aligned_paragraphs
        @aligned_paragraphs ||= begin
          parahraphs = align_paragraphs.sort_by(&:position)
        end
      end

      def build_aligned_paragraph(params)
        AlignedParagraph.new( params.merge(strategy: self.class.name.downcase.to_sym) )
      end
    end
  end
end
