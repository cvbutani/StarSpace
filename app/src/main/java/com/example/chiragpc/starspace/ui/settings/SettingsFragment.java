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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.model.UserAccount;
import com.example.chiragpc.starspace.splash.SplashActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.io.IOException;

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

    private AppCompatTextView mSignOut, mEdit, mSave;

    private SettingsPresenter mPresenter;

    private MaterialProgressBar mProgressBar;

    private AppCompatTextView mUsername;

    private CircularImageView mUserProfilePic;

    private MaterialButton mSendFriendRequest, mCancelFriendRequest;

    private TextInputLayout mEmailTextLayout, mDoBTextLayout;

    private TextInputEditText mEmailEditLayout, mDoBEditLayout;

    private AppCompatTextView mEmailAddress, mEmailAddressLabel, mDateOfBirth,
            mDateOfBirthLabel, mGender, mGenderLabel, mGenderRadioLabel;

    private RadioGroup mGenderRadioGroup;

    private RadioButton mSelectedRadioButton;

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
        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.i(mUserId);
        mPresenter = new SettingsPresenter();
        mPresenter.attachView(this);
        mPresenter.userAccount(mUserId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        mSignOut = rootView.findViewById(R.id.settings_logout);
        mUsername = rootView.findViewById(R.id.settings_username);
        mUserProfilePic = rootView.findViewById(R.id.settings_profile_pic);
        mProgressBar = rootView.findViewById(R.id.settings_progressBar);
        mEdit = rootView.findViewById(R.id.settings_edit);
        mSave = rootView.findViewById(R.id.settings_save);

        mCancelFriendRequest = rootView.findViewById(R.id.profile_cancel_request);
        mCancelFriendRequest.setVisibility(View.GONE);
        mSendFriendRequest = rootView.findViewById(R.id.profile_friend_request);
        mSendFriendRequest.setVisibility(View.GONE);

        mEmailTextLayout = rootView.findViewById(R.id.account_email_text);
        mDoBTextLayout = rootView.findViewById(R.id.account_dob_text);
        mEmailEditLayout = rootView.findViewById(R.id.account_email_address);
        mDoBEditLayout = rootView.findViewById(R.id.account_date_of_birth);

        mEmailAddress = rootView.findViewById(R.id.settings_email_address);
        mDateOfBirth = rootView.findViewById(R.id.settings_date_birth);
        mGender = rootView.findViewById(R.id.settings_gender);
        mEmailAddressLabel = rootView.findViewById(R.id.settings_email_address_label);
        mDateOfBirthLabel = rootView.findViewById(R.id.settings_date_birth_label);
        mGenderLabel = rootView.findViewById(R.id.settings_gender_label);

        mGenderRadioGroup = rootView.findViewById(R.id.gender);
        mGenderRadioLabel = rootView.findViewById(R.id.gender_label);
//        mSelectedRadioButton = rootView.findViewById(mGenderRadioGroup.getCheckedRadioButtonId());

        mSignOut.setOnClickListener(this);
        mUserProfilePic.setOnClickListener(this);
        mEdit.setOnClickListener(this);
        mSave.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void logoutSuccess() {
        mPresenter.logout();
        startActivity(new Intent(getContext(), SplashActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    public void getCurrentuser(UserAccount account) {
        mUsername.setText(account.getUsername());
        if (account.getProfilePic() != null) {
            Picasso.get().load(Uri.parse(account.getProfilePic())).into(mUserProfilePic);
        }
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
            case R.id.settings_edit:
                editUserInfo();
                break;
            case R.id.settings_save:
                saveUserInfo(v);
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
        } else if (resultCode == RESULT_OK && requestCode == GALLERY_PROFILE_PIC) {
            Uri selectedImageUri = data.getData();
//            mUserProfilePic.setImageURI(selectedImageUri);
            mPresenter.profilePic(mUserId, selectedImageUri);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sSettingsFragment = null;
    }

    private void editUserInfo() {
        mEmailTextLayout.setVisibility(View.VISIBLE);
        mDoBTextLayout.setVisibility(View.VISIBLE);
        mGenderRadioLabel.setVisibility(View.VISIBLE);
        mGenderRadioGroup.setVisibility(View.VISIBLE);
        mSave.setVisibility(View.VISIBLE);

        mEmailEditLayout.setText(mEmailAddress.getText());
        mDoBEditLayout.setText(mDateOfBirth.getText());

        mGender.setVisibility(View.GONE);
        mEmailAddress.setVisibility(View.GONE);
        mDateOfBirth.setVisibility(View.GONE);

        mEmailAddressLabel.setVisibility(View.GONE);
        mDateOfBirthLabel.setVisibility(View.GONE);
        mGenderLabel.setVisibility(View.GONE);
        mEdit.setVisibility(View.GONE);
        mSignOut.setVisibility(View.GONE);

    }

    private void saveUserInfo(View view) {
        mGender.setVisibility(View.VISIBLE);
        mEmailAddress.setVisibility(View.VISIBLE);
        mDateOfBirth.setVisibility(View.VISIBLE);

        mEmailAddressLabel.setVisibility(View.VISIBLE);
        mDateOfBirthLabel.setVisibility(View.VISIBLE);
        mGenderLabel.setVisibility(View.VISIBLE);
        mEdit.setVisibility(View.VISIBLE);
        mSignOut.setVisibility(View.VISIBLE);

        mEmailAddress.setText(mEmailTextLayout.getEditText().getText().toString());
        mDateOfBirth.setText(mDoBTextLayout.getEditText().getText().toString());
//        mGenderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                mSelectedRadioButton = group.findViewById(checkedId);
//                mGender.setText(mSelectedRadioButton.getText().toString());
//            }
//        });
        mEmailTextLayout.setVisibility(View.GONE);
        mDoBTextLayout.setVisibility(View.GONE);
        mGenderRadioLabel.setVisibility(View.GONE);
        mGenderRadioGroup.setVisibility(View.GONE);
        mSave.setVisibility(View.GONE);
    }
}
