package edu.iastate.cpre388.lecture24_servicecommunication;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;

public class MessengerService extends Service {
    private final Messenger mMessenger = new Messenger(new IncomingHandler());
    public static final int MSG_GO = 1;
    public static final int MSG_PAUSE = 2;

    public MessengerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    private class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_GO:
                    Toast.makeText(
                            MessengerService.this,
                            "Go received by service. Speed: " + msg.arg1,
                            Toast.LENGTH_SHORT)
                            .show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
