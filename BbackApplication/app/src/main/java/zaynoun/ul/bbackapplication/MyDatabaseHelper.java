package zaynoun.ul.bbackapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "BBackApp.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME="my_bback_upload";
    private static final String COLUMN_NAME="NAME FOOD";
    private static final String COLUMN_PRICE="_price";

    private static final String COLUMN_RATE="_rate";
    private static final String COLUMN_DESCRIPTION="item_description";
    private static final String COLUMN_IMAGE="item_image";
    private static final String COLUMN_TIMING="item_timing";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query=
                "CREATE TABLE " + TABLE_NAME +
                "(" + COLUMN_NAME + " TEXT, " +COLUMN_TIMING +" TEXT, " +
                COLUMN_PRICE + " REAL, " +
                COLUMN_RATE + " INTEGER, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_IMAGE + " TEXT);";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
      onCreate(db);
    }
    void add_item(String name,int price,String description,int rating,String timing,String image){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_TIMING, timing);
        cv.put(COLUMN_PRICE, price);
        cv.put(COLUMN_RATE, rating);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_IMAGE, image);
        long result=db.insert(TABLE_NAME, null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Data inserted", Toast.LENGTH_SHORT).show();
        }
    }
}
