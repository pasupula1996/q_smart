package qualtech_q_smart_home_automation_2018.q_smart.SQLITE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import qualtech_q_smart_home_automation_2018.q_smart.CustomMenu.Security.SecurityPojo;
import qualtech_q_smart_home_automation_2018.q_smart.CustomMenu.Utilities.UtilModel;
import qualtech_q_smart_home_automation_2018.q_smart.model.ServiceModel;

public class LDbHelper extends SQLiteOpenHelper {
    private SQLiteDatabase mSQLiteDatabase;
    private ContentValues values;

    public LDbHelper(Context context) {
        super(context, DbConstants.DB_NAME, null, DbConstants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbConstants.CREATE_TB);
        db.execSQL(DbConstants.CREATE_E_TB);
        db.execSQL(DbConstants.CREATE_U_TB);
        db.execSQL(DbConstants.CREATE_S_TB);
        db.execSQL(DbConstants.CREATE_SECURITY_TB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DbConstants.DROP_TB);
        db.execSQL(DbConstants.DROP_E_TABLE);
        db.execSQL(DbConstants.DROP_U_TB);
        db.execSQL(DbConstants.DROP_S_TABLE);
        db.execSQL(DbConstants.DROP_SECURITY_TABLE);
        onCreate(db);
    }
    public void insertData(String title, int thumbnail,boolean click){
        mSQLiteDatabase = this.getWritableDatabase();
        values = new ContentValues();
        values.put(DbConstants.ROW_TITLE,title);
        values.put(DbConstants.ROW_THUMBNAIL,thumbnail);
        values.put(DbConstants.ROW_CLICKED,String.valueOf(click));
        mSQLiteDatabase.insert(DbConstants.TB_NAME, DbConstants.ROW_ID,values);
        closeDb();
    }
    public void insertEntertainmentTitle(String title, int thumbnail,String iconName, boolean click){
        mSQLiteDatabase = this.getWritableDatabase();
        values = new ContentValues();
        values.put(DbConstants.E_ROW_TITLE,title);
        values.put(DbConstants.E_ROW_THUMBNAIL,thumbnail);
        values.put(DbConstants.E_ROW_THUMBNAIL_NAME,iconName);
        values.put(DbConstants.E_CLICKED,String.valueOf(click));
        mSQLiteDatabase.insert(DbConstants.ENTERTAINMENT_TB_NAME, DbConstants.E_ROW_ID,values);
        closeDb();
    }
    public void insertUtilitiesTitle(String title, int thumbnail, boolean click){
        mSQLiteDatabase = this.getWritableDatabase();
        values = new ContentValues();
        values.put(DbConstants.U_ROW_TITLE,title);
        values.put(DbConstants.U_ROW_THUMBNAIL,thumbnail);
        values.put(DbConstants.U_CLICKED,String.valueOf(click));
        mSQLiteDatabase.insert(DbConstants.UTILITIES_TB_NAME, DbConstants.U_ROW_ID,values);
        closeDb();
    }
    public void insertServicesTitle(String title, int thumbnail,boolean click){
        mSQLiteDatabase = this.getWritableDatabase();
        values = new ContentValues();
        values.put(DbConstants.S_ROW_TITLE,title);
        values.put(DbConstants.S_ROW_THUMBNAIL,thumbnail);
        values.put(DbConstants.S_CLICKED, String.valueOf(click));
        mSQLiteDatabase.insert(DbConstants.SERVICES_TB_NAME, DbConstants.S_ROW_ID,values);
        closeDb();
    }
    public void insertSecurityTitle(String title, int thumbnail, boolean click){
        mSQLiteDatabase = this.getWritableDatabase();
        values = new ContentValues();
        values.put(DbConstants.SECURITY_TITLE,title);
        values.put(DbConstants.SECURITY_THUMBNAIL,thumbnail);
        values.put(DbConstants.SECURITY_CLICKED,click);
        long id = mSQLiteDatabase.insert(DbConstants.SECURITY_TB_NAME,DbConstants.SECURITY_ID,values);
        Log.d("ID",String.valueOf(id));
        closeDb();
    }
    public ArrayList<LightPojo> getLightData(){

        ArrayList<LightPojo> data = new ArrayList<>();
        mSQLiteDatabase = this.getReadableDatabase();
        Cursor cursor = mSQLiteDatabase.rawQuery("select * from " + DbConstants.TB_NAME + ";",null);
        //StringBuffer stringBuffer = new StringBuffer();
        LightPojo pojo;
        if (cursor.moveToFirst()){
            do {

                String title = cursor.getString(cursor.getColumnIndexOrThrow("TITLE"));
                int thumbnail = cursor.getInt(cursor.getColumnIndexOrThrow("THUMBNAIL"));
                String isClicked = cursor.getString(cursor.getColumnIndexOrThrow("CLICKED"));
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                boolean clicked = false;
                if (isClicked.equals("true")){
                    clicked = true;
                }
                pojo = new LightPojo(id,title,thumbnail,clicked);
                //pojo.setName(title);
                //pojo.setThumbnail(thumbnail);
                //stringBuffer.append(pojo);

                data.add(pojo);
            }while (cursor.moveToNext());
        }

        for (LightPojo i: data){

            Log.d("lightData", String.valueOf(i.isClicked()));
        }
        cursor.close();
        closeDb();

        return data;
    }
    public ArrayList<LightPojo> getEntertainmentData(){
        ArrayList<LightPojo> data = new ArrayList<>();
        mSQLiteDatabase = this.getReadableDatabase();
        Cursor cursor = mSQLiteDatabase.rawQuery("select * from " + DbConstants.ENTERTAINMENT_TB_NAME + ";", null);
        LightPojo pojo;
        if (cursor.moveToFirst()){
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow("ETITLE"));
                int thumbnail = cursor.getInt(cursor.getColumnIndexOrThrow("ETHUMBNAIL"));
                String iconName = cursor.getString(cursor.getColumnIndexOrThrow("ICONNAME"));
                String isClicked = cursor.getString(cursor.getColumnIndexOrThrow("ECLICKED"));
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("EID"));
                boolean clicked = false;
                if (isClicked.equals("true")){
                    clicked = true;
                }

                pojo = new LightPojo(id,title,thumbnail,iconName,clicked);
                data.add(pojo);
            }while (cursor.moveToNext());
        }
        cursor.close();
        closeDb();
        return data;
    }
    public ArrayList<LightPojo> getUtilitiesData(){
        ArrayList<LightPojo> data = new ArrayList<>();
        mSQLiteDatabase = this.getReadableDatabase();
        Cursor cursor = mSQLiteDatabase.rawQuery("select * from " + DbConstants.UTILITIES_TB_NAME + ";",null);
        LightPojo pojo;
        if (cursor.moveToFirst()){
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow("UTITLE"));
                int thumbnail = cursor.getInt(cursor.getColumnIndexOrThrow("UTHUMBNAIL"));
                String isClicked = cursor.getString(cursor.getColumnIndexOrThrow("UCLICKED"));
                int id  = cursor.getInt(cursor.getColumnIndexOrThrow("UID"));
                boolean clicked = false;
                if (isClicked.equals("true")){
                    clicked = true;
                }
                pojo = new LightPojo(id,title,thumbnail,clicked);
                data.add(pojo);
            }while (cursor.moveToNext());
        }
        cursor.close();
        closeDb();
        return data;
    }
    public ArrayList<ServiceModel> getServicesData(){
        ArrayList<ServiceModel> data = new ArrayList<>();
        mSQLiteDatabase = this.getReadableDatabase();
        Cursor cursor = mSQLiteDatabase.rawQuery("select * from " + DbConstants.SERVICES_TB_NAME + ";",null);
        ServiceModel pojo;
        if (cursor.moveToFirst()){
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow("STITLE"));
                int thumbnail = cursor.getInt(cursor.getColumnIndexOrThrow("STHUMBNAIL"));
                String isclicked = cursor.getString(cursor.getColumnIndexOrThrow("SCLICKED"));
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("SID"));
                boolean clicked = false;
                if (isclicked.equals("true")){
                    clicked = true;
                }
                pojo = new ServiceModel(id,title,thumbnail,clicked);
                data.add(pojo);
            }while (cursor.moveToNext());

        }
        cursor.close();
        closeDb();
        return data;
    }
    public ArrayList<SecurityPojo> getSecurityData(){
        ArrayList<SecurityPojo> data = new ArrayList<>();
        mSQLiteDatabase = this.getReadableDatabase();
        //insertSecurityTitle("bhanu", R.drawable.ic_linked_camera_black,false);
        Cursor cursor = mSQLiteDatabase.rawQuery("select * from " + DbConstants.SECURITY_TB_NAME + ";",null);
        SecurityPojo pojo;
        if (cursor.moveToFirst()){
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow("C_TITLE"));
                int thumbnail = cursor.getInt(cursor.getColumnIndexOrThrow("C_THUMBNAIL"));
                String isclicked = cursor.getString(cursor.getColumnIndexOrThrow("C_CLICKED"));
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("C_ID"));
                boolean click = false;
                if (isclicked.equals("true")){
                    click = true;
                }
                pojo = new SecurityPojo(id,title,thumbnail,click);
                data.add(pojo);
            }while (cursor.moveToNext());
        }
        cursor.close();
        closeDb();
        return data;
    }



    public void upDateState(LightPojo bundle){
        openDb();
        ContentValues values = new ContentValues();
        values.put(DbConstants.ROW_THUMBNAIL,bundle.getThumbnail());
        values.put(DbConstants.ROW_CLICKED,String.valueOf(bundle.isClicked()));
        mSQLiteDatabase.update(DbConstants.TB_NAME,values, DbConstants.ROW_ID + " =?",new String[]{String.valueOf(bundle.getId())});
        closeDb();
    }
    public void updateEntertainmentState(LightPojo pojo){
        openDb();
        ContentValues values = new ContentValues();
        values.put(DbConstants.E_ROW_THUMBNAIL,pojo.getThumbnail());
        values.put(DbConstants.E_CLICKED,String.valueOf(pojo.isClicked()));
        //values.put(DbConstants.E_ROW_THUMBNAIL_NAME);
        mSQLiteDatabase.update(DbConstants.ENTERTAINMENT_TB_NAME,values, DbConstants.E_ROW_ID+ "=?",new String[] {String.valueOf(pojo.getId())});
        closeDb();
    }
    public void updateUtilitiesState(UtilModel bundle){
        openDb();
        ContentValues values = new ContentValues();
        values.put(DbConstants.U_ROW_THUMBNAIL,bundle.getThumbnail());
        values.put(DbConstants.U_CLICKED,String.valueOf(bundle.isClicked()));
        mSQLiteDatabase.update(DbConstants.UTILITIES_TB_NAME,values, DbConstants.U_ROW_ID + "=?",new String[]{String.valueOf(bundle.getId())});
        closeDb();
    }
    public void updateServicesState(ServiceModel bundle){
        mSQLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbConstants.S_ROW_THUMBNAIL,bundle.getThumbnail());
        values.put(DbConstants.S_CLICKED,String.valueOf(bundle.isClicked()));
        mSQLiteDatabase.update(DbConstants.SERVICES_TB_NAME,values, DbConstants.S_ROW_ID + "=?", new String[]{String.valueOf(bundle.getId())});
        closeDb();
    }
    public void updateSecurityState(SecurityPojo pojo){
        mSQLiteDatabase = this.getWritableDatabase();
        values = new ContentValues();
        values.put(DbConstants.SECURITY_THUMBNAIL,pojo.getThumbnail());
        values.put(DbConstants.SECURITY_CLICKED,String.valueOf(pojo.isClicked));
        mSQLiteDatabase.update(DbConstants.SECURITY_TB_NAME,values,DbConstants.SECURITY_ID + "=?", new String[]{String.valueOf(pojo.getId())});
        closeDb();
    }



    public void editName(LightPojo bundle){
        mSQLiteDatabase = this.getWritableDatabase();
        values = new ContentValues();
        values.put(DbConstants.ROW_TITLE,bundle.getName());
        mSQLiteDatabase.update(DbConstants.TB_NAME,values, DbConstants.ROW_ID + " =?", new String[]{String.valueOf(bundle.getId())});
        closeDb();
    }
    public void editEntertainmentName(LightPojo bundle){
        mSQLiteDatabase = this.getWritableDatabase();
        values = new ContentValues();
        values.put(DbConstants.E_ROW_TITLE,bundle.getName());
        mSQLiteDatabase.update(DbConstants.ENTERTAINMENT_TB_NAME,values, DbConstants.E_ROW_ID + "=?",new String[]{String.valueOf(bundle.getId())});
        closeDb();
    }
    public void editUtilitiesName(UtilModel bundle){
        mSQLiteDatabase = this.getWritableDatabase();
        values = new ContentValues();
        values.put(DbConstants.U_ROW_TITLE,bundle.getName());
        mSQLiteDatabase.update(DbConstants.UTILITIES_TB_NAME,values, DbConstants.U_ROW_ID + "=?",new String[]{String.valueOf(bundle.getId())});
        closeDb();
    }
    public void editServicesName(ServiceModel bundle){
        mSQLiteDatabase = this.getWritableDatabase();
        values = new ContentValues();
        values.put(DbConstants.S_ROW_TITLE,bundle.getName());
        mSQLiteDatabase.update(DbConstants.SERVICES_TB_NAME,values, DbConstants.S_ROW_ID + "=?", new String[]{String.valueOf(bundle.getId())});
        closeDb();
    }
    public void editSecurityName(SecurityPojo pojo){
        mSQLiteDatabase = this.getWritableDatabase();
        values= new ContentValues();
        values.put(DbConstants.SECURITY_TITLE,pojo.getTitle());
        mSQLiteDatabase.update(DbConstants.SECURITY_TB_NAME,values,DbConstants.SECURITY_ID + "=?",new String[]{String.valueOf(pojo.getId())});
        closeDb();
    }



    public void delete(LightPojo pojo){
        mSQLiteDatabase = this.getWritableDatabase();
        mSQLiteDatabase.delete(DbConstants.TB_NAME, DbConstants.ROW_ID +  " =?", new String[]{String.valueOf(pojo.getId())});
        closeDb();
    }
    public void deleteEntertainment(LightPojo pojo){
        mSQLiteDatabase = this.getWritableDatabase();
        mSQLiteDatabase.delete(DbConstants.ENTERTAINMENT_TB_NAME, DbConstants.E_ROW_ID + "=?",new String[]{String.valueOf(pojo.getId())});
        closeDb();
    }
    public void deleteUtility(UtilModel pojo){
        mSQLiteDatabase = this.getWritableDatabase();
        mSQLiteDatabase.delete(DbConstants.UTILITIES_TB_NAME, DbConstants.U_ROW_ID + "=?", new String[]{String.valueOf(pojo.getId())});
        closeDb();
    }
    public void deleteService(ServiceModel pojo){
        mSQLiteDatabase = this.getWritableDatabase();
        mSQLiteDatabase.delete(DbConstants.SERVICES_TB_NAME, DbConstants.S_ROW_ID + "=?",new String[]{String.valueOf(pojo.getId())});
        closeDb();
    }public void deleteSecurity(SecurityPojo pojo){
        openDb();
        mSQLiteDatabase.delete(DbConstants.SECURITY_TB_NAME,DbConstants.SECURITY_ID + "=?",new String[]{String.valueOf(pojo.getId())});
        closeDb();
    }




    private void openDb(){
        mSQLiteDatabase = this.getWritableDatabase();
    }
    private void closeDb(){
        mSQLiteDatabase.close();
    }

}
