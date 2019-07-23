/*
Copyright (c) 2019 Austin DeWitt all rights reserved.
*/

package io.github.austindewitt13.rot.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import io.github.austindewitt13.rot.R;
import io.github.austindewitt13.rot.controller.MainActivity;
import io.github.austindewitt13.rot.service.GoogleSignInService;

/**
 * LoginActivity sets up a content view for Google Sign In and has the User sign into their account.
 */

public class LoginActivity extends AppCompatActivity {

    private static final int LOGIN_REQUEST_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.sign_in).setOnClickListener((view) -> signIn());
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            GoogleSignInService.getInstance().setAccount(account);
            switchToMain();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUEST_CODE) {
            try {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                GoogleSignInAccount account = task.getResult(ApiException.class);
                GoogleSignInService.getInstance().setAccount(account);
                switchToMain();
            } catch (ApiException e) {
                Toast.makeText(this, R.string.authentication_error, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void signIn() {
        Intent intent = GoogleSignInService.getInstance().getClient().getSignInIntent();
        startActivityForResult(intent, LOGIN_REQUEST_CODE);
    }

    private void switchToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

