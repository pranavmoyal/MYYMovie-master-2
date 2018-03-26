package com.example.niraj.mapplication;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by baltech on 15/09/17.
 */

public class Utilities extends Activity {

    private static ProgressBarHandler progressBarHandler;

    public static boolean checkNetworkConnection(Context paramContext) {
        int i = 1;
        boolean flag = true;
        ConnectivityManager connectivity = (ConnectivityManager) paramContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo localNetworkInfo1 = connectivity.getNetworkInfo(i);
            NetworkInfo localNetworkInfo2 = connectivity.getActiveNetworkInfo();
            NetworkInfo[] info = connectivity.getAllNetworkInfo();

            System.out.println("wifi" + localNetworkInfo1.isAvailable());
            System.out.println("info" + localNetworkInfo2);

            if (((localNetworkInfo2 == null) || (!localNetworkInfo2
                    .isConnected())) && (!localNetworkInfo1.isAvailable()))
                i = 0;
            if (info != null) {
                for (int j = 0; j < info.length; j++)
                    if (info[j].getState() == NetworkInfo.State.CONNECTED) {
                        i = 1;
                        break;
                    } else
                        i = 0;
            }

        } else
            i = 0;

        if (i == 0)
            flag = false;
        if (i == 1)
            flag = true;

        return flag;
    }

    public static void showProgressDialog(Context context) {
        if (progressBarHandler != null) {
            progressBarHandler.hide();
        }
        progressBarHandler = new ProgressBarHandler(context);
        progressBarHandler.show();
    }

    public static void dismissProgressDialog() {
        if (progressBarHandler != null) {
            progressBarHandler.hide();
        }
        progressBarHandler = null;
    }
}
