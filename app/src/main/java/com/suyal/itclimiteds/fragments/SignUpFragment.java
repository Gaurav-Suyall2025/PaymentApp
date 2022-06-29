package com.suyal.itclimiteds.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.suyal.itclimiteds.R;


public class SignUpFragment extends Fragment {
    EditText userName, userEmail, phoneNumber, userPassword;
    Button signUpButton;
    FirebaseAuth mAuth;
    TextView signInText;
    CheckBox checkBox;
    FirebaseDatabase database;
    ProgressDialog dialog;

    public SignUpFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userName = view.findViewById(R.id.editNameSignUp);
        phoneNumber = view.findViewById(R.id.editNumberSignUp);
        userEmail = view.findViewById(R.id.editEmailSignUp);
        userPassword = view.findViewById(R.id.editPassSignUp);
        signInText = view.findViewById(R.id.signInText);
        checkBox = view.findViewById(R.id.checkBox);

        dialog = new ProgressDialog(getContext());
        dialog.setTitle("Sign Up");
        dialog.setMessage("Please wait for a moment, we are creating your account");

        signUpButton = view.findViewById(R.id.signUpBtn);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = userName.getText().toString().trim();
                String email = userEmail.getText().toString().trim();
                String number = phoneNumber.getText().toString().trim();
                String password = userPassword.getText().toString().trim();


                if(TextUtils.isEmpty(name)){
                    userName.setError("Name is Required");
                }
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

                if(!checkBox.isChecked()){
                    checkBox.setError("Dear User, You haven't accept terms and conditios");
                }

                dialog.show();
                mAuth.createUserWithEmailAndPassword(userEmail.getText().toString(),
                                userPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                dialog.dismiss();
                                if(task.isSuccessful()){
                                    User user = new User(userName.getText().toString(),userEmail.getText().toString(),
                                            phoneNumber.getText().toString(),userPassword.getText().toString());
                                    String id = task.getResult().getUser().getUid();

                                    database.getReference().child("Users").child(id).setValue(user);

                                    Toast.makeText(getContext(), "User Created Successfully", Toast.LENGTH_SHORT).show();
                                    userName.setText("");
                                    userEmail.setText("");
                                    phoneNumber.setText("");
                                    userPassword.setText("");
                                }else {
                                    Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    userName.setText("");
                                    userEmail.setText("");
                                    phoneNumber.setText("");
                                    userPassword.setText("");
                                }
                            }
                        });
            }
        });

        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Hey Dear, so want to Logged in with your Credentials\nKindly swipe Right", Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }


}