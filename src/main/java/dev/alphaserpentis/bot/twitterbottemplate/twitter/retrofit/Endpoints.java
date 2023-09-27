package dev.alphaserpentis.bot.twitterbottemplate.twitter.retrofit;

import dev.alphaserpentis.bot.twitterbottemplate.data.response.AccessTokenResponse;
import dev.alphaserpentis.bot.twitterbottemplate.data.request.PostTweetBody;
import dev.alphaserpentis.bot.twitterbottemplate.data.response.PostTweetResponse;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import java.util.Map;

public interface Endpoints {

    @Headers("Content-Type: application/json")
    @POST("tweets")
    Single<PostTweetResponse> postTweet(
            @Header("Authorization") String authorizationHeader,
            @Body PostTweetBody body
    );

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("oauth2/token")
    Single<AccessTokenResponse> generateNewAccessToken(
            @Header("Authorization") String authorizationHeader,
            @FieldMap Map<String, String> fields
    );
}
