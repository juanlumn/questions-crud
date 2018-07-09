pipeline {
    agent {
        label 'master'
    }
    triggers {
        pollSCM('')
    }
    stages {
        stage('Setup') {
            steps {
                echo 'Giving execute permissions to gradlew'
                sh "chmod 755 gradlew"
            }
        }
        stage('Test And Build') {
            steps {
                echo ' Building jar'
                sh "./gradlew clean build"
            }
        }
        stage('Deploy') {
            steps {
                sh "sudo docker stop questions-crud || true && sudo docker rm -f questions-crud || true"
                sh "sudo docker image build -t questions-crud ."
                sh "sudo docker run --name questions-crud --restart always -p 1111:1111 -d questions-crud"
            }
        }
    }
}