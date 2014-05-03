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

  def text_attributes
    params.require(:text).permit(:native, :foreign)
  end
end