package com.example.crypto;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CaesarActivity extends AppCompatActivity {

    private Button encrypt_btn, decrypt_btn, clear_btn, copy_btn, paste_btn, paste_btn2;
    private EditText plainText, cipherText, keyText;
    private TextView resultText;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caesar);

        encrypt_btn = findViewById(R.id.encrypt_btn);
        decrypt_btn = findViewById(R.id.decrypt_btn);
        clear_btn = findViewById(R.id.clear_btn);
        copy_btn = findViewById(R.id.copy_btn);
        paste_btn = findViewById(R.id.paste_btn);
        paste_btn2 = findViewById(R.id.paste_btn2);
        resultText = findViewById(R.id.resultText);
        plainText = findViewById(R.id.plainText);
        cipherText = findViewById(R.id.cipherText);
        keyText = findViewById(R.id.keyText);

        //plaintext function
        plainText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.toString().equals("")){
                    encrypt_btn.setEnabled(false);
                } else {
                    encrypt_btn.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals("")){
                    encrypt_btn.setEnabled(false);
                } else {
                    encrypt_btn.setEnabled(true);
                }
            }
        });

        //ciphertext function
        cipherText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.toString().equals("")){
                    decrypt_btn.setEnabled(false);
                } else {
                    decrypt_btn.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals("")){
                    decrypt_btn.setEnabled(false);
                } else {
                    decrypt_btn.setEnabled(true);
                }
            }
        });

        //encrypt button function
        encrypt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x = plainText.getText().toString();
                int key = Integer.parseInt(keyText.getText().toString());
                resultText.setText(EncryptionCaesar(x, key));

                /*cipherText.setText(EncryptionCaesar(x));*/
                plainText.setText("");
                plainText.setHint("Enter Plaintext");
            }
        });

        //decrypt button function
        decrypt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String y = cipherText.getText().toString();
                int key = Integer.parseInt(keyText.getText().toString());
                resultText.setText(DecryptionCaesar(y, key));

                /*plainText.setText(DecryptionCaesar(y));*/
                cipherText.setText("");
                cipherText.setHint("Enter Ciphertext");
            }
        });

        //copy button function
        copy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("results text", resultText.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getApplicationContext(), "Text Copied", Toast.LENGTH_SHORT).show();
            }
        });

        //paste button function
        paste_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                try {
                    CharSequence textToPaste = clipboardManager.getPrimaryClip().getItemAt(0).getText();
                    plainText.setText(textToPaste);
                } catch (Exception e) {
                    return;
                }
                Toast.makeText(getApplicationContext(), "Text Pasted", Toast.LENGTH_SHORT).show();
            }
        });

        //paste button function
        paste_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                try {
                    CharSequence textToPaste2 = clipboardManager.getPrimaryClip().getItemAt(0).getText();
                    cipherText.setText(textToPaste2);
                } catch (Exception e) {
                    return;
                }
                Toast.makeText(getApplicationContext(), "Text Pasted", Toast.LENGTH_SHORT).show();
            }
        });

        //clear button function
        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText("Results Appear Here");
                plainText.setText("");
                plainText.setHint("Enter Plaintext");
                cipherText.setText("");
                cipherText.setHint("Enter Ciphertext");
                keyText.setText("");
                keyText.setHint("Enter Key");
            }
        });


    }


    private StringBuilder EncryptionCaesar(String plain, int key) {
        StringBuilder result = new StringBuilder();
        if(TextUtils.isEmpty(plainText.getText().toString())
                || TextUtils.isEmpty(keyText.getText().toString())){
            Toast.makeText(this, "Please Enter valid Data!", Toast.LENGTH_SHORT).show();
        }
        else {
            for (int i = 0; i < plain.length(); i++) {
                if (plain.charAt(i) == ' ') {
                    String temp = "";
                    temp += ' ';
                    result.append(temp);
                } else if (Character.isUpperCase(plain.charAt(i))) {
                    char ch = (char) (((int) plain.charAt(i) + key - 65) % 26 + 65);
                    result.append(ch);
                } else {
                    char ch = (char) (((int) plain.charAt(i) + key - 97) % 26 + 97);
                    result.append(ch);
                }
            }
        }
        return result;
    }


    private StringBuilder DecryptionCaesar(String cipher, int key) {
        StringBuilder result = new StringBuilder();
        if(TextUtils.isEmpty(cipherText.getText().toString())
                || TextUtils.isEmpty(keyText.getText().toString())){
            Toast.makeText(this, "Please Enter valid Data!", Toast.LENGTH_SHORT).show();
        }
        else {
            for (int i = 0; i < cipher.length(); i++) {
                if (cipher.charAt(i) == ' ') {
                    String temp = "";
                    temp += ' ';
                    result.append(temp);
                } else if (Character.isUpperCase(cipher.charAt(i))) {
                    char ch = (char) (Math.floorMod(((int) cipher.charAt(i) - key - 65), 26) + 65);
                    result.append(ch);
                } else {
                    char ch = (char) (Math.floorMod(((int) cipher.charAt(i) - key - 97), 26) + 97);
                    result.append(ch);
                }
            }
        }
        return result;
    }

}