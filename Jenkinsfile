pipeline {
    agent any

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
                sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
    }
}