package dev.alphaserpentis.bot.twitterbottemplate.twitter;

import com.google.gson.Gson;
import dev.alphaserpentis.bot.twitterbottemplate.data.response.PostTweetResponse;
import dev.alphaserpentis.bot.twitterbottemplate.launcher.Main;
import io.reactivex.rxjava3.annotations.NonNull;

import java.io.IOException;
import java.util.Scanner;

public class TwitterHandler {
    private String accessToken = "";
    private String refreshToken = "";

    public void init(@NonNull String accessToken, @NonNull String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;

        // Async away from main thread
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Write your tweet here: ");
            String response = scanner.nextLine();

            try {
                System.out.println(new Gson().toJson(postTweet(response)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setAccessToken(@NonNull String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(@NonNull String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public PostTweetResponse postTweet(@NonNull String content) throws IOException {
        return Main.endpointsService.postTweet(
                "Bearer " + accessToken,
                content
        );
    }
}
