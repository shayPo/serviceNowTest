package polak.shay.servicenow.shaypolak.mangers.network;


import polak.shay.servicenow.shaypolak.mangers.network.model.JokeModel;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManger
{
    private final String BASE_URL = "https://api.icndb.com/jokes/";
    private NetworkAPI mApi;


    public NetworkManger()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        mApi = retrofit.create(NetworkAPI.class);
    }

    public Call<JokeModel> getJoke(String firstName, String lastName)
    {
        return mApi.getJoke(firstName ,lastName ,"nerdy");
    }
}
