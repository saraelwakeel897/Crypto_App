package com.example.crypto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MonoalphabeticActivity extends AppCompatActivity {

    private Button encrypt_btn, decrypt_btn, clear_btn, copy_btn,  paste_btn, paste_btn2;
    private EditText plainText, cipherText;
    private TextView resultText;

    private static char normalChar[]
            = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

    private static char codedChar[]
            = { 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O',
            'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K',
            'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M' };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monoalphabetic);

        encrypt_btn = findViewById(R.id.encrypt_btn);
        decrypt_btn = findViewById(R.id.decrypt_btn);
        clear_btn = findViewById(R.id.clear_btn);
        copy_btn = findViewById(R.id.copy_btn);
        paste_btn = findViewById(R.id.paste_btn);
        paste_btn2 = findViewById(R.id.paste_btn2);
        resultText = findViewById(R.id.resultText);
        plainText = findViewById(R.id.plainText);
        cipherText = findViewById(R.id.cipherText);

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
                String x = plainText.getText().toString().toLowerCase(Locale.ROOT);
                resultText.setText(EncryptionMonoalphabetic(x));
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
                resultText.setText(DecryptionMonoalphabetic(y));
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
            }
        });


    }


    private String EncryptionMonoalphabetic(String plain) {
        String temp = "";
        for (int i = 0; i < plain.length(); i++) {
            for (int j = 0; j < 26; j++) {
                // comparing the character and adding the corresponding char to the encryptedString
                if (plain.charAt(i) == normalChar[j]) {
                    temp += codedChar[j];
                    break;
                }
                // if there are any special characters add them directly to the string
                if (plain.charAt(i) < 'a' || plain.charAt(i) > 'z') {
                    temp += plain.charAt(i);
                    break;
                }
            }
        }
        return temp;
    }


    private String DecryptionMonoalphabetic (String cipher) {
        String temp = "";
        for (int i = 0; i < cipher.length(); i++)
        {
            for (int j = 0; j < 26; j++) {
                // compare each characters and decode them using indices
                if (cipher.charAt(i) == codedChar[j]) {
                    temp += normalChar[j];
                    break;
                }
                // Add the special characters directly to the String
                if (cipher.charAt(i) < 'A' || cipher.charAt(i) > 'Z') {
                    temp += cipher.charAt(i);
                    break;
                }
            }
        }
        return temp;
    }


}
