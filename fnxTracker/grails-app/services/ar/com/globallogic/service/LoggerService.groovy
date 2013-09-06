package ar.com.globallogic.service

import ar.com.globallogic.domain.LogEntry

import com.google.gdata.data.spreadsheet.ListEntry

class LoggerService {
	
	/** The spreadsheet service to delegate to */
	def spreadsheetService

	/**
	 * Log the task inside the google spreadsheet document

	 * @param entry - {@link LogEntry} the entry to log.
	 * @param accessToken - {@link String} the token to communicate to google.
	 */
    def logTask(final String accessToken, final LogEntry entry) {
		spreadsheetService.insertRow(entry,accessToken)
    }
	
	List<LogEntry> list(final String accessToken) {
		def result = []
		spreadsheetService.list(accessToken).each {
			if(filter(it)){
				def elements = it.getCustomElements()
				LogEntry item = new LogEntry()
				item.task = elements.getValue("Tarea")
				item.hours = Integer.valueOf(elements.getValue("Horas"))
				item.date = elements.getValue("Fecha")
				result << item
			}
		}
		result
	}
	
	/**
	 * Retrieves the Last 5 days
	 */
	List<Date> getLastDays() {
		List<Date> result = [];
		for (i in 5..0) {
			result << new Date() - i
		}
		result.reverse()
	}
	
	private Boolean filter(final ListEntry it){
		def elements = it.getCustomElements()
		Date itemDate = new Date().parse("dd/MM/yyyy",elements.getValue("Fecha"))
		return new Date()-6 < itemDate && new Date() + 1 > itemDate
	}
}
