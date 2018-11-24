package edu.iastate.cpre388.lecture24_servicecommunication;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Messenger mServiceMessenger = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter broadcastFilter = new IntentFilter(ProgressBroadcastService.BROADCAST_ACTION);
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mProgressBroadcastReceiver, broadcastFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Bind to messenger service
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Unbind from messenger service
        unbindService(mConnection);
    }

    public void onReadIntentGo1Click(View v) {
        Intent intent = new Intent(this, ReadIntentService.class);
        intent.setAction(ReadIntentService.ACTION_GO);
        intent.putExtra(ReadIntentService.EXTRA_SPEED, 1);
        startService(intent);
    }

    public void onReadIntentGo5Click(View v) {
        Intent intent = new Intent(this, ReadIntentService.class);
        intent.setAction(ReadIntentService.ACTION_GO);
        intent.putExtra(ReadIntentService.EXTRA_SPEED, 5);
        startService(intent);
    }

    public void onReadIntentPauseClick(View v) {
        Intent intent = new Intent(this, ReadIntentService.class);
        intent.setAction(ReadIntentService.ACTION_PAUSE);
        startService(intent);
    }


    public void onStatusServiceClick(View v) {
        Intent intent = new Intent(this, ProgressBroadcastService.class);
        startService(intent);
    }

    private BroadcastReceiver mProgressBroadcastReceiver = new BroadcastReceiver() {
        // Called when the BroadcastReceiver gets an Intent it's registered to receive
        @Override
        public void onReceive(Context context, Intent intent) {
            int status = intent.getIntExtra(ProgressBroadcastService.EXTRA_STATUS, 0);
            Toast.makeText(context, "Status received: " + status, Toast.LENGTH_SHORT).show();
        }
    };


    public void onMessengerGoClick(View v) {
        if (mServiceMessenger == null) {
            Toast.makeText(this, "Service not bound", Toast.LENGTH_SHORT).show();
            return;
        }

        // Arg 1 is speed, defined by MessengerService
        Message msg = Message.obtain(null, MessengerService.MSG_GO, 1, 0);
        try {
            mServiceMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mServiceMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceMessenger = null;
        }
    };
}
