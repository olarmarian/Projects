package com.example.healthylife.Controller;

import com.example.healthylife.Model.AlimentEntity;
import com.example.healthylife.Model.AlimentRepository;

import java.util.List;

public class Controller {
    private AlimentRepository repository;

    public Controller(AlimentRepository repository) {
        this.repository = repository;
    }

    public void addAliment(String name, String recommendedType, double kcals,double lipids, double proteins, double carbohydrates, double fiber, double sugar){
        this.repository.insertAliment(new AlimentEntity(name,recommendedType,kcals,lipids,proteins,carbohydrates,fiber,sugar));
    }

    public void addTodayAliment(String name, String recommendedType, double kcals,double lipids, double proteins, double carbohydrates, double fiber, double sugar){
        this.repository.insertTodayAliment(new AlimentEntity(name,recommendedType,kcals,lipids,proteins,carbohydrates,fiber,sugar));
    }

    public List<AlimentEntity> getAliments() throws Exception {
        return this.repository.getAliments();
    }
    public List<AlimentEntity> getAlimentsByType(String recommendedType) throws Exception {
        return this.repository.getAlimentsByType(recommendedType);
    }

    public List<AlimentEntity> getAlimentsByName(String name) throws Exception {
        return this.repository.getAlimentsByName(name);
    }

    public List<AlimentEntity> getTodayAliments() throws Exception {
        return this.repository.getTodayAliments();
    }

    public void deleteTodayAliments(List<AlimentEntity> list){
        this.repository.deleteAll(list);
    }

}
