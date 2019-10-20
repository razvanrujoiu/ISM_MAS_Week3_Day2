package com.example.ism_mas_week3_day2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    EditText firstEditText;
    EditText secondEditText;
    Button btnCall;
    TextView tvResult;

    private  IService mService;
    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "On service connected");
            mService = IService.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "On service disconnected");
            mService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstEditText = findViewById(R.id.firstEditText);
        secondEditText = findViewById(R.id.secondEditText);
        tvResult = findViewById(R.id.resultTv);
        btnCall = findViewById(R.id.callBtn);

        if(mService == null) {
            Intent intent = new Intent(this, RemoteService.class);
            boolean result = getApplicationContext().bindService(intent, mServiceConn, Context.BIND_AUTO_CREATE);
            Log.d(TAG, "Service created: " + result);
        }
    }

    public void btnClick(View view) {
        String first = firstEditText.getText().toString();
        String second = secondEditText.getText().toString();

        try {
            String result = mService.concatenate(first, second);
            tvResult.setText(result);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConn);
    }
}
