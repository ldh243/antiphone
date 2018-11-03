package com.example.jun.antiphone;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import entity.StoreDTO;

public class CustomerStoreAdapter extends ArrayAdapter<StoreDTO> {

    private Activity context;
    private List<StoreDTO> stores;
    private int resource;

    public CustomerStoreAdapter(Activity context, int resource, List<StoreDTO> stores) {
        super(context, resource, stores);
        this.context = context;
        this.stores = stores;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);

        TextView address = row.findViewById(R.id.store_address);
        TextView distance = row.findViewById(R.id.store_distance);

        StoreDTO store = this.stores.get(position);
        address.setText(store.getStoreAddress());
        distance.setText(store.getDistanceToCurrentPos());

        return row;
    }
}
