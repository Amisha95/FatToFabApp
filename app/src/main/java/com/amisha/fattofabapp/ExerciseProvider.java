package com.amisha.fattofabapp;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.HashMap;

public class ExerciseProvider extends ContentProvider {

    static final String PROVIDER_NAME="com.example.provider.Exercises";
    static final String URL = "content://" + PROVIDER_NAME + "/exercises";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String NAME = "name";
    static final String DESCRIPTION = "description";

    private static HashMap<String,String> EXERCISES_PROJECTION_MAP;
    static final int EXERCISES=1;
    static final int EXERCISES_ID=2;

    static UriMatcher uriMatcher = null;
    static  {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME,"exercises",EXERCISES);
        uriMatcher.addURI(PROVIDER_NAME,"exercises/#",EXERCISES_ID);
    }

    private SQLiteDatabase db;
    static final String DATABASE_NAME="Exercises";
    static final String EXERCISES_TABLE_NAME = "Exercises";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + EXERCISES_TABLE_NAME +
                    " (name," +
                    " description);";

    private class DatabaseHelper extends SQLiteOpenHelper{
        public DatabaseHelper(Context context) {
            super(context,DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS" + EXERCISES_TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        Context context=getContext();
        DatabaseHelper dbHelper=new DatabaseHelper(context);
        db=dbHelper.getWritableDatabase();
        return (db!=null);
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
        qb.setTables(EXERCISES_TABLE_NAME);
        switch(uriMatcher.match(uri)){
            case EXERCISES: qb.setProjectionMap(EXERCISES_PROJECTION_MAP);
                break;
            case EXERCISES_ID: qb.appendWhere(NAME + "=" +uri.getPathSegments().get(1));
                break;
            default: throw new IllegalArgumentException("Unknown Uri:" +uri);
        }
        if(sortOrder==null || sortOrder==""){
            sortOrder=NAME;
        }
        Cursor c=qb.query(db, projection,selection, selectionArgs,null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){

            case EXERCISES:
                return "vnd.android.cursor.dir/vnd.example.exercises";

            case EXERCISES_ID:
                return "vnd.android.cursor.item/vnd.example.exercises";

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID=db.insert(EXERCISES_TABLE_NAME,"",values);
        if(rowID>0){
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI,rowID);
            getContext().getContentResolver().notifyChange(_uri,null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into:" +uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)){
            case EXERCISES:
                count = db.delete(EXERCISES_TABLE_NAME, selection, selectionArgs);
                break;

            case EXERCISES_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete( EXERCISES_TABLE_NAME, NAME +  " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)){
            case EXERCISES:
                count = db.update(EXERCISES_TABLE_NAME, values, selection, selectionArgs);
                break;

            case EXERCISES_ID:
                count = db.update(EXERCISES_TABLE_NAME, values, NAME + " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
    public void clearTable()
    {
        db.execSQL("DELETE FROM Exercises");
    }
}
