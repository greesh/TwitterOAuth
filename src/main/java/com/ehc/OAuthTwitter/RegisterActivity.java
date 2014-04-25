package com.ehc.OAuthTwitter;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 24/4/14
 * Time: 4:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class RegisterActivity extends Activity {
  EditText userName;
  EditText name;
  EditText lastName;
  public static EditText email;
  EditText password;
  EditText confirmPassword;
  EditText contactNumber;
  EditText address;
  Button signUp;
  Button signUpWithMail;
  public static String profileName;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.register);
    getWidgets();
    applyAction();
    Intent intent = getIntent();
    profileName = intent.getStringExtra("name");
    String ScreenName = intent.getStringExtra("screenName");
    populateFields(profileName, ScreenName);

  }

  private void applyAction() {
    signUpWithMail.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (userName.getText().length() == 0) {
          userName.setError("Field Required");
          return;
        }
        if (name.getText().length() == 0) {
          name.setError("Field Required");
          return;
        }
        if (email.getText().length() == 0) {
          email.setError("Field Required");
          return;
        }
        if (contactNumber.getText().length() == 0) {
          contactNumber.setError("Field Required");
          return;
        }
        if (password.getText().length() == 0) {
          password.setError("Field Required");
          return;
        }
        if (confirmPassword.getText().length() == 0) {
          confirmPassword.setError("Field Required");
          return;
        }
        if (!(password.getText().toString().equals(confirmPassword.getText().toString()))) {
          confirmPassword.setError("Password doesn't match");
          return;
        }
        saveUser();
      }
    });
  }

  private void saveUser() {
    DatabaseHelper dbHelper = new DatabaseHelper(this);
    SQLiteDatabase database = dbHelper.getWritableDatabase();
    if (database != null) {
      String query = "insert into user(NAME,USERNAME,EMAIL,PASSWORD,PHONENUMBER) "
          + "values('" + name.getText()
          + "','" + userName.getText()
          + "','"
          + email.getText()
          + "','" + password.getText()
          + "'," + contactNumber.getText()
          + ")";
      database.execSQL(query);
      database.close();
      Toast.makeText(this, "User Registered Successfully", Toast.LENGTH_LONG).show();
      startDashboard();
    }
  }

  private void startDashboard() {
    Intent intent = new Intent(this, DashBoardActivity.class);
    startActivity(intent);
  }

  private void populateFields(String name, String screenName) {
    userName.setText(screenName);
    this.name.setText(name);
  }

  private void getWidgets() {
    userName = (EditText) findViewById(R.id.user_name);
    name = (EditText) findViewById(R.id.name);
    email = (EditText) findViewById(R.id.email);
    password = (EditText) findViewById(R.id.password);
    confirmPassword = (EditText) findViewById(R.id.confirm_password);
    contactNumber = (EditText) findViewById(R.id.contact_number);
    signUp = (Button) findViewById(R.id.sign_up);
    signUpWithMail = (Button) findViewById(R.id.sign_up_with_twitter);
  }
}
