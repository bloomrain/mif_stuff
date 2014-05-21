module Alignment
  module Strategy
    class Manual < Alignment::Strategy::Base
      def align_paragraphs
        sorted_manual_alignments.map.with_index do |alignment, i|
          build_aligned_paragraph(
            position: i + 1,
            native_paragraphs: alignment_native_paragraphs(alignment),
            foreign_paragraphs: alignment_foreign_paragraphs(alignment),
            probability: 1.0
          )
        end
      end

      private
      def alignment_native_paragraphs(alignment)
        alignment.native_paragraph_positions.map do |position|
          native_paragraphs_by_position[position.to_i]
        end.flatten.compact
      end

      def alignment_foreign_paragraphs(alignment)
        alignment.foreign_paragraph_positions.map do |position|
          foreign_paragraphs_by_position[position.to_i]
        end.flatten.compact
      end

      def original_text
        @original_text = begin
          text_obj = text
          while text_obj.class != Text
            text_obj = text_obj.text
          end
          text_obj
        end
      end

      def sorted_manual_alignments
        if original_text.correct_paragraph_alignments.empty?
          raise 'Text does not have saved correct paragraph alignment'
        end

        original_text.correct_paragraph_alignments.sort_by do |alignment|
          alignment.native_paragraph_positions.first.to_i
        end
      end

      def native_paragraphs_by_position
        native_paragraphs.group_by(&:position)
      end

      def foreign_paragraphs_by_position
        native_paragraphs.group_by(&:position)
      end
    end
  end
end
