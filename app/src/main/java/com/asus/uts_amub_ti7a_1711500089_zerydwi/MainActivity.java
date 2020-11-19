package com.asus.uts_amub_ti7a_1711500089_zerydwi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button mBtnLogin, mBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inisiasi tombol
        mBtnLogin = findViewById(R.id.btnLogin);
        mBtnRegister = findViewById(R.id.btnRegister);

        //toast
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Coming Soon !", Toast.LENGTH_SHORT).show();
            }
        });

        //intent
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerone = new Intent(MainActivity.this, RegisterOneActivity.class);
                startActivity(registerone);
            }
        });
    }
}