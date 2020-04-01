package de.ur.mi.android.tasks.eggtimer;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import de.mi.eggtimer.R;
import de.ur.mi.android.tasks.eggtimer.debug.DebugHelper;
import de.ur.mi.android.tasks.eggtimer.listener.OnEggTimerStatusChangedListener;

public class EggTimerActivity extends Activity {

    private TextView timerView;
    private Spinner spinnerEggSize;
    private Spinner spinnerDoneness;
    private Button startAndStop;

    private EggTimerService myEggTimerService;
    private ServiceConnection serviceConnection;

    private boolean isRunning = false;

    private String KEY_SELECTED_SIZE = "selectedSize";
    private String KEY_SELECTED_COOK_TIME = "selectedCookTime";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egg_timer);

        DebugHelper.logDebugMessage("onCreate");

        setupUI();
        createEggTimer();

        initServiceConnection();
    }

    private void createEggTimer() {
        // Es soll ein neues EggTimer-Objekt erstellt werden.(referenz wird nicht benötigt)
        // ihm werden die ausgewählten itemPositions übergeben
    }

    private int[] getSelectedItemPositions() {
        int[] itemPositions = new int[2];

        // Zurückgeben eines Arrays, das die aktuellen Spinnerpositionen enthält.
        // Diese erhält man mit der Methode getSelectedItemPosition

        return itemPositions;
    }

    private void startEggTimerService() {

        // Intent erstellen, der als Extras die Spinnerpositionen erhält (key strings verwenden KEY_SELECTED_SIZE)
        // Die Methode startService mit diesem Intent als Parameter aufrufen.


    }

    private void modifyButtonLayout(String caption, int colorId) {

        startAndStop.getBackground().setColorFilter(colorId, PorterDuff.Mode.MULTIPLY);
        startAndStop.setText(caption);
    }

    private void setupUI() {

        timerView = findViewById(R.id.timerView);
        spinnerEggSize = findViewById(R.id.spinnerEggSize);
        spinnerDoneness = findViewById(R.id.spinnerDoneness);
        startAndStop = findViewById(R.id.button);

        initButton();
        initSpinner(spinnerEggSize, R.array.eggSizeArray);
        initSpinner(spinnerDoneness, R.array.donenessArray);
    }

    private void initButton() {

        modifyButtonLayout(getString(R.string.start), Color.GREEN);

        startAndStop.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // Falls der Timer gerade läuft, soll dieser über die geeignete Methode des Services gestoppt werden.
                // Der Button soll mit der Methode modifyButtonLayout entsprechend verändert werden
                // Zuletzt soll ein neuer EggTimer instantiiert werden

                // Falls der Timer nicht läuft, soll der Button verändert und ein Timer über den Service gestartet werden.

                // In beiden Fällen muss der Status, ob der Timer gerade läuft, verändert werden


                    DebugHelper.logDebugMessage("Service starting");

            }
        });

    }


    // diese Methode führt zweimal beinahe identischen Code für die zwei
    // Spinner aus und wurde deshalb parametrisiert (spinner und array id)
    private void initSpinner(Spinner spinner, int arrayID) {

        // Adaptersetup
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                EggTimerActivity.this, arrayID,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Benötigten Listener Implementieren und die Methoden überschreiben
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View v,
                                       int position, long arg3) {

                if (!isRunning) {
                    // wenn der Timer nicht läuft, muss die Zeit für den neu
                    // ausgewählten wert immer neu berechnet werden ->
                    // hierzu wird immer ein Timerobjekt mit entsprechender Zeit erstellt
                    // dieses ist dann sofort bereit zum Start
                    createEggTimer();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initServiceConnection() {

        serviceConnection = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
                System.out.println("Service disconnected");
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                System.out.println("Service connected");

                myEggTimerService = ((EggTimerService.LocalBinder) service).getBinder();
                if (myEggTimerService != null) {
                    //Activity beim Service als OnEggTimerStatusChangedListener registrieren
                }
            }
        };
    }


    @Override
    protected void onPause() {
        DebugHelper.logDebugMessage("onPause");
        //ActivityVisibilityState verwenden um Sichtbarkeit einzustellen
        unbindService(serviceConnection);
        super.onPause();
    }

    @Override
    protected void onResume() {
        DebugHelper.logDebugMessage("onResume");
        //ActivityVisibilityState verwenden um Sichtbarkeit einzustellen
        bindService(new Intent(EggTimerActivity.this, EggTimerService.class), serviceConnection, BIND_AUTO_CREATE);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        DebugHelper.logDebugMessage("onDestroy");
		stopService(new Intent(EggTimerActivity.this, EggTimerService.class));
        super.onDestroy();
    }
}
