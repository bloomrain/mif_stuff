module Alignment
  class ForeignParagraph < Paragraph
    def reasign_paragraphs(before_paragraph, after_paragraph)
      if before_paragraph.present? and before_paragraph.foreign_paragraphs.include?(self)
        before_paragraph.foreign_paragraphs -= [self]
      end

      if after_paragraph.present? and not after_paragraph.foreign_paragraphs.include?(self)
        after_paragraph.foreign_paragraphs += [self]
      end
    end
  end
end
