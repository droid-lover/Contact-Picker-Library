package com.jarvis.contactpickerlib;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int TAG_RESULT_PICK_CONTACT = 11;

    @BindView(R.id.mainActivityToolbar)
    Toolbar mToolbar;

    @BindView(R.id.pickAContactCardView)
    CardView mPickAContactCardView;

    @BindView(R.id.pickAUserNumTextView)
    TextView mPickUserNumTextView;

    @BindView(R.id.pickedUserNameTextView)
    TextView mPickedUsernameTextView;

    @BindView(R.id.pickedUserPhoneNumberTextView)
    TextView mPickedUserMobileNumTextView;

    @BindView(R.id.selectedValuesCardView)
    CardView mSelectedValuesCardView;

    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @BindView(R.id.resetButton)
    Button mResetContactScreenButton;

    Cursor cursor = null;

    String userNameValue, userPhoneNumberValue = null;

    int phonePosIndex, namePosIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        intializeViews();

    }

    private void intializeViews() {
        setSupportActionBar(mToolbar);

        mPickAContactCardView.setOnClickListener(this);
        mResetContactScreenButton.setOnClickListener(this);

        mPickUserNumTextView.setText("Pick A Contact");


        fabButtonClick();
    }

    private void fabButtonClick() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Made By Jarvis", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }


        });
    }

    private void restartThisAct() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.pickAContactCardView:
                pickAContact();
                break;
            case R.id.resetButton:
                restartThisAct();
                break;
        }
    }

    private void pickAContact() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(intent, TAG_RESULT_PICK_CONTACT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // checking whether the result is ok
        if (resultCode == RESULT_OK) {
            // Check for the request code, we might be usign multiple startActivityForReslut
            switch (requestCode) {
                case TAG_RESULT_PICK_CONTACT:
                    pickedContact(data);
                    break;
            }
        } else {
            Log.e("There is some error In Picking", "This Intetnt Failed to pick contact");
        }
    }

    private void pickedContact(Intent data) {

        try {

            // getData() method will have the Content Uri of the selected contact
            Uri uri = data.getData();

            //Query the content uri
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();

            // column index of the phone number
            phonePosIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            // column index of the contact name
            namePosIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

            userPhoneNumberValue = cursor.getString(phonePosIndex);
            userNameValue = cursor.getString(namePosIndex);

            if (userPhoneNumberValue != null && userNameValue != null) {

                mSelectedValuesCardView.setVisibility(View.VISIBLE);
                mResetContactScreenButton.setVisibility(View.VISIBLE);
                mPickUserNumTextView.setText("Contact Picked");

                mPickedUsernameTextView.setText(userNameValue);
                mPickedUserMobileNumTextView.setText(userPhoneNumberValue);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
