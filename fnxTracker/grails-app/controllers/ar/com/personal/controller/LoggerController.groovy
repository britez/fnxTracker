package ar.com.personal.controller

import ar.com.globallogic.domain.LogEntry

class LoggerController {
	
	def googleService
	
	def spreadsheetService

    def index() {
		[days: spreadsheetService.getLastDays()]
	}
	
	def log(){
		def accessToken = session["accessToken"]
		 
		if(accessToken == null){
			return redirect(controller: "auth", action: "index")
		}
		
		spreadsheetService.logTask(new LogEntry(params),
								   accessToken)
		
		println(googleService.getEmail(accessToken))
	}
}
