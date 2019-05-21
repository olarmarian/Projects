package com.example.healthylife.Model;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@android.arch.persistence.room.Database(entities = {AlimentEntity.class},version = 1)
public abstract class Database extends RoomDatabase {

    public abstract AlimentDao alimentDao();

    private static Database DATABASE;

    //Database used for "TodayAliments"
    private static Database SECONDARY_DATABASE;

    public static Database getDatabase(Context context){
        if(DATABASE == null){
            DATABASE = Room.databaseBuilder(context,Database.class,"AlimentsDB").build();
        }
        return DATABASE;
    }

    public static Database getSecondaryDatabase(Context context){
        if(SECONDARY_DATABASE == null){
            SECONDARY_DATABASE = Room.databaseBuilder(context,Database.class,"TodayAlimentsDB").build();
        }
        return SECONDARY_DATABASE;
    }
}
