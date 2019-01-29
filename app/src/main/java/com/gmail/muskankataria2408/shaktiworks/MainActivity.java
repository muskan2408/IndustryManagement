package com.gmail.muskankataria2408.shaktiworks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    Button add_new;
    Spinner industry;
    Realm realm;
    RealmResults<IndustryModel> modelList;
    String industryname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);

        RealmConfiguration.Builder builder = new RealmConfiguration.Builder()
                .name("IndustryManagement")
                .schemaVersion(1)
                .migration(new RealmMigrations());

        if (BuildConfig.DEBUG) {
            builder = builder.deleteRealmIfMigrationNeeded();
        }

        RealmConfiguration configuration = builder.build();
        realm = Realm.getInstance(configuration);
        add_new=(Button)findViewById(R.id.addNew);
        industry=(Spinner)findViewById(R.id.industry);

        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,AddNewIndustry.class);
                startActivity(i);
            }
        });

        modelList=realm.where(IndustryModel.class).findAll();
        final ArrayList<String> industries=new ArrayList<>();
        for(IndustryModel i:modelList){

            industries.add(i.getName());
        }
        final ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, industries);

        industry.setAdapter(arrayAdapter);
//        industryname = industry.getSelectedItem().toString();

        industry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(industries!=null) {

                    final Intent intent;

                    intent = new Intent(MainActivity.this, AddIndustryData.class);
// and so on
// .....}}
                    intent.putExtra("name", industry.getSelectedItem().toString());
                    startActivity(intent);

//                Intent intent = new Intent(MainActivity.this,AddIndustryData.class);
//                 intent.putExtra("name",view);
//                 startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
    });
//        {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                 Intent intent = new Intent(MainActivity.this,AddIndustryData.class);
//                 intent.putExtra("name",industryname);
//                 startActivity(intent);
//            }
//        });

        try {

            //collection Notification


            OrderedRealmCollectionChangeListener<RealmResults<IndustryModel>> changeListener = new OrderedRealmCollectionChangeListener<RealmResults<IndustryModel>>() {
                @Override
                public void onChange(RealmResults<IndustryModel> models, OrderedCollectionChangeSet changeSet) {

                    //  arrayAdapter.add(modelObj);


                    if (changeSet == null) {

                        return;
                    }
                    // For deletions, the adapter has to be notified in reverse order.
                    OrderedCollectionChangeSet.Range[] deletions = changeSet.getDeletionRanges();
                    for (int i = deletions.length - 1; i >= 0; i--) {
                        OrderedCollectionChangeSet.Range range = deletions[i];
                      //  arrayAdapter.remove(delobj);
                        arrayAdapter.notifyDataSetChanged();

                    }

                    OrderedCollectionChangeSet.Range[] insertions = changeSet.getInsertionRanges();
                    for (OrderedCollectionChangeSet.Range range : insertions) {
                       // arrayAdapter.add(modelObj);
                        arrayAdapter.notifyDataSetChanged();
                    }

                    OrderedCollectionChangeSet.Range[] modifications = changeSet.getChangeRanges();
                    for (OrderedCollectionChangeSet.Range range : modifications) {

                        // realm.copyToRealmOrUpdate(modelList);
                        //modelList.addChangeListener((RealmChangeListener<RealmResults<Model>>) listener);
                        arrayAdapter.notifyDataSetChanged();
                    }
                }

            };
           modelList.addChangeListener(changeListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
