package com.example.mylist;

import android.content.ClipData;

public interface Listener {
    void addItemFragment();
    void addItem(String description,Float quantity, String type);
    void removeItem(String description,Float quantity, String type);
    void getAllItems();
}
