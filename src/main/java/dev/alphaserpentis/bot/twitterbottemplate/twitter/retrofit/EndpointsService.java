package dev.alphaserpentis.bot.twitterbottemplate.twitter.retrofit;

import dev.alphaserpentis.bot.twitterbottemplate.data.request.PostTweetBody;
import dev.alphaserpentis.bot.twitterbottemplate.data.response.AccessTokenResponse;
import dev.alphaserpentis.bot.twitterbottemplate.data.response.PostTweetResponse;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import retrofit2.HttpException;
import retrofit2.Retrofit;

import java.util.Map;

public class EndpointsService {
    private final Endpoints api;

    public EndpointsService(@NonNull Retrofit retrofit) {
        this.api = retrofit.create(Endpoints.class);
    }

    protected Endpoints getApi() {
        return api;
    }

    public PostTweetResponse postTweet(
            @NonNull String authorizationHeader,
            @NonNull String content
    ) {
        PostTweetBody body = new PostTweetBody(content);

        return execute(api.postTweet(authorizationHeader, body));
    }

    public AccessTokenResponse generateNewAccessToken(
            @NonNull String authorizationHeader,
            @NonNull String grantType,
            @NonNull String refreshToken,
            @NonNull String clientId
    ) {
        Map<String, String> fields = Map.of(
                "grant_type", grantType,
                "refresh_token", refreshToken,
                "client_id", clientId
        );

        return execute(api.generateNewAccessToken(authorizationHeader, fields));
    }

    public AccessTokenResponse generateNewAccessToken(
            @NonNull String authorizationHeader,
            @NonNull String code,
            @NonNull String grantType,
            @NonNull String clientId,
            @NonNull String redirectUri,
            @NonNull String codeVerifier
    ) {
        Map<String, String> fields = Map.of(
                "code", code,
                "grant_type", grantType,
                "client_id", clientId,
                "redirect_uri", redirectUri,
                "code_verifier", codeVerifier
        );

        return execute(api.generateNewAccessToken(authorizationHeader, fields));
    }

    public static <T> T execute(@NonNull Single<T> call) {
        try {
            return call.blockingGet();
        } catch(HttpException e) {
            throw new RuntimeException(e);
        }
    }
}
