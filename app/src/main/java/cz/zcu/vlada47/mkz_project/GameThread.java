package cz.zcu.vlada47.mkz_project;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Vlada47 on 21. 4. 2016.
 */
public class GameThread extends Thread {

    private static final int SLEEP_MILIS = 50;

    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;

    public GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        Canvas canvas;

        while(running) {
            canvas = null;

            try {
                canvas = surfaceHolder.lockCanvas();

                synchronized (surfaceHolder) {
                    if(canvas != null) {
                        gameView.drawMap(canvas);
                    }

                    try {
                        Thread.sleep(SLEEP_MILIS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            finally {
                if(canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

        }
    }
}
