package com.example.tiagoeira.quizgame.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.example.tiagoeira.quizgame.model.Question;


public class QuizProvider extends ContentProvider {

    //identifier
    private static final String AUTHORITY = "com.example.tiagoeira.quizgame.provider.quizprovider";

    //global uri
    public static final Uri CONTENT_URI = Uri.parse(ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY);

    //Uris for each table
    public static final Uri QUESTION_URI = Uri.parse(CONTENT_URI + "/" + QuizContract.QUESTIONS_TABLE);
    public static final Uri ANSWER_URI = Uri.parse(CONTENT_URI + "/" + QuizContract.ANSWERS_TABLE);

    //Matcher to see if the type is 1 element or all
    private static UriMatcher URIMATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int Q_QUIZ_ID = 1;
    private static final int Q_QUIZ_ALL = 2;
    private static final int A_QUIZ_ID = 3;
    private static final int A_QUIZ_ALL = 4;

    //content type mime for questions
    private static final String MIME_ALL = "vnd.android.cursor.dir/vnd.com.example.tiagoeira.quizgame.provider.quizprovider." + QuizContract.QUESTIONS_TABLE;
    private static final String MIME_ONE = "vnd.android.cursor.item/vnd.com.example.tiagoeira.quizgame.provider.quizprovider." + QuizContract.QUESTIONS_TABLE;

    //content type mime for answers
    private static final String A_MIME_ALL = "vnd.android.cursor.dir/vnd.com.example.tiagoeira.quizgame.provider.quizprovider." + QuizContract.ANSWERS_TABLE;
    private static final String A_MIME_ONE = "vnd.android.cursor.item/vnd.com.example.tiagoeira.quizgame.provider.quizprovider." + QuizContract.ANSWERS_TABLE;

    // DB Helper instance for access to SQLite DB.
    private SQLiteOpenHelper helper;

    static {
        URIMATCHER.addURI(AUTHORITY, QuizContract.QUESTIONS_TABLE +"/#", Q_QUIZ_ID);
        URIMATCHER.addURI(AUTHORITY, QuizContract.QUESTIONS_TABLE, Q_QUIZ_ALL);
        URIMATCHER.addURI(AUTHORITY, QuizContract.ANSWERS_TABLE + "/#", A_QUIZ_ID);
        URIMATCHER.addURI(AUTHORITY, QuizContract.ANSWERS_TABLE, A_QUIZ_ALL);
    }


    @Override
    public boolean onCreate() {
        helper = new QuizHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = helper.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        switch (URIMATCHER.match(uri)){
            case Q_QUIZ_ALL:
                builder.setTables(QuizContract.QUESTIONS_TABLE);
                if (TextUtils.isEmpty(sortOrder)){
                    sortOrder = QuizContract.QUESTION_ID + " ASC";
                }
                break;
            case Q_QUIZ_ID:
                builder.setTables(QuizContract.QUESTIONS_TABLE);
                builder.appendWhere(QuizContract.QUESTION_ID + " = " + uri.getLastPathSegment());
                break;
            case A_QUIZ_ALL:
                builder.setTables(QuizContract.ANSWERS_TABLE);
                break;
            case A_QUIZ_ID:
                builder.setTables(QuizContract.ANSWERS_TABLE);
                builder.setTables(QuizContract.ANSWER_ID + " = " + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (URIMATCHER.match(uri)) {
            case Q_QUIZ_ID:
                return MIME_ONE;
            case Q_QUIZ_ALL:
                return MIME_ALL;
            case A_QUIZ_ID:
                return A_MIME_ONE;
            case A_QUIZ_ALL:
                return A_MIME_ALL;
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (URIMATCHER.match(uri) != Q_QUIZ_ALL && URIMATCHER.match(uri) != A_QUIZ_ALL){
            throw new IllegalArgumentException("Wrong URI for insertion: " + uri);
        }
        SQLiteDatabase db = helper.getWritableDatabase();
        if (URIMATCHER.match(uri) == Q_QUIZ_ALL){
            long row = db.insert(QuizContract.QUESTIONS_TABLE, null, values);
            Log.i("PRVDR_LOG", "insert - " + row);
            return (row != -1) ? null : ContentUris.withAppendedId(uri, row);
        }
        else {
            long row = db.insert(QuizContract.ANSWERS_TABLE, null, values);
            Log.i("PRVDR_LOG", "insert - " + row);
            return (row != -1) ? null : ContentUris.withAppendedId(uri, row);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            switch (URIMATCHER.match(uri)){
                case Q_QUIZ_ID:
                    return db.delete(QuizContract.QUESTIONS_TABLE, selection, selectionArgs);
                case A_QUIZ_ID:
                    return db.delete(QuizContract.ANSWERS_TABLE, selection, selectionArgs);
                default:
                    throw new IllegalArgumentException("Wrong uri:" + uri);
            }
        }
        finally {
            db.close();
        }
    }

    //not used
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            switch (URIMATCHER.match(uri)) {
                case Q_QUIZ_ID:
                    return db.update(QuizContract.QUESTIONS_TABLE, values, selection, selectionArgs);
                case A_QUIZ_ID:
                    return db.update(QuizContract.ANSWERS_TABLE, values, selection, selectionArgs);
                default:
                    throw new IllegalArgumentException("Wrong uri:" + uri);
            }
        }
        finally {
            db.close();
        }
    }

    //Database helper
    private static class QuizHelper extends SQLiteOpenHelper{

        public QuizHelper(Context context)
        {
            super(context, "quiz_game.sql", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            //create Questions columns
            String QuestionColumns = QuizContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    QuizContract.QUESTION_ID + " INTEGER, " + QuizContract.KEY_QUESTION + " TEXT";

            //create Answer columns
            String AnswerColumns = QuizContract.A_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + QuizContract.ANSWER_ID + " TEXT, " +
                    QuizContract.KEY_ANSWER + " TEXT, " + QuizContract.KEY_CORRECT + " TEXT, " + QuizContract.KEY_Q_ID +
                    " INTEGER, " + "FOREIGN KEY (" + QuizContract.KEY_Q_ID + ") REFERENCES " + QuizContract.QUESTIONS_TABLE + " (" + QuizContract.QUESTION_ID + ")";

            Log.i("DB_LOG", " - " + AnswerColumns);

            //Create tables
            String create_question_table = "CREATE TABLE IF NOT EXISTS " + QuizContract.QUESTIONS_TABLE + " (" + QuestionColumns + ")";
            String create_answer_table = "CREATE TABLE IF NOT EXISTS " + QuizContract.ANSWERS_TABLE + " (" + AnswerColumns + ")";
            sqLiteDatabase.execSQL(create_question_table);
            sqLiteDatabase.execSQL(create_answer_table);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            //Drop old tables
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + QuizContract.QUESTIONS_TABLE);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + QuizContract.ANSWERS_TABLE);

            //create new tables
            onCreate(sqLiteDatabase);
        }
    }
}
