pipeline {
    agent { label 'slave' }
    stages {
        stage('git-pull-stage') {
            steps {
                git branch: 'main', url: 'https://github.com/Anilbamnote/student-ui-app.git'
            }
        }
         stage('build-stage') {
            steps {
                sh '/opt/maven/bin/mvn clean package'
            }
        }
         stage('test-stage') {
            steps {
                withSonarQubeEnv(installationName: 'sonar',credentialsId: 'cred1-sonar') {
                 sh ' /opt/maven/bin/mvn clean verify sonar:sonar '
            }

            }
        }
         stage('Quality_Gate') {
            steps {
                timeout(10) {
   
            }
                waitForQualityGate true, credentialsId: 'cred1-sonar'
            }
        }

         stage('deploy-stage') {
            steps {
                echo 'code deploy sucessfully'
            }
        }
    }
}
