package de.ur.mi.android.tasks.eggtimer.timer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import de.mi.eggtimer.R;
import de.ur.mi.android.tasks.eggtimer.EggTimerActivity;
import de.ur.mi.android.tasks.eggtimer.broadcast.EggTimerBroadcastReceiver;

/**
 * Der EggTimerService sorgt dafür, dass Nutzer*innen eine Benachrichtigung erhalten können -
 * auch dann, wenn die App zum Abschluss des Timers bereits geschlossen wurde.
 * Der Service läuft also unabhängig von der restlichen App im Hintergrund weiter, bis der Timer abgelaufen ist.
 */
public class EggTimerService extends Service {

    /**
     * Konstanten zur Erstellung der Benachrichtungen
     */
    public static final String NOTIFICATION_CHANNEL_ID = "EGG_TIMER_CHANNEL_ID";
    public static final String NOTIFICATION_CHANNEL_NAME = "EggTimer Channel";
    public static final String NOTIFICATION_CHANNEL_DESCRIPTION= "Notification channel for EggTimer";
    public static  final String EGG_ORDER_EXTRA_KEY = "EGG_ORDER";

    private static int currentNotificationID = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        Notification foregroundNotification = getNotificationForForegroundService();
        startForeground(getCurrentNotificationID(), foregroundNotification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        EggOrder order = (EggOrder) intent.getSerializableExtra(EGG_ORDER_EXTRA_KEY);
        startTimerForOrder(order);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * Startet den EggTimer mit der jeweils ausgewählten Kochdauer.
     * Dem EggTimer wird als Listener eine anonyme Klasse übergeben, die das EggTimerListener-Interface implementiert.
     */
    private void startTimerForOrder(EggOrder order) {
        EggTimer timer = new EggTimer(order, new EggTimerListener() {
            @Override
            public void onUpdate(int remainingTimeInSeconds) {
                broadcastTimerUpdate(remainingTimeInSeconds);
            }

            @Override
            public void onFinished() {
                broadcastTimerFinished();
            }
        });
        timer.start();
    }

    /**
     * In dieser Methode wird ein Broadcast mit der verbleibenden Zeit bis zum Ablauf des Timers
     * vom Service an die Activity gesendet.
     */
    private void broadcastTimerUpdate(int remainingTimeInSeconds) {
        Intent intent = EggTimerBroadcastReceiver.getUpdateIntent(remainingTimeInSeconds);
        sendBroadcast(intent);
    }

    /**
     * In dieser Methode wird ein Broadcast an die Activity gesendet, über den diese vom Ablauf
     * des Timers in Kenntnis gesetzt wird.
     * Gleichzeitig wird eine Benachrichtung erstellt und über den NotificationManager an den/die Nutzer*in gesendet.
     * Da der Service nach Ablauf des Timers nicht mehr benötigt wird, wird dieser mit stopSelf() beendet.
     */
    private void broadcastTimerFinished() {
        Intent intent = EggTimerBroadcastReceiver.getEndIntent();
        sendBroadcast(intent);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.notify(getCurrentNotificationID(), getNotificationForFinishedTimer());
        this.stopSelf();
    }

    /**
     * Inkrementiert einen Zähler, der als Identifikationsnummer der aktuellen Benachrichtigung genutzt wird.
     */
    private int getCurrentNotificationID() {
        currentNotificationID++;
        return currentNotificationID;
    }

    /**
     * Erstellt einen Benachrichtigungskanal für die Anwendung.
     * Seit API Level 26 muss jede Benachrichtigung einem solchen Kanal zugeordnet werden.
     * Dadurch können Benachrichtigungen gruppiert und bestimmte Kanäle hervorgehoben oder ausgeblendet werden.
     */
    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription(NOTIFICATION_CHANNEL_DESCRIPTION);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

    }

    /**
     * Erstellt und konfiguriert die Benachrichtigung, die Nutzer*innen angezeigt wird,
     * sobald ein Timer gestartet wurde.
     */
    private Notification getNotificationForForegroundService() {
        Intent notificationIntent = new Intent(this, EggTimerActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        return new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                        .setContentTitle(getText(R.string.foreground_notification_title))
                        .setContentText(getText(R.string.foreground_notification_text))
                        .setSmallIcon(R.drawable.ic_timer_icon_foreground)
                        .setContentIntent(pendingIntent)
                        .setTicker(getText(R.string.foreground_notification_ticker))
                        .build();
    }


    /**
     * Erstellt und konfiguriert die Benachrichtigung, die Nutzer*innen angezeigt wird,
     * sobald ein Timer abgelaufen ist.
     */
    private Notification getNotificationForFinishedTimer() {
        Intent notificationIntent = new Intent(this, EggTimerActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        return new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(getText(R.string.timer_finished_notification_title))
                .setContentText(getText(R.string.timer_finished_notification_text))
                .setSmallIcon(R.drawable.ic_timer_icon_foreground)
                .setContentIntent(pendingIntent)
                .build();
    }
}
