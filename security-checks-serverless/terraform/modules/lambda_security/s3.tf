locals {
  deploy_path = "lambda_functions/${var.lambda_name}.zip"
}

data "archive_file" "lambda_archive" {
  source_dir  = "../../src"
  output_path = ".terraform/${var.lambda_name}.zip"
  type        = "zip"
}

data "aws_s3_bucket" "lambda_deploy_bucket" {
  bucket = "${var.cluster_name}-${var.region_short}-deployments"
}

resource "aws_s3_bucket_object" "lambda_deploy" {
  bucket = data.aws_s3_bucket.lambda_deploy_bucket.id
  key    = local.deploy_path
  source = data.archive_file.lambda_archive.output_path
  etag   = filemd5(data.archive_file.lambda_archive.output_path)
}
