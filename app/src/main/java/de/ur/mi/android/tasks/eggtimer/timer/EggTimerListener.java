package de.ur.mi.android.tasks.eggtimer.timer;

public interface EggTimerListener {

    void onUpdate(int remainingTimeInSeconds);

    void onFinished();

}
