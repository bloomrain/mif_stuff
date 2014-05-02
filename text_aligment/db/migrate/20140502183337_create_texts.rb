class CreateTexts < ActiveRecord::Migration
  def self.up
    create_table :texts do |t|
      t.text :lt 
      t.text :en 
      t.datetime :updated_at 
      t.datetime :created_at 
    end
  end
  
  def self.down
    drop_table :texts
  end
end
