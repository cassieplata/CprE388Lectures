package edu.iastate.cpre388.lecture24_servicecommunication;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;


public class ProgressBroadcastService extends IntentService {
    public static final String BROADCAST_ACTION = "edu.iastate.cpre388.lecture24_servicecommunication.action.broadcast";
    public static final String EXTRA_STATUS = "edu.iastate.cpre388.lecture24_servicecommunication.extra.status";

    public ProgressBroadcastService() {
        super("ProgressBroadcastService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Do work
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(1000);
                // Report status
                reportStatus(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void reportStatus(int progress) {
        Intent intent = new Intent(BROADCAST_ACTION);
        intent.putExtra(EXTRA_STATUS, progress);
        // Send a broadcast with the status
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
