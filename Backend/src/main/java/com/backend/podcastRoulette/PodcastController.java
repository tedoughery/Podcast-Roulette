package com.backend.podcastRoulette;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.SavedShow;


import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.data.library.GetUsersSavedShowsRequest;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/podcastRoulette")
@CrossOrigin("http://localhost:4200")
public class PodcastController {
    private static final Dotenv dotenv = Dotenv
            .configure()
            .load();

    private static final String clientID = dotenv.get("CLIENT_ID");
    private static final String clientSecret = dotenv.get("CLIENT_SECRET");

    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/podcastRoulette/getUserCode");

    private static final SpotifyApi spotApi = new SpotifyApi.Builder()
            .setClientId(clientID)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();

    @GetMapping("/login")
    @ResponseBody
    public String login() {
        AuthorizationCodeUriRequest authUriReq = spotApi.authorizationCodeUri()
                .scope("user-read-private, user-read-email, user-library-read")
                .show_dialog(true)
                .build();
        final URI uri = authUriReq.execute();
        return uri.toString();
    }

    @GetMapping("/getUserCode")
    public String getUserCode(@RequestParam("code") String userCode, HttpServletResponse response) throws IOException {
        AuthorizationCodeRequest authCodeRequest = spotApi.authorizationCode(userCode).build();

        try {
            final AuthorizationCodeCredentials authCredentials = authCodeRequest.execute();

            spotApi.setAccessToken(authCredentials.getAccessToken());
            spotApi.setRefreshToken(authCredentials.getRefreshToken());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        response.sendRedirect("http://localhost:4200/podcast"); //TODO: replace with actual podcast code when it's ready to go
        return spotApi.getAccessToken();
    }

    //Just gets whatever podcasts the user has saved
    //TODO: implement a function to random a given podcast's episodes and return a selection
    @GetMapping("/getPodcast")
    public SavedShow[] getPodcast() {
        final GetUsersSavedShowsRequest getShowsReq = spotApi.getUsersSavedShows().build();
        try {
            final Paging<SavedShow> showPaging = getShowsReq.execute();
            return showPaging.getItems();
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return new SavedShow[0];
    }
}
