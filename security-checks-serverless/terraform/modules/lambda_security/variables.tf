variable "cluster_name" {
  description = "Name of the cluster to deploy to."
  type        = string
}
variable "concurrent_executions" {
  description = "Number of concurrent executions allowed for the Lambda function."
  type        = number
  default     = 1
}
variable "description" {
  description = "Description of the Lambda function."
  type        = string
  default     = ""
}
variable "environment_variables" {
  description = "Environment variables to use with the Lambda function."
  type        = map
  default     = {
    "ENV_VAR_DEFINED" = "none"
  }
}
variable "handler" {
  description = "Handler name for the Lambda function."
  type        = string
}
variable "lambda_name" {
  description = "Name of the Lambda function."
  type        = string
}
variable "region_short" {
  description = "Short name for the region."
  type        = string
}
variable "runtime" {
  description = "Runtime that should be used to execute the Lambda function."
  type        = string
}
variable "timeout" {
  description = "Timeout value for the Lambda function."
  type        = number
  default     = 100
}
