package com.example.crypto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ImageCryptographyActivity extends AppCompatActivity {

    private Button encrypt_btn, decrypt_btn, clear_btn, upload_btn;
    private ImageView img_to_encrypt;
    private TextView resultText;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_cryptography);

        resultText = findViewById(R.id.resultText);
        encrypt_btn = findViewById(R.id.encrypt_btn);
        decrypt_btn = findViewById(R.id.decrypt_btn);
        clear_btn = findViewById(R.id.clear_btn);
        upload_btn = findViewById(R.id.upload_btn);
        img_to_encrypt = findViewById(R.id.img_to_encrypt);

        //upload image button function
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(ImageCryptographyActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ImageCryptographyActivity.this, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 100);
                }
                else{
                    selectImg();
                }
            }
        });

        //encrypt button function
        encrypt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EncryptionImg();
                img_to_encrypt.setImageResource(R.drawable.enc_img);
            }
        });

        //decrypt button function
        decrypt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecryptionImg();
            }
        });

        //clear button function
        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_to_encrypt.setImageResource(R.drawable.img);
                resultText.setText("Results Appear Here");
            }
        });

    }

    private void selectImg() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            selectImg();
        }
        else {
            Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            img_to_encrypt.setImageURI(selectedImageUri);
        }
    }


    private String EncryptionImg() {
        img_to_encrypt.buildDrawingCache();
        Bitmap bitmap = img_to_encrypt.getDrawingCache();
        String image;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = stream.toByteArray();
        image = Base64.encodeToString(bytes, Base64.DEFAULT);
        resultText.setText(image);
        Toast.makeText(this, "Image Encrypted!", Toast.LENGTH_SHORT).show();
        return image;
    }

    private void DecryptionImg() {
        byte[] bytes = Base64.decode(resultText.getText().toString(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        img_to_encrypt.setImageBitmap(bitmap);
    }


}