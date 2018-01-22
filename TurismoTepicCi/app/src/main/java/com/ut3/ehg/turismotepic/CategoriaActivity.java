package com.ut3.ehg.turismotepic;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ut3.ehg.turismotepic.db.db_pois;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class CategoriaActivity extends Fragment implements ListView.OnItemClickListener{
    ArrayList<String> listaPoisid;
    ViewGroup root;
    Context cntx;
    //FloatingActionButton fab;
    SharedPreferences cat,poi;
    SharedPreferences.Editor editarCat;//,editarPoi;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //final AppBarLayout appBarLayout;
        root = (ViewGroup) inflater.inflate(R.layout.activity_categoria, null);
        cntx = container.getContext();

        db_pois pois= new db_pois(root.getContext());

        int idCat=1;

        cat=this.getActivity().getSharedPreferences("categoria",MODE_PRIVATE);
        idCat=cat.getInt("idCat",0);
        ListView lvPois = (ListView) root.findViewById(R.id.lvPois);
        ImageView imageCat = (ImageView)root.findViewById(R.id.ivCategoria);
        TextView tvCat=(TextView)root.findViewById(R.id.tvCategoria);
        ArrayList<String> listaPois=pois.obtenerPois(idCat);
        listaPoisid=pois.obtenerPoisId(idCat);
        //TextView acompa = (TextView) root.findViewById(R.id.tvTexto_spPoi);
        ArrayAdapter<String> adp = new ArrayAdapter<String>(getActivity(),R.layout.sp_poi,R.id.tvTexto_spPoi,listaPois);
        lvPois.setOnItemClickListener(this);
        lvPois.setAdapter(adp);


        switch(idCat){
            case 1:
                imageCat.setImageResource(R.drawable.hoteles);
                tvCat.setText("Hoteles");
                break;
            case 2:
                imageCat.setImageResource(R.drawable.restaurantes);
                tvCat.setText("Restaurantes");
                break;
            case 3:
                imageCat.setImageResource(R.drawable.monumentos);
                tvCat.setText("Monumentos");
                break;
            case 4:
                imageCat.setImageResource(R.drawable.museos);
                tvCat.setText("Museos");
                break;
            case 5:
                imageCat.setImageResource(R.drawable.bancos);
                tvCat.setText("Bancos");
                break;
            case 6:
                imageCat.setImageResource(R.drawable.farmacias);
                tvCat.setText("Farmacias");
                break;
            case 7:
                imageCat.setImageResource(R.drawable.tiendas);
                tvCat.setText("Tiendas");
                break;
            case 8:
                imageCat.setImageResource(R.drawable.mall);
                tvCat.setText("Plazas comerciales");
                break;
            case 9:
                imageCat.setImageResource(R.drawable.parques);
                tvCat.setText("Parques");
                break;
            case 10:
                imageCat.setImageResource(R.drawable.otros);
                tvCat.setText("Otros lugares");
                break;
        }


        return root;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String idPoi = listaPoisid.get(position);
        poi = getContext().getSharedPreferences("poi",MODE_PRIVATE);
        editarCat=poi.edit();
        editarCat.putString("idPoi",idPoi);
        editarCat.commit();
        String fragmentTemp="com.ut3.ehg.turismotepic.DetalladoActivity";
        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        FragmentTransaction tx = getActivity().getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.lframe, Fragment.instantiate(getContext(), fragmentTemp)).addToBackStack("tag");
        tx.commit();
        drawer.closeDrawer(GravityCompat.START);
    }
}
