package dev.alphaserpentis.bot.twitterbottemplate.twitter;

import dev.alphaserpentis.bot.twitterbottemplate.data.response.AccessTokenResponse;
import dev.alphaserpentis.bot.twitterbottemplate.launcher.Main;
import io.reactivex.rxjava3.annotations.NonNull;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AuthorizationHandler {
    private static final String basicAuthorizationEncoded = Base64.getEncoder().encodeToString(
            (System.getenv().get("CLIENT_ID") + ":" + System.getenv().get("CLIENT_SECRET")).getBytes(StandardCharsets.UTF_8)
    );

    public static AccessTokenResponse generateNewAccessToken(@NonNull String refreshToken) {
        return Main.endpointsService.generateNewAccessToken(
                "Basic " + basicAuthorizationEncoded,
                "refresh_token",
                refreshToken,
                System.getenv().get("CLIENT_ID")
        );
    }

    public static AccessTokenResponse processAuthorizationCode(@NonNull String authorizationCode) {
        return Main.endpointsService.generateNewAccessToken(
                "Basic " + basicAuthorizationEncoded,
                authorizationCode,
                "authorization_code",
                System.getenv().get("CLIENT_ID"),
                System.getenv().get("REDIRECT_URI"),
                "challenge"
        );
    }
}
