package com.prm392.assignment.util;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.prm392.assignment.Cart;
import com.prm392.assignment.model.CartModel;
import com.prm392.assignment.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class CartDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cart.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CART = "cart";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_PRODUCT_NAME = "product_name";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_QUANTITY = "quantity";

    public CartDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_CART + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PRODUCT_NAME + " TEXT, " +
                COLUMN_IMAGE + " TEXT, " +
                COLUMN_PRICE + " REAL, " +
                COLUMN_QUANTITY + " INTEGER)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }

    public void logAllProductsInCart() {
        SQLiteDatabase db = getReadableDatabase();

        // Retrieve all rows from the cart table
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CART, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String productName = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME));
                @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE));
                @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));
                @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY));

                Log.d("Product", productName + " " + image + " " + price + " " + quantity);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }

    public void addProductToCart(String productName, String image, double price, int quantity) {
        SQLiteDatabase db = getWritableDatabase();
        // Check if the product already exists in the cart
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CART + " WHERE " + COLUMN_PRODUCT_NAME + " = ?", new String[]{productName});
        Log.d("Cursor", String.valueOf(cursor.getCount()));
        if (cursor.moveToFirst()) {
            // Product already exists, update the quantity
            @SuppressLint("Range") int currentQuantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY));
            int newQuantity = currentQuantity + quantity;
            ContentValues values = new ContentValues();
            values.put(COLUMN_QUANTITY, newQuantity);
            db.update(TABLE_CART, values, COLUMN_PRODUCT_NAME + " = ?", new String[]{productName});
        } else {
            // Product doesn't exist, insert a new row
            ContentValues values = new ContentValues();
            values.put(COLUMN_PRODUCT_NAME, productName);
            values.put(COLUMN_IMAGE, image);
            values.put(COLUMN_PRICE, price);
            values.put(COLUMN_QUANTITY, quantity);
            db.insert(TABLE_CART, null, values);
        }
        logAllProductsInCart();
        cursor.close();
        db.close();
    }

    public List<CartModel> getCartProducts() {
        List<CartModel> cartItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CART, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String productName = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME));
                @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE));
                @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));
                @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY));
                CartModel cart = new CartModel(productName, image, price, quantity);
                cartItems.add(cart);
            } while (cursor.moveToNext());
        }
        Log.d("ProductList", cartItems.toString());
        cursor.close();
        db.close();
        return cartItems;
    }

    public int getCartCount() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT SUM(" + COLUMN_QUANTITY + ") FROM " + TABLE_CART;
        Cursor cursor = db.rawQuery(query, null);
        int cartCount = 0;
        if (cursor.moveToFirst()) {
            cartCount = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return cartCount;
    }

    public void removeAllProducts() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_CART, null, null);
        db.close();
    }

    public void removeProduct(String productName) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_CART, COLUMN_PRODUCT_NAME + " = ?", new String[]{productName});
        db.close();
    }

    public void increaseQuantity(String productName) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CART + " WHERE " + COLUMN_PRODUCT_NAME + " = ?", new String[]{productName});
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int currentQuantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY));
            int newQuantity = currentQuantity + 1;
            ContentValues values = new ContentValues();
            values.put(COLUMN_QUANTITY, newQuantity);
            db.update(TABLE_CART, values, COLUMN_PRODUCT_NAME + " = ?", new String[]{productName});
        }
        cursor.close();
        db.close();
    }

    public void decreaseQuantity(String productName) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CART + " WHERE " + COLUMN_PRODUCT_NAME + " = ?", new String[]{productName});
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int currentQuantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY));
            int newQuantity = currentQuantity - 1;
            if (newQuantity == 0) {
                removeProduct(productName);
                return;
            }
            ContentValues values = new ContentValues();
            values.put(COLUMN_QUANTITY, newQuantity);
            db.update(TABLE_CART, values, COLUMN_PRODUCT_NAME + " = ?", new String[]{productName});
        }
        cursor.close();
        db.close();
    }

    public double calculateTotal() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT SUM(" + COLUMN_PRICE + " * " + COLUMN_QUANTITY + ") FROM " + TABLE_CART;
        Cursor cursor = db.rawQuery(query, null);
        double total = 0;
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        cursor.close();
        db.close();
        return total;
    }
}