package pt.ubi.di.pdm.projetopdm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.core.database.CursorWindowCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class UserLocalDB extends SQLiteOpenHelper {

    public UserLocalDB(Context context){
        super(context, "Login2.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table user (username text primary key, password text);");
        db.execSQL("Create table info (username text, website text, login text, password text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists info");
        onCreate(db);
    }
    //insert in database
    public boolean insert(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password", password);
        long ins = db.insert("user","null", contentValues);
        if(ins==-1) return false;
        else return true;
    }

    // insert in database user info for certain website
    public boolean insertInfo(String username, String website, String login, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("website", website);
        contentValues.put("login", login);
        contentValues.put("password", password);
        long ins = db.insert("info", "null", contentValues);
        if (ins==-1) return false;
        else return true;
    }

    // check if user already as info on the website
    public Boolean chkInfo(String website, String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from info where  username=? and website=?", new String[]{username, website});
        if (cursor.getCount() > 0) return false;
        else return true;
    }

    //checking if user exists
    public Boolean chkusername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username=?", new String[]{username});
        if (cursor.getCount() > 0) return false;
        else return true;
    }

    // delete login chosen by user
    public boolean deleteInfo(String username, String Website){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("info","website=? and username=?", new String[]{Website, username}) >0 ;
    }

    // update user info for certain website
    public void updateByWebsite(String website, String user, String login, String password){
        ContentValues contentValues = new ContentValues();
        contentValues.put("login", login);
        contentValues.put("password", password);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update("info", contentValues,"website=? and username=?", new String[]{website, user});
    }




    public List<String> getData(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM info WHERE username=?", new String[]{username});

        List<String> attrStr = new Vector<String>();

        if(cursor.moveToFirst()){
            do{
                attrStr.add(cursor.getString(cursor.getColumnIndex("website")));
                Arrays.toString(new List[]{attrStr});
            }while(cursor.moveToNext());
        }
        return attrStr;
    }

    // checking the username and password;
    public Boolean userpass(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username=? and password=?", new String[]{username,password});
        if(cursor.getCount()>0 ) return true;
        else return false;
    }


}
