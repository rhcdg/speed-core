class CreateCisCases < ActiveRecord::Migration[6.0]
  def change
    create_table :cis_cases do |t|
      t.string :a_number, null: false
      t.string :fco, null: false
      t.string :date_file_open, null: false
      t.string :last_name, null: false
      t.string :first_name, null: false
      t.string :middle_name
      t.string :mother
      t.string :father
      t.string :aka_last_name
      t.string :aka_first_name
      t.string :dob, null: false
      t.string :cob, null: false
      t.string :coc
      t.string :coa, null: false
      t.string :poe
      t.string :doe
      t.string :sex
      t.string :soc_sec_no
      t.string :i_94_adm_num
      t.string :passport_num
      t.string :fbi_number
      t.string :driver_license_no
      t.string :finger_print_code
      t.string :travel_document
      t.string :ident_fin

      t.timestamps
    end
  end
end
