package com.gmail.muskankataria2408.shaktiworks;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;

public class ViewAllData extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView industryName,year,month,sum;
    Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_data);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        industryName=(TextView)findViewById(R.id.IndustryName);
        year=(TextView)findViewById(R.id.year);
        month=(TextView)findViewById(R.id.month);
        sum=(TextView)findViewById(R.id.sum);

        RealmConfiguration.Builder builder = new RealmConfiguration.Builder()
                .name("IndustryManagement")
                .schemaVersion(1)
                .migration(new RealmMigrations());

        if (BuildConfig.DEBUG) {
            builder = builder.deleteRealmIfMigrationNeeded();
        }

        RealmConfiguration configuration = builder.build();
        realm = Realm.getInstance(configuration);

        String name=getIntent().getStringExtra("name");
        industryName.setText(name);
        year.setText(getIntent().getStringExtra("year"));
        month.setText(getIntent().getStringExtra("month"));

        String key=getIntent().getStringExtra("year")+getIntent().getStringExtra("month");

        IndustryModel industryModel =realm.where(IndustryModel.class).equalTo("name",name).findFirst();

        MonthModel monthModel = industryModel.getMonthModel().where().equalTo("compositePrimaryKey",key).findFirst();
          if(monthModel!=null) {
              RealmList<ItemsModel> realmResults = monthModel.getItemsModel();

              final RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(realmResults,realmResults);
              recyclerView.setAdapter(recyclerViewAdapter);
              recyclerView.setLayoutManager(new LinearLayoutManager(this));


              recyclerViewAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                  @Override
                  public void onChanged() {
                      super.onChanged();
                  }

                  @Override
                  public void onItemRangeChanged(int positionStart, int itemCount) {
                      super.onItemRangeChanged(positionStart, itemCount);
                  }

                  @Override
                  public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {

                      super.onItemRangeChanged(positionStart, itemCount, payload);
                  }

                  @Override
                  public void onItemRangeInserted(int positionStart, int itemCount) {
                      super.onItemRangeInserted(positionStart, itemCount);
                      recyclerViewAdapter.notifyDataSetChanged();
                  }

                  @Override
                  public void onItemRangeRemoved(int positionStart, int itemCount) {
                      super.onItemRangeRemoved(positionStart, itemCount);
                      // tv.setText( recyclerViewAdapter.getItem(positionStart).getNumber() + " removed");

                  }

                  @Override
                  public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                      super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                  }
              });


          }
          else{

              Toast.makeText(ViewAllData.this,"Add Items to the month before",Toast.LENGTH_SHORT).show();
          }

    }
}
