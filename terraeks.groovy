pipeline {
    agent {label 'slave'}
    stages {
        stage('Pull') {
            steps {
                git branch: 'main', url: 'https://github.com/Samiksha-Kamble39/Terraform.git'
            }
        }
        stage('Test') {
            steps {
                sh '''cd eks
                    terraform init
                    terraform plan'''
            }
        }
        stage('Deploy') {
            steps {
                sh ''' cd eks
                    terraform init
                    terraform apply --auto-approve'''
            }
        }
    }
}
