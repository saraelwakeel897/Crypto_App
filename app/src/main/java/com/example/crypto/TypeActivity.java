package com.example.crypto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TypeActivity extends AppCompatActivity {
    CardView caesar_card, playfair_card, monoalphabetic_card, polyalphabetic_card, img_card;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        caesar_card = findViewById(R.id.caesar_card);
        playfair_card = findViewById(R.id.playfair_card);
        monoalphabetic_card = findViewById(R.id.monoalphabetic_card);
        polyalphabetic_card = findViewById(R.id.polyalphabetic_card);
        img_card = findViewById(R.id.img_card);

        caesar_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TypeActivity.this, CaesarActivity.class);
                startActivity(intent);
            }
        });

        playfair_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TypeActivity.this, PlayFairActivity.class);
                startActivity(intent);
            }
        });

        monoalphabetic_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TypeActivity.this, MonoalphabeticActivity.class);
                startActivity(intent);
            }
        });

        polyalphabetic_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TypeActivity.this, Poly_alphabeticActivity.class);
                startActivity(intent);
            }
        });

        img_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TypeActivity.this, ImageCryptographyActivity.class);
                startActivity(intent);
            }
        });


    }
}