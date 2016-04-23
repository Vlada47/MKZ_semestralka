package cz.zcu.vlada47.mkz_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.Arrays;

import cz.zcu.vlada47.mkz_project.static_data.MapsInfo;
import cz.zcu.vlada47.mkz_project.static_data.MapsLayout;

/**
 *
 */
public class GameActivity extends Activity {

    /**
     *
     */
    public static final int BLANK = 0;

    /**
     *
     */
    public static final int WALL = 1;

    /**
     *
     */
    public static final int CRATE = 2;

    /**
     *
     */
    private GameView gameView;

    /**
     *
     */
    private int mapId;

    /**
     *
     */
    private int[][] mapLayout;

    /**
     *
     */
    private int[] playerPos;

    /**
     *
     */
    private int[][] targetsPos;

    /**
     *
     */
    private int stepCnt;

    /**
     *
     */
    private long timeStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        mapId = intent.getIntExtra("mapId", 0);
        mapLayout = copyMapLayout(MapsLayout.MAPS_LAYOUT[mapId]);

        MapsInfo[] maps = MapsInfo.values();
        playerPos = Arrays.copyOf(maps[mapId].getPlayerPos(), 2);
        targetsPos= maps[mapId].getTargetsPos();
        stepCnt = 0;

        FrameLayout frame = (FrameLayout) findViewById(R.id.gameFrame);
        gameView = new GameView(this, mapLayout, playerPos, targetsPos);
        frame.addView(gameView);
        timeStart = System.currentTimeMillis();
    }

    /**
     *
     * @param v
     */
    public void goUp(View v) {
        int[] newPlayerPos = new int[playerPos.length];
        newPlayerPos[0] = playerPos[0] - 1;
        newPlayerPos[1] = playerPos[1];
        moveToPosition(newPlayerPos);
    }

    /**
     *
     * @param v
     */
    public void goDown(View v) {
        int[] newPlayerPos = new int[playerPos.length];
        newPlayerPos[0] = playerPos[0] + 1;
        newPlayerPos[1] = playerPos[1];
        moveToPosition(newPlayerPos);
    }

    /**
     *
     * @param v
     */
    public void goLeft(View v) {
        int[] newPlayerPos = new int[playerPos.length];
        newPlayerPos[0] = playerPos[0];
        newPlayerPos[1] = playerPos[1] - 1;
        moveToPosition(newPlayerPos);
    }

    /**
     *
     * @param v
     */
    public void goRight(View v) {
        int[] newPlayerPos = new int[playerPos.length];
        newPlayerPos[0] = playerPos[0];
        newPlayerPos[1] = playerPos[1] + 1;
        moveToPosition(newPlayerPos);
    }

    /**
     *
     * @param newPlayerPos
     */
    private void moveToPosition(int[] newPlayerPos) {

        if(mapLayout[newPlayerPos[0]][newPlayerPos[1]] == BLANK) {
            playerPos = newPlayerPos;
            stepCnt++;
        } else if(mapLayout[newPlayerPos[0]][newPlayerPos[1]] == CRATE) {
            //move up
            if(newPlayerPos[0] < playerPos[0]) {
                if(mapLayout[newPlayerPos[0]-1][newPlayerPos[1]] == BLANK) {
                    mapLayout[newPlayerPos[0]-1][newPlayerPos[1]] = CRATE;
                    mapLayout[newPlayerPos[0]][newPlayerPos[1]] = BLANK;
                    playerPos = newPlayerPos;
                    stepCnt++;
                }
            }
            //move down
            else if(newPlayerPos[0] > playerPos[0]) {
                if(mapLayout[newPlayerPos[0]+1][newPlayerPos[1]] == BLANK) {
                    mapLayout[newPlayerPos[0]+1][newPlayerPos[1]] = CRATE;
                    mapLayout[newPlayerPos[0]][newPlayerPos[1]] = BLANK;
                    playerPos = newPlayerPos;
                    stepCnt++;
                }
            }
            //move left
            else if(newPlayerPos[1] < playerPos[1]) {
                if(mapLayout[newPlayerPos[0]][newPlayerPos[1]-1] == BLANK) {
                    mapLayout[newPlayerPos[0]][newPlayerPos[1]-1] = CRATE;
                    mapLayout[newPlayerPos[0]][newPlayerPos[1]] = BLANK;
                    playerPos = newPlayerPos;
                    stepCnt++;
                }
            }
            //move right
            else if(newPlayerPos[1] > playerPos[1]) {
                if(mapLayout[newPlayerPos[0]][newPlayerPos[1]+1] == BLANK) {
                    mapLayout[newPlayerPos[0]][newPlayerPos[1]+1] = CRATE;
                    mapLayout[newPlayerPos[0]][newPlayerPos[1]] = BLANK;
                    playerPos = newPlayerPos;
                    stepCnt++;
                }
            }
        }

        System.out.println(Arrays.toString(playerPos));

        gameView.setMapLayout(mapLayout);
        gameView.setPlayerPos(playerPos);

        if(checkForWin()) {
            gameView.endGame();
            Toast.makeText(getApplicationContext(), "You've finished the map!", Toast.LENGTH_SHORT).show();
            showYesNoDialog();
        }
    }

    /**
     *
     * @return
     */
    private boolean checkForWin() {
        boolean isWin = true;

        for (int[] targetsPo : targetsPos) {
            if (mapLayout[targetsPo[0]][targetsPo[1]] != CRATE) {
                isWin = false;
                break;
            }
        }

        return isWin;
    }

    /**
     *
     */
    private void showYesNoDialog() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent saveScoreIntent = new Intent(getApplicationContext(), SaveScoreActivity.class);
                        saveScoreIntent.putExtra("mapId", mapId);
                        saveScoreIntent.putExtra("stepCnt", stepCnt);
                        saveScoreIntent.putExtra("time", (System.currentTimeMillis() - timeStart)/1000);
                        startActivity(saveScoreIntent);
                        finish();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        finish();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage("Do you want to save your score?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .show();
    }

    /**
     *
     * @param originalLayout
     * @return
     */
    private int[][] copyMapLayout(int[][] originalLayout) {

        int[][] copiedLayout = new int[originalLayout.length][originalLayout[0].length];

        for(int i = 0; i < copiedLayout.length; i++) {
            System.arraycopy(originalLayout[i], 0, copiedLayout[i], 0, copiedLayout[0].length);
        }

        return copiedLayout;
    }
}