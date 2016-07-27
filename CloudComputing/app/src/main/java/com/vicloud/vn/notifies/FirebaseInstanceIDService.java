package com.vicloud.vn.notifies;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.vicloud.vn.network.CloudAPIs;

/**
 * Created by huunc on 7/26/16.
 */
public class FirebaseInstanceIDService extends FirebaseInstanceIdService  {
    private static final String TAG = "MyFirebaseIIDService";
    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + token);
        registerToken(token);
    }
    private void registerToken(String token) {
        CloudAPIs.registerFCM(token);
    }
}






