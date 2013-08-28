package ar.com.globallogic.service

class SpreadsheetService {

    def getLastDays() {
		def result = [];
		for (i in 5..0) {
			result << new Date() - i
		}
		result
    }
}
