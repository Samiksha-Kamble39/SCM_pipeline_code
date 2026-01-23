psh '''mvn clean install sonar:sonar   -Dsonar.projectKey=studentUI-app   -Dsonar.host.url=http://13.63.63.140:9000   -Dsonar.login=2298879029859d00ce7dfad6435ceee9631ca5d3   -Dsonar.java.binaries=target/classes
'''ipeline {
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
         stage('tesr-stage') {
            steps {
                sh '''/opt/maven/bin/mvn clean install sonar:sonar   -Dsonar.projectKey=studentUI-app   -Dsonar.host.url=http://13.63.63.140:9000   -Dsonar.login=2298879029859d00ce7dfad6435ceee9631ca5d3   -Dsonar.java.binaries=target/classes
'''
            }
        }
         stage('Quality_Gate') {
            steps {
                timeout(10) {
   
            }
                waitForQualityGate true
            }
        }

         stage('deploy-stage') {
            steps {
                echo 'code deploy sucessfully'
            }
        }
    }
}
