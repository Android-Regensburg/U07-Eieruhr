package de.ur.mi.android.tasks.eggtimer.timer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Die EggTimer-Klasse dient dazu, die Zeit bis zum Ablauf der Kochdauer eines Eies zu stoppen.
 * Diese Operation muss zeitlich parallel zum Rest der Anwendung ausgeführt werden.
 * Dazu wird das 'Runnable'-Interface implementiert. Dieses sorgt dafür, dass Code innerhalb der Klasse, also die überschriebene run-Methode,
 * in einem seperaten Thread ausgeführt werden kann. Man spricht hier von asynchronem Computing
 */
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

    /**
     * Diese Methode nutzt die 'Executors'-Klasse, die in Java zur Verwaltung von Threads verwendet werden kann.
     * In diesem Fall wird ein regelmäßiges Ausführen mit einem zeitlichen Abstand von 1000 Millisekunden geplant.
     * Als Kommando wird 'this' übergeben, da es sich beim EggTimer um ein Runnable handelt.
     * Da der Timer irgendwann abläuft, kann der Hintergrundthread irgendwann gestoppt werden.
     * Dafür wird der Rückgabewert des Aufrufs einer Instanzvariable vom Typ 'ScheduledFuture' zugewiesen.
     * Auf dieser kann später die 'cancel'-Methode gestartet werden.
     */
    public void start() {
        scheduledTimerFuture = Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(this, DEFAULT_DELAY, DEFAULT_DELAY, DEFAULT_TIME_UNIT);
    }

    /**
     * Überschriebene Methode des Runnable-Interfaces.
     * Die run-Methode wird aufgerufen, wenn ein neuer Thread mit dem EggTimer gestartet wird.
     * Es wird geprüft, ob es sich bei der aktuellen Zeit um die Endzeit des Timers handelt, bzw. die Differenz bis zu dieser berechnet.
     * Wenn die Endzeit erreicht ist, wird der Listener informiert und ein weiterer Aufruf abgebrochen.
     * Ansonsten wird dem Listener die übrige Zeit mitgeteilt.
     */
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
