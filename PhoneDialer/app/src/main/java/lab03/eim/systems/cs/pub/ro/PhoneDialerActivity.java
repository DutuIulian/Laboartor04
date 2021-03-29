package lab03.eim.systems.cs.pub.ro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class PhoneDialerActivity extends AppCompatActivity {

    final static int PERMISSION_REQUEST_CALL_PHONE = 1;
    final static int CONTACTS_MANAGER_REQUEST_CODE = 207;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_dialer);

        NumericButtonListener listener = new NumericButtonListener();
        Button button_0 = (Button) findViewById(R.id.number_0_button);
        button_0.setOnClickListener(listener);
        Button button_1 = (Button) findViewById(R.id.number_1_button);
        button_1.setOnClickListener(listener);
        Button button_2 = (Button) findViewById(R.id.number_2_button);
        button_2.setOnClickListener(listener);
        Button button_3 = (Button) findViewById(R.id.number_3_button);
        button_3.setOnClickListener(listener);
        Button button_4 = (Button) findViewById(R.id.number_4_button);
        button_4.setOnClickListener(listener);
        Button button_5 = (Button) findViewById(R.id.number_5_button);
        button_5.setOnClickListener(listener);
        Button button_6 = (Button) findViewById(R.id.number_6_button);
        button_6.setOnClickListener(listener);
        Button button_7 = (Button) findViewById(R.id.number_7_button);
        button_7.setOnClickListener(listener);
        Button button_8 = (Button) findViewById(R.id.number_8_button);
        button_8.setOnClickListener(listener);
        Button button_9 = (Button) findViewById(R.id.number_9_button);
        button_9.setOnClickListener(listener);
        ImageButton button_backspace = (ImageButton) findViewById(R.id.backspace_image_button);
        button_backspace.setOnClickListener(new BackspaceButtonListener());
        ImageButton button_call = (ImageButton) findViewById(R.id.call_image_button);
        button_call.setOnClickListener(new CallButtonListener());
        ImageButton button_hangup = (ImageButton) findViewById(R.id.hangup_image_button);
        button_hangup.setOnClickListener(new HangupButtonListener());
        ImageButton button_contacts = (ImageButton) findViewById(R.id.contacts_image_button);
        button_contacts.setOnClickListener(new ContactsButtonListener());
    }

    class BackspaceButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            EditText phoneNumberEditText = (EditText)findViewById(R.id.editTextPhone);
            String phoneNumber = phoneNumberEditText.getText().toString();
            if (!phoneNumber.isEmpty()) {
                phoneNumberEditText.setText(phoneNumber.substring(0, phoneNumber.length() - 1));
            }
        }
    }

    class CallButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(PhoneDialerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        PhoneDialerActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        PERMISSION_REQUEST_CALL_PHONE);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                EditText phoneNumberEditText = (EditText)findViewById(R.id.editTextPhone);
                intent.setData(Uri.parse("tel:" + phoneNumberEditText.getText().toString()));
                startActivity(intent);
            }
        }
    }

    class HangupButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    class ContactsButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            EditText phoneNumberEditText = (EditText)findViewById(R.id.editTextPhone);
            String phoneNumber = phoneNumberEditText.getText().toString();
            if (phoneNumber.length() > 0) {
                Intent intent = new Intent("ro.pub.cs.systems.eim.lab04.contactsmanager.intent.action.ContactsManagerActivity");
                intent.putExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY", phoneNumber);
                startActivityForResult(intent, CONTACTS_MANAGER_REQUEST_CODE);
            } else {
                Toast.makeText(getApplication(), "Phone error", Toast.LENGTH_LONG).show();
            }
        }
    }

    class NumericButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            EditText phoneNumberEditText = (EditText)findViewById(R.id.editTextPhone);
            String newString = phoneNumberEditText.getText().toString() + ((Button)v).getText().toString();
            phoneNumberEditText.setText(newString);
        }
    }
}