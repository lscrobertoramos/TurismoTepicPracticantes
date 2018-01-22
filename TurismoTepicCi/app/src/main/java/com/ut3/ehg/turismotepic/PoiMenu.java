package com.ut3.ehg.turismotepic;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


import static android.content.Context.MODE_PRIVATE;

public class PoiMenu extends Fragment implements View.OnClickListener{

    ViewGroup root;
    Context cntx;
    FloatingActionButton fab;
    SharedPreferences cat;
    SharedPreferences.Editor editarCat;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final AppBarLayout appBarLayout;
        root = (ViewGroup) inflater.inflate(R.layout.activity_poi_menu, null);
        cntx = container.getContext();

        ImageButton bhotel = (ImageButton) root.findViewById(R.id.hoteles);
        ImageButton brestaurant = (ImageButton) root.findViewById(R.id.restaurantes);
        ImageButton bmonumento = (ImageButton) root.findViewById(R.id.monumentos);
        ImageButton bmuseos = (ImageButton) root.findViewById(R.id.museo);
        ImageButton bbanco = (ImageButton) root.findViewById(R.id.bancos);
        ImageButton bfarmacia = (ImageButton) root.findViewById(R.id.farmacias);
        ImageButton btienda = (ImageButton) root.findViewById(R.id.tiendas);
        ImageButton bplaza = (ImageButton) root.findViewById(R.id.plazas);
        ImageButton bparque= (ImageButton) root.findViewById(R.id.parques);
        ImageButton botro = (ImageButton) root.findViewById(R.id.otros);

        bhotel.setOnClickListener(this);
        brestaurant.setOnClickListener(this);
        bmonumento.setOnClickListener(this);
        bmuseos.setOnClickListener(this);
        bbanco.setOnClickListener(this);
        bfarmacia.setOnClickListener(this);
        btienda.setOnClickListener(this);
        bplaza.setOnClickListener(this);
        bparque.setOnClickListener(this);
        botro.setOnClickListener(this);

        return root;
    }

        @Override
        public void onClick(View v){
            int idCat;
            switch(v.getId()){
                case R.id.hoteles:
                    idCat =1;
                    break;
                case R.id.restaurantes:
                    idCat =2;
                    break;
                case R.id.monumentos:
                    idCat =3;
                    break;
                case R.id.museo:
                    idCat =4;
                    break;
                case R.id.bancos:
                    idCat =5;
                    break;
                case R.id.farmacias:
                    idCat =6;
                    break;
                case R.id.tiendas:
                    idCat =7;
                    break;
                case R.id.plazas:
                    idCat =8;
                    break;
                case R.id.parques:
                    idCat =9;
                    break;
                case R.id.otros:
                    idCat =10;
                    break;
                default:
                    idCat=0;
            }
            cat = getContext().getSharedPreferences("categoria",MODE_PRIVATE);
            editarCat=cat.edit();
            editarCat.putInt("idCat",idCat);
            editarCat.commit();
             String fragmentTemp="com.ut3.ehg.turismotepic.CategoriaActivity";
            DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
            FragmentTransaction tx = getActivity().getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.lframe, Fragment.instantiate(getContext(), fragmentTemp)).addToBackStack("tag");
            tx.commit();
            drawer.closeDrawer(GravityCompat.START);
        }
}

