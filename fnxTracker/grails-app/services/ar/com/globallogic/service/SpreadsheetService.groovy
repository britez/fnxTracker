package ar.com.globallogic.service

import ar.com.globallogic.domain.LogEntry

import com.google.gdata.client.spreadsheet.SpreadsheetQuery
import com.google.gdata.client.spreadsheet.SpreadsheetService as GoogleSpreadsheetService
import com.google.gdata.client.spreadsheet.WorksheetQuery
import com.google.gdata.data.docs.SpreadsheetEntry
import com.google.gdata.data.spreadsheet.ListEntry
import com.google.gdata.data.spreadsheet.ListFeed
import com.google.gdata.data.spreadsheet.SpreadsheetFeed
import com.google.gdata.data.spreadsheet.WorksheetEntry
import com.google.gdata.data.spreadsheet.WorksheetFeed


/**
 * This class is responsible to manage the
 * spreadsheets from google API
 * 
 * @author Maximiliano Britez
 */
class SpreadsheetService {

	/** The SHEET_NAME */	
	private final String SHEET_NAME = "TEST"
	
	/** The APP_NAME */
	private final String APP_NAME = "Fnx Tracker"
	
	/** The WORKSHEET_NAME */
	private final String WORKSHEET_NAME = "Datos cargados"
	
	/** The google service to delegate to */
	def googleService

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
	
	/**
	 * Log the task inside the google spreadsheet document

	 * @param entry - {@link LogEntry} the entry to log.
	 * @param accessToken - {@link String} the token to communicate to google.
	 */
	void logTask(final LogEntry entry, final String accessToken){
		
	    SpreadsheetEntry document = this.getDocument(accessToken)
		
		WorksheetEntry worksheet = this.getWorksheet(document, accessToken)
		
		this.insertRow(worksheet,entry,accessToken)
	}

	/**
	 * Retrieves the document.
	 * 	
	 * @param accessToken - {@link String} the token to communicate to google.
	 * @return {@link SpreadsheetEntry}
	 */
	private SpreadsheetEntry getDocument(final String accessToken){
		GoogleSpreadsheetService service = this.getService(accessToken)
		URL spreadSheetFeedUrl = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full")
		SpreadsheetQuery query = new SpreadsheetQuery(spreadSheetFeedUrl)
		query.setTitleQuery(SHEET_NAME)
		query.setTitleExact(true)
		SpreadsheetFeed entries = service.query(query, SpreadsheetFeed.class)
		entries.getEntries().get(0)
	}
	
	/**
	 * Retrieves the {@link WorksheetEntry} to log the tasks
	 * 
	 * @param document - {@link SpreadsheetEntry}
	 * @param accessToken - {@link String}
	 * @return {@link WorksheetEntry}
	 */
    private	WorksheetEntry getWorksheet(final SpreadsheetEntry document, final String accessToken){
		GoogleSpreadsheetService service = this.getService(accessToken)
		WorksheetQuery query = new WorksheetQuery(document.getWorksheetFeedUrl())
		query.setTitleExact(true)
		query.setTitleQuery(WORKSHEET_NAME)
		WorksheetFeed entries = service.query(query, WorksheetFeed.class)
		entries.getEntries().get(0)
	}
	
	/**
	 * Insert a row into the given work sheet.
	 * 
	 * @param worksheet - {@link WorksheetEntry}
	 * @param entry - {@link LogEntry}
	 * @param accessToken - {@link String}
	 */
	private void insertRow(final WorksheetEntry worksheet, final LogEntry entry, final String accessToken){
		GoogleSpreadsheetService service = this.getService(accessToken)
		URL listFeedUrl = worksheet.getListFeedUrl()
		ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class)
		ListEntry row = new ListEntry();
		row.getCustomElements().setValueLocal("Timestamp", new Date().toTimestamp());
		row.getCustomElements().setValueLocal("Username", this.googleService.getEmail());
		row.getCustomElements().setValueLocal("Tarea", entry.task);
		row.getCustomElements().setValueLocal("Horas", entry.hours);
		row.getCustomElements().setValueLocal("Fecha", entry.day.parse("dd/MM/yyyy"));
		service.insert(listFeedUrl, row);
	}
	
	/**
	 * Creates a SpreadsheetService with the given
	 * accessToken
	 * 
	 * @param accessToken - {@link String}
	 * @return {@link SpreadsheetService}
	 */
	private GoogleSpreadsheetService getService(final String accessToken){
		GoogleSpreadsheetService service = new GoogleSpreadsheetService(APP_NAME)
		service.setHeader("Authorization", "Bearer " + accessToken)
		return service
	}
}
