package com.example.azkar.data.Pojo.azkar;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Content implements Serializable {
    public Content(int ID,String text, String count, String fadl, String source, int counter, String AUDIO) {
        this.text = text;
        this.count = count;
        this.fadl = fadl;
        this.source = source;
        this.counter = counter;
        this.AUDIO = AUDIO;
        this.ID = ID;
    }

    @SerializedName("text")
    public String text;
    @SerializedName("count")
    public String count;
    @SerializedName("fadl")
    public String fadl;
    @SerializedName("source")
    public String source;
    @SerializedName("counter")
    public int counter;
    @SerializedName("AUDIO")
    public String AUDIO;
    @SerializedName("ID")
    public int ID;

    public int getCounter() {
        return counter;
    }

    public String getAUDIO() {
        return AUDIO;
    }

    public void setAUDIO(String AUDIO) {
        this.AUDIO = AUDIO;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getFadl() {
        return fadl;
    }

    public void setFadl(String fadl) {
        this.fadl = fadl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


}
