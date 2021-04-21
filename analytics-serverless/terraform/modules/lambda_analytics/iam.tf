# Shared CloudWatch Policy
data "aws_iam_policy_document" "cloudwatch_access_policy" {
  statement {
    sid    = "CloudWatchAccess"
    effect = "Allow"

    actions = [
        "cloudwatch:Describe*",
        "cloudwatch:Get*",
        "cloudwatch:List*",
        "logs:CreateLogGroup",
        "logs:CreateLogStream",
        "logs:DescribeLogGroups",
        "logs:DescribeLogStreams",
        "logs:FilterLogEvents",
        "logs:GetLogEvents",
        "logs:PutLogEvents",
        "logs:TestMetricFilter"
    ]
    resources = ["*"]
  }
}

resource "aws_iam_policy" "cloudwatch_policy" {
  name = "${var.lambda_name}-cloudwatch-policy"
  path = "/"
  description = "CloudWatch IAM policy for the ${var.lambda_name} Lambda."
  policy = data.aws_iam_policy_document.cloudwatch_access_policy.json
}

# Lambda CloudWatch Access
data "aws_iam_policy_document" "lambda_assume_role_policy" {
  statement {
    sid    = "LambdaAssumeRole"
    effect = "Allow"

    actions = ["sts:AssumeRole"]

    principals {
      type        = "Service"
      identifiers = ["lambda.amazonaws.com"]
    }
  }
}

resource "aws_iam_role" "lambda_role" {
  name               = "${var.lambda_name}-lambda-role"
  assume_role_policy = data.aws_iam_policy_document.lambda_assume_role_policy.json
}

resource "aws_iam_role_policy_attachment" "lambda_attachment" {
  role       = aws_iam_role.lambda_role.name
  policy_arn = aws_iam_policy.cloudwatch_policy.arn
}

# API Gateway CloudWatch Access
data "aws_iam_policy_document" "api_gw_assume_role_policy" {
  statement {
    sid    = "APIGatewayAssumeRole"
    effect = "Allow"

    actions = ["sts:AssumeRole"]

    principals {
      type        = "Service"
      identifiers = ["apigateway.amazonaws.com"]
    }
  }
}

resource "aws_iam_role" "api_gw_role" {
  name               = "${var.lambda_name}-api-gw-role"
  assume_role_policy = data.aws_iam_policy_document.api_gw_assume_role_policy.json
}

resource "aws_iam_role_policy_attachment" "api_gw_attachment" {
  role       = aws_iam_role.api_gw_role.name
  policy_arn = aws_iam_policy.cloudwatch_policy.arn
}
