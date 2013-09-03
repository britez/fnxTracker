package ar.com.globallogic.service

import java.util.Date;
import java.util.List;

import ar.com.globallogic.domain.LogEntry

import com.google.gdata.data.spreadsheet.SpreadsheetEntry
import com.google.gdata.data.spreadsheet.WorksheetEntry

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
			def item = new LogEntry()
			def elements = it.getCustomElements()
			item.task = elements.getValue("Tarea")
			item.hours = Integer.valueOf(elements.getValue("Horas"))
			item.date = elements.getValue("Fecha")
			result << item
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
}
