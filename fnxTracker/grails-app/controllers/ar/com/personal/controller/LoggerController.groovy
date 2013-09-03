package ar.com.personal.controller

import javax.security.auth.login.AppConfigurationEntry;

import ar.com.globallogic.domain.LogEntry

import com.google.gdata.data.spreadsheet.ListEntry

class LoggerController {
	
	def loggerService
	
	private String BASE_URL = "http://localhost:8080/fnxTracker/"

    def index() {
		def accessToken = session["accessToken"]
		if(accessToken == null){
			return redirect(controller: "auth", action: "index", params: [redirect_uri:BASE_URL+"logger/log"])
		}
		[days: loggerService.getLastDays()]
	}
	
	def log(){
		def accessToken = session["accessToken"]
		loggerService.logTask(accessToken, new LogEntry(params))
		flash.message = "Horas logeadas correctamente"
		render(view:"index", model:[days:loggerService.getLastDays()])
	}
	
	def list() {
		def accessToken = session["accessToken"]
		if(accessToken == null){
			return redirect(controller: "auth", action: "index", params: [redirect_uri:BASE_URL+"logger/list"])
		}
		[logs:loggerService.list(accessToken)]
	}
}
