/*
stageEmail (Email Notification by emailext)

@param recipient // Email address 
@param check // Health status returned by DockerCheckStage

@version 1.0
@copyright 2018, EPAM systems, All Rights Reserved
*/

def call(Closure body) {
    stage ('Send Email') {
        def config = [:]
        body.resolveStrategy = Closure.DELEGATE_FIRST
        body.delegate = config
        body()
        if(config.check.equals("HTTP/1.1 200")) {
            emailext(subject: "${env.JOB_NAME} was " + "${env.BUILD_STATUS}", body: "Commit short hash " + "${env.shortCommit}", to: "${config.recipient}", replyTo: '');
        }else {
            System.exit(1)
        }
    }
}
