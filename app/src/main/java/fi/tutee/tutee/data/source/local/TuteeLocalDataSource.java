package fi.tutee.tutee.data.source.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.SparseArray;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import fi.tutee.tutee.data.entities.APIResponse;
import fi.tutee.tutee.data.entities.AuthResponse;
import fi.tutee.tutee.data.entities.CreateFreeTimeRequest;
import fi.tutee.tutee.data.entities.CreateMessageRequest;
import fi.tutee.tutee.data.entities.CreateTutorshipRequest;
import fi.tutee.tutee.data.entities.DeviceRegisterRequest;
import fi.tutee.tutee.data.entities.LoginRequest;
import fi.tutee.tutee.data.entities.RegisterRequest;
import fi.tutee.tutee.data.entities.RegisterTutorExtraRequest;
import fi.tutee.tutee.data.entities.Subject;
import fi.tutee.tutee.data.entities.TimesResponse;
import fi.tutee.tutee.data.entities.TutorshipsResponse;
import fi.tutee.tutee.data.entities.UpdateUserRequest;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.data.entities.events.GeneralMessage;
import fi.tutee.tutee.data.entities.events.LatestMessagesChangedEvent;
import fi.tutee.tutee.data.source.TuteeDataSource;
import okhttp3.MultipartBody;
import retrofit2.Callback;
import retrofit2.Response;

public class TuteeLocalDataSource implements TuteeDataSource {
    private static TuteeLocalDataSource instance;
    private SharedPreferences pref;
    private Gson gson;

    private HashSet<Integer> tutorIDs = new HashSet<Integer>();
    private HashSet<Integer> tuteeIDs = new HashSet<Integer>();
    private ArrayList<Subject> cachedSubjects = new ArrayList<Subject>();
    private ArrayList<GeneralMessage> cachedLatestMessages = new ArrayList<GeneralMessage>();
    private SparseArray<User> cachedUsers = new SparseArray<User>();
    private ArrayList<WeekViewEvent> cachedFreeTimes = new ArrayList<WeekViewEvent>();

    private static String PERSIST_LOGIN_DATA = "fi.tutee.tutee.PERSIST_LOGIN_DATA";

    private Context context;
    private TimesResponse cachedTimes;

    public TuteeLocalDataSource(Context context) {
        this.context = context;

        gson = new Gson();
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        EventBus.getDefault().register(this);

    }

    public static TuteeLocalDataSource getInstance(Context context) {
        if (instance == null) {
            instance = new TuteeLocalDataSource(context);
        }

        return instance;
    }

    @Override
    public void basicLogin(LoginRequest req, Callback<APIResponse<AuthResponse>> cb) {
        cb.onFailure(null, new UnsupportedOperationException("Register not implemented for local data source"));
    }

    @Override
    public void googleLogin(String token) {
    }

    @Override
    public void changeAvatar(MultipartBody.Part body, Callback<APIResponse<User>> cb) {
        cb.onFailure(null, new Exception("Cannot change local avatar"));
    }

    @Override
    public void register(RegisterRequest req, Callback<APIResponse<AuthResponse>> cb) {
        cb.onFailure(null, new UnsupportedOperationException("Register not implemented for local data source"));
    }

    @Override
    public void registerTutorExtra(RegisterTutorExtraRequest req, Callback<APIResponse> cb) {
        cb.onFailure(null, new UnsupportedOperationException("Register not implemented for local data source"));
    }

    @Override
    public void registerUserDevice(DeviceRegisterRequest req) {
        // No-op
    }

    @Override
    public void removeUserDevice(DeviceRegisterRequest req) {
        // No-op
    }

    @Override
    public void getUser(int userID, Callback<APIResponse<User>> cb) {
        APIResponse<User> apiResponse = new APIResponse<User>();
        User user = this.cachedUsers.get(userID);
        apiResponse.setResponse(user);
        apiResponse.setStatus(200);
        Response<APIResponse<User>> resp = retrofit2.Response.success(apiResponse);
        cb.onResponse(null, resp);
    }

    @Override
    public void logOut() {
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(PERSIST_LOGIN_DATA);
        editor.commit();

        cachedSubjects.clear();
        cachedLatestMessages.clear();
        cachedUsers.clear();
        tutorIDs.clear();
        tuteeIDs.clear();
        cachedFreeTimes.clear();
        cachedTimes = null;

        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(GeneralMessage message) {
        updateCachedMessage(message);
        EventBus.getDefault().post(new LatestMessagesChangedEvent());
    }

    public void persistUserLogin(AuthResponse authResponse) {
        String json = gson.toJson(authResponse);

        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PERSIST_LOGIN_DATA, json);
        editor.commit();
    }

    @Override
    public void updateUser(UpdateUserRequest req, Callback<APIResponse<User>> cb) {
        AuthResponse persistedAuthResponse = fetchPersistedUserLogin();

        if (persistedAuthResponse != null) {
            persistedAuthResponse.setUser(req.getUser());
            persistUserLogin(persistedAuthResponse);
        }
    }

    @Override
    public void getSubjects(Callback<APIResponse<ArrayList<Subject>>> cb) {
        if (this.hasCachedSubjects()) {
            APIResponse<ArrayList<Subject>> apiResponse = new APIResponse<ArrayList<Subject>>();
            apiResponse.setResponse(cachedSubjects);
            apiResponse.setStatus(200);
            Response<APIResponse<ArrayList<Subject>>> resp = retrofit2.Response.success(apiResponse);
            cb.onResponse(null, resp);
        }
    }

    @Override
    public void getTutorsBySubject(int subjectID, Callback<APIResponse<ArrayList<User>>> cb) {
        cb.onFailure(null, new Exception("Not yet implemented!"));
    }

    @Override
    public void getMessagesFrom(int userId, int fromOffset, int toOffset, Callback<APIResponse<ArrayList<GeneralMessage>>> cb) {
        cb.onFailure(null, new Exception("Not yet implemented!"));
    }

    @Override
    public void createTutorship(CreateTutorshipRequest req, Callback<APIResponse> cb) {
        cb.onFailure(null, new Exception("Cannot perform action locally"));
    }

    @Override
    public void getTutorships(Callback<APIResponse<TutorshipsResponse>> cb) {
        if (this.hasCachedTutorships()) {
            TutorshipsResponse tutorshipsResponse = new TutorshipsResponse();

            tutorshipsResponse.setTutees(getCachedTutees());
            tutorshipsResponse.setTutors(getCachedTutors());

            APIResponse<TutorshipsResponse> apiResponse = new APIResponse<TutorshipsResponse>();
            apiResponse.setResponse(tutorshipsResponse);
            apiResponse.setStatus(200);
            Response<APIResponse<TutorshipsResponse>> resp = retrofit2.Response.success(apiResponse);
            cb.onResponse(null, resp);
        }
    }

    @Override
    public void createMessage(CreateMessageRequest req, Callback<APIResponse<GeneralMessage>> cb) {
        cb.onFailure(null, new Exception("Cannot create message locally"));
    }

    @Override
    public void getLatestMessages(Callback<APIResponse<ArrayList<GeneralMessage>>> cb) {
        if (this.hasCachedSubjects()) {
            APIResponse<ArrayList<GeneralMessage>> apiResponse = new APIResponse<ArrayList<GeneralMessage>>();
            apiResponse.setResponse(cachedLatestMessages);
            apiResponse.setStatus(200);
            Response<APIResponse<ArrayList<GeneralMessage>>> resp = retrofit2.Response.success(apiResponse);
            cb.onResponse(null, resp);
        }
    }

    @Override
    public void createFreeTime(CreateFreeTimeRequest req, Callback<APIResponse<WeekViewEvent>> cb) {
        cb.onFailure(null, new Exception("Cannot set free time locally"));
    }

    @Override
    public void removeTime(WeekViewEvent event, Callback<APIResponse> cb) {
        if (this.cachedTimes != null) {
            ArrayList<WeekViewEvent> events = this.cachedTimes.getOwnEvents();
            events.remove(event);
            cachedTimes.setOwnEvents(events);
        }
    }

    @Override
    public void getFreeTimes(int tutorID, Callback<APIResponse<ArrayList<WeekViewEvent>>> cb) {
        if (this.hasCachedFreeTimes(tutorID)) {
            APIResponse<ArrayList<WeekViewEvent>> apiResponse = new APIResponse<ArrayList<WeekViewEvent>>();
            apiResponse.setResponse(cachedFreeTimes);
            apiResponse.setStatus(200);
            Response<APIResponse<ArrayList<WeekViewEvent>>> resp = retrofit2.Response.success(apiResponse);
            cb.onResponse(null, resp);
        }
    }

    @Override
    public void reserveTime(WeekViewEvent event, Callback<APIResponse> cb) {
        cb.onFailure(null, new Exception("Cannot reserve time locally"));
    }

    @Override
    public void getTimes(Callback<APIResponse<TimesResponse>> cb) {
        if (this.hasCachedTimes()) {
            APIResponse<TimesResponse> apiResponse = new APIResponse<TimesResponse>();
            apiResponse.setResponse(cachedTimes);
            apiResponse.setStatus(200);
            Response<APIResponse<TimesResponse>> resp = retrofit2.Response.success(apiResponse);
            cb.onResponse(null, resp);
        }
    }

    @Override
    public boolean isUserTutor(User user) {
        return tutorIDs.contains(user.getId());
    }

    public ArrayList<User> getCachedTutees() {
        ArrayList<User> users = new ArrayList<>();

        for (Integer i: tuteeIDs) {
            users.add(cachedUsers.get(i));
        }

        return  users;
    }


    public void addCachedReservedTime(WeekViewEvent event) {
        ArrayList<WeekViewEvent> events = cachedTimes.getReservedEvents();

        events.add(event);

        cachedTimes.setReservedEvents(events);
    }

    public ArrayList<User> getCachedTutors() {
        ArrayList<User> users = new ArrayList<>();

        for (Integer i: tutorIDs) {
            users.add(cachedUsers.get(i));
        }

        return  users;
    }

    public void setCachedTutors(ArrayList<User> tutors) {
        if (tutorIDs == null) {
            tutorIDs = new HashSet<>();
        }

        for (User u: tutors) {
            tutorIDs.add(u.getId());
        }

        setCachedUsers(tutors);
    }

    public void setCachedTutees(ArrayList<User> tutees) {
        if (tuteeIDs == null) {
            tuteeIDs = new HashSet<>();
        }

        for (User u: tutees) {
            tuteeIDs.add(u.getId());
        }

        setCachedUsers(tutees);
    }

    public void setCachedUsers(ArrayList<User> users) {
        if (cachedUsers == null) {
            cachedUsers = new SparseArray<User>();
        }

        for (User u: users) {
            int id = u.getId();
            cachedUsers.put(id, u);
        }
    }

    public void setCachedLatestMessages(ArrayList<GeneralMessage> cachedLatestMessages) {
        this.cachedLatestMessages = cachedLatestMessages;
    }


    public void markUserAsTutor(int tutorID) {
        if (!tutorIDs.contains(tutorID)) {
            tutorIDs.add(tutorID);
        }

        GeneralMessage msg = new GeneralMessage();

        msg.setSentAt(new Date());
        msg.setReceiverId(tutorID);
        msg.setSenderId(tutorID);
        msg.setContent("");
        msg.setId(0);

        cachedLatestMessages.add(msg);
    }

    public void setCachedSubjects(ArrayList<Subject> cachedSubjects) {
        this.cachedSubjects = cachedSubjects;
    }

    public boolean hasCachedTutorships() {
        return (this.tuteeIDs != null && this.tutorIDs != null && this.cachedUsers != null
                && this.cachedUsers.size() > 0);
    }

    public boolean hasCachedSubjects() {
        return (this.cachedSubjects != null && this.cachedSubjects.size() > 0);
    }

    public boolean hasCachedLatestMessages() {
        return (this.cachedLatestMessages != null && this.cachedLatestMessages.size() > 0);
    }

    public void updateCachedMessage(GeneralMessage msg) {
        int receiverId = msg.getReceiverId();
        int senderId = msg.getSenderId();
        int i;
        boolean found = false;
        for (i = 0; i < cachedLatestMessages.size(); i++) {
            GeneralMessage message = cachedLatestMessages.get(i);
            int cachedMessageReceiverId = message.getReceiverId();
            int cachedMessageSenderId = message.getSenderId();

            if (receiverId == cachedMessageReceiverId && senderId == cachedMessageSenderId) {
                found = true;
                break;
            } else if (receiverId == cachedMessageSenderId && senderId == cachedMessageReceiverId) {
                found = true;
                break;
            }
        }
        if (found) {
            cachedLatestMessages.set(i, msg);
        }
    }

    public AuthResponse fetchPersistedUserLogin() {
        String json = pref.getString(PERSIST_LOGIN_DATA, null);

        if (json == null) {
            return null;
        }


        try {
            return gson.fromJson(json, AuthResponse.class);
        } catch(JsonSyntaxException ex) {
            this.logOut();
            return null;
        }
    }

    public boolean hasCachedFreeTimes(int tutorID) {
        //TODO:
        return false;
    }

    public void setCachedFreeTimes(int tutorID, ArrayList<WeekViewEvent> events) {
        // TODO:
    }

    public void setCachedTimes(TimesResponse events) {
        //TODO:
        cachedTimes = events;
    }

    public boolean hasCachedTimes() {
        return (cachedTimes != null);
    }


    public void addCachedFreeTime(WeekViewEvent event) {
        ArrayList<WeekViewEvent> ownEvents = cachedTimes.getOwnEvents();

        if (ownEvents == null) {
            ownEvents = new ArrayList<WeekViewEvent>();
        }

        ownEvents.add(event);

        cachedTimes.setOwnEvents(ownEvents);
    }

    public boolean hasCachedReservedTimes() {
        //TODO:
        return (cachedTimes != null && cachedTimes.getReservedEvents() != null);
    }

    public void setCachedReservedTimes(ArrayList<WeekViewEvent> events) {
        //TODO:
    }
}
