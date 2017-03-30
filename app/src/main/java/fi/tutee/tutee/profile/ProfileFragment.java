package fi.tutee.tutee.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
    private boolean avatarChanged = false;
    private Uri avatarUri;

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
                    presenter.changeAvatar(Uri.parse(avatarSrc.toString()));
                }
            }
        });

        changeAvatar = (Button) root.findViewById(R.id.change_avatar);

        changeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Picture"), CHANGE_AVATAR);
            }
        });

        return root;
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
    public void onAvatarSuccess() {}

    @Override
    public void onUpdateFailure() {
        edit.setEnabled(true);
        String errorMessage = "Something went wrong!";
        Snackbar.make(getView(), errorMessage, Snackbar.LENGTH_LONG).show();
    }
}
