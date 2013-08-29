package ar.com.globallogic.service

import java.io.IOException;

import org.apache.commons.logging.LogFactory

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse
import com.google.api.client.http.HttpRequest
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.oauth2.Oauth2
import com.google.api.services.oauth2.model.Userinfo
import com.google.gdata.client.spreadsheet.SpreadsheetQuery
import com.google.gdata.client.spreadsheet.SpreadsheetService
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
	final String SPREADSHEET_SCOPE = "https://spreadsheets.google.com/feeds"
	
	/** The EMAIL SCOPE */
	final String EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo#email"
	
	/** The app's client secret */
	final String CLIENT_SECRET = "9vMO2377dZqikiuxjf2qubZL"
	
	/** The app's client id */
	final String CLIENT_ID = "119451786372-t5c5ba52gb77thuv60cd1fa6j4ue5c1h.apps.googleusercontent.com"
	
	/** The app's redirection URI */
	final String REDIRECT_URI = "http://localhost:8080/fnxTracker" + "/auth/code"

	/** Creates a URL to allow the END-USER to grant persmissions */
    String getAuthUrl() {
		log.info("Obteniendo url para dar permisos de accesso a la aplicación")
	    this.buildGoogleFlow().newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build()
    }
	
	/** Retrieves the TokenResponse and RefreshToken */ 
	GoogleTokenResponse getTokenResponse(final String code){
		log.info("Obteniendo session token and refresh token")
		this.buildGoogleFlow().newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute()
	}
	
	/** Retrieves the email by the given token */ 
	String getEmail(final String accessToken){
		Oauth2 userInfoService = new Oauth2.Builder(new NetHttpTransport(), new JacksonFactory(), new GoogleHttpRequestInitializer(accessToken)).setApplicationName("theApiName").build();
		log.info("Obteniendo el mail para el cliente actual")
		userInfoService.userinfo().get().execute().getEmail();
	}

	/** Builds a GoogleAuthorizationCodeFlow */
	private GoogleAuthorizationCodeFlow buildGoogleFlow(){
		log.info("Creando CodeFlow de google")
		def flow = new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(),new JacksonFactory(),CLIENT_ID,CLIENT_SECRET,[SPREADSHEET_SCOPE,EMAIL_SCOPE])
		flow.setAccessType("offline").setApprovalPrompt("force").build()
	}
	
	/**
	 * This class is responsible to set the 
	 * authorization header with the correct access token
	 * 
	 * @author Maximiliano Britez
	 */
	private class GoogleHttpRequestInitializer implements HttpRequestInitializer {
		
		/** The given accessToken */ 
		private String accessToken; 
		
		/**
		 * Default conturctor to store the 
		 * access token
		 * 
		 * @param accessToken - {@link String}
		 */
		GoogleHttpRequestInitializer(final String accessToken){
			this.accessToken = accessToken;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void initialize(HttpRequest req) throws IOException {
			req.getHeaders().put("Authorization", ["Bearer " + accessToken])
		}
		
	}
}
// Refresh a token (SHOULD ONLY BE DONE WHEN ACCESS TOKEN EXPIRES)
//access.refreshToken();
//System.out.println("Original Token: " + accessToken + " New Token: " + access.getAccessToken());