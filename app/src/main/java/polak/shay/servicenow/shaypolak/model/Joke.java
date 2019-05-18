package polak.shay.servicenow.shaypolak.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Joke {

    @PrimaryKey
    @NonNull
    private String id = "";

    @SerializedName("joke")
    @Expose
    private String joke;

    public void setId(String id) {
        this.id = id;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public String getId() {
        return id;
    }

    public String getJoke() {
        return joke;
    }
}
