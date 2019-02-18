package norbury.englishv101;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;




public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "sozlukDB.db";
    public static final String TABLE_NAME = "kelime_table";
    public static final String COL1 = "ID";
    public static final String COL2 = "ENG";
    public static final String COL3 = "TR";
    public static final String COL4 = "PRO";
    public static final String COL5 = "COM";
    public static final String COL6 = "TRA";
    public static final String COL7 = "TTA";
    public static final String COL8 = "PER";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " ENG TEXT UNIQUE COLLATE NOCASE, TR TEXT, PRO TEXT,COM TEXT,TRA INTEGER,TTA INTEGER,PER INTEGER)";
        db.execSQL(createTable);
    }
// Db oluşturma
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String Eng, String Tr, String Pro,String Com){
        int sifir=0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,Eng);
        contentValues.put(COL3,Tr);
        contentValues.put(COL4,Pro);
        contentValues.put(COL5,Com);
        contentValues.put(COL6,sifir);
        contentValues.put(COL7,sifir);
        contentValues.put(COL8,sifir);
        long result  = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor showData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }
    public Cursor ShowDataPercent(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME+" ORDER BY PER", null);
        return data;
    }

    public boolean updateData(String id,String Eng, String Tr,String Pro, String Com){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL1,id);
        contentValues.put(COL2,Eng);
        contentValues.put(COL3,Tr);
        contentValues.put(COL4,Pro);
        contentValues.put(COL5,Com);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {id});
        return true;
    }

    public boolean updateDataValues(String id, int tra,int toa){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL1,id);
        contentValues.put(COL6,tra);
        contentValues.put(COL7,toa);
        int percent=(tra/toa)*100;
        contentValues.put(COL8,percent);

        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {id});
        return true;
    }


    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }





}
