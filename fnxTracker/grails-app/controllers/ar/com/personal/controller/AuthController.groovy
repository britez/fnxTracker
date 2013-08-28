package ar.com.personal.controller

import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse

/**
 * Controller endpoint to authorize and
 * authenticate with google.
 * 
 * @author Maximiliano Britez
 */
class AuthController {

	/** The google service to delegate to */
	def googleService

	/**
	 * Redirects the end user to the google authorization
	 * url.	
	 */
    def index() {
		redirect url:this.googleService.getAuthUrl()
	}
	
	/**
	 * Request the session token and refresh token
	 */
	def code() {
		GoogleTokenResponse tokenResponse = this.googleService.getTokenResponse(params.code)
	}
}
