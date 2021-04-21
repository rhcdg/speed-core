module "east_2_lambda_security" {
  source                = "../modules/lambda_security"
  cluster_name          = "steampunk-c2c24"
  description           = "Security Lambda"
  environment_variables = var.environment_variables
  handler               = "main.handler"
  lambda_name           = "lambda_security-ci"
  region_short          = "east2"
  runtime               = "nodejs10.x"
}
