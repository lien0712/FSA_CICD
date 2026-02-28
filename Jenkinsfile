pipeline {

     agent {
            docker {
                image 'maven:3.9.6-eclipse-temurin-21'
            }
        }

//     tools {
//         maven 'Maven'
//         jdk 'JDK'
//     }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo "prepare ..."
                sh 'mvn clean package -Dmaven.repo.local=$WORKSPACE/.m2'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
    }
}