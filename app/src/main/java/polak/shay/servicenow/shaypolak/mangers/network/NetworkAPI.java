package polak.shay.servicenow.shaypolak.mangers.network;

import polak.shay.servicenow.shaypolak.mangers.network.model.JokeModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkAPI
{
    @GET("random")
    Call<JokeModel> getJoke(@Query("firstName") String first, @Query("lastName") String last, @Query("limitTo") String limitTo);
}
