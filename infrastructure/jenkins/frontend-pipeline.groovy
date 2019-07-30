podTemplate(
    label: 'worker', 
    containers: [
        containerTemplate(args: 'cat', name: 'docker', command: '/bin/sh -c', image: 'docker', ttyEnabled: true)
    ],
    volumes: [
      hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock')
    ]
){
    node('worker') {
        echo 'Iniciando Pipeline'
        stage('Clone') {
            checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'scm-global', url: 'git@gitlab.com:questcode/backends/scm.git']]])
            sh 'ls -ltra'
        }
        stage('Build') {
            container('docker') {
                sh 'docker ps'
            }
        }
    }
}