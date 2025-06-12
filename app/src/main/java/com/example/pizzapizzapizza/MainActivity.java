

package com.example.pizzapizzapizza;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
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
                        ciastoMnoznik = 1.4;
                        break;
                    case 3:
                        pizzaRozmiar.setText("69cm");
                        ciastoMnoznik = 1.6;
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

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Zamówienie");
        builder.setMessage("Dziękujęmy za zamówienie! Po kliknięciu w powiadomienie, zostaną " +
                "państwo przesłani do formularza zapłaty");

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
        //zerowanie dodatków, aby nie dodawały się ciągiem
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
}