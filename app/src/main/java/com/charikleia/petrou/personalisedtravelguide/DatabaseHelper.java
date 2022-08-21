package com.charikleia.petrou.personalisedtravelguide;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_Name = "Pois";
    public static final String TABLE_Name = "GreecePois";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "CATEGORY";
    public static final String COL_4 = "SUBCATEGORY";
    public static final String COL_5 = "LON";
    public static final String COL_6 = "LAT";
    public static final String COL_7 = "SRID";
    public static final String COL_8 = "WKT";
    public static final String COL_9 = "INTERNATIONAL_NAME";
    public static final String COL_10 = "STREET";
    public static final String COL_11 = "WIKIPEDIA";
    public static final String COL_12 = "PHONE";
    public static final String COL_13 = "CITY";
    public static final String COL_14 = "EMAIL";
    public static final String COL_15 = "ALTERNATIVE_NAME";
    public static final String COL_16 = "OPENING_HOURS";
    public static final String COL_17 = "DESCRIPTION";
    public static final String COL_18 = "WEBSITE";
    public static final String COL_19 = "LAST_UPDATE";
    public static final String COL_20 = "OPERATOR";
    public static final String COL_21 = "POSTCODE";
    public static final String COL_22 = "COUNTRY";
    public static final String COL_23 = "FAX";
    public static final String COL_24 = "IMAGE";
    public static final String COL_25 = "HOUSENUMBER";
    public static final String COL_26 = "OTHER_TAGS";
    @Nullable
    private final Context context;


    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_Name, null, 1);
        this.context = context;

//        System.out.println("b4 insert");
//        insertData();

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        db = openOrCreateDatabase("DB_Name", Context.MODE_PRIVATE,null);

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+DB_Name+"."+TABLE_Name+" (ID INTEGER PRIMARY KEY, NAME CHAR(100), CATEGORY CHAR(100), SUBCATEGORY CHAR(100), LON DOUBLE, LAT DOUBLE, SRID  INTEGER, WKT CHAR(100), INTERNATIONAL_NAME CHAR(100), STREET CHAR(100), WIKIPEDIA CHAR(100), PHONE CHAR(100), CITY CHAR(100), EMAIL CHAR(100), ALTERNATIVE_NAME CHAR(100), OPENING_HOURS CHAR(100), DESCRIPTION  CHAR(100), WEBSITE CHAR(100), LAST_UPDATE CHAR(100), OPERATOR CHAR(100), POSTCODE CHAR(100), COUNTRY  CHAR(100), FAX CHAR(100), IMAGE CHAR(100), HOUSENUMBER CHAR(100),  OTHER_TAGS  CHAR(100) )");
        System.out.println("table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DB_Name+"."+TABLE_Name);
        onCreate(sqLiteDatabase);

    }

    public void insertData(int ID, String Name , String Category, String Subcategory, Double Lon, Double Lat, int  SRID, String wkt, String InternationalLName, String Street, String Wikipedia, String Phone, String City, String Email, String ALTERNATIVE_NAME, String Opening_hours, String DESCRIPTION , String Website, String LastUpdate, String Operator, String Postcode, String Country, String Fax, String Image, String HouseNumber, String  OtherTags) {
        SQLiteDatabase db = this.getWritableDatabase() ;
        ContentValues conval = new ContentValues();

//        int ID, String Name , String Category, String Subcategory, Double Lon, Double Lat, int  SRID, String wkt, String InternationalLName, String Street, String Wikipedia, String Phone, String City, String Email, String ALTERNATIVE_NAME, String Opening_hours, String DESCRIPTION , String Website, String LastUpdate, String Operator, String Postcode, String Country, String Fax, String Image, String HouseNumber, String  OtherTags
//        ID = 26859928;
//        Name = 26859928,"Arion","ACCOMMODATION","CAMPING",22.5894381,40.0158316,4326,"POINT (22.589438100000002 40.015831600000006)","","","","+30 2352 041500","","","","","","httpwww.camping-arion.gr","","","","","","","","";
//        Category = 26859928,"Arion","ACCOMMODATION","CAMPING",22.5894381,40.0158316,4326,"POINT (22.589438100000002 40.015831600000006)","","","","+30 2352 041500","","","","","","httpwww.camping-arion.gr","","","","","","","","";
//        Subcategory = 26859928,"Arion","ACCOMMODATION","CAMPING",22.5894381,40.0158316,4326,"POINT (22.589438100000002 40.015831600000006)","","","","+30 2352 041500","","","","","","httpwww.camping-arion.gr","","","","","","","","";
//        Lon = 26859928,"Arion","ACCOMMODATION","CAMPING",22.5894381,40.0158316,4326,"POINT (22.589438100000002 40.015831600000006)","","","","+30 2352 041500","","","","","","httpwww.camping-arion.gr","","","","","","","","";
//        Lat = 26859928,"Arion","ACCOMMODATION","CAMPING",22.5894381,40.0158316,4326,"POINT (22.589438100000002 40.015831600000006)","","","","+30 2352 041500","","","","","","httpwww.camping-arion.gr","","","","","","","","";
//        SRID = 26859928,"Arion","ACCOMMODATION","CAMPING",22.5894381,40.0158316,4326,"POINT (22.589438100000002 40.015831600000006)","","","","+30 2352 041500","","","","","","httpwww.camping-arion.gr","","","","","","","","";
//        wkt = 26859928,"Arion","ACCOMMODATION","CAMPING",22.5894381,40.0158316,4326,"POINT (22.589438100000002 40.015831600000006)","","","","+30 2352 041500","","","","","","httpwww.camping-arion.gr","","","","","","","","";
//        InternationalLName = 26859928,"Arion","ACCOMMODATION","CAMPING",22.5894381,40.0158316,4326,"POINT (22.589438100000002 40.015831600000006)","","","","+30 2352 041500","","","","","","httpwww.camping-arion.gr","","","","","","","","";
//        Street = 26859928,"Arion","ACCOMMODATION","CAMPING",22.5894381,40.0158316,4326,"POINT (22.589438100000002 40.015831600000006)","","","","+30 2352 041500","","","","","","httpwww.camping-arion.gr","","","","","","","","";
//        Wikipedia = 26859928,"Arion","ACCOMMODATION","CAMPING",22.5894381,40.0158316,4326,"POINT (22.589438100000002 40.015831600000006)","","","","+30 2352 041500","","","","","","httpwww.camping-arion.gr","","","","","","","","";
//        Phone = 26859928,"Arion","ACCOMMODATION","CAMPING",22.5894381,40.0158316,4326,"POINT (22.589438100000002 40.015831600000006)","","","","+30 2352 041500","","","","","","httpwww.camping-arion.gr","","","","","","","","";
//        City = 26859928,"Arion","ACCOMMODATION","CAMPING",22.5894381,40.0158316,4326,"POINT (22.589438100000002 40.015831600000006)","","","","+30 2352 041500","","","","","","httpwww.camping-arion.gr","","","","","","","","";
//        Email = 26859928,"Arion","ACCOMMODATION","CAMPING",22.5894381,40.0158316,4326,"POINT (22.589438100000002 40.015831600000006)","","","","+30 2352 041500","","","","","","httpwww.camping-arion.gr","","","","","","","","";
//        ALTERNATIVE_NAME = 26859928,"Arion","ACCOMMODATION","CAMPING",22.5894381,40.0158316,4326,"POINT (22.589438100000002 40.015831600000006)","","","","+30 2352 041500","","","","","","httpwww.camping-arion.gr","","","","","","","","";
//        Opening_hours = 26859928,"Arion","ACCOMMODATION","CAMPING",22.5894381,40.0158316,4326,"POINT (22.589438100000002 40.015831600000006)","","","","+30 2352 041500","","","","","","httpwww.camping-arion.gr","","","","","","","","";
//        DESCRIPTION = 26859928,"Arion","ACCOMMODATION","CAMPING",22.5894381,40.0158316,4326,"POINT (22.589438100000002 40.015831600000006)","","","","+30 2352 041500","","","","","","httpwww.camping-arion.gr","","","","","","","","";
//        Website = 26859928,"Arion","ACCOMMODATION","CAMPING",22.5894381,40.0158316,4326,"POINT (22.589438100000002 40.015831600000006)","","","","+30 2352 041500","","","","","","httpwww.camping-arion.gr","","","","","","","","";
//        LastUpdate = 26859928,"Arion","ACCOMMODATION","CAMPING",22.5894381,40.0158316,4326,"POINT (22.589438100000002 40.015831600000006)","","","","+30 2352 041500","","","","","","httpwww.camping-arion.gr","","","","","","","","";
//        Operator = 26859928,"Arion","ACCOMMODATION","CAMPING",22.5894381,40.0158316,4326,"POINT (22.589438100000002 40.015831600000006)","","","","+30 2352 041500","","","","","","httpwww.camping-arion.gr","","","","","","","","";
//        Postcode = 26859928,"Arion","ACCOMMODATION","CAMPING",22.5894381,40.0158316,4326,"POINT (22.589438100000002 40.015831600000006)","","","","+30 2352 041500","","","","","","httpwww.camping-arion.gr","","","","","","","","";
//        Country = 26859928,"Arion","ACCOMMODATION","CAMPING",22.5894381,40.0158316,4326,"POINT (22.589438100000002 40.015831600000006)","","","","+30 2352 041500","","","","","","httpwww.camping-arion.gr","","","","","","","","";
//        Fax = 26859928,"Arion","ACCOMMODATION","CAMPING",22.5894381,40.0158316,4326,"POINT (22.589438100000002 40.015831600000006)","","","","+30 2352 041500","","","","","","httpwww.camping-arion.gr","","","","","","","","";
//        Image = 26859928,"Arion","ACCOMMODATION","CAMPING",22.5894381,40.0158316,4326,"POINT (22.589438100000002 40.015831600000006)","","","","+30 2352 041500","","","","","","httpwww.camping-arion.gr","","","","","","","","";
//        HouseNumber = 26859928,"Arion","ACCOMMODATION","CAMPING",22.5894381,40.0158316,4326,"POINT (22.589438100000002 40.015831600000006)","","","","+30 2352 041500","","","","","","httpwww.camping-arion.gr","","","","","","","","";
//        OtherTags = 26859928,'Arion','ACCOMMODATION','CAMPING',22.5894381,40.0158316,4326,'POINT (22.589438100000002 40.015831600000006)','','','','+30 2352 041500','','','','','','httpwww.camping-arion.gr','','','','','','','','';

//        System.out.println("Inside insert");
//        db = openOrCreateDatabase(DB_Name,null);
//        db.execSQL("INSERT INTO (ID, NAME, CATEGORY, SUBCATEGORY, LON, LAT, SRID, WKT, INTERNATIONAL_NAME, STREET , WIKIPEDIA, PHONE, CITY, EMAIL, ALTERNATIVE_NAME, OPENING_HOURS, DESCRIPTION, WEBSITE, LAST_UPDATE, OPERATOR, POSTCODE, COUNTRY, FAX, IMAGE, HOUSENUMBER,  OTHER_TAGS ) VALUES ()");
//        db.execSQL("INSERT INTO "+DB_Name+"."+TABLE_Name+"(ID, NAME, CATEGORY, SUBCATEGORY, LON, LAT, SRID, WKT, INTERNATIONAL_NAME, STREET , WIKIPEDIA, PHONE, CITY, EMAIL, ALTERNATIVE_NAME, OPENING_HOURS, DESCRIPTION, WEBSITE, LAST_UPDATE, OPERATOR, POSTCODE, COUNTRY, FAX, IMAGE, HOUSENUMBER,  OTHER_TAGS ) VALUES (26859928,'Arion','ACCOMMODATION','CAMPING',22.5894381,40.0158316,4326,'POINT (22.589438100000002 40.015831600000006)','','','','+30 2352 041500','','','','','','httpwww.camping-arion.gr','','','','','','','','')");
//        System.out.println("after sql insert");

//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues conval = new ContentValues();
        conval.put(COL_1,ID);
        conval.put(COL_2,Name);
        conval.put(COL_3,Category);
        conval.put(COL_4,Subcategory);
        conval.put(COL_5,Lon);
        conval.put(COL_6,Lat);
        conval.put(COL_7,SRID);
        conval.put(COL_8,wkt);
        conval.put(COL_9,InternationalLName);
        conval.put(COL_10,Street);
        conval.put(COL_11,Wikipedia);
        conval.put(COL_12,Phone);
        conval.put(COL_13,City);
        conval.put(COL_14,Email);
        conval.put(COL_15,ALTERNATIVE_NAME);
        conval.put(COL_16,Opening_hours);
        conval.put(COL_17,DESCRIPTION);
        conval.put(COL_18,Website);
        conval.put(COL_19,LastUpdate);
        conval.put(COL_20,Operator);
        conval.put(COL_21,Postcode);
        conval.put(COL_22,Country);
        conval.put(COL_23,Fax);
        conval.put(COL_24,Image);
        conval.put(COL_25,HouseNumber);
        conval.put(COL_26,OtherTags);

//        //checks for error in case of -1 there is an error
        long result = db.insert(TABLE_Name, null, conval);
        if(result == -1)
            Toast.makeText(context,"Failed", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context,"Success", Toast.LENGTH_SHORT).show();

    }
}
