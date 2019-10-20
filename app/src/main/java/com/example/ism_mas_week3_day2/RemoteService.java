package com.example.ism_mas_week3_day2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

public class RemoteService extends Service {

    private final IService.Stub mBinder = new IService.Stub() {

        @Override
        public String concatenate(String a, String b) throws RemoteException {
            return a + " - " + b;
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
