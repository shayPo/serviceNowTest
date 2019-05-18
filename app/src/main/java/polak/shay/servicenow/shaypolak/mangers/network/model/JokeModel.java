package polak.shay.servicenow.shaypolak.mangers.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import polak.shay.servicenow.shaypolak.model.Joke;

public class JokeModel
{
    @SerializedName("type")
    @Expose
    public String mType;

    @SerializedName("value")
    @Expose
    public Joke mData;

}
