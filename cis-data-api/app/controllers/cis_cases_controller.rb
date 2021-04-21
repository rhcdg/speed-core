class CisCasesController < ApplicationController
  def index
    @cis_cases = CisCase.all
    render json: @cis_cases.as_json(only: :a_number)
  end

  def show
    @cis_case = CisCase.find_by(a_number: params[:a_number])
    render json: @cis_case.nil? ? 'NOT FOUND' : @cis_case
  end
end
