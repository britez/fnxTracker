package ar.com.globallogic.service

import java.awt.Desktop

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory

class GoogleService {

    def auth() {
		HttpTransport httpTransport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
        .setAccessType("offline")
        .setApprovalPrompt("force").build();

    String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
    System.out.println("Enter authorization code:");
    Desktop.getDesktop().browse(new URI(url));
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String code = br.readLine();

    GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
	response.getAccessToken()
		
	}
//		// Generate the URL to which we will direct users
//		String authorizeUrl = new GoogleAuthorizationRequestUrl(CLIENT_ID,
//			CALLBACK_URL, SCOPE).build();
//		System.out.println("Paste this url in your browser: " + authorizeUrl);
//	
//		// Wait for the authorization code
//		System.out.println("Type the code you received here: ");
//		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		String authorizationCode = in.readLine();
//	
//		// Exchange for an access and refresh token
//		GoogleAuthorizationCodeGrant authRequest = new GoogleAuthorizationCodeGrant(TRANSPORT,
//			JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, authorizationCode, CALLBACK_URL);
//		authRequest.useBasicAuthorization = false;
//		AccessTokenResponse authResponse = authRequest.execute();
//		String accessToken = authResponse.accessToken;
//		GoogleAccessProtectedResource access = new GoogleAccessProtectedResource(accessToken,
//			TRANSPORT, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, authResponse.refreshToken);
//		HttpRequestFactory rf = TRANSPORT.createRequestFactory(access);
//		System.out.println("Access token: " + authResponse.accessToken);
//	
//		// Make an authenticated request
//		GenericUrl shortenEndpoint = new GenericUrl("https://www.googleapis.com/urlshortener/v1/url");
//		String requestBody =
//			"{\"longUrl\":\"http://farm6.static.flickr.com/5281/5686001474_e06f1587ff_o.jpg\"}";
//		HttpRequest request = rf.buildPostRequest(shortenEndpoint,
//			ByteArrayContent.fromString("application/json", requestBody))
//		HttpResponse shortUrl = request.execute();
//		BufferedReader output = new BufferedReader(new InputStreamReader(shortUrl.getContent()));
//		System.out.println("Shorten Response: ");
//		for (String line = output.readLine(); line != null; line = output.readLine()) {
//		  System.out.println(line);
//		}
//	
//		// Refresh a token (SHOULD ONLY BE DONE WHEN ACCESS TOKEN EXPIRES)
//		access.refreshToken();
//		System.out.println("Original Token: " + accessToken + " New Token: " + access.getAccessToken());
}
