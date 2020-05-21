package de.ur.mi.android.tasks.eggtimer.timer;

import android.app.Activity;
import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class EggTimer implements Runnable {

    private static final int DEFAULT_DELAY = 1000;
    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MILLISECONDS;

    private EggTimerListener listener;
    private ScheduledFuture scheduledTimerFuture;

    private long endTime;

    public EggTimer(EggOrder order, EggTimerListener listener) {
        this.listener = listener;
        this.endTime = System.currentTimeMillis() + (order.targetTime * 1000);
    }

    public void start() {
        scheduledTimerFuture = Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(this, DEFAULT_DELAY, DEFAULT_DELAY, DEFAULT_TIME_UNIT);
    }

    @Override
    public void run() {
        final long now = System.currentTimeMillis();
        final int secondsRemaining = (int) (endTime - now) / 1000;
        listener.onUpdate(secondsRemaining);
        if (now >= endTime) {
            listener.onFinished();
            scheduledTimerFuture.cancel(true);
        }
    }

}
