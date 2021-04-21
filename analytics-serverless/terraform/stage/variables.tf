variable "environment_variables" {
  description = "Environment variables to use with the Lambda function."
  type        = map
  default     = {
    "ENV_VAR_DEFINED" = "none"
  }
}
