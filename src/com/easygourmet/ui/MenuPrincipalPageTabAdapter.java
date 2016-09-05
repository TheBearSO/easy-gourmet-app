package com.easygourmet.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.easygourmet.app.FragmentTabNevera;
import com.easygourmet.app.FragmentTabRecetas;
import com.easygourmet.app.FragmentTabUsuarios;

public class MenuPrincipalPageTabAdapter extends FragmentPagerAdapter {

	private final int TABS = 3;
	
	public MenuPrincipalPageTabAdapter(FragmentManager fm) {
		super(fm);
	}
	
	@Override
	public Fragment getItem(int position) {
		switch (position) {
			case 0:
				return new FragmentTabNevera();
			case 1:
				return new FragmentTabRecetas();
			case 2:
				return new FragmentTabUsuarios();
			default:
				return new FragmentTabNevera();
		}

	}

	@Override
	public int getCount() {
		return TABS;
	}

}
