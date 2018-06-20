package com.brahma.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.brahma.R;
import com.brahma.Room.RoomDatabase;
import com.brahma.Room.User;
import com.brahma.utils.Utils;
import com.brahma.viewModel.Injection;
import com.brahma.viewModel.UserViewModel;
import com.brahma.viewModel.ViewModelfactory;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.LifecycleActivity;

import org.json.JSONObject;

import java.util.Arrays;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SignUpActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private static final String TAG = SignUpActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;

    private GoogleApiClient mGoogleApiClient;
    CallbackManager callbackManager;
    private Button mGoogleSignUp, mFacebookSignUp;
    AccessToken mAccessToken;
    private String fbName, fbProfileImg, fbLocation, fbEmail;
    boolean isLoggedIn;
    RoomDatabase roomDatabase;
    private UserViewModel mUserViewModel;
    private ViewModelfactory mViewModelFactory;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_sign_up);
        AppEventsLogger.activateApp(this, getString(R.string.facebook_app_id));
        initViews();
        mViewModelFactory = Injection.provideViewModelFactory(this.getApplication());
        mUserViewModel = ViewModelProviders.of(this,mViewModelFactory).get(UserViewModel.class);

        callbackManager = CallbackManager.Factory.create();
        mGoogleSignUp.setOnClickListener(this);
        mFacebookSignUp.setOnClickListener(this);
        initGoogleApiClient();
    }


    private void initGoogleApiClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void initViews() {
        mGoogleSignUp = (Button) findViewById(R.id.signUpGoogleButton);
        mFacebookSignUp = (Button) findViewById(R.id.signUpFacebookButton);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signUpGoogleButton:

                signIn();
                break;

            case R.id.signUpFacebookButton:

                facebookSignUp();
                break;

        }


    }

    private void facebookSignUp() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("user_photos", "email", "user_birthday", "public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mAccessToken = loginResult.getAccessToken();
                if (mAccessToken != null && !mAccessToken.isExpired()) {
                    getUserProfile(mAccessToken);
                }
            }

            @Override
            public void onCancel() {
                Utils.hideProgressDialog();
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "error log in");
                Utils.hideProgressDialog();
            }
        });


    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        Utils.showProgressDialog(this);
    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // updateUI(false);
                    }
                });
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

            fbName = acct.getDisplayName();
            // String personPhotoUrl = acct.getPhotoUrl().toString();
            fbEmail = acct.getEmail();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("name", fbName);
            intent.putExtra("email", fbEmail);
            intent.putExtra("picUrl", acct.getPhotoUrl());

            User user = new User(fbName, fbEmail);
            mUserViewModel.insert(user);
            startActivity(intent);

            Log.e(TAG, "Name: " + fbName + ", email: " + fbEmail + "url" + acct.getPhotoUrl());

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.

            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    Utils.hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        //You can fetch user info like this…
                        //fbProfileImg = object.getJSONObject(“picture”).

                        //object.getString(“name”);
                        //object.getString(“email”));
                        //object.getString(“id”));
                        Log.e(TAG, object.toString());
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,birthday,gender,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
