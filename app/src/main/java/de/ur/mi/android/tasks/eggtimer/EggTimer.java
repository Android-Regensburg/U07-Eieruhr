package de.ur.mi.android.tasks.eggtimer;

import android.content.ContentValues;
import android.os.CountDownTimer;

import de.mi.eggtimer.R;
import de.ur.mi.android.tasks.eggtimer.listener.OnEggTimerStatusChangedListener;

public class EggTimer {

   // Dies sind Wertpaare zur Zuweisung von Kochzeiten zu
   // den ausgewählten Punkten im Spinner, der im Prinzip
   // die Ordnung der Begriffe im zugehörigen string-Array (strings.xml)
   // übernimmt. Deshalb ist die Arrayposition 1 (also Index 0)
   // hier ein "kleines Ei"
   public static final int EGGSIZE_SMALL = 0;
   public static final int VALUE_EGGSIZE_SMALL = 1; // 1 Minute

   public static final int EGGSIZE_MEDIUM = 1;
   public static final int VALUE_EGGSIZE_MEDIUM = 3;

   public static final int EGGSIZE_BIG = 2;
   public static final int VALUE_EGGSIZE_BIG = 5;

   public static final int VALUE_EGGSIZE_DEFAULT = 2; // sofern keine Auswahl getroffen wird

   // Dieselbe Zuweisung gilt für den Zweiten Spinner:
   // Array (0: kurz bzw. weich, 1: lang bzw. hart)
   public static final int PRODUCTIONTIME_SHORT = 0;
   public static final int VALUE_PRODUCTIONTIME_SHORT = 2;

   public static final int PRODUCTIONTIME_LONG = 1;
   public static final int VALUE_PRODUCTIONTIME_LONG = 4;

   public static final int VALUE_PRODUCTIONTIME_DEFAULT = 3;

   // Läuft der Timer gerade? Auslagerung der Werte in Configdatei
   public static final int TIMERSTATUS_STOPPED = 0;
   public static final int TIMERSTATUS_RUNNING = 1;

   // Schlüssel für das Contentvalues-Objekt der MIN:SEC Darstellung
   // werden hier hinterlegt, Fehler zu vermeiden
   public static final String KEY_MINUTES = "Minuten";
   public static final String KEY_SECONDS = "Sekunden";


   private MyCountDownTimer myCountDownTimer;
   private OnEggTimerStatusChangedListener onEggTimerStatusChangedListener;

   private int status = TIMERSTATUS_STOPPED;

   public EggTimer(OnEggTimerStatusChangedListener onEggTimerStatusChangedListener, int selectedEggSize, int selectedCookTime) {
      // Belegen der noch nicht initialisierten Klassenvariablen (countDownIntervall = 1000)

   }

   private long calculateEggProductionTime(int selectedEggSize, int selectedCookSize) {
      // Zeit in Millisekunden - vorerst in Minuten (in config werden minuten
      // gespeichert)
      long timeInMinutes = 0;

      // Die Zuweisung der Kochzeit zu einem ausgewählten Punkt
      // im Spinner funktioniert folgendermaßen:
      // StringArray hält die Label in Spinner-Reihenfolge
      // Spinner wird nach Array gefällt
      // Positionen der Einträge im Spinner entsprechen denen im Array
      // die Zuweisung der Werte zu den Labels geschieht mittels der
      // Values-Klasse in welcher die Indizes in "sprechenden" Konstanten gespeichert
      // werden. Diese Konstanten existieren als Paar. Die Partnerkonstante beginnt
      // entsprechend mit VALUE_

      if(selectedEggSize == EGGSIZE_SMALL){
         timeInMinutes = VALUE_EGGSIZE_SMALL;
      }


      // Millisekunden erzeugen
      return timeInMinutes * 60000;
   }

   public void start() {
      myCountDownTimer.start();
   }

   public void cancel() {
      myCountDownTimer.cancel();
   }

   public int getTimerStatus() {
       return status;
   }

   class MyCountDownTimer extends CountDownTimer {

      private long millisInFuture;

      public MyCountDownTimer(long millisInFuture,
                              long countDownInterval) {
         super(millisInFuture, countDownInterval);

         // Das Feld millisInFutre belegen
         // Zeitanzeige aktualisieren
      }

      @Override
      public void onFinish() {

         // Über eine Interface-Methode die Zeitanzeige zurücksetzen ("00:00")
         // Status des Timers aktualisieren
         // Nachricht mittels des Interfaces, dass der Timer abgelaufen ist


      }

      @Override
      public void onTick(long millisUntilFinished) {
         // Status des Timers aktualisieren
         // millisInFuture anpassen
         // Zeitanzeige aktualisieren

      }

      private void updateTimerView() {

         // Mit Hilfe der Methode getFormattedTime ein ContentValues-Objekt erzeugen, die enthaltenen
         // Extras auslesen und über die entsprechende Interface-Methode die Anzeige aktualisieren (minuten:sekunden)



         onEggTimerStatusChangedListener.onUpdateCountdownView(null);
      }

      private ContentValues getFormattedTime() {

         // ContentValues-Objekt erstellen und diesem die Minuten und Sekunden als
         // Extras hinzufügen. Diese erhält man aus der in millisInFuture gespeicherten
         // Zahl. Dabei ist darauf zu achten, dass stehts die Form "XX:XX" eingehalten wird.
         // Zum Anschluss das erstellte Objekt zurückgeben.

         ContentValues contentValues = new ContentValues();

         int min = (int) (millisInFuture);



         return contentValues;
      }
   }

}
