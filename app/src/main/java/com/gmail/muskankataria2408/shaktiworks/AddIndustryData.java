package com.gmail.muskankataria2408.shaktiworks;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class AddIndustryData extends AppCompatActivity implements View.OnClickListener {


    Spinner month,year,itemsList;
    Button AddData,ViewData,add;
    TextInputEditText item,quantity;
    TextView existing;
    Realm realm;
    String industryName;
    ItemsModel itemsModel;
    String itemNames[];
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industry_data);
       industryName = getIntent().getStringExtra("name");
        setTitle(getIntent().getStringExtra("name"));
        month = findViewById(R.id.month);
        year =findViewById(R.id.year);
        AddData=(Button)findViewById(R.id.viewData);
        ViewData=(Button)findViewById(R.id.addData);
        add=(Button)findViewById(R.id.add);
        item=(TextInputEditText)findViewById(R.id.addItem) ;
        quantity=(TextInputEditText)findViewById(R.id.quantity);
        itemsList=(Spinner)findViewById(R.id.Items);
        existing=(TextView)findViewById(R.id.existing);
        Realm.init(this);

      //  AddData.setVisibility(View.GONE);

        item.setVisibility(View.VISIBLE);
        quantity.setVisibility(View.VISIBLE);
        add.setVisibility(View.VISIBLE);
        itemsList.setVisibility(View.VISIBLE);
        existing.setVisibility(View.VISIBLE);



        RealmConfiguration.Builder builder = new RealmConfiguration.Builder()
                .name("IndustryManagement")
                .schemaVersion(1)
                .migration(new RealmMigrations());

        if (BuildConfig.DEBUG) {
            builder = builder.deleteRealmIfMigrationNeeded();
        }

        RealmConfiguration configuration = builder.build();
        realm = Realm.getInstance(configuration);

        final String[] items = new String[]{"January", "February", "March","April","May","June","July","August","September","October","November","December"};
        String[] years = new String[]{"2017","2018","2019","2020","2021","2022","2023","2024","2025","2026","2027"};

        ArrayAdapter<String> monthadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, years);

        year.setAdapter(yearAdapter);
        month.setAdapter(monthadapter);

        RealmResults<ItemsModel> itemModelResults = realm.where(ItemsModel.class).findAll();
        List<String> itemName=new ArrayList<>();
        //  itemNames= new String[5000];
        // int i=0;
        for(ItemsModel itemModel: itemModelResults)
        {
            itemName.add(itemModel.getItem());

        }


        final ArrayAdapter itemsAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, itemName);
        itemsList.setAdapter(itemsAdapter);


        ViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AddIndustryData.this,ViewAllData.class);
                i.putExtra("name",industryName);
                i.putExtra("year",year.getSelectedItem().toString());
                i.putExtra("month",month.getSelectedItem().toString());
                startActivity(i);
            }
        });


                add.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String month1 = month.getSelectedItem().toString();
                        String year1 = year.getSelectedItem().toString();
                         final ItemsModel itemsModel =new ItemsModel(item.getEditableText().toString(),Long.parseLong(quantity.getEditableText().toString()));
                        final MonthModel monthModel=new MonthModel(year1,month1,year1+month1);
                        final RealmList<ItemsModel> itemsModelList=new RealmList<>();
                       itemsModelList.add(itemsModel);
                        final IndustryModel industryModel =realm.where(IndustryModel.class).equalTo("name",industryName).findFirst();
                        try {
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    Number currentid = realm.where(ItemsModel.class).max("id");
                                    if (currentid == null) {
                                        id = 1;
                                    } else {
                                        id = currentid.intValue() + 1;
                                    }
                                    //fetchId = id;
                                    itemsModel.setId(id);
                                    realm.insertOrUpdate(itemsModel);


                                    monthModel.addItemsModel(itemsModel);
                                    realm.insertOrUpdate(monthModel);

                                    industryModel.addMonthModel(monthModel);
                                    realm.insertOrUpdate(industryModel);
                                    Log.e("Path REal", realm.getPath());
                                    //realm.deleteAll();

                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


//                        try{
//                            realm.executeTransaction(new Realm.Transaction() {
//                                @Override
//                                public void execute(Realm realm) {
//                                    industryModel.addMonthModel(monthModel);
//                                    realm.insertOrUpdate(industryModel);
//                                }
//                            });
//
//                        }
//                        catch (Exception e)
//                        {
//                            e.printStackTrace();
//                        }

                        Toast.makeText(AddIndustryData.this,"Added SuccessFully",Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onClick(View view) {


    }
}
