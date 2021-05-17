package de.ur.mi.android.tasks.eggtimer.timer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class EggTimer implements Runnable {

    private static final int DEFAULT_DELAY = 1000;
    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MILLISECONDS;

    private final EggTimerListener listener;
    private ScheduledFuture scheduledTimerFuture;

    private final long endTime;

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
