import com.lab.build.Colorizer

/**
 * stageDockerCheck
 *
 * Check docker availability
 *
 * @param ApplicationIP
 * @param TimeOutCheck
 *
 * @version $Id: $
 * @copyright 2018, EPAM, Inc.  All Rights Reserved
 */

def call (Closure body) {
    stage('Docker Check Stage') {
        def config = [:]
        body.resolveStrategy = Closure.DELEGATE_FIRST
        body.delegate = config
        body()
	    
        def Response
        timeout (time: config.TimeOutCheck, unit:'SECONDS') { 
	    // change it with 
            echo 'Check Successful docker container Up'
            sleep 10
            while(Response!="HTTP/1.1 200") {
                def Curl = "curl -I ${config.ApplicationIP}".execute().text
                Response = Curl[0..11]
		println Response
            }
	return Response
// Please return human readable information about success or failed response in colorized info echo
// like this:	echo Colorizer.info('Some text')
        }
    }
}
