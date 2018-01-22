package com.ut3.ehg.turismotepic;

import android.app.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v4.app.FragmentTransaction;

/**
 * Created by LAB-DES-05 on 07/02/2017.
 */
// Se modifico el layout de ayuda para que quede como acerca de
public class Ayuda extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root;


        root = (ViewGroup) inflater.inflate(R.layout.ayuda, null);

        return root;
        }
    }
