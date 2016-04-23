package cz.zcu.vlada47.mkz_project;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 *
 */
public class ShowScoreActivity extends Activity {

    /**
     *
     */
    private DatabaseHelper dbHelper;

    /**
     *
     */
    private Cursor scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_score);

        dbHelper = new DatabaseHelper(this);
        scores = dbHelper.getScores();

        final ArrayAdapter<String> scoreListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, createScoreStrings(scores));
        ListView scoreListView = (ListView) findViewById(R.id.scoreList);
        scoreListView.setAdapter(scoreListAdapter);
    }
;
    @Override
    public void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    private String[] createScoreStrings(Cursor scores) {
        String[] scoreStrings = new String[scores.getCount()];
        int i = 0;

        while(scores.moveToNext()) {
            scoreStrings[i] = "Player name: " + scores.getString(1) + "; Map ID: " + scores.getInt(2) + "\n"
                    + "Time: " + scores.getInt(3) + "s; Number of steps: " + scores.getInt(4);
            i++;
        }

        return scoreStrings;
    }

}
