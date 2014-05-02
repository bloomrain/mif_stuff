class CreateSignalsCollections < ActiveRecord::Migration
  def self.up
    create_table :signals_collections do |t|
      t.string :url
      t.datetime :updated_at
      t.datetime :created_at
    end
    add_attachment :signals_collections, :file
  end

  def self.down
    drop_table :signals_collections
  end
end
