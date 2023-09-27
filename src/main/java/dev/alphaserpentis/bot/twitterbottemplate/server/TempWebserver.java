package dev.alphaserpentis.bot.twitterbottemplate.server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;
import dev.alphaserpentis.bot.twitterbottemplate.data.response.AccessTokenResponse;
import dev.alphaserpentis.bot.twitterbottemplate.launcher.Main;
import dev.alphaserpentis.bot.twitterbottemplate.twitter.AuthorizationHandler;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class TempWebserver implements HttpHandler {
    private SSLContext ssl;
    private HttpsServer server;

    public void loadInSsl() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
        char[] passphrase = System.getenv("CERT_PASSPHRASE").toCharArray();
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream(System.getenv("CERT_PATH")), passphrase);

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, passphrase);

        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ks);

        ssl = SSLContext.getInstance("TLS");
        ssl.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
    }

    public void startServer() throws IOException {
        server = HttpsServer.create(new InetSocketAddress(8443), 0);
        server.setHttpsConfigurator(new HttpsConfigurator(ssl));
        server.createContext("/", this);
        server.setExecutor(null); // Default executor
        server.start();
        System.out.println("Temporary webserver is up!");
    }

    public void closeServer() {
        server.stop(0);
        System.out.println("Temporary webserver closed!");
    }

    @Override
    public void handle(HttpExchange exchange) {
        URI requestURI = exchange.getRequestURI();
        String query = requestURI.getQuery();
        String code = query.split("=")[2]; // This is the authorization code
        AccessTokenResponse response;

        try {
            response = AuthorizationHandler.processAuthorizationCode(code);

            Main.twitterHandler.init(response.getAccessToken(), response.getRefreshToken());

            // Set the Location header back to Twitter
            Headers headers = exchange.getResponseHeaders();
            headers.add("Location", "https://twitter.com");

            exchange.sendResponseHeaders(302, -1);

            exchange.close();
            closeServer(); // Close server after request
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
