package de.ur.mi.android.tasks.eggtimer.broadcast;

public interface EggTimerBroadcastListener {

    void onTimerUpdate(int remainingTimeInSeconds);

    void onTimerFinished();

}