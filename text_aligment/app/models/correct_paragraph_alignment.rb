class CorrectParagraphAlignment < ActiveRecord::Base
  belongs_to :text

  serialize :native_paragraph_positions, Array
  serialize :foreign_paragraph_positions, Array

  structure do
    native_paragraph_positions :text
    foreign_paragraph_positions :text

    timestamps
  end
end
