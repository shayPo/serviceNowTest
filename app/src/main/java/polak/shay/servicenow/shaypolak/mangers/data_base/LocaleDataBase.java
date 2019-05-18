package polak.shay.servicenow.shaypolak.mangers.data_base;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;

import polak.shay.servicenow.shaypolak.model.Joke;
import polak.shay.servicenow.shaypolak.model.User;

@Database(entities = {User.class, Joke.class}, version = 2)
public abstract class LocaleDataBase extends RoomDatabase {
    public abstract LocaleBaseDao LocaleDataDao();
}

