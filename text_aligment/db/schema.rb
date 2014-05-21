# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20140521142305) do

  create_table "correct_paragraph_alignments", force: true do |t|
    t.integer  "text_id"
    t.text     "native_paragraph_positions"
    t.text     "foreign_paragraph_positions"
    t.datetime "updated_at"
    t.datetime "created_at"
  end

  add_index "correct_paragraph_alignments", ["text_id"], name: "index_correct_paragraph_alignments_on_text_id"

  create_table "texts", force: true do |t|
    t.text     "native"
    t.text     "foreign"
    t.datetime "updated_at"
    t.datetime "created_at"
  end

end
