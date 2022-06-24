package dsai.group07.force.utils;

import javafx.animation.AnimationTimer;

public abstract class GameAnimationTimer extends AnimationTimer {

    long pauseStart;
    //long animationStart;

    long lastFrameTimeNanos;

    boolean isPaused;
    boolean isActive; // save for the rainy day

    boolean pauseScheduled;
    boolean playScheduled;
    boolean restartScheduled;

    public boolean isPaused() {
        return isPaused;
    }

    public boolean isActive() {
        return isActive;
    }


    public void pause() {
        if (!isPaused) {
            pauseScheduled = true;
        }
    }

    public void play() {
        if (isPaused) {
            playScheduled = true;
        }
    }

    @Override
    public void start() {
        super.start();
        isActive = true;
        restartScheduled = true;
    }

    @Override
    public void stop() {
        super.stop();
        pauseStart = 0;
        isPaused = false;
        isActive = false;
        pauseScheduled = false;
        playScheduled = false;
    }

    @Override
    public void handle(long now) {
        if (pauseScheduled) {
            pauseStart = now;
            isPaused = true;
            pauseScheduled = false;
        }

        if (playScheduled) {
//            animationStart += (now - pauseStart);
            isPaused = false;
            playScheduled = false;
            
            lastFrameTimeNanos = now;
        }

        if (restartScheduled) {
            isPaused = false;
//            animationStart = now;
            restartScheduled = false;
            
            lastFrameTimeNanos = now;
        }

        if (!isPaused) {
            float secondsSinceLastFrame = (float) ((now - lastFrameTimeNanos) / 1e9);
            lastFrameTimeNanos = now;
            tick(secondsSinceLastFrame);
        }
    }

    public abstract void tick(float secondsSinceLastFrame);
}