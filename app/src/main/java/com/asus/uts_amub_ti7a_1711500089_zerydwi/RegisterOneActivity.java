package com.asus.uts_amub_ti7a_1711500089_zerydwi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.asus.uts_amub_ti7a_1711500089_zerydwi.Model.User;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterOneActivity extends AppCompatActivity {

    Button mBtnNext;
    EditText mEtUsername, mEtPassword, mEtEmail;

    //awesome validation
    AwesomeValidation awesomeValidation;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        //init firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_login = database.getReference("RegisterOne");

        //deklarasi
        mEtUsername = findViewById(R.id.etUsername);
        mEtPassword = findViewById(R.id.etPassword);
        mEtEmail = findViewById(R.id.etEmail);

        mBtnNext = findViewById(R.id.btnNext);

        //initialize validation style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //add validation username
        awesomeValidation.addValidation(this, R.id.etUsername,
                RegexTemplate.NOT_EMPTY, R.string.nama_tidak_boleh_kosong);
        //add validation password
        awesomeValidation.addValidation(this, R.id.etPassword,
                ".{6,}", R.string.password_harus_terdiri_dari_6_angka);
        //add validation email
        awesomeValidation.addValidation(this, R.id.etEmail,
                RegexTemplate.NOT_EMPTY, R.string.email_tidak_boleh_kosong);

        //progress bar
        dialog = new ProgressDialog(this);
        dialog.setTitle("Mendaftar");
        dialog.setMessage("Silahkan Tunggu ...");
        dialog.setCanceledOnTouchOutside(true);

        //btn daftar onclick
        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {
                    dialog.show();

                    table_login.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //Cek jika user telah terdaftar atau belum
                            if (dataSnapshot.child(mEtUsername.getText().toString()).exists()) {
                                dialog.dismiss();
                                Toast.makeText(RegisterOneActivity.this, "Akun Telah Terdaftar, Silahkan Gunakan Username Lainnya", Toast.LENGTH_SHORT).show();
                            } else {
                                dialog.dismiss();
                                User user = new User(mEtUsername.getText().toString(),
                                        mEtPassword.getText().toString(),
                                        mEtEmail.getText().toString());
                                table_login.child(mEtUsername.getText().toString()).setValue(user);
                                Toast.makeText(RegisterOneActivity.this, "Sukses Mendaftar !", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterOneActivity.this, RegisterTwoActivity.class));
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

    }

    public void onBackPressed () {

    }

}