package com.gmail.muskankataria2408.shaktiworks;

import android.content.Context;
import android.graphics.ColorSpace;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import io.realm.OrderedRealmCollection;
import io.realm.RealmList;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class RecyclerViewAdapter extends RealmRecyclerViewAdapter<ItemsModel, RecyclerViewAdapter.ViewHolder> {

    Context context;
    RealmList<ItemsModel> results;

    public RecyclerViewAdapter(OrderedRealmCollection<ItemsModel> data,RealmList<ItemsModel> results) {
        super(data, true);
        this.results=results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.set_view, viewGroup, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
         ItemsModel itemsModel  = getItem(i);
        viewHolder.textView.setText(String.valueOf(results.get(i).getItem()));
        viewHolder.quantity.setText(String.valueOf(results.get(i).getQuantity()));


    }



    /*@Override
    public int getItemCount() {
        return results.size();
    }*/


    static public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        TextView textView = (TextView) itemView.findViewById(R.id.text_item);
        TextView quantity =(TextView)itemView.findViewById(R.id.quantity);
    }
}
