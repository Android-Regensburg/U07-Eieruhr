package de.ur.mi.android.tasks.eggtimer.broadcast;

/**
 * Interface zur Informierung der EggTimerActivity über empfangene Broadcasts
 */
public interface EggTimerBroadcastListener {

    void onTimerUpdate(int remainingTimeInSeconds);

    void onTimerFinished();

}