package com.example.azkar.data.Pojo.azkar;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Azker1 {
    @SerializedName("id")
    String id;
    @SerializedName("title")
    public String title;

    @SerializedName("count")
    public String count;
    @SerializedName("bookmark")
    public String bookmark;
    @SerializedName("content")
    public List<Content> content;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getBookmark() {
        return bookmark;
    }

    public void setBookmark(String bookmark) {
        this.bookmark = bookmark;
    }

    public List<Content> getContent() {

        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
