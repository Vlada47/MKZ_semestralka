package cz.zcu.vlada47.mkz_project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Vlada47 on 27. 3. 2016.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    /**
     *
     */
    private static final int BLOCK_SIZE = 30;

    /**
     *
     */
    private static final int POINT_SIZE = 5;

    /**
     *
     */
    private SurfaceHolder holder;

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
    private GameThread gameThread;

    /**
     *
     * @param context
     * @param mapLayout
     * @param playerPos
     * @param targetsPos
     */
    public GameView(Context context, int[][] mapLayout, int[] playerPos, int[][] targetsPos) {
        super(context);
        this.mapLayout = mapLayout;
        this.playerPos = playerPos;
        this.targetsPos = targetsPos;
        initialize();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;

        while(retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     */
    private void initialize() {
        setWillNotDraw(false);
        holder = getHolder();
        holder.addCallback(this);
        gameThread = new GameThread(holder, this);
    }

    /**
     *
     * @param canvas
     */
    public void drawMap(Canvas canvas) {
        Paint drawing = new Paint();
        drawing.setAntiAlias(true);

        for(int i = 0; i < mapLayout.length; i++) {
            for(int j = 0; j < mapLayout[i].length; j++) {
                drawing.setStyle(Paint.Style.FILL);

                switch(mapLayout[i][j]) {
                    case GameActivity.BLANK:
                        drawing.setColor(Color.BLACK); break;
                    case GameActivity.WALL:
                        drawing.setColor(Color.GRAY); break;
                    case GameActivity.CRATE:
                        drawing.setColor(Color.YELLOW); break;
                }

                Rect r = new Rect(j*BLOCK_SIZE, i*BLOCK_SIZE, (j*BLOCK_SIZE)+BLOCK_SIZE, (i*BLOCK_SIZE)+BLOCK_SIZE);
                canvas.drawRect(r, drawing);

                drawing.setStyle(Paint.Style.STROKE);
                drawing.setColor(Color.WHITE);
                canvas.drawRect(r, drawing);
            }
        }

        drawing.setStyle(Paint.Style.FILL);
        drawing.setColor(Color.GREEN);
        canvas.drawCircle(playerPos[1] * BLOCK_SIZE + (BLOCK_SIZE / 2), playerPos[0] * BLOCK_SIZE + (BLOCK_SIZE / 2), BLOCK_SIZE / 2, drawing);

        drawing.setColor(Color.BLUE);
        for (int[] targetsPo : targetsPos) {
            canvas.drawCircle(targetsPo[1] * BLOCK_SIZE + (BLOCK_SIZE / 2), targetsPo[0] * BLOCK_SIZE + (BLOCK_SIZE / 2), POINT_SIZE, drawing);
        }
    }

    /**
     *
     * @param mapLayout
     */
    public void setMapLayout(int[][] mapLayout) {
        this.mapLayout = mapLayout;
    }

    /**
     *
     * @param playerPos
     */
    public void setPlayerPos(int[] playerPos) {
        this.playerPos = playerPos;
    }

    public void endGame() {
        gameThread.setRunning(false);
    }
}
