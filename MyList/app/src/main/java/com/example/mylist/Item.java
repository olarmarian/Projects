package com.example.mylist;

import java.util.Objects;

public class Item {

    private String id;
    private String description;
    private Float quantity;
    private String type;

    public Item(){}
    public Item(String id, String description,Float quantity,String type) {
        this.id = id;
        this.description = description;
        this.quantity = quantity;
        this.type = type;

    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Float getQuantity() {
        return quantity;
    }
    public String getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Description: "+this.description+" | Quantity : " + this.quantity + " "+this.type;
    }
}
