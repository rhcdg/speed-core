resource "aws_lambda_function" "default" {
  s3_bucket                      = data.aws_s3_bucket.lambda_deploy_bucket.id
  s3_key                         = local.deploy_path
  function_name                  = var.lambda_name
  handler                        = var.handler
  role                           = aws_iam_role.lambda_role.arn
  runtime                        = var.runtime
  description                    = var.description
  timeout                        = var.timeout
  source_code_hash               = filebase64sha256(data.archive_file.lambda_archive.output_path)
  reserved_concurrent_executions = var.concurrent_executions

  environment {
    variables = var.environment_variables
  }
}

resource "aws_lambda_permission" "apigw" {
  statement_id  = "AllowAPIGatewayInvoke"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.default.function_name
  principal     = "apigateway.amazonaws.com"
  source_arn    = "${aws_api_gateway_rest_api.default.execution_arn}/*/*"
}
