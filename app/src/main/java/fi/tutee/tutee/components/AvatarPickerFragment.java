package fi.tutee.tutee.components;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.squareup.picasso.Picasso;

import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;
import fi.tutee.tutee.R;

import static android.app.Activity.RESULT_OK;

public class AvatarPickerFragment extends Fragment {
    private CircleImageView avatarImg;
    private Button changeAvatar;

    private static int CHANGE_AVATAR = 1;
    private static int REQUEST_AVATAR_OPEN = 2;

    private static String DEFAULT_AVATAR = "DEFAULT_AVATAR";

    private OnAvatarChangedListener listener;

    public interface OnAvatarChangedListener {
        void avatarChanged(String avatarUri);
    }

    public void setListener(OnAvatarChangedListener listener) {
        this.listener = listener;
    }

    public static final AvatarPickerFragment newInstance(URI startAvatarURL) {
        AvatarPickerFragment f = new AvatarPickerFragment();
        Bundle b = new Bundle(1);

        if (startAvatarURL != null) {
            b.putString(DEFAULT_AVATAR, startAvatarURL.toString());
        }

        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.component_avatar_picker, container, false);

        avatarImg = (CircleImageView)  root.findViewById(R.id.avatar_image);
        changeAvatar = (Button) root.findViewById(R.id.change_avatar_button);

        String startAvatar = getArguments().getString(DEFAULT_AVATAR);

        if (startAvatar != null) {
            Picasso.with(getActivity()).load(startAvatar).into(avatarImg);
        }

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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode ==  REQUEST_AVATAR_OPEN && grantResults.length  == 1) {
            openFilePicker();
        } else {
            Snackbar.make(getView(), "Please enable file picking", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHANGE_AVATAR && resultCode == RESULT_OK && data.getData() != null ){
            Uri uri = data.getData();
            Picasso.with(getContext()).load(uri.toString()).into(avatarImg);

            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            android.database.Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
            if (cursor == null)
                return;

            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

            String avatarUri = cursor.getString(columnIndex);

            if (this.listener != null) {
                this.listener.avatarChanged(avatarUri);
            }

            cursor.close();
        }
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

    private boolean permittedToOpenFile() {
        return (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestFilePermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
        }, REQUEST_AVATAR_OPEN);
    }
}
