package ar.com.personal.controller

class LoggerController {
	
	def spreadsheetService

    def index() {
		[days: spreadsheetService.getLastDays()]
	}
}
