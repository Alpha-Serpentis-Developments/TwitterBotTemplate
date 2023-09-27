package dev.alphaserpentis.bot.twitterbottemplate.launcher;

import com.google.gson.GsonBuilder;
import dev.alphaserpentis.bot.twitterbottemplate.twitter.TwitterHandler;
import dev.alphaserpentis.bot.twitterbottemplate.server.TempWebserver;
import dev.alphaserpentis.bot.twitterbottemplate.twitter.retrofit.EndpointsService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class Main {
    public static EndpointsService endpointsService;
    public static TwitterHandler twitterHandler = new TwitterHandler();

    public static void main(String[] args)
            throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
        TempWebserver tempWebserver = new TempWebserver();
        String url = "https://twitter.com/i/oauth2/authorize?response_type=code&client_id=%s&redirect_uri=%s&scope=tweet.write%%20tweet.read%%20users.read%%20offline.access&state=state&code_challenge=challenge&code_challenge_method=plain";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.twitter.com/2/")
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();

        System.out.println("Loading temporary webserver...");
        tempWebserver.loadInSsl();
        tempWebserver.startServer();

        url = url.formatted(
                System.getenv("CLIENT_ID"),
                System.getenv("REDIRECT_URI")
        );

        System.out.println("Go to " + url + " to authorize the bot account!");

        endpointsService = new EndpointsService(retrofit);
    }
}