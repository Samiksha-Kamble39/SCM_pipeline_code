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
                withSonarQubeEnv(installationName: 'sonar',credentialsId: 'sonar-cred') {
                 sh ' /opt/maven/bin/mvn clean verify sonar:sonar '
            }

            }
        }
         stage('Quality_Gate') {
            steps {
                timeout(2) {
   
            }
                waitForQualityGate true
            }
        }
         stage('Artifactory-stage') {
            steps {
               sh 'aws s3 cp target/studentapp-2.2-SNAPSHOT.war  s3://tf-bucket22'
            }
        }
         stage('deploy-stage') {
            steps {
                echo 'code deploy sucessfully'
            }
        }
    }
}
