package edu.iastate.cpre388.lecture24_servicecommunication;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class ReadIntentService extends IntentService {
    private static final String TAG = "ReadIntentService";

    // Actions for intent commands
    public static final String ACTION_GO = "edu.iastate.cpre388.lecture24_servicecommunication.action.go";
    public static final String ACTION_PAUSE = "edu.iastate.cpre388.lecture24_servicecommunication.action.pause";

    /** Parameter for ACTION_GO */
    public static final String EXTRA_SPEED = "edu.iastate.cpre388.lecture24_servicecommunication.extra.speed";

    public ReadIntentService() {
        super("ReadIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GO.equals(action)) {
                int speed = intent.getIntExtra(EXTRA_SPEED, 0);
                handleActionGo(speed);
            } else if (ACTION_PAUSE.equals(action)) {
                handleActionPause();
            }
        }
    }

    private void handleActionGo(int speed) {
        Log.d(TAG, "Received go command. Speed: " + speed);
    }

    private void handleActionPause() {
        Log.d(TAG, "Received pause command.");
    }
}
