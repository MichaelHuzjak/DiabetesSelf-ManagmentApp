package team5.diabetesself_managmentapp;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // region Variables
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "database.db";

    Context context;

    // Category Variables
    public static final String TABLE_CATEGORIES = "categories";
    public static final String CATEGORY_ID = "cat_id";
    public static final String CATEGORY_NAME = "cat_name";

    // Prescription Variables
    public static final String TABLE_PRESCRIPTION = "prescriptions";
    public static final String PRE_ID = "pre_id";
    public static final String PRE_DESC = "pre_desc";
    public static final String PRE_REPEAT = "pre_repeat";
    public static final String PRE_CAT = "pre_cat";

    // BGL Variables
    public static final String TABLE_BGL = "bgl_value";
    public static final String BGL_ID = "bgl_id";
    public static final String BGL_DateTime = "bgl_datetime";
    public static final String BGL_Value = "bgl_value";

    // Medication Variables
    public static final String TABLE_MED = "medications";
    public static final String MED_ID = "med_id";
    public static final String MED_DESC = "med_desc";
    public static final String MED_AMOUNT = "med_amount";
    public static final String MED_DATETIME = "med_datetime";
    public static final String MED_PRES = "med_pres";

    // Diet Variables
    public static final String TABLE_DIET = "diets";
    public static final String DIET_ID = "diet_id";
    public static final String DIET_DESC = "diet_desc";
    public static final String DIET_AMOUNT = "diet_amount";
    public static final String DIET_DATETIME = "diet_datetime";
    public static final String DIET_PRES = "diet_pres";

    // Exercise
    public static final String TABLE_EXERCISE = "exercise";
    public static final String EXER_ID = "exer_id";
    public static final String EXER_DESC = "exer_desc";
    public static final String EXER_DATETIME = "exer_datetime";
    public static final String EXER_DURATION = "exer_duration";
    public static final String EXER_PRES = "exer_pres";

    // endregion

    private boolean createCategoryDBOnce = false;

    // region Interface Methods

    public DatabaseHelper(Context context, String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,DATABASE_NAME,factory,DATABASE_VERSION);
        System.out.println("DatabaseHelper CONSTRUCTOR");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("DatabaseHelper onCreate() BEGIN");
        // Table Categories
        String query = "CREATE TABLE " + TABLE_CATEGORIES + " (" +
                CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CATEGORY_NAME + " TEXT " +
                ");";
        db.execSQL(query);

        // Table BGL
        query = "CREATE TABLE " + TABLE_BGL + " (" +
                BGL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BGL_DateTime + " TEXT, " +
                BGL_Value + " INTEGER " +
                ");";
        db.execSQL(query);

         //Prescription
        query = "CREATE TABLE " + TABLE_PRESCRIPTION + " (" +
                PRE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PRE_DESC + " TEXT, " +
                PRE_REPEAT + " TEXT, " +
                PRE_CAT + " INTEGER,"
                + " FOREIGN KEY ("+PRE_CAT+") REFERENCES "+TABLE_CATEGORIES+"("+CATEGORY_ID+"));";
        db.execSQL(query);

        //Medication
        query = "CREATE TABLE " + TABLE_MED + " (" +
                MED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MED_DESC + " TEXT, " +
                MED_DATETIME + " TEXT, " +
                MED_AMOUNT + " INTEGER, " +
                MED_PRES + " INTEGER,"
                + " FOREIGN KEY ("+MED_PRES+") REFERENCES "+TABLE_PRESCRIPTION+"("+PRE_ID+"));";
        db.execSQL(query);

        //Diet
        query = "CREATE TABLE " + TABLE_DIET + " (" +
                DIET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DIET_DESC + " TEXT, " +
                DIET_DATETIME + " TEXT, " +
                DIET_AMOUNT + " INTEGER, " +
                DIET_PRES + " INTEGER,"
                + " FOREIGN KEY ("+DIET_PRES+") REFERENCES "+TABLE_PRESCRIPTION+"("+PRE_ID+"));";
        db.execSQL(query);

        //Exercise
        query = "CREATE TABLE " + TABLE_EXERCISE + " (" +
                EXER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EXER_DESC + " TEXT, " +
                EXER_DATETIME + " TEXT, " +
                EXER_DURATION + " INTEGER, " +
                EXER_PRES + " INTEGER,"
                + " FOREIGN KEY ("+EXER_PRES+") REFERENCES "+TABLE_PRESCRIPTION+"("+PRE_ID+"));";
        db.execSQL(query);

        // Initialize initial Categories
        /* THESE FUNCTIONS CALLED FOR A REFERENCE FOR THE BASE
        THUS MAKING A RECURSIVE CALL THAT WOULD HAVE BEEN INFINITE
        LOOP SO WE INCLUDE THE DATABASE OBJECT AS A PARAMETER AND
        ENSURE THESE SET OF FUNCTIONS RUN ONCE
         */
        if(!createCategoryDBOnce)
        {
            createCategoryDBOnce = true;
            CreateCategory("Diet", db);
            CreateCategory("Exercise", db);
            CreateCategory("Medication", db);
            CreateCategory("BGL", db);
        }


        System.out.println("DatabaseHelper onCreate() COMPLETE");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ClearDatabase();
    }


    public void ClearDatabase(){
        //context.deleteDatabase("Database1.db");
        //context.deleteDatabase(DATABASE_NAME);
        SQLiteDatabase db = getWritableDatabase();
        //db.close();
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BGL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRESCRIPTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        onCreate(db);
    }

    // endregion

    // region Diet
    // Create Diet
    public void CreateDiet(int preid, String desc, int amount, String date){

        System.out.println("preid: " + preid);
        System.out.println("desc: " + desc);
        System.out.println("amount: " + amount);
        System.out.println("date: " + date);

        ContentValues values = new ContentValues();
        values.put(DIET_PRES, preid);
        values.put(DIET_DESC, desc);
        values.put(DIET_AMOUNT, amount);
        values.put(DIET_DATETIME, date);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_DIET,null,values);
        db.close();
    }
    // Create Diet
    public void CreateDiet( String desc, int amount, String date){
        System.out.println("Creating!");
        ContentValues values = new ContentValues();
        values.put(DIET_DESC, desc);
        values.put(DIET_AMOUNT, amount);
        values.put(DIET_DATETIME, date);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_DIET,null,values);
        db.close();
    }
    // Search Diet by Keyword
    public List<Diet> GetAllDiet(){
        List<Diet> diets = new ArrayList<Diet>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_DIET + " WHERE 1;";
        Cursor c = db.rawQuery(query,null);
        Diet diet;
        System.out.println("Diet " + c.getCount());

        if(c.getCount() > 0){
            while(c.moveToNext()){
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:aa");
                try {
                    diet = new Diet(Integer.valueOf(c.getString(0)),c.getString(1),format.parse(c.getString(2)),Integer.valueOf(c.getString(3)));
                    diets.add(diet);
                }catch(ParseException e){
                    e.printStackTrace();
                }
            }
        }
        db.close();
        return diets;
    }
    // Get All Diet after a date
    public List<Diet> GetAllDietAfterDate(Date date){
        List<Diet> after = new ArrayList<Diet>();
        for(Diet diet: GetAllDiet()){
            if(diet.get_date().after(date)){
                after.add(diet);
            }
        }
        return after;
    }
    // Get All Diet after a date
    public List<Diet> GetAllDietBeforeDate(Date date){
        List<Diet> after = new ArrayList<Diet>();
        for(Diet diet: GetAllDiet()){
            if(diet.get_date().before(date)){
                after.add(diet);
            }
        }
        return after;
    }
    // Search Diet by Keyword
    public List<Diet> GetDietByKeyword(String keyword){
        List<Diet> diets = new ArrayList<Diet>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_DIET +
                " WHERE " +
                DIET_DESC + "='" + keyword + "';"
                ;
        Cursor c = db.rawQuery(query,null);
        Diet diet;
        if(c != null) {
            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:aa");
                    try {
                        diet = new Diet(Integer.valueOf(c.getString(0)), c.getString(1), format.parse(c.getString(2)), Integer.valueOf(c.getString(3)) );
                        diets.add(diet);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        db.close();
        return diets;
    }
    // Get Category by id
    public Diet GetDiet(int id){
        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_DIET + " WHERE " + DIET_ID + " = " + id, null);
        if(c!=null){
            c.moveToFirst();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:aa");
            try {
                Diet diet = new Diet(Integer.valueOf(c.getString(0)),c.getString(1),format.parse(c.getString(2)),Integer.valueOf(c.getString(3)),Integer.valueOf(c.getString(4)));
                db.close();
                return diet;
            }catch(ParseException e){
                e.printStackTrace();
                db.close();
                return null;
            }
        }else{
            db.close();
            return null;
        }
    }
    // Update Diet
    public void UpdateDiet(Diet diet){
        ContentValues values = new ContentValues();
        values.put(DIET_PRES, diet.get_prescriptionId());
        values.put(DIET_DESC, diet.get_description());
        values.put(DIET_AMOUNT, diet.get_amount());
        values.put(DIET_DATETIME, new SimpleDateFormat("yyyy-MM-dd hh:mm:aa").format(diet.get_date()));
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_DIET,values,DIET_ID+"="+diet.get_id(),null);
        db.close();
    }
    // Delete Diet row by ID
    public void DeleteDiet(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_DIET + " WHERE " + DIET_ID + " = " + id + " ;");
        db.close();
    }

    //endregion

    // region Exercise
    // Create Exercise
    public void CreateExercise(int preid, String desc, int duration, String date){
        ContentValues values = new ContentValues();
        values.put(EXER_PRES, preid);
        values.put(EXER_DESC, desc);
        values.put(EXER_DURATION, duration);
        values.put(EXER_DATETIME, date);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_EXERCISE,null,values);
        db.close(); //THE ACTIVITY BETTER BE EXITING AFTER THIS SINCE ITS CLOSING THE DB
    }
    // Search Exercise by Keyword
    public List<Exercise> GetExercisesByKeyword(String keyword){
        List<Exercise> exercises = new ArrayList<Exercise>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EXERCISE +
                " WHERE " +
                  EXER_DESC + " = " + keyword
                ;
        Cursor c = db.rawQuery(query,null);
        Exercise exer;
        if(c.getCount() > 0){
            while(c.moveToNext()){
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:aa");
                try {
                    exer = new Exercise(Integer.valueOf(c.getString(0)),c.getString(1),format.parse(c.getString(2)),Integer.valueOf(c.getString(3)),Integer.valueOf(c.getString(4)));
                    exercises.add(exer);
                }catch(ParseException e){
                    e.printStackTrace();
                }
            }
        }
        db.close();
        return exercises;
    }
    // Get Excerise by id
    public Exercise GetExercise(int id){
        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_EXERCISE + " WHERE " + EXER_ID + " = " + id, null);
        if(c!=null){
            c.moveToFirst();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:aa");
            try {
                Exercise exer = new Exercise(Integer.valueOf(c.getString(0)),c.getString(1),format.parse(c.getString(2)),Integer.valueOf(c.getString(3)),Integer.valueOf(c.getString(4)));
                db.close();
                return exer;
            }catch(ParseException e){
                e.printStackTrace();
                db.close();
                return null;
            }
        }else{
            db.close();
            return null;
        }
    }
    // Update Exercise
    public void UpdateExercise(Exercise exer){
        ContentValues values = new ContentValues();
        values.put(EXER_PRES, exer.get_prescriptionId());
        values.put(EXER_DESC, exer.get_description());
        values.put(EXER_DURATION, exer.get_duration());
        values.put(MED_DATETIME, new SimpleDateFormat("yyyy-MM-dd hh:mm:aa").format(exer.get_dateTime()));
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_EXERCISE,values,MED_ID+"="+exer.get_id(),null);
        db.close();
    }
    // Delete Exercise row by ID
    public void DeleteExercise(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_EXERCISE + " WHERE " + EXER_ID + " = " + id + " ;");
        db.close();
    }
    //endregion

    // region Medication

    // Create Medication
    public void CreateMedication(int preid, String desc, int amount, String date){
        ContentValues values = new ContentValues();
        values.put(MED_PRES, preid);
        values.put(MED_DESC, desc);
        values.put(MED_AMOUNT, amount);
        values.put(MED_DATETIME, date);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_MED,null,values);
        db.close();
    }
    // Search for all Medication
    public List<Medication> GetAllMedication(){
        List<Medication> medications = new ArrayList<Medication>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MED + " WHERE 1;";
        Cursor c = db.rawQuery(query,null);
        Medication med;
        System.out.println("Diet " + c.getCount());

        if(c.getCount() > 0){
            while(c.moveToNext()){
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:aa");
                try {
                    med = new Medication(Integer.valueOf(c.getString(0)),c.getString(1),format.parse(c.getString(2)),Integer.valueOf(c.getString(3)));
                    medications.add(med);
                }catch(ParseException e){
                    e.printStackTrace();
                }
            }
        }
        db.close();
        return medications;
    }
    // Search Med by Keyword
    public List<Medication> GetMedByKeyword(String keyword){
        List<Medication> medications = new ArrayList<Medication>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MED +
                " WHERE " +
                MED_DESC + "='" + keyword + "';"
                ;
        Cursor c = db.rawQuery(query,null);
        Medication med;
        if(c != null) {
            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:aa");
                    try {
                        med = new Medication(Integer.valueOf(c.getString(0)), c.getString(1), format.parse(c.getString(2)), Integer.valueOf(c.getString(3)) );
                        medications.add(med);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        db.close();
        return medications;
    }
    // Get Medication by id
    public Medication GetMedication(int id){
        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_MED + " WHERE " + MED_ID + " = " + id, null);
        if(c!=null){
            c.moveToFirst();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:aa");
            try {
                Medication med = new Medication(Integer.valueOf(c.getString(0)),c.getString(1),format.parse(c.getString(2)),Integer.valueOf(c.getString(3)),Integer.valueOf(c.getString(4)));
                db.close();
                return med;
            }catch(ParseException e){
                e.printStackTrace();
                db.close();
                return null;
            }
        }else{
            db.close();
            return null;
        }
    }
    // Get All Medication before a date
    public List<Medication> GetAllMedicationBeforeDate(Date date){
        List<Medication> after = new ArrayList<Medication>();
        for(Medication med: GetAllMedication()){
            if(med.get_datetime().before(date)){
                after.add(med);
            }
        }
        return after;
    }
    // Get All Medication after a date
    public List<Medication> GetAllMedicationAfterDate(Date date){
        List<Medication> after = new ArrayList<Medication>();
        for(Medication med: GetAllMedication()){
            if(med.get_datetime().after(date)){
                after.add(med);
            }
        }
        return after;
    }
    // Update Medication
    public void UpdateMedication(Medication med){
        ContentValues values = new ContentValues();
        values.put(MED_PRES, med.get_prescriptionId());
        values.put(MED_DESC, med.get_description());
        values.put(MED_AMOUNT, med.get_amount());
        values.put(MED_DATETIME, new SimpleDateFormat("yyyy-MM-dd hh:mm:aa").format(med.get_datetime()));
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_MED,values,MED_ID+"="+med.get_id(),null);
        db.close();
    }
    // Delete Medication row by ID
    public void DeleteMedication(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_MED + " WHERE " + MED_ID + " = " + id + " ;");
        db.close();
    }

    // endregion

    // region Prescription

    // Create Prescription
    public void CreatePrescription(int catid,String desc,String repeat){
        System.out.println("Creating a Prescription");
        ContentValues values = new ContentValues();
        values.put(PRE_CAT, catid);
        values.put(PRE_DESC, desc);
        values.put(PRE_REPEAT, repeat);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PRESCRIPTION,null,values);
        db.close();
    }
    // Update Prescription
    public void UpdatePrescription(Prescription prescription){
        ContentValues values = new ContentValues();
        values.put(PRE_CAT, prescription.get_categoryId());
        values.put(PRE_DESC, prescription.get_description());
        values.put(PRE_REPEAT, prescription.get_repeat());
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_PRESCRIPTION,values,PRE_ID+"="+prescription.get_id(),null);
        db.close();
    }
    public List<Prescription> GetAllPrescriptions(){
        List<Prescription> prescriptions = new ArrayList<Prescription>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " +
                TABLE_PRESCRIPTION + "." + PRE_ID + ", " +
                TABLE_PRESCRIPTION + "." + PRE_DESC + ", " +
                TABLE_PRESCRIPTION + "." + PRE_REPEAT + ", " +
                TABLE_PRESCRIPTION + "." + PRE_CAT + ", " +
                TABLE_CATEGORIES + "." + CATEGORY_NAME +
                " FROM " + TABLE_CATEGORIES + ", " + TABLE_PRESCRIPTION +
                " WHERE " +
                TABLE_CATEGORIES + "." + CATEGORY_ID + " = " +
                TABLE_PRESCRIPTION + "." + PRE_CAT + ";"
                ;
        Cursor c = db.rawQuery(query,null);
        Prescription prescription;
        int i = 0;
        if(c.getCount() > 0){
            while(c.moveToNext()){
                prescription = new Prescription(Integer.valueOf(c.getString(0)),Integer.valueOf(c.getString(3)),c.getString(4),c.getString(1),c.getString(2));
                prescriptions.add(prescription);
            }
        }
        db.close();
        return prescriptions;
    }
    // Get Prescription by ID
    public Prescription GetPrescription(int id){
        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery("SELECT " +
                TABLE_PRESCRIPTION + "." + PRE_ID + ", " +
                TABLE_PRESCRIPTION + "." + PRE_DESC + ", " +
                TABLE_PRESCRIPTION + "." + PRE_REPEAT + ", " +
                TABLE_PRESCRIPTION + "." + PRE_CAT + ", " +
                TABLE_CATEGORIES + "." + CATEGORY_NAME +
                " FROM " + TABLE_CATEGORIES + ", " + TABLE_PRESCRIPTION +
                " WHERE " +
                TABLE_CATEGORIES + "." + CATEGORY_ID + " = " +
                TABLE_PRESCRIPTION + "." + PRE_CAT + " AND " +
                TABLE_CATEGORIES + "." + CATEGORY_ID + " = " + id + ";)",null);

        if(c!=null){
            c.moveToFirst();
            Prescription p = new Prescription(Integer.valueOf(c.getString(0)),Integer.valueOf(c.getString(3)),c.getString(4),c.getString(1),c.getString(2));
            db.close();
            return p;
        }else{
            db.close();
            return null;
        }
    }
    // Get Diets from associated Prescription
    public List<Medication> GetMedicationsFromPrescription(int id){
        List<Medication> medications = new ArrayList<Medication>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MED +
                " WHERE " +
                TABLE_MED + "." + MED_PRES + " = " + id
                ;
        Cursor c = db.rawQuery(query,null);
        Medication med;
        if(c.getCount() > 0){
            while(c.moveToNext()){
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:aa");
                try {
                    med = new Medication(Integer.valueOf(c.getString(0)),c.getString(1),format.parse(c.getString(2)),Integer.valueOf(c.getString(3)),Integer.valueOf(c.getString(4)));
                    medications.add(med);
                }catch(ParseException e){
                    e.printStackTrace();
                }
            }
        }
        db.close();
        return medications;
    }
    // Get Diets from associated Prescription
    public List<Diet> GetDietsFromPrescription(int id){
        List<Diet> diets = new ArrayList<Diet>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_DIET +
                " WHERE " +
                TABLE_DIET + "." + DIET_PRES + " = " + id
                ;
        Cursor c = db.rawQuery(query,null);
        Diet diet;
        if(c.getCount() > 0){
            while(c.moveToNext()){
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:aa");
                try {
                    diet = new Diet(Integer.valueOf(c.getString(0)),c.getString(1),format.parse(c.getString(2)),Integer.valueOf(c.getString(3)),Integer.valueOf(c.getString(4)));
                    diets.add(diet);
                }catch(ParseException e){
                    e.printStackTrace();
                }
            }
        }
        db.close();
        return diets;
    }
    // Search Diet by Keyword
    public List<Exercise> GetExerByKeyword(String keyword){
        List<Exercise> exercises = new ArrayList<Exercise>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MED +
                " WHERE " +
                MED_DESC + "='" + keyword + "';"
                ;
        Cursor c = db.rawQuery(query,null);
        Exercise exer;
        if(c != null) {
            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:aa");
                    try {
                        exer = new Exercise(Integer.valueOf(c.getString(0)), c.getString(1), format.parse(c.getString(2)), Integer.valueOf(c.getString(3)) );
                        exercises.add(exer);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        db.close();
        return exercises;
    }
    public List<Exercise> GetAllExercise(){
        List<Exercise> exercises = new ArrayList<Exercise>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EXERCISE + " WHERE 1;";
        Cursor c = db.rawQuery(query,null);
        Exercise exer;

        if(c.getCount() > 0){
            while(c.moveToNext()){
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:aa");
                try {
                    exer = new Exercise(Integer.valueOf(c.getString(0)),c.getString(1),format.parse(c.getString(2)),Integer.valueOf(c.getString(3)));
                    exercises.add(exer);
                }catch(ParseException e){
                    e.printStackTrace();
                }
            }
        }
        db.close();
        return exercises;
    }
    // Get All Medication after a date
    public List<Exercise> GetAllExerciseBeforeDate(Date date){
        List<Exercise> after = new ArrayList<Exercise>();
        for(Exercise exer: GetAllExercise()){
            if(exer.get_dateTime().before(date)){
                after.add(exer);
            }
        }
        return after;
    }
    // Get All Medication after a date
    public List<Exercise> GetAllExerciseAfterDate(Date date){
        List<Exercise> after = new ArrayList<Exercise>();
        for(Exercise exer: GetAllExercise()){
            if(exer.get_dateTime().after(date)){
                after.add(exer);
            }
        }
        return after;
    }
    // Get All Exercises Associated with Prescription
    public List<Exercise> GetExercisesFromPrescription(int id){
        List<Exercise> exercises = new ArrayList<Exercise>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EXERCISE +
                " WHERE " +
                TABLE_EXERCISE + "." + EXER_PRES + " = " + id
                ;
        Cursor c = db.rawQuery(query,null);
        Exercise exer;
        if(c.getCount() > 0){
            while(c.moveToNext()){
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:aa");
                try {
                    exer = new Exercise(Integer.valueOf(c.getString(0)),c.getString(1),format.parse(c.getString(2)),Integer.valueOf(c.getString(3)),Integer.valueOf(c.getString(4)));
                    exercises.add(exer);
                }catch(ParseException e){
                    e.printStackTrace();
                }
            }
        }
        db.close();
        return exercises;
    }
    // Delete Prescription
    public void DeletePrescription(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRESCRIPTION + " WHERE " + PRE_ID + " = " + id + " ;");
        db.close();
    }
    // endregion

    // region BGL
    // Create a BGL Entry
    public void CreateBGL(Date date,int value){
        ContentValues values = new ContentValues();
        String dateString = new SimpleDateFormat("yyyy-MM-dd hh:mm:aa").format(date);
        values.put(BGL_DateTime, dateString);
        values.put(BGL_Value, value);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_BGL,null,values);
        db.close();
    }
    // Get BGL by id
    public BGL GetBGL(int id){
        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_BGL + " WHERE " + BGL_ID + " = " + id, null);
        if(c!=null){
            c.moveToFirst();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:aa");
            try {
                BGL bgl = new BGL(Integer.valueOf(c.getString(0)),format.parse(c.getString(1)),Integer.valueOf(c.getString(2)));
                db.close();
                return bgl;
            }catch(ParseException e){
                e.printStackTrace();
                db.close();
                return null;
            }
        }else{
            db.close();
            return null;
        }
    }
    // Update BGL
    public void UpdateBGL(BGL bgl){
        ContentValues values = new ContentValues();
        System.out.println("Updating BGL of ID: " + bgl.get_id() + " value: " + bgl.get_value() + " date: " + bgl.get_date());
        values.put(BGL_DateTime, bgl.GetFormatedDate());
        values.put(BGL_Value, bgl.get_value());

        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_BGL,values,BGL_ID+"="+bgl.get_id(),null);
        db.close();
        System.out.println("closed");
    }
    // Delete BGL
    public void DeleteBGL(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_BGL + " WHERE " + BGL_ID + " = " + id + " ;");
        db.close();
    }
    public List<BGL> GetAllBGL(){
        List<BGL> bgls = new ArrayList<BGL>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_BGL + " WHERE 1;";
        Cursor c = db.rawQuery(query,null);
        BGL bgl;
        if(c.getCount() > 0){
            while(c.moveToNext()){
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:aa");
                try {
                    bgl = new BGL(Integer.valueOf(c.getString(0)), format.parse(c.getString(1)),Integer.valueOf(c.getString(2)));
                    bgls.add(bgl);
                }catch(ParseException e){
                    e.printStackTrace();
                }
            }
        }
        c.close();
        db.close();
        return bgls;
    }
    // Get All BGL before a date
    public List<BGL> GetAllBGLBeforeDate(Date date){
        List<BGL> before = new ArrayList<BGL>();
        for(BGL bgl: GetAllBGL()){
            if(bgl.get_date().before(date)){
                before.add(bgl);
            }
        }
        return before;
    }
    // Get All BGL after a date
    public List<BGL> GetAllBGLAfterDate(Date date){
        List<BGL> after = new ArrayList<BGL>();
        for(BGL bgl: GetAllBGL()){
            if(bgl.get_date().after(date)){
                after.add(bgl);
            }
        }
        return after;
    }

    // endregion

    // region Category
    // Create a Category
    public void CreateCategory(String categoryName, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(CATEGORY_NAME, categoryName);
        db.insert(TABLE_CATEGORIES,null,values);
        // CANNOT CLOSE DB, SEND OTHER INSERTS WILL ATTEMPT TO RE-OPEN A PREVIOUSLY CLOSE DB.
        // RECOMMEND TO CLOSE DB ONLY WHEN THE ACTIVITY EXITS
        // WHEN THE ACTIVITY COMES BACK INTO RUNNING STATE, THE NEW WILL BE CALLED IMPLYING THE OPEN
    }

    // Get Category by id
    public Category GetCategory(int id){
        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_CATEGORIES + " WHERE " + CATEGORY_ID + " = " + id, null);
        if(c!=null){
            c.moveToFirst();
            Category cat = new Category(Integer.valueOf(c.getString(0)),c.getString(1));
            db.close();
            return cat;
        }else{
            db.close();
            return null;
        }
    }
    // Update a Category
    public void UpdateCategory(Category category){
        ContentValues values = new ContentValues();
        values.put(CATEGORY_NAME, category.getName());
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_CATEGORIES,values,CATEGORY_ID+"="+category.getId(),null);
        db.close();
    }
    // Delete Category row by ID
    public void DeleteCategory(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CATEGORIES + " WHERE " + CATEGORY_ID + " = " + id + " ;");
        db.close();
    }
    // Get All Categories
    public List<Category> GetCategories(){
        List<Category> categories = new ArrayList<Category>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CATEGORIES + " WHERE 1";
        Cursor c = db.rawQuery(query,null);
        Category category;
        if(c.getCount() > 0){
            while(c.moveToNext()){
                category = new Category(Integer.valueOf(c.getString(0)),c.getString(1));
                categories.add(category);
            }
        }
        db.close();
        return categories;
    }

    // endregion

    // Explicitly close the database
    public void closeDB(){
        SQLiteDatabase db = getWritableDatabase();
        db.close();
    }

}
