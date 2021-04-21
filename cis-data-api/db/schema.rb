# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# This file is the source Rails uses to define your schema when running `rails
# db:schema:load`. When creating a new database, `rails db:schema:load` tends to
# be faster and is potentially less error prone than running all of your
# migrations from scratch. Old migrations may fail to apply correctly if those
# migrations use external dependencies or application code.
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 2020_11_05_185737) do

  create_table "cis_cases", force: :cascade do |t|
    t.string "a_number", null: false
    t.string "fco", null: false
    t.string "date_file_open", null: false
    t.string "last_name", null: false
    t.string "first_name", null: false
    t.string "middle_name"
    t.string "mother"
    t.string "father"
    t.string "aka_last_name"
    t.string "aka_first_name"
    t.string "dob", null: false
    t.string "cob", null: false
    t.string "coc"
    t.string "coa", null: false
    t.string "poe"
    t.string "doe"
    t.string "sex"
    t.string "soc_sec_no"
    t.string "i_94_adm_num"
    t.string "passport_num"
    t.string "fbi_number"
    t.string "driver_license_no"
    t.string "finger_print_code"
    t.string "travel_document"
    t.string "ident_fin"
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
  end

end
