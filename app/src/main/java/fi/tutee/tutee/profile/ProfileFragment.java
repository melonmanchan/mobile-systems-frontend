package fi.tutee.tutee.profile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;

import fi.tutee.tutee.R;
import fi.tutee.tutee.data.entities.APIError;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.usertypeselection.UserTypeSelectionFragment;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment implements ProfileContract.View  {
    private ProfileContract.Presenter presenter;

    private static int CHANGE_AVATAR = 1;
    private static int REQUEST_AVATAR_OPEN = 2;

    private boolean avatarChanged = false;
    private String avatarUri;

    private ImageView avatar;
    private TextView firstname;
    private TextView lastname;
    private TextView email;
    private Button edit;
    private Button changeAvatar;
    private User user;

    public ProfileFragment() {}

    public static ProfileFragment newInstance(boolean isTutor) {
        Bundle arguments = new Bundle();
        arguments.putBoolean(UserTypeSelectionFragment.IS_TUTOR, isTutor);
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.content_profile, container, false);

        firstname = (EditText) root.findViewById(R.id.profile_firstname);
        firstname.setText(user.getFirstName());

        lastname = (EditText) root.findViewById(R.id.profile_lastname);
        lastname.setText(user.getLastName());

        email = (EditText) root.findViewById(R.id.profile_email);
        email.setText(user.getEmail());

        avatar = (ImageView)  root.findViewById(R.id.profile_avatar);
        final URI avatarSrc = user.getAvatar();
        Picasso.with(getActivity()).load(avatarSrc.toString()).into(avatar);

        edit = (Button) root.findViewById(R.id.save_profile);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setEnabled(false);

                String firstName = firstname.getText().toString();
                String lastName = lastname.getText().toString();

                if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)) {
                    presenter.updateUser(firstName, lastName);
                }

                if (avatarChanged) {
                    presenter.changeAvatar(avatarUri);
                }
            }
        });

        changeAvatar = (Button) root.findViewById(R.id.change_avatar);

        changeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (permittedToOpenFile()) {
                    openFilePicker();
                } else {
                    requestFilePermission();
                }
            }
        });

        return root;
    }

    private void openFilePicker() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Avatar");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, CHANGE_AVATAR);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode ==  REQUEST_AVATAR_OPEN && grantResults.length  == 1) {
            openFilePicker();
        } else {
            Snackbar.make(getView(), "Please enable file picking", Snackbar.LENGTH_LONG).show();
        }
    }

    private boolean permittedToOpenFile() {
        return (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestFilePermission() {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
            }, REQUEST_AVATAR_OPEN);
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHANGE_AVATAR && resultCode == RESULT_OK && data.getData() != null ){
            avatarChanged = true;
            Uri uri = data.getData();
            Picasso.with(getContext()).load(uri.toString()).into(avatar);

            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            android.database.Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
            if (cursor == null)
                return;

            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            avatarUri = cursor.getString(columnIndex);
            cursor.close();
        }
    }

    @Override
    public void onUpdateSuccess() {
        edit.setEnabled(true);
        String errorMessage = "Successfully updated!";
        Snackbar.make(getView(), errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onAvatarFailed(ArrayList<APIError> errors) {
        String errorMessage = "Changing avatar failed!";
        Snackbar.make(getView(), errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onAvatarSuccess() {
        String errorMessage = "Changing avatar succeeded!";
        Snackbar.make(getView(), errorMessage, Snackbar.LENGTH_LONG).show();
        avatarChanged = false;
    }

    @Override
    public void onUpdateFailure() {
        edit.setEnabled(true);
        String errorMessage = "Something went wrong!";
        Snackbar.make(getView(), errorMessage, Snackbar.LENGTH_LONG).show();
    }
}
