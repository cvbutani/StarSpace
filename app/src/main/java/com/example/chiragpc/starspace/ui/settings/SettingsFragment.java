package com.example.chiragpc.starspace.ui.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.model.UserAccount;
import com.example.chiragpc.starspace.splash.SplashActivity;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import androidx.fragment.app.Fragment;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static android.app.Activity.RESULT_OK;
import static com.example.chiragpc.starspace.config.AppConfig.CAMERA_PROFILE_PIC;
import static com.example.chiragpc.starspace.config.AppConfig.GALLERY_PROFILE_PIC;
import static com.example.chiragpc.starspace.config.AppConfig.USER_ID;

/**
 * Created by Chirag on 12/17/2018 at 21:04.
 * Project - StarSpace
 */
public class SettingsFragment extends Fragment implements SettingsContract.View, View.OnClickListener {

    private LinearLayoutCompat mLinearLayout;

    private SettingsPresenter mPresenter;

    private MaterialProgressBar mProgressBar;

    private AppCompatTextView mUsername;

    private CircularImageView mUserProfilePic;

    private static SettingsFragment sSettingsFragment;

    private String mUserId;

    public static SettingsFragment newInstance(String userId) {
        if (sSettingsFragment == null) {
            SettingsFragment fragment = new SettingsFragment();
            Bundle args = new Bundle();
            args.putString(USER_ID, userId);
            fragment.setArguments(args);
            sSettingsFragment = fragment;
        }
        return sSettingsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getString(USER_ID);
        }
        mPresenter = new SettingsPresenter();
        mPresenter.attachView(this);
        mPresenter.userAccount(mUserId);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        mLinearLayout = rootView.findViewById(R.id.settings_logout);
        mUsername = rootView.findViewById(R.id.settings_username);
        mUserProfilePic = rootView.findViewById(R.id.settings_profile_pic);
        mProgressBar = rootView.findViewById(R.id.settings_progressBar);
        mLinearLayout.setOnClickListener(this);
        mUserProfilePic.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void logoutSuccess() {
        mPresenter.logout();
        startActivity(new Intent(getContext(), SplashActivity.class));
    }

    @Override
    public void getCurrentuser(UserAccount account) {
        mUsername.setText(account.getUsername());
    }

    @Override
    public void getCurrentUserFailure(String error) {

    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_logout:
                Logger.i("Signing Out");
                logoutSuccess();
                break;
            case R.id.settings_profile_pic:
                Logger.i("Profile Pic");
                startProfilePicIntent();
                break;
        }
    }

    private void startProfilePicIntent() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder profilePicDialog = new AlertDialog.Builder(getContext());
        profilePicDialog.setTitle("Upload Pictures Options");
        profilePicDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Camera")) {

                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_PROFILE_PIC);

                } else if (items[which].equals("Gallery")) {

                    Intent galleryIntent
                            = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select Image"), GALLERY_PROFILE_PIC);

                } else if (items[which].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        }).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == CAMERA_PROFILE_PIC) {
            Bundle bundle = data.getExtras();
            final Bitmap bitmap = (Bitmap) bundle.get("data");
            mUserProfilePic.setImageBitmap(bitmap);
        } else if (resultCode == RESULT_OK && requestCode == GALLERY_PROFILE_PIC){
            Uri selectedImageUri = data.getData();
            mUserProfilePic.setImageURI(selectedImageUri);
        }
    }
}
