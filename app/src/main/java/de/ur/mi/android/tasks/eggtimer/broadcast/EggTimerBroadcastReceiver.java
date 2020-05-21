package de.ur.mi.android.tasks.eggtimer.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class EggTimerBroadcastReceiver extends BroadcastReceiver {

    private static final String TIMER_UPDATE = "de.ur.mi.android.task.eggtimer.TIMER_UPDATE";
    private static final String TIMER_FINISHED = "de.ur.mi.android.task.eggtimer.TIMER_FINISHED";
    public static final String REMAINING_TIME_IN_SECONDS = "REMAINING_TIME_IN_SECONDS";

    private EggTimerBroadcastListener listener;

    public EggTimerBroadcastReceiver(EggTimerBroadcastListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case TIMER_UPDATE:
                int remainingTimeInSeconds = intent.getExtras().getInt(REMAINING_TIME_IN_SECONDS);
                listener.onTimerUpdate(remainingTimeInSeconds);
                break;
            case TIMER_FINISHED:
                listener.onTimerFinished();
                break;
        }
    }

    public static IntentFilter getIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(EggTimerBroadcastReceiver.TIMER_UPDATE);
        filter.addAction(EggTimerBroadcastReceiver.TIMER_FINISHED);
        return filter;
    }

}
