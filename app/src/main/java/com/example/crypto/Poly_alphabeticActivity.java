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

import java.util.Locale;

public class Poly_alphabeticActivity extends AppCompatActivity {

    private Button encrypt_btn, decrypt_btn, clear_btn, copy_btn, paste_btn, paste_btn2;
    private EditText plainText, cipherText, keyText;
    private TextView resultText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poly_alphabetic);

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
                String str = LowerToUpper(plainText.getText().toString());
                String keyword = LowerToUpper(keyText.getText().toString());

                String key = generateKey(str, keyword);
                String x = EncryptionPolyalphabetic(str, key);

                resultText.setText(x.toLowerCase(Locale.ROOT));
                /*cipherText.setText(EncryptionCaesar(x));*/
                plainText.setText("");
                plainText.setHint("Enter Plaintext");
            }
        });

        //decrypt button function
        decrypt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = LowerToUpper(cipherText.getText().toString());
                String keyword = LowerToUpper(keyText.getText().toString());

                String key = generateKey(str, keyword);
                String y = DecryptionPolyalphabetic(str, key);

                resultText.setText(y.toLowerCase(Locale.ROOT));
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


    // This function generates the key in a cyclic manner until it's length isn't equal to the length of original text
    private String generateKey(String str, String key) {
        int x = str.length();
        for (int i = 0; ; i++) {
            if (x == i)
                i = 0;
            if (key.length() == str.length())
                break;
            key+=(key.charAt(i));
        }
        return key;
    }

    // This function will convert the lower case character to Upper case
    private String LowerToUpper(String s) {
        StringBuffer str =new StringBuffer(s);
        for(int i = 0; i < s.length(); i++) {
            if(Character.isLowerCase(s.charAt(i))) {
                str.setCharAt(i, Character.toUpperCase(s.charAt(i)));
            }
        }
        s = str.toString();
        return s;
    }


    // This function returns the encrypted text generated with the help of the key
    private String EncryptionPolyalphabetic(String plain_text, String key) {
        String temp="";
        if(TextUtils.isEmpty(plainText.getText().toString())
                || TextUtils.isEmpty(keyText.getText().toString())){
            Toast.makeText(this, "Please Enter valid Data!", Toast.LENGTH_SHORT).show();
        }
        else {
            for (int i = 0; i < plain_text.length(); i++) {
                if (plain_text.charAt(i) == ' ') {
                    temp += ' ';
                }
                else {
                    // converting in range 0-25
                    int x = (plain_text.charAt(i) + key.charAt(i)) % 26;

                    // convert into alphabets(ASCII)
                    x += 'A';
                    temp += (char) (x);
                }
            }
        }
        return temp;
    }


    // This function decrypts the encrypted text and returns the original text
    private String DecryptionPolyalphabetic(String cipher_text, String key) {
        String temp = " ";
        if(TextUtils.isEmpty(cipherText.getText().toString())
                || TextUtils.isEmpty(keyText.getText().toString())){
            Toast.makeText(this, "Please Enter valid Data!", Toast.LENGTH_SHORT).show();
        }
        else {
            for (int i = 0; i < cipher_text.length() && i < key.length(); i++) {
                if (cipher_text.charAt(i) == ' ') {
                    temp += ' ';
                }
                else {
                    // converting in range 0-25
                    int x = (cipher_text.charAt(i) -
                            key.charAt(i) + 26) % 26;
                    // convert into alphabets(ASCII)
                    x += 'A';
                    temp += (char) (x);
                }
            }
        }
        return temp;
    }


}