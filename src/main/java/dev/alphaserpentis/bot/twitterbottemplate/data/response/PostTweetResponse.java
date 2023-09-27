package dev.alphaserpentis.bot.twitterbottemplate.data.response;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PostTweetResponse {
    @SerializedName("data")
    private TweetData data;

    public static class TweetData {
        @SerializedName("id")
        private String id;
        @SerializedName("text")
        private String text;

        public String getId() {
            return id;
        }

        public String getText() {
            return text;
        }
    }

    public TweetData getData() {
        return data;
    }
}
