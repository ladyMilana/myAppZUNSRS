package com.example.appzunsrs;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

public class ListAdapter extends ArrayAdapter {

    private Activity mContext;
    List<Radnje> listaRadnji;


    public ListAdapter(Activity mContext, List<Radnje> listaRadnji){

        super(mContext, R.layout.store_info, listaRadnji);
        this.mContext=mContext;
        this.listaRadnji=listaRadnji;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=mContext.getLayoutInflater();
        View listItemView=inflater.inflate(R.layout.store_info, null, true);

        TextView tvNaziv=listItemView.findViewById(R.id.storeInfo1);
        TextView tvAdresa=listItemView.findViewById(R.id.storeInfo2);
        TextView tvTelefon=listItemView.findViewById(R.id.storeInfo3);

        Radnje radnje=listaRadnji.get(position);
        tvNaziv.setText(radnje.getNaziv());
        tvAdresa.setText(radnje.getAdresa());
        tvTelefon.setText(radnje.getTelefon());

        return listItemView;

    }
}
