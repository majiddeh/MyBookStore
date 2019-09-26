package com.example.mybookstore.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.mybookstore.Models.ModelFav;
import com.example.mybookstore.Models.ModelItemProduct;

import org.bouncycastle.jcajce.provider.symmetric.DES;

import java.util.ArrayList;

public class DbSqlite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db_shop";
    private static final String TABLE_NAME = "tbl_favorite";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String IMAGE = "image";
    private static final String VISIT = "visit";
    private static final String PRICE = "price";
    private static final String LABLE = "lable";
    private static final String DESC = "description";
    private static final String OFF_PRICE = "offprice";
    private static final String AUTHOR = "author_name";
    private static final String PUBLISHER = "publisher_name";

    private static final String CREATE_TABLE = "CREATE TABLE " +TABLE_NAME+"("+
            " "+ID+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, "+
            " "+IMAGE+" TEXT NOT NULL,"+
            " "+TITLE+" TEXT NOT NULL,"+
            " "+VISIT+" TEXT NOT NULL,"+
            " "+PRICE+" TEXT NOT NULL,"+
            " "+OFF_PRICE+" TEXT NOT NULL,"+
            " "+LABLE+" TEXT NOT NULL,"+
            " "+ DESC +" TEXT NOT NULL,"+
            " "+AUTHOR+" TEXT NOT NULL,"+
            " "+PUBLISHER+" TEXT NOT NULL"+
            ") ;";

    Context context;

    public DbSqlite(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertFav (ModelFav modelFav){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID,modelFav.getId());
        values.put(IMAGE,modelFav.getImage());
        values.put(TITLE,modelFav.getTitle());
        values.put(VISIT,modelFav.getVisit());
        values.put(PRICE,modelFav.getPrice());
        values.put(LABLE,modelFav.getLable());
        values.put(DESC,modelFav.getDesc());
        values.put(PUBLISHER,modelFav.getPublisher());
        values.put(AUTHOR,modelFav.getAuthor());
        values.put(OFF_PRICE,modelFav.getOffPrice());

        long isInserted = sqLiteDatabase.insert(TABLE_NAME,null,values);
        sqLiteDatabase.close();
        if (isInserted>0){
            return true;
        }else return false;

    }

    public ArrayList<ModelFav> showData(){

        ArrayList<ModelFav> modelFavs = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_NAME ;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if (cursor.moveToFirst()){

            do {
                ModelFav fav = new ModelFav();
                fav.setId(Integer.parseInt(cursor.getString(0)));
                fav.setImage(cursor.getString(1));
                fav.setTitle(cursor.getString(2));
                fav.setVisit(cursor.getString(3));
                fav.setPrice(cursor.getString(4));
                fav.setOffPrice(cursor.getString(5));
                fav.setLable(cursor.getString(6));
                fav.setDesc(cursor.getString(7));
                fav.setAuthor(cursor.getString(8));
                fav.setPublisher(cursor.getString(9));

                modelFavs.add(fav);
            }while (cursor.moveToNext());

        }

        return modelFavs;

    }

    public boolean deleteFav (int id){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int isDeleted =sqLiteDatabase.delete(TABLE_NAME,"id="+id,null);
        sqLiteDatabase.close();
        if (isDeleted == 1){
            return true;
        }else return false;

    }

    public Cursor cu (int id){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE id="+id,null);
        return cursor;
    }


}
