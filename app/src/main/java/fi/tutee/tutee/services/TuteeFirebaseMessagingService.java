package fi.tutee.tutee.services;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import java.util.Map;
import fi.tutee.tutee.data.entities.events.GeneralMessage;

public class TuteeFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {
            Object event = new Object();
            Gson gson = new Gson();

            Map<String, String> dataMap = remoteMessage.getData();
            String type = dataMap.get("type");
            String payload = dataMap.get("payload");

            if (type == "general") {
                event = gson.fromJson(payload, GeneralMessage.class);
            }

            EventBus.getDefault().post(event);
        }
    }
}
