package com.example.healthylife.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Aliment")
public class AlimentEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "recommendedType")
    private String recommendedType;

    @ColumnInfo(name = "kcals")
    private double kcals;

    @ColumnInfo(name = "lipids")
    private double lipids;

    @ColumnInfo(name = "proteins")
    private double proteins;

    @ColumnInfo(name = "carbohydrates")
    private double carbohydrates;

    @ColumnInfo(name = "fiber")
    private double fiber;

    @ColumnInfo(name = "sugar")
    private double sugar;


    public AlimentEntity(String name, String recommendedType, double kcals,double lipids, double proteins, double carbohydrates, double fiber, double sugar) {
        this.name = name;
        this.lipids = lipids;
        this.recommendedType = recommendedType;
        this.kcals = kcals;
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.fiber = fiber;
        this.sugar = sugar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getRecommendedType() {
        return recommendedType;
    }

    public double getKcals() {
        return kcals;
    }

    public double getProteins() {
        return proteins;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public double getFiber() {
        return fiber;
    }

    public double getSugar() {
        return sugar;
    }

    public double getLipids() {
        return lipids;
    }

    @Override
    public String toString() {
//        return  name + " | " + recommendedType + " | "+ kcals + " | " + lipids + " | " + proteins + " | " + carbohydrates + " | " + fiber + " | " + sugar;
        return name;
    }
}
