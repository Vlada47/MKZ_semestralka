package cz.zcu.vlada47.mkz_project;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 *
 */
public class SaveScoreActivity extends Activity {

    /**
     *
     */
    private DatabaseHelper dbHelper;

    /**
     *
     */
    private int map;

    /**
     *
     */
    private long time;

    /**
     *
     */
    private int stepCnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_score);

        Intent intent = getIntent();
        map = intent.getIntExtra("mapId", 0);
        time = intent.getLongExtra("time", 0);
        stepCnt = intent.getIntExtra("stepCnt", 0);
        dbHelper = new DatabaseHelper(this);

        TextView timeTextView = (TextView) findViewById(R.id.saveScoreTime);
        timeTextView.setText("Time: "+time+"s");

        TextView stepsTextView = (TextView) findViewById(R.id.saveScoreSteps);
        stepsTextView.setText("Steps: "+stepCnt);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    /**
     *
     */
    public void confirm() {
        String name = ((EditText) findViewById(R.id.saveScoreName)).getText().toString();
        dbHelper.addScore(name, map, time, stepCnt);
    }

    /**
     *
     */
    public void cancel() {
        finish();
    }
}
