package com.example.halthcare;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper{

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String qry1 = "create table Users(username text,email text,password text)";
        sqLiteDatabase.execSQL(qry1);

        String qry2 = "create table Cart(username text,Product text,Price float,otype text)";
        sqLiteDatabase.execSQL(qry2);

        String qry3 = "create table Orderplace(username text,FullName text,Address text, contactNumber text ,pinCode int,date text,time text,price float,otype text)";
        sqLiteDatabase.execSQL(qry3);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void register(String username,String email,String password)
    {
        ContentValues cv = new ContentValues();
        cv.put("username",username);
        cv.put("email",email);
        cv.put("password",password);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("Users",null,cv);
        db.close();
    }
    public int login(String username,String password)
    {
        int result = 0;
        SQLiteDatabase db = getReadableDatabase();
        String[] str = new String[2];
        str[0] = username;
        str[1] = password;
        @SuppressLint("Recycle") Cursor c  =db.rawQuery("select * from Users where username =? and password =?",str);
        if(c.moveToFirst())
        {
            result = 1;
        }

        return result;
    }
    public void addCart(String username,String Product,float Price,String otype)
    {
        ContentValues cv = new ContentValues();
        cv.put("Username",username);
        cv.put("Product",Product);
        cv.put("Price",Price);
        cv.put("otype",otype);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("cart",null,cv);
        db.close();
    }

    public int checkCartItem(String Username,String Product)
    {
        int result = 0;
        String[] str = new String[2];
        str[0] = Username;
        str[1] = Product;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from cart where Username = ? and Product = ?",str);
        if(c.moveToFirst())
        {
            result = 1;
        }
        db.close();
        return result;
    }

    public void RemoveCartItem(String Username,String otype)
    {
        String[] str = new String[2];
        str[0] = Username;
        str[1] = otype;
        SQLiteDatabase db = getWritableDatabase();
        db.delete("cart","Username=? and otype = ?",str);
        db.close();
    }
    public ArrayList getCartData(String username,String otype) {
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String str[] = new String[2];
        str[0] = username;
        str[1] = otype;
        Cursor c = db.rawQuery("select * from cart where username = ? and otype = ?", str);
        if (c.moveToFirst())
        {
            do {
                String product = c.getString(1);
                String price = c.getString(2);
                arr.add(product+"$"+price);
            }while(c.moveToNext());
        }
        db.close();
        return arr;
    }

    public void AddOrder(String username, String fullName,String address, String contactNumber, int pinCode,
                         String date, String time, float price, String otype) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("fullName", fullName);
        contentValues.put("Address", address);
        contentValues.put("contactNumber", contactNumber);
        contentValues.put("pinCode", pinCode);
        contentValues.put("date", date);
        contentValues.put("time", time);
        contentValues.put("price", price);
        contentValues.put("otype", otype);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("Orderplace", null, contentValues);
        db.close();
    }
    public ArrayList getOrderData(String username)
    {
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String str[]= new String[1];
        str[0] = username;
        Cursor c = db.rawQuery("select * from Orderplace where username = ?", str);
        if(c.moveToFirst()){
            do{
                arr.add(c.getString(1) + "$" + c.getString(2) + "$" + c.getString(3) + "$" + c.getString(4) + "$" + c.getString(5) + "$" + c.getString(6) + "$" + c.getString(7) + "$" + c.getString(8));
            }while (c.moveToNext());
        }
        db.close();
        return arr;
    }
    /*public int CheckAppointmentExists(String Username,String FullName,String Address,String Contact,String Date,String Time)
    {
        int result=0;
        String str[] = new String[6];

        str[0]= Username;
        str[1]= FullName;
        str[4]= Address;
        str[3]= Contact;
        str[4] = Date;
        str[5] = Time;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery()(sql:"select * from orderplace where username = ? and fullname = ? and address = ?"));

        if(c.moveToFirst()){
            result=1;
        }
        db.close();
        return result;

    }*/
    public int CheckAppointmentExists(String username, String fullName, String address, String contact, String date, String time) {
        int result = 0;
        String str[] = new String[6];

        str[0]= username;
        str[1]= fullName;
        str[4]= address;
        str[3]= contact;
        str[4] = date;
        str[5] = time;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM orderplace WHERE username = ? AND fullname = ? AND address = ?", new String[]{username, fullName, address});

        if (c.moveToFirst()) {
            result = 1;
        }

        c.close();
        db.close();
        return result;
    }

}
