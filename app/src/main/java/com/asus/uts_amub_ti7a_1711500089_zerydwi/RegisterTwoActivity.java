package com.asus.uts_amub_ti7a_1711500089_zerydwi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.asus.uts_amub_ti7a_1711500089_zerydwi.Model.UserTwo;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class RegisterTwoActivity extends AppCompatActivity {

    ImageView mIvAddPhoto;
    Button mBtnRegister;
    EditText mEtHobi, mEtAlamat;

    //firebase
    FirebaseStorage storage;
    private StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;

    //awesome validation
    AwesomeValidation awesomeValidation;
    ProgressDialog dialog;

    private Uri mImageUri;

    private static final int PICK_IMAGE_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);


        //deklarasi
        //iv nanti ditabah di line ini
        mEtHobi = findViewById(R.id.etHobi);
        mEtAlamat = findViewById(R.id.etAlamat);
        mIvAddPhoto = findViewById(R.id.ivAddPhoto);

        mBtnRegister = findViewById(R.id.btnRegisterTwo);

        mStorageReference = FirebaseStorage.getInstance().getReference("Images");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("RegisterTwo");

        //initialize validation style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //add validation Hobi
        awesomeValidation.addValidation(this, R.id.etHobi,
                RegexTemplate.NOT_EMPTY, R.string.hobi_tidak_boleh_kosong);
        //add validation Alamat
        awesomeValidation.addValidation(this, R.id.etAlamat,
                RegexTemplate.NOT_EMPTY, R.string.alamat_tidak_boleh_kosong);


        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadFile();
                startActivity(new Intent(RegisterTwoActivity.this, MainActivity.class));
            }
        });


        mIvAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenImageChooser();
            }
        });
    }

    private void OpenImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == PICK_IMAGE_REQUEST || resultCode == RESULT_OK
                || data != null || data.getData() != null
        ){
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(mIvAddPhoto);
        }
    }

    private String getFileExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void UploadFile(){
        if(mImageUri != null) {
            StorageReference filereference = mStorageReference.child(System.currentTimeMillis()
                    +"."+getFileExt(mImageUri));

            filereference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(RegisterTwoActivity.this, "Sukses Mendaftar", Toast.LENGTH_SHORT).show();
                            UserTwo userTwo = new UserTwo(mEtHobi.getText().toString().trim(),
                                    mEtAlamat.getText().toString().trim(),
                                    taskSnapshot.getUploadSessionUri().toString());
                            String uploadId = mDatabaseReference.push().getKey();
                            mDatabaseReference.child(uploadId).setValue(userTwo);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterTwoActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

        }else{
            Toast.makeText(this, "No File Selected !", Toast.LENGTH_SHORT).show();
        }
    }
}
