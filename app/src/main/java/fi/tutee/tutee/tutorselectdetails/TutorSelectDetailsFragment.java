package fi.tutee.tutee.tutorselectdetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fi.tutee.tutee.R;
import fi.tutee.tutee.data.entities.APIError;
import fi.tutee.tutee.data.entities.User;
import fi.tutee.tutee.reservecalendar.ReserveCalendarActivity;

/**
 * Created by mat on 12/04/2017.
 */

public class TutorSelectDetailsFragment extends Fragment implements  TutorSelectDetailsContract.View {
    private TutorSelectDetailsContract.Presenter presenter;
    private User user;

    private FloatingActionButton chooseTutor;
    private FloatingActionButton tutorChosen;
    private ImageView userImage;
    private TextView userName;
    private TextView userDescription;
    private TextView tutorPrice;
    private Button availableTimes;
    private boolean paired = false;

    public static String TUTOR_ID = "fi.tutee.tutee.TUTOR_ID";
    public static String TUTOR_PRICE = "fi.tutee.tutee.TUTOR_PRICE";
    public static String ALREADY_PAIRED = "fi.tutee.tutee.ALREADY_PAIRED";

    public static TutorSelectDetailsFragment newInstance(int userID) {
        Bundle arguments = new Bundle(1);
        arguments.putInt(TUTOR_ID, userID);
        TutorSelectDetailsFragment fragment = new TutorSelectDetailsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void setPresenter(TutorSelectDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.content_tutor_select_details, container, false);

        userImage = (ImageView) root.findViewById(R.id.tutor_select_details_avatar);
        userName = (TextView) root.findViewById(R.id.tutor_select_details_name);
        userDescription = (TextView) root.findViewById(R.id.tutor_select_details_description);
        chooseTutor = (FloatingActionButton)  root.findViewById(R.id.tutor_select_details_choose_tutor);
        tutorChosen = (FloatingActionButton)  root.findViewById(R.id.tutor_select_details_tutor_chosen);
        tutorPrice = (TextView) root.findViewById(R.id.tutor_price);
        availableTimes = (Button) root.findViewById(R.id.available_times);

        availableTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReserveCalendarActivity.class);
                intent.putExtra(TUTOR_ID, getArguments().getInt(TUTOR_ID));
                intent.putExtra(ALREADY_PAIRED, paired);
                intent.putExtra(TUTOR_PRICE, user.getPrice());
                startActivity(intent);
            }
        });


        if (!presenter.getCurrentUser().isTutor()) {
            chooseTutor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chooseTutor.setEnabled(false);
                    chooseTutor.setAlpha(0.5f);

                    presenter.pairWithTutor(user.getId());
                }
            });
        } else {
            chooseTutor.setVisibility(View.GONE);
        }

        this.presenter.getTutorByID(getArguments().getInt(TUTOR_ID));

        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void setTutor(User user) {
        this.user = user;

        if (presenter.alreadyPairedWith(user)) {
            paired = true;
            chooseTutor.setVisibility(View.GONE);
            tutorChosen.setVisibility(View.VISIBLE);
        }

        Picasso.with(getContext()).load(user.getAvatar().toString()).into(userImage);
        userName.setText(user.getFirstName() + " " + user.getLastName());
        userDescription.setText(user.getDescription());
        tutorPrice.setText(user.getPrice() + "€");
    }

    @Override
    public void pairTutorSucceeded() {
        Snackbar.make(getView(), "Pairing with tutor succeeded!", Snackbar.LENGTH_LONG).show();
        paired = true;
    }

    @Override
    public void pairTutorFailed(ArrayList<APIError> errors) {
        chooseTutor.setEnabled(true);
        chooseTutor.setAlpha(1.0f);

        String errorMessage = "Fetching tutors failed!";

        if (errors != null) {
            errorMessage = errors.get(0).getMessage();
        }

        Snackbar.make(getView(), errorMessage, Snackbar.LENGTH_LONG).show();
    }
}
