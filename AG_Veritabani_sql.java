package com.example.kitabeviotomasyonu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AG_Veritabani_sql extends SQLiteOpenHelper {

    private static final String androidprogramming = "kitapotomasyon.sqlite3";
    private static final int SURUM = 1;
    public AG_Veritabani_sql(Context c){
        super(c, androidprogramming,null,SURUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Kitap (ag_kitapadi Text,ag_kitapyazari Text, ag_kitapturu Text, ag_kitapstok Text," +
                " ag_kitapdili Text,ag_kitapfiyati Text, ag_foto Blob);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Kitap");
        onCreate(db);
    }
}
