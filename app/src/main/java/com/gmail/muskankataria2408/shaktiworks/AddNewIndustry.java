package com.gmail.muskankataria2408.shaktiworks;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmObjectChangeListener;

public class AddNewIndustry extends AppCompatActivity implements View.OnClickListener{

    Realm realm;
    TextInputEditText name;
    Button Add;
    int id = 0;
    Spinner month,year;
    String TAG="AddNewIndustry";

    RealmObjectChangeListener<IndustryModel> listener;

    private RealmChangeListener realmListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_industry);

         month = findViewById(R.id.month);
         year =findViewById(R.id.year);
        name=(TextInputEditText)findViewById(R.id.name);
        Add=(Button)findViewById(R.id.addIndustry);

        RealmConfiguration.Builder builder = new RealmConfiguration.Builder()
                .name("IndustryManagement")
                .schemaVersion(1)
                .migration(new RealmMigrations());

        if (BuildConfig.DEBUG) {
            builder = builder.deleteRealmIfMigrationNeeded();
        }

        RealmConfiguration configuration = builder.build();
        realm = Realm.getInstance(configuration);

        String[] items = new String[]{"January", "February", "March","April","May","June","July","August","September","October","November","December"};
        String[] years = new String[]{"2017","2018","2019","2020","2021","2022","2023","2024","2025","2026","2027"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, years);

        year.setAdapter(yearAdapter);
        month.setAdapter(adapter);

        //Insertion to database
        Add.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.addIndustry)
        {
            String CollaborationMonth= month.getSelectedItem().toString();
            String Collaborationyear=year.getSelectedItem().toString();
            final IndustryModel industryModel =new IndustryModel(name.getEditableText().toString(),CollaborationMonth,Collaborationyear);
//Insert
            try {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Number currentid = realm.where(IndustryModel.class).max("id");
                        if (currentid == null) {
                            id = 1;
                        } else {
                            id = currentid.intValue() + 1;
                        }
                        //fetchId = id;
                        industryModel.setId(id);
                        realm.insertOrUpdate(industryModel);
                        Log.e("Path REal", realm.getPath());
                        //realm.deleteAll();

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            Toast.makeText(this,"IndustryModel Added Successfully",Toast.LENGTH_SHORT).show();

        }
    }
}
