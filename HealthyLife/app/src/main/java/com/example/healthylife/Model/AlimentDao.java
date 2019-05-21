package com.example.healthylife.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public abstract class AlimentDao {

    @Insert
    public abstract void insertAliment(AlimentEntity alimentEntity);

    @Update
    public abstract void updateAliment(AlimentEntity alimentEntity);

    @Delete
    public abstract void deleteAliment(AlimentEntity alimentEntity);

    @Query("SELECT * FROM Aliment")
    public abstract List<AlimentEntity> getAliments();

    @Query("SELECT * FROM Aliment WHERE recommendedType=:type")
    public abstract List<AlimentEntity> getAlimentsByType(String type);

    @Query("SELECT * FROM Aliment WHERE name LIKE :name")
    public abstract List<AlimentEntity> getAlimentsByName(String name);

    @Query("SELECT * FROM Aliment WHERE name = :name")
    public abstract AlimentEntity getAliment(String name);

    @Delete
    public abstract void deleteAll(List<AlimentEntity> list);
}
