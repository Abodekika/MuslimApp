package com.example.azkar.data.Pojo.azkar;

import java.util.List;

public class FavItem {

    String id;
    private String title;
    private String count;
    public String bookmark;
    public List<Content> content;




    public FavItem(String id, String title, String count, String bookmark, List<Content> content) {
        this.id = id;
        this.title = title;
        this.count = count;
        this.bookmark = bookmark;
        this.content = content;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }


}
