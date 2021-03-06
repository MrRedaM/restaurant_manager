package com.example.tp_final.controller.activitiesAndFragments.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.tp_final.R;
import com.example.tp_final.model.Plat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddPlatActivity extends AppCompatActivity {

    private EditText nomEditText;
    private RadioGroup categorieRadioGroup;
    private RadioButton radioEntree;
    private RadioButton radioPrincipal;
    private RadioButton radioDessert;
    private EditText prixEditText;
    private EditText descriptionEditText;
    private ImageButton ajouterButton;
    private ImageButton cancelAjout;

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
        cancelAjout = findViewById(R.id.buttonCancelPlat);

        //ajouterButton.setEnabled(false);

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
                //ajouterButton.setEnabled(b[0] && b[1] && b[2] && b[3]);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        categorieRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                b[1] = true;
                //ajouterButton.setEnabled(b[0] && b[1] && b[2] && b[3]);
            }
        });

        prixEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                b[2] = s.length() > 0;
                //ajouterButton.setEnabled(b[0] && b[1] && b[2] && b[3]);
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
                //ajouterButton.setEnabled(b[0] && b[1] && b[2] && b[3]);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ajouterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
                View view = null;
                boolean addPlat = true;
                for (boolean bool : b) {
                    switch (i) {
                        case 0:
                            view = nomEditText;
                            break;
                        case 1:
                            view = categorieRadioGroup;
                            break;
                        case 2:
                            view = prixEditText;
                            break;
                        case 3:
                            view = descriptionEditText;
                            break;
                    }
                    if (!bool) {
                        YoYo.with(Techniques.Shake)
                                .duration(700)
                                .playOn(view);
                        addPlat = false;
                    }
                    i++;
                }

                if (addPlat) {
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

                    savePlat(plat);

                    //end activity
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(EXTRA_PLAT, plat);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }

            }
        });

        cancelAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });

    }

    public void saveData(Plat plat) {
        ObjectOutputStream out = null;
        try {
            switch (plat.getCategorie()) {
                case ENTREE:
                    out = new ObjectOutputStream(openFileOutput("entree.ser", MODE_PRIVATE));
                    break;
                case PRINCIPAL:
                    out = new ObjectOutputStream(openFileOutput("principal.ser", MODE_PRIVATE));
                    break;
                case DESSERT:
                    out = new ObjectOutputStream(openFileOutput("dessert.ser", MODE_PRIVATE));
            }
            out.writeObject(plat);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void savePlat(Plat plat) {

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String key = null;
        switch (plat.getCategorie()) {
            case ENTREE:
                key = "entree";
                break;
            case PRINCIPAL:
                key = "principal";
                break;
            case DESSERT:
                key = "dessert";
                break;
        }
        String json = appSharedPrefs.getString(key, "");
        Type type = new TypeToken<List<Plat>>() {
        }.getType();
        ArrayList<Plat> plats = gson.fromJson(json, type);
        if (plats == null) plats = new ArrayList<>();
        plats.add(plat);
        String jsonNew = gson.toJson(plats);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.putString(key, jsonNew);
        prefsEditor.apply();

    }
}
