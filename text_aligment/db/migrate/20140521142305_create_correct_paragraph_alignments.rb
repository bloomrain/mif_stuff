class CreateCorrectParagraphAlignments < ActiveRecord::Migration
  def self.up
    create_table :correct_paragraph_alignments do |t|
      t.integer :text_id 
      t.text :native_paragraph_positions 
      t.text :foreign_paragraph_positions 
      t.datetime :updated_at 
      t.datetime :created_at 
    end
    add_index :correct_paragraph_alignments, :text_id
  end
  
  def self.down
    drop_table :correct_paragraph_alignments
  end
end
