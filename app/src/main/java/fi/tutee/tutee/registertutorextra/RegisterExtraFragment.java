package fi.tutee.tutee.registertutorextra;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import fi.tutee.tutee.R;
import fi.tutee.tutee.data.entities.Skill;

/**
 * Created by lehtone1 on 09/03/17.
 */

public class RegisterExtraFragment extends Fragment implements RegisterExtraContract.View {
    private Spinner registerCountrySpin;
    private Spinner registerCitySpin;
    private EditText registerTutorExtraDescription;
    private Button registerTutorExtraBtn;
    private Button registerTutorExtraNewSkillBtn;
    private LinearLayout registerTutorExtraSkillsLayout;


    private RegisterExtraContract.Presenter presenter;

    public RegisterExtraFragment() {
            }

    public static RegisterExtraFragment newInstance() {
            Bundle arguments = new Bundle();
            RegisterExtraFragment fragment = new RegisterExtraFragment();
            fragment.setArguments(arguments);
            return fragment;
            }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

            View root = inflater.inflate(R.layout.content_register_extra, container, false);

            registerCountrySpin = (Spinner) root.findViewById(R.id.registerCountrySpinner);
            registerCitySpin = (Spinner) root.findViewById(R.id.registerCitySpinner);
            registerTutorExtraDescription = (EditText) root.findViewById(R.id.registerTutorExtraDescription);
            registerTutorExtraBtn = (Button) root.findViewById(R.id.registerTutorExtraButton);
            registerTutorExtraNewSkillBtn = (Button) root.findViewById(R.id.registerTutorExtraNewSkillButton);
            registerTutorExtraSkillsLayout = (LinearLayout) root.findViewById(R.id.content_register_tutor_extra_skills_layout);





            registerTutorExtraBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        String country = registerCountrySpin.getSelectedItem().toString();
                        String city = registerCitySpin.getSelectedItem().toString();
                        String description = (String) registerTutorExtraDescription.getText().toString();
                        ArrayList<Skill> skills = new ArrayList<Skill>();

                        for(int index = 0; index < (registerTutorExtraSkillsLayout.getChildCount()); ++index) {
                            LinearLayout skillWrapper = (LinearLayout) ((registerTutorExtraSkillsLayout.getChildAt(index)));

                            EditText skillDescription = (EditText) skillWrapper.getChildAt(0);

                            Spinner skillLevelSpinner = (Spinner) skillWrapper.getChildAt(1);

                            String skillText = (String) skillDescription.getText().toString();

                            String level = (String) skillLevelSpinner.getSelectedItem().toString();

                            skills.add(new Skill(skillText, level));
                        }

                        presenter.registerTutorExtra(country, city, description, skills);




                    /**
                        if (!TextUtils.isEmpty(description) {
                        presenter.register(firstName, lastName, email, password, "TUTOR");
                        }
                     **/
                }

            });


            registerTutorExtraNewSkillBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registerTutorExtraSkillsLayout.addView(createNewSkillLayout());
                }

            });

            setPreferedAdress();

            registerCountrySpin.post(new Runnable() {
                @Override
                public void run() {
                    registerCountrySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            getCitiesFromJson((String) parent.getItemAtPosition(position));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

                }
            });




            return root;

    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("countriesToCities.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void getCountriesFromJson() {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            Iterator<String> iterator = obj.keys();
            ArrayAdapter<String> adapter;
            List<String> list;

            list = new ArrayList<String>();
            while (iterator.hasNext()) {
                list.add(iterator.next());
            }

            Collections.sort(list);

            adapter = new ArrayAdapter<String>(
                    this.getActivity(), android.R.layout.simple_spinner_item, list);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            registerCountrySpin.setAdapter(adapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getCitiesFromJson(String country) {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());


            JSONArray jsonArray;
            ArrayAdapter<String> adapter;
            List<String> list;

            jsonArray = obj.getJSONArray(country);

            list = new ArrayList<String>();
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getString(i));
            }

            Collections.sort(list);

            adapter = new ArrayAdapter<String>(
                    this.getActivity(), android.R.layout.simple_spinner_item, list);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            registerCitySpin.setAdapter(adapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setPreferedAdress() {

        Geocoder geocoder;
        LocationManager lm;

        lm = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);

        getCountriesFromJson();
        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        double longitude = location.getLongitude();

        double latitude = location.getLatitude();


        try {
            geocoder = new Geocoder(this.getActivity(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            Address obj = addresses.get(0);

            String country = obj.getCountryName();
            String city = obj.getLocality();

            registerCountrySpin.setSelection(((ArrayAdapter) registerCountrySpin.getAdapter()).getPosition(country));
            getCitiesFromJson(country);
            registerCitySpin.setSelection(((ArrayAdapter) registerCitySpin.getAdapter()).getPosition(city));





        } catch (IOException e) {
            e.printStackTrace();
        }


    }




    private LinearLayout createNewSkillLayout() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout newLayout = (LinearLayout) inflater.inflate(R.layout.content_one_skill_layout, null);
        return newLayout;

    }





    @Override
    public void setPresenter(RegisterExtraContract.Presenter presenter) {
            this.presenter = presenter;
            }

    @Override
    public void onRegisterSuccess() {
            System.out.println("registersuccess");
            }

    @Override
    public void onRegisterFail() {
            System.out.println("registerfail");

            }
            }

