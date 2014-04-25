package com.ehc.OAuthTwitter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 25/4/14
 * Time: 11:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class DashBoardActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
    setContentView(R.layout.dashboard);
    TextView text = (TextView) findViewById(R.id.text);
    TextView text1 = (TextView) findViewById(R.id.text1);
    text.setText("Welcome to EHC" + "\n" + RegisterActivity.profileName + "\n" + RegisterActivity.email.getText());
    text1.setText(" App is under Development");
  }
}
