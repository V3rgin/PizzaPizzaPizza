

package com.example.pizzapizzapizza;

import android.content.DialogInterface;
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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        {
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
        }


        //Seekbar Pizzy (rozmiar)
        pizzaRozmiar.setText("32cm");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean b) {
                switch (progressValue){
                    case 0:
                        pizzaRozmiar.setText("32cm");
                        break;
                    case 1:
                        pizzaRozmiar.setText("42cm");
                        break;
                    case 2:
                        pizzaRozmiar.setText("52cm");
                        break;
                    case 3:
                        pizzaRozmiar.setText("69cm");
                        break;
                    case 4:
                        pizzaRozmiar.setText("100cm");
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
            boolean isValid = false;
            for (CheckBox checkbox : checkBoxes) {
                if(checkbox.isChecked()){
                    checked.add(checkbox.getText().toString());
                }
            }

            isValid = true;
            //walidacja danych osobowych
            String daneOsoboweString = daneOsobowe.getText().toString().trim();
            String numerTelEditTextString = numerTelEditText.getText().toString().trim();

            if(!daneOsoboweString.matches("^[A-Za-z ]+$")){
                Toast.makeText(this, "Wpisz poprawne dane osobowe!", Toast.LENGTH_SHORT).show();
                isValid = false;
            }
            if(!(numerTelEditTextString.matches("^[0-9]+$") && numerTelEditTextString.length() == 9)) {
                Toast.makeText(this, "Wpisz poprawny numer telefonu!", Toast.LENGTH_SHORT).show();
                isValid = false;
            }


            if(isValid){
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
                if(checkbox.isChecked()){
                    checkbox.setChecked(false);
                }
            }
        });
    }

    private void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Zamówienie");
        builder.setMessage("Dziękujęmy za zamówienie! Po kliknięciu w powiadomoenie, zostaną " +
                "państwo przesłani do formularza zapłaty");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}