class CisCase < ApplicationRecord
  validates :a_number, :fco, :date_file_open,
            :last_name, :first_name, :dob, :cob, :coa,
            presence: true
end
