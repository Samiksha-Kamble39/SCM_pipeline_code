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
                sh '''/opt/maven/bin/mvn sonar:sonar \
                      -Dsonar.projectKey=sonar_project \
                      -Dsonar.host.url=http://13.51.254.163:9000 \
                      -Dsonar.login=e3a04cfdf4f26e77efd47329aa833c93c10b5b66'''
            }
        }
         stage('deploy-stage') {
            steps {
                echo 'code deploy sucessfully'
            }
        }
    }
}
