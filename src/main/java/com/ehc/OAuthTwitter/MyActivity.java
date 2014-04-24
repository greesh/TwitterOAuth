package com.ehc.OAuthTwitter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.util.List;

public class MyActivity extends Activity {
  Button logInWithTwitter;
  Twitter twitter;
  User user;
  RequestToken requestToken;
  public final static String consumerKey = "HwNoqP04wIfesPWCtOI5wUv2u";
  public final static String consumerSecret = "iNT7l2YZiM7IlWdOgt7TjysUgvf6KX37lnoYuse0NXJ1QOM0wd";
  private final String CALL_BACK_URL = "oauth://t4jsample";


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    logInWithTwitter = (Button) findViewById(R.id.log_in_with_twitter);
    logInWithTwitter.setBackgroundResource(R.drawable.twitter);
    Log.d("oncreate", "oncreate");

    logInWithTwitter.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        OAuthLogin();
      }
    });

  }

  void OAuthLogin() {
    try {
      twitter = new TwitterFactory().getInstance();
      twitter.setOAuthConsumer(consumerKey, consumerSecret);
      requestToken = twitter.getOAuthRequestToken(CALL_BACK_URL);
      String authUrl = requestToken.getAuthenticationURL();
      Log.d("address", authUrl);
      this.startActivity(new Intent(Intent.ACTION_VIEW, Uri
          .parse(authUrl)));
    } catch (TwitterException ex) {
      Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
      Log.e("in Main.OAuthLogin", ex.getMessage());
    }
  }


  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    Log.d("onCreate", "calling on new intent");

    Uri uri = intent.getData();
    try {
      Log.d("onCreate", "Verifier");
      String verifier = uri.getQueryParameter("oauth_verifier");
      AccessToken accessToken = twitter.getOAuthAccessToken(requestToken,
          verifier);
      String token = accessToken.getToken(), secret = accessToken
          .getTokenSecret();
      displayTimeLine(token, secret); //after everything, display the first tweet

    } catch (TwitterException ex) {
      Log.e("c", "" + ex.getMessage());
    }

  }


  @SuppressWarnings("deprecation")
  void displayTimeLine(String token, String secret) {
    if (null != token && null != secret) {
      List<Status> statuses = null;
      try {
       twitter.setOAuthAccessToken(new AccessToken(token, secret));
        statuses = twitter.getHomeTimeline();

        int id = (int) twitter.getId();
        user = twitter.showUser(id);
        Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
        intent.putExtra("name",user.getName());
        intent.putExtra("screenName",user.getScreenName());
        Log.d("info of the user", user.getName() + "\n" + user.getFriendsCount() + "\n" + user.getBiggerProfileImageURL() + "\n" + user.isVerified() + "\n" + user.toString() + "\n" + user.getId() + "\n" + user.getBiggerProfileImageURLHttps() + "\n" + user.getDescription() + "\n" + user.getLocation() + "\n" + user.getScreenName() + "\n" + user.getStatus() + "\n" + user.getURL());
        startActivity(intent);
      } catch (Exception ex) {
        Toast.makeText(this, "Error:" + ex.getMessage(),
            Toast.LENGTH_LONG).show();
        Log.d("Main.displayTimeline", "" + ex.getMessage());
      }

    } else {
      Toast.makeText(this, "Not Verified", Toast.LENGTH_LONG).show();
    }
  }
}
