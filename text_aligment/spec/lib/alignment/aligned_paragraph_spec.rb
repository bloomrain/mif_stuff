require 'spec_helper'

describe Alignment::AlignedParagraph do
  subject do
    Alignment::AlignedParagraph.new(
      strategy: :blind,
      position: 1,
      native_paragraphs: initial_native_paragraphs
    )
  end

  let(:native_paragraph){ Alignment::NativeParagraph.new(position: 1) }
  let(:initial_native_paragraphs){ [] }

  describe "#native_paragraphs=" do
    context "when no paragraphs was setted before" do
      it "sets native paragraphs" do
        expect {
          subject.native_paragraphs = [native_paragraph]
        }.to change {
          subject.native_paragraphs
        }.from([]).to([native_paragraph])
      end

    it 'sets aligned paragraph for native paragraphs' do
        expect {
          subject.native_paragraphs = [native_paragraph]
        }.to change {
          native_paragraph.aligned_paragraph(:blind)
        }.from(nil).to(subject)
      end
    end

    context "when changing already setted paragraphs" do
      let(:other_native_paragraph) { Alignment::NativeParagraph.new(position: 2) }
      let(:initial_native_paragraphs) { [ other_native_paragraph ] }

      it "sets native paragraphs" do
        expect {
          subject.native_paragraphs = [native_paragraph]
        }.to change {
          subject.native_paragraphs
        }.from([other_native_paragraph]).to([native_paragraph])
      end

      it 'sets aligned paragraph for new native paragraphs' do
        expect {
          subject.native_paragraphs = [native_paragraph]
        }.to change {
          native_paragraph.aligned_paragraph(:blind)
        }.from(nil).to(subject)
      end

      it 'removes aligned paragraph for all old native paragraphs' do
        expect {
          subject.native_paragraphs = [native_paragraph]
        }.to change {
          other_native_paragraph.aligned_paragraph(:blind)
        }.from(subject).to(nil)
      end
    end
  end

  describe "#foreign_paragraphs="
end
