

package com.example.pizzapizzapizza;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.app.NotificationManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "DEFAULT_CHANNEL_ID";
    //private LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main);
    private EditText daneOsobowe, numerTelEditText, adresEditText;
    private CheckBox pepperoni, szynka, salami, boczek;
    private CheckBox pomidory, jalapeno, pieczarki, ser2;
    private CheckBox oliwki, oregano, rukola, tabasco;
    private RadioButton jasne, ciemne, pszenne;
    private RadioGroup radioGroup;
    private TextView pizzaRozmiar;
    private SeekBar seekBar;
    private Button wyczyscZamowienie, zamow;

    private double ciastoMnoznik = 1.0;
    private double ciastoRodzajCena = 0.0;
    private double dodatkiCena = 0.0;

    private final List<String> resultChecked = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        daneOsobowe = findViewById(R.id.daneOsobowe);
        numerTelEditText = findViewById(R.id.numerTelEditText);
        adresEditText = findViewById(R.id.adresEditText);
        pepperoni = findViewById(R.id.pepperoni);
        szynka = findViewById(R.id.szynka);
        salami = findViewById(R.id.salami);
        boczek = findViewById(R.id.boczek);
        pomidory = findViewById(R.id.pomidory);
        jalapeno = findViewById(R.id.jalapeno);
        pieczarki = findViewById(R.id.pieczarki);
        ser2 = findViewById(R.id.ser2);
        oliwki = findViewById(R.id.oliwki);
        oregano = findViewById(R.id.oregano);
        rukola = findViewById(R.id.rukola);
        tabasco = findViewById(R.id.tabasco);
        jasne = findViewById(R.id.jasne);
        ciemne = findViewById(R.id.ciemne);
        pszenne = findViewById(R.id.pszenne);
        radioGroup = findViewById(R.id.radioGroup);
        pizzaRozmiar = findViewById(R.id.pizzaRozmiar);
        seekBar = findViewById(R.id.seekBar);
        wyczyscZamowienie = findViewById(R.id.wyczyscZamowienie);
        zamow = findViewById(R.id.zamow);


        //Seekbar Pizzy (rozmiar)
        pizzaRozmiar.setText("32cm");
        seekBar.setProgress(0);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean b) {
                switch (progressValue) {
                    case 0:
                        pizzaRozmiar.setText("32cm");
                        break;
                    case 1:
                        pizzaRozmiar.setText("42cm");
                        ciastoMnoznik = 1.25;
                        break;
                    case 2:
                        pizzaRozmiar.setText("52cm");
                        ciastoMnoznik = 1.45;
                        break;
                    case 3:
                        pizzaRozmiar.setText("69cm");
                        ciastoMnoznik = 1.65;
                        break;
                    case 4:
                        pizzaRozmiar.setText("100cm");
                        ciastoMnoznik = 2.0;
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //srawdzamy checkboxy
        CheckBox[] checkBoxes = {pepperoni, szynka, salami, boczek, pomidory, jalapeno, pieczarki, ser2, oliwki,
                oregano, rukola, tabasco};

        List<String> checked = new ArrayList<>();

        zamow.setOnClickListener(view -> {
            for (CheckBox checkbox : checkBoxes) {
                if (checkbox.isChecked()) {
                    dodatkiCena += 1.0;
                    checked.add(checkbox.getText().toString());
                    resultChecked.add(checkbox.getText().toString());
                }
            }

            boolean isValid = true;

            //walidacja danych osobowych, isValid sprawdza czy dane osobowe, numer i adres zostały podane i czy numer i dane osobowe poprawnie, a adres czy nie jest pusty
            String daneOsoboweString = daneOsobowe.getText().toString().trim();
            String numerTelEditTextString = numerTelEditText.getText().toString().trim();
            String adresEditTextString = adresEditText.getText().toString().trim();

            if (!daneOsoboweString.matches("^[A-Za-z ]+$")) {
                Toast.makeText(this, "Wpisz poprawne dane osobowe!", Toast.LENGTH_SHORT).show();
                isValid = false;
            }

            if (!(numerTelEditTextString.matches("^[0-9]+$") && numerTelEditTextString.length() == 9)) {
                Toast.makeText(this, "Wpisz poprawny numer telefonu!", Toast.LENGTH_SHORT).show();
                isValid = false;
            }

            if (adresEditTextString.isEmpty()) {
                Toast.makeText(this, "Wpisz adres!", Toast.LENGTH_SHORT).show();
                isValid = false;
            }

            //Sprawdzanie Radio button

            int radioButtonChoice = radioGroup.getCheckedRadioButtonId();

            if (radioButtonChoice == R.id.jasne) {
                ciastoRodzajCena = 20;
            } else if (radioButtonChoice == R.id.ciemne) {
                ciastoRodzajCena = 25;
            } else if (radioButtonChoice == R.id.pszenne) {
                ciastoRodzajCena = 30;
            } else {
                Toast.makeText(this, "Wybierz ciasto!", Toast.LENGTH_SHORT).show();
                isValid = false;
            }

            if (isValid) {
                showAlertDialog();
            }
        });

        //czyszczenie
        wyczyscZamowienie.setOnClickListener(view -> {
            daneOsobowe.setText("");
            numerTelEditText.setText("");
            adresEditText.setText("");
            radioGroup.clearCheck();
            seekBar.setProgress(0);
            pizzaRozmiar.setText("32cm");

            for (CheckBox checkbox : checkBoxes) {
                if (checkbox.isChecked()) {
                    checkbox.setChecked(false);
                }
            }
        });
    }

    private String wynikZamowienia() {
        String rodzajCiasta = "";
        String skladniki = "";
        String rozmiarPizzy;

        int radioButtonChoice = radioGroup.getCheckedRadioButtonId();
        if (radioButtonChoice == R.id.jasne) {
            rodzajCiasta = "jasne";
        } else if (radioButtonChoice == R.id.ciemne) {
            rodzajCiasta = "ciemne";
        } else if (radioButtonChoice == R.id.pszenne) {
            rodzajCiasta = "pszenne";
        }

        for (int i = 0; i < resultChecked.size(); i++) {
            skladniki += resultChecked.get(i) + ", ";
        }

        rozmiarPizzy = String.valueOf(pizzaRozmiar.getText());

        return " Ciasto: " + rodzajCiasta + ",\n Skladniki: " + skladniki + "\n Rozmiar Pizzy: " + rozmiarPizzy + "\n \n Po klinknięciu 'OK' zostanie wysłane" + "\n powiadomienie z formularzem zaplaty.";
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Wynik zamówienia: ");
        builder.setMessage(wynikZamowienia());

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sendNotification();
            }
        });
        builder.create().show();
    }

    private void sendNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                    PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        100);
                createNotificationChannel();
            }
        }

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra("pizza", String.valueOf((ciastoRodzajCena * ciastoMnoznik) + dodatkiCena));

        //zerowanie dodatków, aby poszczególne zamówienia nie dodawały się do siebie
        dodatkiCena = 0;

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Prosimy o zapłatę!")
                .setContentText("Kliknij w powiadomienie, aby zapłacić!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Kanał Ogólny";
            String description = "Domyślny kanał dla powiadomień";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("DEFAULT_CHANNEL_ID", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void saveInformation() {
        SharedPreferences preferences = getSharedPreferences("mojeDane", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("DaneOsobowe", daneOsobowe.getText().toString());
        editor.putString("NumerTelefonu", numerTelEditText.getText().toString());
        editor.putString("Adres", adresEditText.getText().toString());

        editor.apply();
    }

    private void setSavedInformation() {
        SharedPreferences preferences = getSharedPreferences("mojeDane", MODE_PRIVATE);
        daneOsobowe.setText(preferences.getString("DaneOsobowe", ""));
        numerTelEditText.setText(preferences.getString("NumerTelefonu", ""));
        adresEditText.setText(preferences.getString("Adres", ""));
    }

    protected void onPause() {
        super.onPause();
        saveInformation();
    }

    protected void onResume() {
        super.onResume();
        String czyKolejneZamowienie = getIntent().getStringExtra("kolejneZamowienie");

        //nie działa w drugą stronę, więc sprawdza czy String "tak", równy jest przesłanej wartości w intencie, jeśli tak, to wstaw informacje zapisane
        //dzięki metodzie Sharedpreferences();

        if ("tak".equals(czyKolejneZamowienie)) {
            setSavedInformation();
        }
    }

}