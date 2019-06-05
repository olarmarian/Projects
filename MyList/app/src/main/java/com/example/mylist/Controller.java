package com.example.mylist;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

public class Controller {

    private IRepository<String,Item> repository;
    public Controller(IRepository<String,Item> repo) {
        this.repository = repo;
    }

    public void addItem(String description, Float quantity, String type) throws UnsupportedEncodingException {
        String source = description + quantity;
        byte[] bytes = source.getBytes("UTF-8");
        UUID uuid = UUID.nameUUIDFromBytes(bytes);
        Item item = new Item(uuid.toString(),description,quantity,type);
        repository.add(item);
    }

    public void removeItem(String description, Float quantity, String type) throws UnsupportedEncodingException {
        String source = description + quantity;
        byte[] bytes = source.getBytes("UTF-8");
        UUID uuid = UUID.nameUUIDFromBytes(bytes);
        Item item = new Item(uuid.toString(),description,quantity,type);
        repository.remove(item);
    }

    public List<Item> getAllItems(){
        return repository.findAll();
    }

}
