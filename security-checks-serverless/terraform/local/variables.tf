variable "environment_variables" {
  description = "Environment variables to use with the Lambda function."
  type        = map
  default     = {
    "ENV_VAR_DEFINED" = "none"
  }
}

variable "lambda_name" {
  description = "Name of the Lambda function."
  type        = string
}
