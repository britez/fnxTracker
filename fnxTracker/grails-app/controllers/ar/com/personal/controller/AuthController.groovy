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
		redirect(url:googleService.getAuthUrl(), params:[redirect_url:params.redirect_url])
	}
	
	/**
	 * Request the session token and refresh token
	 */
	def code() {
		GoogleTokenResponse tokenResponse = googleService.getTokenResponse(params.code)
		session["accessToken"] = tokenResponse.getAccessToken()
		session["refreshToken"] = tokenResponse.getRefreshToken()
		
		redirect url: params.redirect_url
	}
}
