require 'csv'

CisCase.destroy_all

CSV.foreach("#{Rails.root}/db/data_file.csv", headers: true) do |row|
  CisCase.create(row.to_h)
end
