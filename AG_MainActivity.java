package com.example.kitabeviotomasyonu;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class AG_MainActivity extends AppCompatActivity {

    EditText ag_adi, ag_yazari, ag_fiyati;
    Spinner ag_tur;
    RadioGroup ag_stokdurum;
    CheckBox ag_turkce, ag_ingilizce, ag_almanca, ag_fransizca, ag_diger;
    RadioButton ag_stok_var, ag_stok_yok;
    ArrayList<String> ag_checkbox , ag_radio;
    ArrayList<AG_KITAP> ag_kitaplar;
    Button ag_kitapliste, ag_foto_ekle;
    ImageView ag_foto;
    Bitmap ag_secilenresim;
    int request_imageopen=0;
    private AG_Veritabani_sql v1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v1 = new AG_Veritabani_sql(this);
        ag_CallInputs();
        ag_kitaplar = new ArrayList<>();
        ag_checkbox = new ArrayList<>();
        ag_radio = new ArrayList<>();
        //Veritabanı sıfırlama
        /*SQLiteDatabase db = v1.getReadableDatabase();
        v1.onUpgrade(db,1,1);*/
        //KİTAPLARI LİSTELE BUTONU
        ag_kitapliste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ag_intent=new Intent(AG_MainActivity.this, AG_ListeAktivite.class);
                startActivity(ag_intent);
            }
        });

        //CHECHBOX SEÇİLİ OLDUĞU DURUM KOŞULLARI
        ag_turkce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ag_turkce.isChecked())) {
                    ag_checkbox.add("Türkçe");
                } else {
                    ag_checkbox.remove("Türkçe");
                }
            }
        });
        ag_ingilizce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ag_ingilizce.isChecked()) {
                    ag_checkbox.add("İngilizce");
                } else {
                    ag_checkbox.remove("İngilizce");
                }
            }
        });
        ag_almanca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ag_almanca.isChecked()) {
                    ag_checkbox.add("Almanca");
                } else {
                    ag_checkbox.remove("Almanca");
                }
            }
        });
        ag_fransizca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ag_fransizca.isChecked()) {
                    ag_checkbox.add("Fransızca");
                } else {
                    ag_checkbox.remove("Fransızca");
                }
            }
        });
        ag_diger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ag_diger.isChecked()) {
                    ag_checkbox.add("Diğer Diller");
                } else {
                    ag_checkbox.remove("Diğer Diller");
                }
            }
        });
        ag_stok_var.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ag_stok_var.isChecked()) {
                    ag_radio.add("Stok Var");
                } else {
                    ag_radio.remove("Stok Var");
                }
            }
        });
        ag_stok_yok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ag_stok_yok.isChecked()) {
                    ag_radio.add("Stok Yok");
                } else {
                    ag_radio.remove("Stok Yok");
                }
            }
        });

        //FOTOĞRAF ÇEKMEK İÇİN KODLAR
        ag_foto_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ag_kamera = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                ag_kamera.setType("image/*");
                ag_kamera.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(ag_kamera, request_imageopen);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == request_imageopen && resultCode == RESULT_OK){
            Uri uri = data.getData();
            try{
                if(Build.VERSION.SDK_INT >= 28){
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(),uri);
                    ag_secilenresim = ImageDecoder.decodeBitmap(source);
                    ag_foto.setImageBitmap(ag_secilenresim);
                }else{
                    ag_secilenresim = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                    ag_foto.setImageBitmap(ag_secilenresim);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public byte[] byte2array(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ag_secilenresim.compress(Bitmap.CompressFormat.PNG,50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return bytes;
    }
    public void ag_CallInputs(){
        ag_adi = findViewById(R.id.ag_etxt_kitap_adi);
        ag_yazari = findViewById(R.id.ag_etxt_kitap_yazari);
        ag_fiyati = findViewById(R.id.ag_etxt_kitap_fiyat);
        ag_tur = findViewById(R.id.ag_spn_kitap_tur);
        ag_stokdurum = findViewById(R.id.ag_rdgrp);
        ag_turkce = findViewById(R.id.ag_chb_turkce);
        ag_ingilizce = findViewById(R.id.ag_chb_ingilizce);
        ag_almanca = findViewById(R.id.ag_chb_almanca);
        ag_fransizca = findViewById(R.id.ag_chb_fransizca);
        ag_diger = findViewById(R.id.ag_chb_diger_diller);
        ag_stok_var = findViewById(R.id.ag_rdbtn_stok_var);
        ag_stok_yok = findViewById(R.id.ag_rdbtn_stok_yok);
        ag_kitapliste = findViewById(R.id.ag_btn_kitap_listele);
        ag_foto_ekle = findViewById(R.id.ag_btn_kitap_foto);
        ag_foto = findViewById(R.id.ag_fotograf_ekle);
    }
    // KİTAP OLUŞTUR BUTONU
    public void ag_kitapEkle(View view) {
        int selectedId = ag_stokdurum.getCheckedRadioButtonId();
        for (String ag_kitap_dili_ekle  : ag_checkbox) {
            for (String ag_stok_add : ag_radio) {
                AG_KITAP ag_kitap = new AG_KITAP(AG_MainActivity.this,ag_adi.getText().toString(),
                        ag_yazari.getText().toString(),
                        ag_tur.getSelectedItem().toString(),
                        ag_kitap_dili_ekle,
                        ag_stok_add,
                        Double.parseDouble(ag_fiyati.getText().toString()),
                        ag_secilenresim);
                ag_kitap.setAg_kitapfiyati(Double.parseDouble(ag_fiyati.getText().toString()));

                if (ag_kitap.getAg_kitapfiyati() > 0) {
                    ag_kitaplar.add(ag_kitap);
                    SQLiteDatabase db = v1.getWritableDatabase();
                    ContentValues data = new ContentValues();
                    byte[] photo = byte2array();
                    data.put("ag_kitapadi",ag_adi.getText().toString());
                    data.put("ag_kitapyazari",ag_yazari.getText().toString());
                    data.put("ag_kitapturu",ag_tur.getSelectedItem().toString());
                    data.put("ag_kitapstok",ag_stok_add);
                    data.put("ag_kitapdili",ag_kitap_dili_ekle);
                    data.put("ag_kitapfiyati",ag_fiyati.getText().toString());
                    data.put("ag_foto",photo);
                    db.insertOrThrow("Kitap",null,data);
                    Toast.makeText(getApplicationContext(),"Kitap eklendi!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Ekleme başarısız", Toast.LENGTH_SHORT).show();
                }
            }
        }
        Toast.makeText(getApplicationContext(),
                        " Eklenen : " + ag_kitaplar.get(ag_kitaplar.size() - 1).getAg_kitapadi(),
                Toast.LENGTH_LONG).show();
    }
}