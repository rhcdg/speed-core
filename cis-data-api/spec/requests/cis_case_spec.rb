require 'swagger_helper'

RSpec.describe 'cis_case', type: :request do
  path "/cis_cases" do
    get(summary: 'List all dummy Cis Cases') do
      consumes 'application/json'
      produces 'application/json'
      tags "CisCases"

      response(200, description: 'Return all the available cases') do
        it 'Return all cases' do
          body = JSON(response.body)
          expect(body.count).to eq(3)
        end
      end
    end
  end

  path '/cis_cases/{a_number}' do
    get(summary: 'Get Case') do
      consumes 'application/json'
      produces 'application/json'
      tags "CisCases"

      parameter name: :a_number, in: :path, type: :string, required: true, description: 'a_number'

      response(200, description: 'Return the selected case') do
        schema type: :object,
               properties: {
                id: {type: :integer},
                a_number: {type: :string, description: '9 digit A Number'},
                fco: {type: :string, description: '3 character file control office'},
                date_file_open: {type: :string, description: '8 digit date the file was opened displayed in MMDDYYYY format'},
                last_name: {type: :string, description: 'Up to 40 characters last name'},
                first_name: {type: :string, description: 'Up to 25 characters first name'},
                middle_name: {type: :string, description: 'Up to 25 characters middle name'},
                mother: {type: :string, description: "Up to 18 characters Mother's first name"},
                father: {type: :string, description: "Up to 18 characters Father's first name"},
                aka_last_name: {type: :string, description: 'Up to 40 characters alias last name'},
                aka_first_name: {type: :string, description: 'Up to 25 characters alias first name'},
                dob: {type: :string, description: '8 digit date of birth displayed in MMDDYYYY format'},
                cob: {type: :string, description: 'Up to 5 characters Country of Birth code'},
                coc: {type: :string, description: 'Up to 5 characters Country of Citizenship code'},
                coa: {type: :string, description: '3 characters Class of Admission code with 34 character description'},
                poe: {type: :string, description: '3 character Port of Entry code'},
                doe: {type: :string, description: '8 digit date of entry into the United States displayed in MMDDYYYY format'},
                sex: {type: :string, description: '1 character Sex code (M/F/U)'},
                soc_sec_no: {type: :string, description: '9 digit Social Security number'},
                i_94_adm_num: {type: :string, description: '11 digit arrival/departure number'},
                passport_num: {type: :string, description: 'Up to 20 alpha/numeric character passport number'},
                fbi_number: {type: :string, description: 'Up to 12 alpha/numeric character FBI file number'},
                driver_license_no: {type: :string, description: 'Up to 20 alpha/numeric character driver’s license number'},
                finger_print_code: {type: :string, description: 'Up to 20 character Fingerprint code'},
                travel_document: {type: :string, description: 'Up to 20 character travel document number'}, 
                ident_fin: {type: :string, description: '4-10 digit IDENT Fingerprint Identification Number (FIN) number'},
                created_at: {type: :created_at},
                updated_at: {type: :created_at}
               }
        run_test!
      end

      response(404, description: 'Case not found') do
        let(:a_number) { 'A121212121' }
        run_test!
      end
    end
  end
end
