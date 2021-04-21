# Security Checks Terraform Configuration

This Terraform configuartion creates and AWS Lambda function based on the of `src` directory in the root of the repository. An AWS API Gateway is then created that allows the Lambda to be access through a web browser.

## Lambda Implementation

The Lambda created by this Terraform will have the following configuration:

- Name: `lambda_security`
- AWS Region: `us-east-2`

The Terraform configuration will output a `base_url` parameter that can be used to access the Lambda via the API Gateway.

## Testing

When testing the Lambda on a feature branch, increase the `destroyTimeout` value to allow more time before the Terraform configuration is destroyed. Value is in seconds.
