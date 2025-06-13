package com.example.pizzapizzapizza;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.net.Proxy;

public class PaymentActivity extends AppCompatActivity {
    private TextView cenaTextView;
    private EditText zaplataEditText;
    private Button potwierdzButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_form);

        cenaTextView = findViewById(R.id.cenaTextView);
        zaplataEditText = findViewById(R.id.zaplataEditText);
        potwierdzButton = findViewById(R.id.potwierdzButton);

        String getCena = getIntent().getStringExtra("pizza");
        cenaTextView.setText(getCena + "zł");

        potwierdzButton.setOnClickListener(view -> {
            String zaplataEditTextString = zaplataEditText.getText().toString().trim();

            if (getCena.equals(zaplataEditTextString)) {
                showAlertDialog();
            } else {
                Toast.makeText(this, "Wpisz poprawną cenę!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Zamówienie");
        builder.setMessage("Dziękujemy za zapłatę!");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                startActivity(intent);
            }

        });

        builder.setNegativeButton("Chce jeszcze zamowić!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                intent.putExtra("kolejneZamowienie", "tak");
                startActivity(intent);
            }

        });

        builder.create().show();
    }
}
