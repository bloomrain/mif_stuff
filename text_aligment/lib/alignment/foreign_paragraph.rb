module Alignment
  class ForeignParagraph < Paragraph
    def reasign_paragraphs(before_paragraph, after_paragraph)
      if before_paragraph.present? and before_paragraph.native_paragraphs.include?(self)
        before_paragraph.native_paragraphs -= [self]
      end

      if after_paragraph.present? and not after_paragraph.native_paragraphs.include?(self)
        after_paragraph.native_paragraphs += [self]
      end
    end
  end
end
