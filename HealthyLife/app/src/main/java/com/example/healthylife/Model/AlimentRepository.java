package com.example.healthylife.Model;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AlimentRepository {

    private Context context;
    public AlimentRepository(Context ctx) {
        this.context = ctx;
    }

    public void insertTodayAliment(final AlimentEntity alimentEntity) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                Database.getSecondaryDatabase(context).alimentDao().insertAliment(alimentEntity);
            }
        }.start();
    }

    public void insertAliment(final AlimentEntity alimentEntity){
        new Thread(){
            @Override
            public void run() {
                super.run();
                Database.getDatabase(context).alimentDao().insertAliment(alimentEntity);
            }
        }.start();
    }

    public void updateAliment(final AlimentEntity alimentEntity){
        new Thread(){
            @Override
            public void run() {
                super.run();
                Database.getDatabase(context).alimentDao().updateAliment(alimentEntity);
            }
        }.start();
    }

    public void deleteAliment(final AlimentEntity alimentEntity){
        new Thread(){
            @Override
            public void run() {
                super.run();
                Database.getDatabase(context).alimentDao().deleteAliment(alimentEntity);
            }
        }.start();
    }

    public List<AlimentEntity> getAliments() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<AlimentEntity>> callable = new Callable<List<AlimentEntity>>() {
            @Override
            public List<AlimentEntity> call() throws Exception {
                return Database.getDatabase(context).alimentDao().getAliments();
            }
        };
        Future<List<AlimentEntity>> result = executor.submit(callable);
        executor.shutdown();
        try {
            return result.get();
        } catch (InterruptedException e) {
            throw new Exception("getAliments : InterruptedExeption");
        } catch (ExecutionException e) {
            throw new Exception("getAliments : InterruptedExeption");
        }
    }

    public List<AlimentEntity> getAlimentsByType(final String type) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<AlimentEntity>> callable = new Callable<List<AlimentEntity>>() {
            @Override
            public List<AlimentEntity> call() throws Exception {
                return Database.getDatabase(context).alimentDao().getAlimentsByType(type);
            }
        };
        Future<List<AlimentEntity>> result = executor.submit(callable);
        executor.shutdown();
        try {
            return result.get();
        } catch (InterruptedException e) {
            throw new Exception("getAlimentsByType : InterruptedExeption");
        } catch (ExecutionException e) {
            throw new Exception("getAlimentsByType : ExecutionExeption");
        }
    }

    public List<AlimentEntity> getAlimentsByName(final String name) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<AlimentEntity>> callable = new Callable<List<AlimentEntity>>() {
            @Override
            public List<AlimentEntity> call() throws Exception {
                return Database.getDatabase(context).alimentDao().getAlimentsByName(name);
            }
        };
        Future<List<AlimentEntity>> result = executor.submit(callable);
        executor.shutdown();
        try {
            return result.get();
        } catch (InterruptedException e) {
            throw new Exception("getAlimentsByName : InterruptedExeption");
        } catch (ExecutionException e) {
            throw new Exception("getAlimentsByName : ExecutionExeption");
        }
    }

    public AlimentEntity getAliment(final String name) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<AlimentEntity> callable = new Callable<AlimentEntity>() {
            @Override
            public AlimentEntity call() throws Exception {
                return Database.getDatabase(context).alimentDao().getAliment(name);
            }
        };
        Future<AlimentEntity> result = executor.submit(callable);
        try {
            return result.get();
        } catch (InterruptedException e) {
            throw new Exception("getAliment : InterruptedExeption");
        } catch (ExecutionException e) {
            throw new Exception("getAliment : ExecutionExeption");
        }
    }

    public List<AlimentEntity> getTodayAliments() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<AlimentEntity>> callable = new Callable<List<AlimentEntity>>() {
            @Override
            public List<AlimentEntity> call() throws Exception {
                return Database.getSecondaryDatabase(context).alimentDao().getAliments();
            }
        };
        Future<List<AlimentEntity>> result = executor.submit(callable);
        try {
            return result.get();
        } catch (InterruptedException e) {
            throw new Exception("getTodayAliments : InterruptedExeption");
        } catch (ExecutionException e) {
            throw new Exception("getTodayAliments : ExecutionExeption");
        }
    }

    public void deleteAll(final List<AlimentEntity> list) {
        new Thread(){
            @Override
            public void run() {
                Database.getSecondaryDatabase(context).alimentDao().deleteAll(list);
            }
        };
    }
}
