package fedulova.polina303.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper { //конструктор класса
    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS my_test (my_key TEXT, my_value TEXT);"; //create new table with 2 columns
        db.execSQL(sql); //run query

    }

    public void do_insert (String key, String value){
        String sql = "INSERT INTO my_test VALUES('" + key + "', '" + value + "');"; //insert new row into table
        SQLiteDatabase db = getWritableDatabase(); //приготовьтесь к записи в базу данных
        db.execSQL(sql);//запустите запрос
    }

    public String do_select (String key){
        String sql = "SELECT my_value FROM my_test WHERE my_key = '" + key + "';"; //find value inside table by given key
        SQLiteDatabase db = getReadableDatabase(); //приготовьтесь к чтению из базы данных
        Cursor cur = db.rawQuery(sql, null); //запустите запрос и получите результат

        if (cur.moveToFirst() == true) //перейти к первой (и единственной) соответствующей записи, если это возможно
            return cur.getString(0); //возвращаемое значение из первого столбца (my_value)

        return "empty"; //return special text when no result
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void do_update (String key, String value){
        String sql = "UPDATE my_test SET my_value='" + value + "' WHERE my_key='" + key + "';"; //update
        SQLiteDatabase db = getWritableDatabase(); //приготовьтесь к записи в базу данных
        db.execSQL(sql);//запустите запрос
    }

    public void do_delete (String key){
        String sql = "DELETE FROM my_test WHERE my_key='" + key + "';"; //delete
        SQLiteDatabase db = getWritableDatabase(); //приготовьтесь к записи в базу данных
        db.execSQL(sql);//запустите запрос
    }
}
