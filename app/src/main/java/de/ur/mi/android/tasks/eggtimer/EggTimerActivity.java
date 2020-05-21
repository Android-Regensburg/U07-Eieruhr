package de.ur.mi.android.tasks.eggtimer;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

import de.mi.eggtimer.R;
import de.ur.mi.android.tasks.eggtimer.broadcast.EggTimerBroadcastListener;
import de.ur.mi.android.tasks.eggtimer.broadcast.EggTimerBroadcastReceiver;
import de.ur.mi.android.tasks.eggtimer.timer.EggOrder;

public class EggTimerActivity extends AppCompatActivity implements EggTimerBroadcastListener {

    private TextView currentTimerValue;
    private Spinner variationSelector;
    private String[] validVariations;

    private EggTimerBroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initApplication();
        initUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterBroadcastReceiver();
    }

    private void registerBroadcastReceiver() {
        unregisterBroadcastReceiver();
        broadcastReceiver = new EggTimerBroadcastReceiver(this);
        this.registerReceiver(broadcastReceiver, EggTimerBroadcastReceiver.getIntentFilter());
    }

    private void unregisterBroadcastReceiver() {
        if (broadcastReceiver != null) {
            this.unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }

    /**
     * Hier können Sie alle Initalisierungsvorgänge implementieren, die keine direkte Verbindung
     * zum User Interface haben.
     */
    private void initApplication() {
        validVariations = getResources().getStringArray(R.array.variation_values);
    }

    private void initUI() {
        setContentView(R.layout.activity_egg_timer);
        currentTimerValue = findViewById(R.id.timer_time);
        variationSelector = findViewById(R.id.egg_variation_selector);
        Button startTimeButton = findViewById(R.id.start_timer_button);
        startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStartTimerButtonClicked();
            }
        });
    }

    /**
     * Diese Methode wird ausgeführt, wenn NutzerInnen auf den Start-Button klicken
     */
    private void onStartTimerButtonClicked() {
        // Hier wird der aktuelle Wert aus der Auswahliste ausgelesen ...
        String userSelectedVariation = variationSelector.getSelectedItem().toString();
        // ... und in ein "Bestellung" umgewandelt. Mit der EggOrder-Klasse werden die
        // unterschiedlichen Zubereitungswünsche, bzw. Zubereitungszeiten repräsentiert. Intern
        // nutzt die Klasse das Enum EggVariation, über das die drei Typen weich-, mittel- und hart-
        // gekochte Eier repräsentiert werden.
        EggOrder eggOrder = EggOrder.fromString(userSelectedVariation, validVariations);
        // Nur wenn eine korrekte Bestellung (= bekannte Zubereitungsdauer) erstellt werden konnte,
        // wird der Timer gestartet.
        if (eggOrder != null) {
            startTimeFor(eggOrder);
        }
    }

    /**
     * Diese Methode startet den Timer für die Zubereitungszeit, die für die gewünschte Zubereitungs-
     * dauer notwendig ist.
     */
    private void startTimeFor(EggOrder order) {
        // An dieser Stelle wissen wir, was für eine Ei-Variation die NutzerInnen kochen möchten.
        // Über order.targetTime erhalten Sie die Zeit in Sekunden, die der Timer laufen soll.
        //
        // Starten Sie hier das nebenläufige Herunterzählen des Timers und verbinden Sie den Thread
        // und später den Service mit dieser Activity.
        // @TODO: Implementieren Sie einen Timer, der parallel zum UI Thread die benötigte Zeit herunterzählt
        // @TODO: Lagern Sie den Timer in einen Service aus, der auch dann weiterläuft, wenn die Activity beendet wird
        Log.d("EGG_TIMER", "Should start EggTimer for " + order.targetTime + " seonds");
    }

    /**
     * Nutzen Sie diese Methode, um die noch verbleibende Zeit des Timers im User Interface dar-
     * zustellen. Die angegebenen Sekunden werden automatisch in das richtige Anzeigeformat über-
     * tragen und korrekt im TextView dargstellt.
     */
    private void updateTimerValue(int remainingSeconds) {
        DecimalFormat df = new DecimalFormat("00");
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        currentTimerValue.setText(df.format(minutes) + ":" + df.format(seconds));
    }

    @Override
    public void onTimerUpdate(int remainingTimeInSeconds) {
        // Hier können Sie die per Broadcast vom Service ausgesendenten Updates verarbeiten
    }

    @Override
    public void onTimerFinished() {
        // Hier können Sie die per Broadcast vom Service ausgesendenten Updates verarbeiten
    }
}
