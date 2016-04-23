package cz.zcu.vlada47.mkz_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlada47 on 4. 4. 2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     *
     */
    private static final String DATABASE_NAME = "playerScore.db";

    /**
     *
     */
    private static final int DATABASE_VERSION = 1;

    /**
     *
     */
    private static final String TABLE_NAME = "player_score";

    /**
     *
     */
    private static final String NAME_COLUMN = "name";

    /**
     *
     */
    private static final String MAP_COLUMN = "map";

    /**
     *
     */
    private static final String TIME_COLUMN = "time";

    /**
     *
     */
    private static final String STEP_COLUMN = "steps";

    /**
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_NAME + "("
                + BaseColumns._ID + " integer primary key autoincrement, "
                + NAME_COLUMN + " text not null, "
                + MAP_COLUMN + " integer not null, "
                + TIME_COLUMN + " integer not null, "
                + STEP_COLUMN + " integer not null);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion >= newVersion) {
            return;
        }

        String sql = null;
        if (oldVersion == 1)
            sql = "alter table " + TABLE_NAME + " add note text;";
        if (oldVersion == 2)
            sql = "";

        if (sql != null)
            db.execSQL(sql);
    }

    /**
     *
     * @param name
     * @param map
     * @param time
     * @param steps
     */
    public void addScore(String name, int map, long time, int steps) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COLUMN, name);
        values.put(MAP_COLUMN, map);
        values.put(TIME_COLUMN, time);
        values.put(STEP_COLUMN, steps);
        db.insert(TABLE_NAME, null, values);
    }

    /**
     *
     * @return
     */
    public Cursor getScores() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null,
                null, null);

        return cursor;
    }
}
