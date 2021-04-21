module "east_2_lambda_analytics" {
  source                = "../modules/lambda_analytics"
  cluster_name          = "steampunk-c2c24"
  description           = "Analytics Lambda"
  environment_variables = var.environment_variables
  handler               = "main.handler"
  lambda_name           = "lambda_analytics-qa"
  region_short          = "east2"
  runtime               = "nodejs10.x"
}
