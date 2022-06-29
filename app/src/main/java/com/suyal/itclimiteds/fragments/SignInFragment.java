package com.suyal.itclimiteds.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.suyal.itclimiteds.R;
import com.suyal.itclimiteds.navigationdrawer.MainActivity;
import com.suyal.itclimiteds.phoneverification.GetNumberActivity;
import com.suyal.itclimiteds.registration.SignInActivity;

public class SignInFragment extends Fragment {

    GoogleSignInClient mGoogleSignInClient;
    EditText userEmail,userPassword;
    Button signInButton;
    ImageView facebookLoginBtn;
    TextView signUpText;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    ImageView googleLoginButton;
    ProgressDialog dialog;


    public SignInFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userEmail = view.findViewById(R.id.editEmailSignIN);
        userPassword = view.findViewById(R.id.editPassSignIn);

        facebookLoginBtn = view.findViewById(R.id.facebookLoginBtn);
        signInButton = view.findViewById(R.id.signInBtn);
        signUpText = view.findViewById(R.id.signUpText);
        dialog = new ProgressDialog(getContext());
        dialog.setTitle("Sign In");
        dialog.setMessage("Welcome Dear, Let's start a new beginning");

        facebookLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), GetNumberActivity.class);
                startActivity(intent);
            }
        });


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    userEmail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    userEmail.setError("Password is Required");
                    return;
                }
                if(password.length() > 20){
                    userPassword.setError("Password must be less than 20 charaters");
                }

                dialog.show();
                mAuth.signInWithEmailAndPassword(userEmail.getText().toString(),
                        userPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if(task.isSuccessful()){
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        if(mAuth.getCurrentUser()!=null){
            Intent intent = new Intent(getContext(),MainActivity.class);
            startActivity(intent);
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("355180174702-5oa5qt4tn015es11mr2pv9uvq96lnf0u.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(),gso);

        googleLoginButton = view.findViewById(R.id.googleLoginBtn);

        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Hey Dear, so want to Registered yourself with your Credentials\nKindly swipe Left", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }



    int RC_SIGN_IN=65;
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) SignInFragment.this.getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            User users=new User();
                            users.setUserid(user.getUid());
                            users.setName(user.getDisplayName());
                            users.setImage(user.getPhotoUrl().toString());
                            database.getReference().child("Users").child(user.getUid()).setValue(users);

                            Intent intent=new Intent(getContext(), MainActivity.class);
                            startActivity(intent);

                            Toast.makeText(getContext(), "Dear User, You Logged In Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }


}