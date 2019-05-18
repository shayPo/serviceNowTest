package polak.shay.servicenow.shaypolak.mangers.data_base;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import polak.shay.servicenow.shaypolak.model.Joke;
import polak.shay.servicenow.shaypolak.model.User;

@Dao
public interface LocaleBaseDao {
    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM User")
    LiveData<User[]> getUser();


    @Query("SELECT * FROM Joke")
    LiveData<Joke[]> getJokes();

    @Insert
    void addJoke(Joke joke);
}
