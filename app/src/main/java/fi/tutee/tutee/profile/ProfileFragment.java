package fi.tutee.tutee.profile;

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
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.vision.text.Line;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;

import fi.tutee.tutee.R;
import fi.tutee.tutee.components.AvatarPickerFragment;
import fi.tutee.tutee.data.entities.APIError;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.usertypeselection.UserTypeSelectionFragment;
import fi.tutee.tutee.utils.ActivityUtils;

public class ProfileFragment extends Fragment implements ProfileContract.View  {
    private ProfileContract.Presenter presenter;

    private boolean avatarChanged = false;
    private String avatarUri;

    private SeekBar priceSlider;
    private TextView priceDisplay;

    private TextView firstname;
    private TextView lastname;
    private TextView email;
    private TextView description;
    private Button edit;
    private User user;

    private AvatarPickerFragment avatarPickerFragment;

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

        AvatarPickerFragment avatarPickerFragment = (AvatarPickerFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.profile_avatar_picker);

        if (avatarPickerFragment == null) {
            avatarPickerFragment = AvatarPickerFragment.newInstance(user.getAvatar());
            ActivityUtils.addFragmentToActivity(getActivity().getSupportFragmentManager(),
                    avatarPickerFragment, R.id.profile_avatar_picker);

            this.avatarPickerFragment = avatarPickerFragment;
        }

        this.avatarPickerFragment.setListener(new AvatarPickerFragment.OnAvatarChangedListener() {
            @Override
            public void avatarChanged(String newAvatarUri) {
                avatarUri = newAvatarUri;
                avatarChanged = true;
            }
        });

        firstname = (EditText) root.findViewById(R.id.profile_firstname);
        firstname.setText(user.getFirstName());

        lastname = (EditText) root.findViewById(R.id.profile_lastname);
        lastname.setText(user.getLastName());

        email = (EditText) root.findViewById(R.id.profile_email);
        email.setText(user.getEmail());

        description = (EditText) root.findViewById(R.id.profile_description);
        description.setText(user.getDescription());


        priceSlider = (SeekBar) root.findViewById(R.id.price_slider);
        priceDisplay = (TextView) root.findViewById(R.id.price_display);

        if (!user.isTutor()) {
            priceSlider.setVisibility(View.GONE);
            (root.findViewById(R.id.slider_text_container)).setVisibility(View.GONE);
        } else {
            priceSlider.setProgress(user.getPrice());
            priceDisplay.setText(user.getPrice() + "€");
            priceSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    priceDisplay.setText(String.valueOf(progress) + "€");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        }

        edit = (Button) root.findViewById(R.id.save_profile);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setEnabled(false);

                String firstName = firstname.getText().toString();
                String lastName = lastname.getText().toString();
                String desc = description.getText().toString();
                int price = priceSlider.getProgress();

                if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)) {
                    presenter.updateUser(firstName, lastName, desc, price);
                }

                if (avatarChanged) {
                    presenter.changeAvatar(avatarUri);
                }
            }
        });

        return root;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
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
