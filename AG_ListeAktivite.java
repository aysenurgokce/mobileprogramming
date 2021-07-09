package com.example.kitabeviotomasyonu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AG_ListeAktivite extends AppCompatActivity {
     ListView ag_list;
     private ArrayList<AG_KITAP> ag_kitaplar = new ArrayList<AG_KITAP>();
    private String ag_kitapadi,ag_kitapturu,ag_kitapyazari,ag_kitapstok,ag_kitapdili;
    private Double ag_kitapfiyati;
    private AG_Veritabani_sql v1;
    byte[] f;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_aktivite);
        ag_list=findViewById(R.id.ag_liste);
        v1 = new AG_Veritabani_sql(this);
        showdata(readdata());
        AG_KitapAdapter ag_kitapAdapter=new AG_KitapAdapter(AG_ListeAktivite.this,0,ag_kitaplar);
        ag_list.setAdapter(ag_kitapAdapter);
    }
    private String[] columns={"ag_kitapadi","ag_kitapyazari","ag_kitapturu","ag_kitapstok","ag_kitapdili","ag_kitapfiyati","ag_foto"};
    private Cursor readdata(){
        SQLiteDatabase db = v1.getReadableDatabase();
        Cursor read = db.query("Kitap",columns,null,null,null,null,null);
        return read;
    }
    private void showdata(Cursor show){
        while( show.moveToNext()) {
            ag_kitapadi = show.getString(show.getColumnIndex("ag_kitapadi"));
            ag_kitapyazari = show.getString(show.getColumnIndex("ag_kitapyazari"));
            ag_kitapturu = show.getString(show.getColumnIndex("ag_kitapturu"));
            ag_kitapstok = show.getString(show.getColumnIndex("ag_kitapstok"));
            ag_kitapdili = show.getString(show.getColumnIndex("ag_kitapdili"));
            ag_kitapfiyati = Double.parseDouble(show.getString(show.getColumnIndex("ag_kitapfiyati")));
            f = show.getBlob(show.getColumnIndex("ag_foto"));
            bitmap = BitmapFactory.decodeByteArray(f,0,f.length);
            AG_KITAP x = new AG_KITAP(AG_ListeAktivite.this,ag_kitapadi,ag_kitapyazari,ag_kitapturu,ag_kitapstok,ag_kitapdili,ag_kitapfiyati,bitmap);
            ag_kitaplar.add(x);
        }
    }
}