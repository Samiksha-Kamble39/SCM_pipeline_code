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
                sh '''mvn clean install sonar:sonar   -Dsonar.projectKey=studentUI-app   -Dsonar.host.url=http://172.31.10.19:9000   -Dsonar.login=2298879029859d00ce7dfad6435ceee9631ca5d3   -Dsonar.java.binaries=target/classes
'''
            }
        }
         stage('deploy-stage') {
            steps {
                echo 'code deploy sucessfully'
            }
        }
    }
}
