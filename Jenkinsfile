pipeline {
    agent any
    options {
        buildDiscarder logRotator(numToKeepStr: '10')
    }
    stages {
        stage('Clean') {
            steps {
                sh 'chmod +x ./gradlew';
                sh './gradlew clean';
            }
        }
        stage('Build') {
            steps {
                sh './gradlew jar';
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test';
                junit '**/build/test-results/test/*.xml';
            }
        }
        stage('Create zip') {
            steps {
               
            }
        }
        stage('Sources') {
            steps {
                sh './gradlew sourceJar';
            }
        }
        stage('Publish') {
           steps {
                sh './gradlew publish';
           }
        }
    }
}
