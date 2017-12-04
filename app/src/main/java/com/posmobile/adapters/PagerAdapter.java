package com.posmobile.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.posmobile.Compras;
import com.posmobile.SeleccionProducto;

/**
 * Created by personal on 02/12/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int numeroTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.numeroTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Compras tab1 = new Compras();
                return tab1;
            case 1:
                SeleccionProducto tab2 = new SeleccionProducto();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numeroTabs;
    }

}