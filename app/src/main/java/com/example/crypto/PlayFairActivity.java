package com.example.crypto;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

public class PlayFairActivity extends AppCompatActivity {

    private Button encrypt_btn, decrypt_btn, clear_btn, copy_btn, paste_btn, paste_btn2;
    private EditText plainText, cipherText, keyText;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_fair);

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
                String x = plainText.getText().toString().toLowerCase(Locale.ROOT);
                String key = keyText.getText().toString();
                resultText.setText(EncryptionPlayFair(x, key));
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
                String key = keyText.getText().toString();


                resultText.setText(DecryptionPlayFair(y, key));
                String res = resultText.getText().toString().replace("x", "").replace("i", "j");
                String res2 = resultText.getText().toString().replace("i", "j");
                resultText.setText(res);
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


    private char[][] buildMatrix(String key){
        // at first we replace every 'j' with 'i'
        key = key.replace('j', 'i');
        key += "abcdefghiklmnopqrstuvwxyz";

        // then we check the key for duplicate and remove it
        StringBuilder matrixChars = new StringBuilder();
        key.chars().distinct().forEach(c -> matrixChars.append((char) c));

        char[][] matrix = new char[5][5];

        // filling matrix with values
        for (int row = 0; row < 5; row ++)
            for(int column = 0; column < 5; column ++)
                matrix[row][column] = matrixChars.charAt(column + row * 5);
        return matrix;
    }


    private StringBuilder preparePlainText(String plain_text) {
        // ensure that plaintext does not contain 'j'
        plain_text = plain_text.replace('j', 'i');
        StringBuilder new_plain_text = new StringBuilder(plain_text);

        // ensure that there are no 2 consecutive letters that are similar
        for (int i = 0; i < new_plain_text.length() - 1; i += 2) {
            if(new_plain_text.charAt(i) == new_plain_text.charAt(i + 1))
                if(new_plain_text.charAt(i) == 'x')
                    new_plain_text.insert(i + 1, 'z');
                else
                    new_plain_text.insert(i + 1, 'x');
        }

        // ensure that the numbers of characters in plaintext are even
        if (new_plain_text.length() % 2 != 0)
            if (new_plain_text.charAt(new_plain_text.length() - 1) == 'x')
                new_plain_text.append('z');
            else
                new_plain_text.append('x');
        return new_plain_text;
    }


    private int[] findPosition(char[][] matrix, char c){
        for(int i = 0; i < 5; i ++)
            for(int j = 0; j < 5; j ++)
                if(matrix[i][j] == c)
                    return new int[]{i, j};
        return new int[]{};
    }


    private StringBuilder EncryptionPlayFair(String plain, String key) {
        StringBuilder newPlain = preparePlainText(plain);
        StringBuilder result = new StringBuilder();
        char[][] matrix = buildMatrix(key);

        if(TextUtils.isEmpty(plainText.getText().toString())
                || TextUtils.isEmpty(keyText.getText().toString())){
            Toast.makeText(this, "Please Enter valid Data!", Toast.LENGTH_SHORT).show();
        }
        else {
            for (int i = 0; i < newPlain.length() - 1; i += 2) {
                char ch1 = newPlain.charAt(i);
                char ch2 = newPlain.charAt(i + 1);

                int[] pos1 = findPosition(matrix, ch1);
                int[] pos2 = findPosition(matrix, ch2);
                if(pos1[0] == pos2[0]){
                    result.append(matrix[pos2[0]][Math.floorMod(pos1[1] + 1, 5)]);
                    result.append(matrix[pos1[0]][Math.floorMod(pos2[1] + 1, 5)]);
                }
                else if(pos1[1] == pos2[1]){
                    result.append(matrix[Math.floorMod(pos1[0] + 1, 5)][pos1[1]]);
                    result.append(matrix[Math.floorMod(pos2[0] + 1, 5)][pos2[1]]);

                }
                else{
                    result.append(matrix[pos1[0]][pos2[1]]);
                    result.append(matrix[pos2[0]][pos1[1]]);

                }
            }
        }
        return result;
    }


    private StringBuilder DecryptionPlayFair(String cipher, String key) {
        StringBuilder newCipher = preparePlainText(cipher);
        StringBuilder result = new StringBuilder();
        char[][] matrix = buildMatrix(key);

        if(TextUtils.isEmpty(cipherText.getText().toString())
                || TextUtils.isEmpty(keyText.getText().toString())){
            Toast.makeText(this, "Please Enter valid Data!", Toast.LENGTH_SHORT).show();
        }
        else {
            for (int i = 0; i < newCipher.length() - 1; i += 2) {
                char ch1 = newCipher.charAt(i);
                char ch2 = newCipher.charAt(i + 1);

                int[] pos1 = findPosition(matrix, ch1);
                int[] pos2 = findPosition(matrix, ch2);

                if(pos1[0] == pos2[0]){
                    result.append(matrix[pos2[0]][Math.floorMod(pos1[1] - 1, 5)]);
                    result.append(matrix[pos1[0]][Math.floorMod(pos2[1] - 1, 5)]);
                }
                else if(pos1[1] == pos2[1]){
                    result.append(matrix[Math.floorMod(pos1[0] - 1, 5)][pos1[1]]);
                    result.append(matrix[Math.floorMod(pos2[0] - 1, 5)][pos2[1]]);

                }
                else{
                    result.append(matrix[pos1[0]][pos2[1]]);
                    result.append(matrix[pos2[0]][pos1[1]]);

                }
            }
        }
        return result;
    }




}