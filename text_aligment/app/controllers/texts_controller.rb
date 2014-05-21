class TextsController < ApplicationController
  expose(:texts)
  expose(:text, attributes: :text_attributes)

  def index

  end

  def create
    text.save!
    redirect_to texts_path
  end

  def update
    text.save!
    redirect_to texts_path
  end

  def update_alignment
    text = Text.find(params[:id])
    params.permit!
    native_paragraphs_by_group = {}
    params[:native_paragraph_group].each_pair do |paragraph_position, group_number|
      native_paragraphs_by_group[group_number] ||= []
      native_paragraphs_by_group[group_number] << paragraph_position
    end

    foreign_paragraphs_by_group = {}
    params[:foreign_paragraph_group].each_pair do |paragraph_position, group_number|
      foreign_paragraphs_by_group[group_number] ||= []
      foreign_paragraphs_by_group[group_number] << paragraph_position
    end

    text.correct_paragraph_alignments.destroy_all
    (foreign_paragraphs_by_group.keys + native_paragraphs_by_group.keys).uniq.each do |group|
      text.correct_paragraph_alignments.create!(
        native_paragraph_positions: native_paragraphs_by_group[group] || [],
        foreign_paragraph_positions: foreign_paragraphs_by_group[group] || [],
      )
    end
    
    redirect_to :back
  end

  def text_attributes
    params.require(:text).permit(:native, :foreign)
  end
end
