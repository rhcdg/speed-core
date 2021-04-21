output "arn" {
  value = aws_lambda_function.default.arn
}

output "name" {
  value = aws_lambda_function.default.function_name
}

output "base_url" {
  value = aws_api_gateway_deployment.default.invoke_url
}
