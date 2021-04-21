terraform {
  required_version = "~> 0.13.0"

  required_providers {
    aws = {
      version = "~> 3.0"
    }
  }

  backend "s3" {
    bucket = "steampunk-tfstate"
    key    = "states/td-devops/lambda_security-prod.tfstate"
    region = "us-east-2"
  }
}

provider "aws" {
  region = "us-east-2"
}
