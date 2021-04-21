#!/usr/bin/env sh

# Must be run from repository root as ops/terraform_deploy.sh.
# Running it from the containing directory will produce errors due to relative paths.

# Check that BRANCH_NAME is set
if [ -z BRANCH_NAME ]
then
	echo 'BRANCH_NAME not specified'
	exit 1
fi

# Set the Terraform backend based on the branch name
case BRANCH_NAME in
	develop)
		TF_BACKEND='develop'
		;;
	prod)
		TF_BACKEND='prod'
		;;
	qa)
		TF_BACKEND='qa'
		;;
	stage)
		TF_BACKEND='stage'
		;;
	*)
		TF_BACKEND='local'
		;;
esac

# Change to the appropriate directory and initialize Terraform
cd terraform/${TF_BACKEND}
terraform init -no-color

# Apply the Terraform configuration
if [ $TF_BACKEND = 'local' ]
then
	eval "terraform apply -var 'lambda_name=lambda-$(echo $JOB_NAME | awk -F '/' '{print $2}')-$(echo $BRANCH_NAME | sed 's/[^a-zA-Z0-9_\-]/-/g')' -input=false -auto-approve -no-color"
else
	eval "terraform apply -input=false -auto-approve -no-color"
fi
