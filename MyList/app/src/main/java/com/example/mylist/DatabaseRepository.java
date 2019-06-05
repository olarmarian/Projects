package com.example.mylist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.support.v4.os.IResultReceiver;

import java.util.ArrayList;
import java.util.List;

public class DatabaseRepository extends SQLiteOpenHelper implements IRepository<String,Item> {

    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "MyListDB.db";
    private static final String TABLE_NAME="Items";
    private static final String KEY_ID="id";
    private static final String KEY_DESCRIPTION="description";
    private static final String KEY_QUANTITY="quantity";
    private static final String KEY_TYPE="type";

    public DatabaseRepository(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"(" +KEY_ID+ " TEXT PRIMARY KEY,"+ KEY_DESCRIPTION+" TEXT," +KEY_QUANTITY +" REAL," + KEY_TYPE +" TEXT"+")";
//        System.out.println(CREATE_TABLE);
//        String CREATE_TABLE = "CREATE TABLE Items(id TEXT PRIMARY KEY, description TEXT, quantity REAL, type TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void add(Item elem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID,elem.getId());
        values.put(KEY_DESCRIPTION,elem.getDescription());
        values.put(KEY_QUANTITY,elem.getQuantity());
        values.put(KEY_TYPE,elem.getType());

        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    @Override
    public void update(String s, Item elem) {

    }

    @Override
    public void remove(Item elem) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME,KEY_DESCRIPTION+" =?",new String[]{String.valueOf(elem.getDescription())});
        db.close();
    }

    @Override
    public Item findOne(String s) {
        return null;
    }

    @Override
    public List<Item> findAll() {
        List<Item> listItems = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                Item item = new Item();
                item.setId(cursor.getString(0));
                item.setDescription(cursor.getString(1));
                item.setQuantity(cursor.getFloat(2));
                item.setType(cursor.getString(3));
                listItems.add(item);
            }while (cursor.moveToNext());
        }
        return listItems;
    }
}
