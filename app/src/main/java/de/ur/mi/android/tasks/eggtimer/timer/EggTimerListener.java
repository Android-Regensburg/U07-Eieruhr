package de.ur.mi.android.tasks.eggtimer.timer;

/**
 * Interface zur Definition der Callback-Methoden für den EggTimer
 */
public interface EggTimerListener {

    void onUpdate(int remainingTimeInSeconds);

    void onFinished();

}
