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
                deploy adapters: [tomcat9(alternativeDeploymentContext: '', credentialsId: 'tomcat-red', path: '', url: 'http://13.50.238.105:8080/')], contextPath: '/', war: '**/*.war'
            }
        }
    }
}
