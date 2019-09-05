package com.example.tp_final.controller.activitiesAndFragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.tp_final.R;
import com.example.tp_final.model.Plat;

public class AddPlatActivity extends AppCompatActivity {

    private EditText nomEditText;
    private RadioGroup categorieRadioGroup;
    private RadioButton radioEntree;
    private RadioButton radioPrincipal;
    private RadioButton radioDessert;
    private EditText prixEditText;
    private EditText descriptionEditText;
    private Button ajouterButton;

    //public static final String EXTRA_NOM = "EXTRA_NOM";
    //public static final String EXTRA_PRIX = "EXTRA_PRIX";
    //public static final String EXTRA_DESC = "EXTRA_DESC";
    //public static final String EXTRA_CAT = "EXTRA_CAT";
    public static final String EXTRA_PLAT = "EXTRA_PLAT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plat);

        nomEditText = findViewById(R.id.nomEditText);
        categorieRadioGroup = findViewById(R.id.cetegorieRadioGroup);
        radioEntree = findViewById(R.id.radioEntree);
        radioPrincipal = findViewById(R.id.radioPrincipal);
        radioDessert = findViewById(R.id.radioDessert);
        prixEditText = findViewById(R.id.prixEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        ajouterButton = findViewById(R.id.ajouterPlatButton);

        final boolean[] b = new boolean[4];
        for (boolean bool : b) {
            bool = false;
        }

        nomEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                b[0] = s.length() > 0;
                ajouterButton.setEnabled(b[0] && b[1] && b[2] && b[3]);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        categorieRadioGroup.addView(radioEntree, 0);
        categorieRadioGroup.addView(radioPrincipal, 1);
        categorieRadioGroup.addView(radioDessert, 2);
        categorieRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                b[1] = true;
                ajouterButton.setEnabled(b[0] && b[1] && b[2] && b[3]);
            }
        });

        prixEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                b[3] = s.length() > 0;
                ajouterButton.setEnabled(b[0] && b[1] && b[2] && b[3]);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        descriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                b[3] = s.length() > 0;
                ajouterButton.setEnabled(b[0] && b[1] && b[2] && b[3]);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ajouterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ajouter plat
                Plat.Categorie c = Plat.Categorie.ENTREE;
                switch (categorieRadioGroup.getCheckedRadioButtonId()) {
                    case R.id.radioEntree:
                        c = Plat.Categorie.ENTREE;
                        break;
                    case R.id.radioPrincipal:
                        c = Plat.Categorie.PRINCIPAL;
                        break;
                    case R.id.radioDessert:
                        c = Plat.Categorie.DESSERT;
                        break;
                }
                Plat plat = new Plat(nomEditText.getText().toString(),
                        Float.valueOf(prixEditText.getText().toString()),
                        c,
                        descriptionEditText.getText().toString());
                //end activity
                Intent returnIntent = new Intent();
                returnIntent.putExtra(EXTRA_PLAT, plat);
                setResult(RESULT_OK, returnIntent);
                finish();

                //bundle.putString(EXTRA_NOM, nomEditText.getText().toString());
                //bundle.putFloat(EXTRA_PRIX, Float.valueOf(prixEditText.getText().toString()));

            }
        });

    }
}
