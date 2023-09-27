package dev.alphaserpentis.bot.twitterbottemplate.data.request;

import com.google.gson.annotations.SerializedName;

public class PostTweetBody {
    @SerializedName("text")
    private final String text;

    public PostTweetBody(String text) {
        this.text = text;
    }
}
