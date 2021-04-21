terraform {
  required_version = "~> 0.13.0"

  required_providers {
    aws = {
      version = "~> 3.0"
    }
  }

  backend "local" {
    path = "./.terraform/local.tfstate"
  }
}

provider "aws" {
  region = "us-east-2"
}
