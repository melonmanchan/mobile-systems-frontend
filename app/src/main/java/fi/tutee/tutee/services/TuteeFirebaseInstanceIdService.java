package fi.tutee.tutee.services;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import fi.tutee.tutee.TuteeApplication;

public class TuteeFirebaseInstanceIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();

        if (token != null) {
            TuteeApplication app = (TuteeApplication)  getApplication();
            app.setDeviceToken(token);
        }
    }
}
