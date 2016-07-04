package com.easygourmet.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.easygourmet.app.FragmentTabIngredientes;
import com.easygourmet.app.FragmentTabNevera;
import com.easygourmet.app.FragmentTabRecetas;

public class MyPageAdapter extends FragmentPagerAdapter {
		
	    /**
	     * @param fm
	     */
	    public MyPageAdapter(FragmentManager fm) {
	    	super(fm);
	        
	    }
	    /* (non-Javadoc)
	     * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
	     */
	    @Override
	    public Fragment getItem(int position) {
	    	switch (position) {
		        case 0:
		            return new FragmentTabNevera();
		        case 1:
		        	return new FragmentTabRecetas();
		        case 2:
		        	return new FragmentTabIngredientes();
		        default:
		        	return new FragmentTabNevera();
	        }
	 
	       
	    }
	 
	    /* (non-Javadoc)
	     * @see android.support.v4.view.PagerAdapter#getCount()
	     */
	    @Override
	    public int getCount() {
	        return 3;
	    }

}
