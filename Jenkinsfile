pipeline {
    agent {
        docker {
            image 'maven:3.9.6-eclipse-temurin-21'
            // Keep the volume mount consistent
            args '-v $HOME/.m2:/root/.m2'
        }
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -Dmaven.repo.local=$WORKSPACE/.m2/repository'
            }
        }

        stage('Test') {
            steps {
                // Consistency is key: use the same repo path
                sh 'mvn test -Dmaven.repo.local=/root/.m2/repository'
            }
        }
    }

//     post {
//         always {
//             // Ensure you have configured 'Allure' in Manage Jenkins > Global Tool Configuration
//             allure([
//                 includeProperties: false,
//                 jdk: '',
//                 results: [[path: 'target/allure-results']] // Ensure this matches your project output
//             ])
//         }
//     }
}