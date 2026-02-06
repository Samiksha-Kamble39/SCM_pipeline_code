pipeline {
    agent { label 'slave' }
    stages {
        stage('git-pull-stage') {
            steps {
                git branch: 'main', url: 'https://github.com/Samiksha-Kamble39/Terraform.git'
            }
        }
         stage('Test-stage') {
            steps {
                sh '''cd /Terraform/eks 
                     terraform init 
                     terraform plan'''
            }
        }
         stage('deploy-stage') {
            steps {
                sh '''cd /Terraform/eks
                       terraform init
                       terraform apply --auto-approve'''
            }
         }
     }
}
        
