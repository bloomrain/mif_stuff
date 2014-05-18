module Alignment
  module Strategy
    class Blind < Alignment::Strategy::Base
      # def refresh_alignment!
      #   @alignment = {}
      #   native_paragraphs.length.times do |native_index|
      #     @curr_n_index = native_index
      #     foreign_paragraphs.length.times do |foreign_index|
      #       @curr_f_index = foreign_index
      #       update_alignment!(native_index, foreign_index)
      #     end
      #   end
      #   @alignment
      # end
      #
      # def update_alignment!(native_index, foreign_index)
      #   native_key = "N#{native_index}"
      #   foreign_key = "F#{foreign_index}"
      #
      #   @alignment[native_key] ||= {}
      #   @alignment[foreign_key] ||= {}
      #   ratio = paragraphs_probability(native_index, foreign_index)
      #
      #   @alignment[native_key][foreign_key] = ratio
      #   @alignment[foreign_key][native_key] = ratio
      # end

      def align_paragraphs
        blindly_aligned_paragraphs
      end

      private

      def blindly_aligned_paragraphs
        unused_f_paragraphs = foreign_paragraphs.clone
        unused_n_paragraphs = native_paragraphs.clone

        paragraphs = []
        position = 0
        (0..min_paragraphs).to_a.each do |i|
          position = if i % 2 == 0
            1 + (i / 2)
          else
            min_paragraphs - (i - 1) / 2
          end

          break if (unused_n_paragraphs.size == 1 || unused_f_paragraphs.size == 1)

          if i % 2 == 0
            native_paragraph = unused_n_paragraphs.shift
            foreign_paragraph = unused_f_paragraphs.shift
          else
            native_paragraph = unused_n_paragraphs.pop
            foreign_paragraph = unused_f_paragraphs.pop
          end

          paragraphs << build_aligned_paragraph(
            foreign_paragraphs: Array(foreign_paragraph),
            native_paragraphs: Array(native_paragraph),
            position: position,
            probability: match_probability_of(native_paragraph, foreign_paragraph)
          )
        end

        max_unused_items = [unused_f_paragraphs.length, unused_n_paragraphs.length].max
        probability = [
          unused_f_paragraphs.length.to_f / max_unused_items,
          unused_n_paragraphs.length.to_f / max_unused_items
        ].min

        paragraphs << build_aligned_paragraph(
          foreign_paragraphs: unused_f_paragraphs,
          native_paragraphs: unused_n_paragraphs,
          position: position,
          probability: probability
        )

      end

      def match_probability_of(native_paragraph, foreign_paragraph)
        return 0.0 if native_paragraph.nil? || foreign_paragraph.nil?

        if native_paragraph.position == 1 and foreign_paragraph.position == 1
          1.0
        elsif native_paragraph.position == native_paragraphs.length && foreign_paragraph.position == foreign_paragraphs.length
          1.0
        else
          [
            (native_paragraphs.length.to_f / foreign_paragraphs.length.to_f),
            (foreign_paragraphs.length.to_f / native_paragraphs.length.to_f)
          ].min
        end
      end
    end
  end
end
