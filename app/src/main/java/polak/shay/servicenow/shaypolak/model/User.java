package polak.shay.servicenow.shaypolak.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class User
{
    @PrimaryKey()
    public int uid;

    @ColumnInfo(name = "first_name")
    public String mFirstName;

    @ColumnInfo(name = "last_name")
    public String mLastName;

    @ColumnInfo(name = "after")
    public boolean mAfterApprove = false;

}
