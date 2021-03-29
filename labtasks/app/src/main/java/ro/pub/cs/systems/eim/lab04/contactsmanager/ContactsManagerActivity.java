package ro.pub.cs.systems.eim.lab04.contactsmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {

    LinearLayout additionalFieldsContainer;

    Button showAdditionalFieldsButton;
    Button saveButton;
    Button cancelButton;

    EditText nameEditText;
    EditText phoneEditText;
    EditText emailEditText;
    EditText addressEditText;
    EditText jobTitleEditText;
    EditText companyEditText;
    EditText websiteEditText;
    EditText imEditText;

    final static int CONTACTS_MANAGER_REQUEST_CODE = 207;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        ButtonListener listener = new ButtonListener();
        showAdditionalFieldsButton = (Button)findViewById(R.id.show_additional_fields);
        showAdditionalFieldsButton.setOnClickListener(listener);
        saveButton = (Button)findViewById(R.id.save_button);
        saveButton.setOnClickListener(listener);
        cancelButton = (Button)findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(listener);

        nameEditText = (EditText)findViewById(R.id.name_edit_text);
        phoneEditText = (EditText)findViewById(R.id.phone_number_edit_text);
        emailEditText = (EditText)findViewById(R.id.email_edit_text);
        addressEditText = (EditText)findViewById(R.id.address_edit_text);
        jobTitleEditText = (EditText)findViewById(R.id.job_title_edit_text);
        companyEditText = (EditText)findViewById(R.id.company_edit_text);
        websiteEditText = (EditText)findViewById(R.id.website_edit_text);
        imEditText = (EditText)findViewById(R.id.im_edit_text);

        additionalFieldsContainer = (LinearLayout)findViewById(R.id.additional_fields_container);

        Intent intent = getIntent();
        if (intent != null) {
            String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
            if (phone != null) {
                Log.e("a!", phone);
                phoneEditText.setText(phone);
            } else {
                Toast.makeText(this, "Phone error", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.show_additional_fields:
                    switch (additionalFieldsContainer.getVisibility()) {
                        case View.VISIBLE:
                            showAdditionalFieldsButton.setText("Show additional fields");
                            additionalFieldsContainer.setVisibility(View.INVISIBLE);
                            break;
                        case View.INVISIBLE:
                            showAdditionalFieldsButton.setText("Hide additional fields");
                            additionalFieldsContainer.setVisibility(View.VISIBLE);
                            break;
                    }
                    break;
                case R.id.save_button:
                    String name = nameEditText.getText().toString();
                    String phone = phoneEditText.getText().toString();
                    String email = emailEditText.getText().toString();
                    String address = addressEditText.getText().toString();
                    String jobTitle = jobTitleEditText.getText().toString();
                    String company = companyEditText.getText().toString();
                    String website = websiteEditText.getText().toString();
                    String im = imEditText.getText().toString();

                    Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                    if (name != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                    }
                    if (phone != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                    }
                    if (email != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                    }
                    if (address != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                    }
                    if (jobTitle != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
                    }
                    if (company != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                    }
                    ArrayList<ContentValues> contactData = new ArrayList<>();
                    if (website != null) {
                        ContentValues websiteRow = new ContentValues();
                        websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                        websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                        contactData.add(websiteRow);
                    }
                    if (im != null) {
                        ContentValues imRow = new ContentValues();
                        imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                        imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                        contactData.add(imRow);
                    }
                    intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                    startActivityForResult(intent, CONTACTS_MANAGER_REQUEST_CODE);
                    break;
                case R.id.cancel_button:
                    setResult(Activity.RESULT_CANCELED, new Intent());
                    finish();
                    break;
            }
        }
    }
}