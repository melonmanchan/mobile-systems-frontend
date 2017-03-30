package fi.tutee.tutee.register;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import fi.tutee.tutee.R;
import fi.tutee.tutee.data.entities.APIError;
import fi.tutee.tutee.home.HomeActivity;
import fi.tutee.tutee.registertutorextra.RegisterExtraActivity;
import fi.tutee.tutee.usertypeselection.UserTypeSelectionFragment;
import fi.tutee.tutee.utils.ActivityUtils;

import static android.app.Activity.RESULT_OK;

public class RegisterFragment extends Fragment implements RegisterContract.View {
    private ImageView registerImgView;
    private Button registerLoadPictureBtn;
    private EditText registerEmail;
    private EditText registerFirstname;
    private EditText registerLastname;
    private TextInputEditText registerPassword;
    private Button registerBtn;
    private int RESULT_LOAD_IMAGE = 1;

    private boolean isTutor;

    private RegisterContract.Presenter presenter;

    public RegisterFragment() {}

    public static RegisterFragment newInstance(boolean isTutor) {
        Bundle arguments = new Bundle();
        arguments.putBoolean(UserTypeSelectionFragment.IS_TUTOR, isTutor);
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.content_register, container, false);

        this.isTutor = getArguments().getBoolean(UserTypeSelectionFragment.IS_TUTOR, false);

        registerImgView = (ImageView) root.findViewById(R.id.registerImgView);
        registerLoadPictureBtn = (Button) root.findViewById(R.id.registerLoadPictureButton);
        registerEmail = (EditText) root.findViewById(R.id.registerEmail);
        registerFirstname = (EditText) root.findViewById(R.id.registerFirstname);
        registerLastname = (EditText) root.findViewById(R.id.registerLastname);
        registerPassword = (TextInputEditText) root.findViewById(R.id.registerPassword);
        registerBtn = (Button) root.findViewById(R.id.registerButton);


        //registerImgView.setDrawingCacheEnabled(true);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = registerEmail.getText().toString();
                String password = registerPassword.getText().toString();
                String firstName = registerFirstname.getText().toString();
                String lastName = registerLastname.getText().toString();
                //Bitmap profilePicture = registerImgView.getDrawingCache();
                Bitmap profilePicture = ((BitmapDrawable) registerImgView.getDrawable()).getBitmap();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)
                        && !TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)) {
                    registerBtn.setEnabled(false);
                    ActivityUtils.hideKeyboard(getActivity());
                    presenter.register(firstName, lastName, email, password, isTutor ? "TUTOR" : "TUTEE", profilePicture );
                }
            }
        });

        registerLoadPictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data.getData() != null ){
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), uri);
                registerImgView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onRegisterSuccess() {
        if (this.isTutor) {
            Intent intent = new Intent(getContext(), RegisterExtraActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getContext(), HomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onRegisterFail(ArrayList<APIError> errors) {
        String errorMessage = "Something went wrong!";

        registerBtn.setEnabled(true);

        if (errors != null && errors.size() > 0) {
            errorMessage = errors.get(0).getMessage();
        }

        Snackbar.make(getView(), errorMessage, Snackbar.LENGTH_LONG).show();
    }
}
