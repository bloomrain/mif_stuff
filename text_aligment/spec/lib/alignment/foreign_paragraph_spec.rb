require 'spec_helper'

describe Alignment::ForeignParagraph do
  let(:aligned_paragraph) { Alignment::AlignedParagraph.new(strategy: :blind, position: 1) }
  let(:other_aligned_paragraph) { Alignment::AlignedParagraph.new(strategy: :blind, position: 2) }
  let(:primary_aligned_paragraph) { nil }

  subject do
    Alignment::ForeignParagraph.new(
      content: 'Foo',
      position: 1,
      aligned_paragraph: primary_aligned_paragraph
    )
  end

  describe "#alignment_paragraph=" do
    context "when changing existing alignment paragraph" do
      let(:primary_aligned_paragraph) do
        Alignment::AlignedParagraph.new(strategy: :blind, position: 3)
      end

      it "adds new alignment to alignment paragraps" do
        expect{
          subject.aligned_paragraph = aligned_paragraph
        }.to change {
          subject.aligned_paragraphs
        }.from([primary_aligned_paragraph]).to([aligned_paragraph])
      end

      it 'removes paragraph from old alignment paragraph' do
        expect{
          subject.aligned_paragraph = aligned_paragraph
        }.to change {
          primary_aligned_paragraph.foreign_paragraphs
        }.from([subject]).to([])
      end

      it 'adds paragraph to new aligment paragraph' do
        expect{
          subject.aligned_paragraph = aligned_paragraph
        }.to change {
          aligned_paragraph.foreign_paragraphs
        }.from([]).to([subject])
      end
    end

    context "when setting new alignment paragraph" do
      it "adds new alignment to alignment paragraps" do
        expect{
          subject.aligned_paragraph = aligned_paragraph
        }.to change {
          subject.aligned_paragraphs
        }.from([]).to([aligned_paragraph])
      end

      it 'adds paragraph to aligment paragraph' do
        expect{
          subject.aligned_paragraph = aligned_paragraph
        }.to change {
          aligned_paragraph.foreign_paragraphs
        }.from([]).to([subject])
      end
    end
  end

  describe "#aligned_paragraph" do
    let(:primary_aligned_paragraph) do
      Alignment::AlignedParagraph.new(strategy: :blind, position: 3)
    end

    context "with existing type" do
      it "returns alignment paragraph by given type" do
        expect(subject.aligned_paragraph(:blind)).to eq primary_aligned_paragraph
      end
    end

    context 'with non existing type' do
      it 'returns nil' do
        expect(subject.aligned_paragraph(:asdf)).to be_nil
      end
    end
  end
end
