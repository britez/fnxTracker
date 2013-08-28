package ar.com.globallogic.service

import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.commons.GrailsApplication;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.gdata.client.spreadsheet.SpreadsheetService
import com.google.gdata.data.spreadsheet.SpreadsheetEntry
import com.google.gdata.data.spreadsheet.SpreadsheetFeed

/**
 * This class is responsible to authorize and authenticate
 * the application with Google.
 * 
 * @author Maximiliano Britez
 */
class GoogleService {
	
	/** logger */
	private static final log = LogFactory.getLog(this)
	
	/** The spreadsheet SCOPE */
	final String SCOPE = "https://spreadsheets.google.com/feeds"
	
	/** The app's client secret */
	final String CLIENT_SECRET = grailsApplication.config.google.client_secret
	
	/** The app's client id */
	final String CLIENT_ID = grailsApplication.config.google.client_id
	
	/** The app's redirection URI */
	final String REDIRECT_URI = grailsApplication.config.grails.serverURL + "/auth/code"

	/** Creates a URL to allow the END-USER to grant persmissions */
    def getAuthUrl() {
		log.info("Obteniendo url para dar permisos de accesso a la aplicación")
	    this.buildGoogleFlow().newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build()
    }
	
	/** Retrieves the TokenResponse and RefreshToken */ 
	GoogleTokenResponse getTokenResponse(final String code){
		log.info("Obteniendo session token and refresh token")
		this.buildGoogleFlow().newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute()
	}
	
	def listDocs(final String accessToken){
		SpreadsheetService service = new SpreadsheetService("yourAppName")
		service.setHeader("Authorization", "Bearer " + accessToken)
		URL metafeedUrl = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full")
		SpreadsheetFeed feed = service.getFeed(metafeedUrl, SpreadsheetFeed.class)
		
		
		List spreadsheets = feed.getEntries()
		for (int i = 0; i < spreadsheets.size(); i++) {
			SpreadsheetEntry entry = spreadsheets.get(i)
			System.out.println("\t" + entry.getTitle().getPlainText())
		}
		
	}
	
	/** Builds a GoogleAuthorizationCodeFlow */
	private def buildGoogleFlow(){
		log.info("Creando CodeFlow de google")
		def flow = new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(),new JacksonFactory(),CLIENT_ID,CLIENT_SECRET,[SCOPE])
		flow.setAccessType("offline").setApprovalPrompt("force").build()
	}

	// Refresh a token (SHOULD ONLY BE DONE WHEN ACCESS TOKEN EXPIRES)
	//access.refreshToken();
	//System.out.println("Original Token: " + accessToken + " New Token: " + access.getAccessToken());
}
