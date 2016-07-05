package com.easygourmet.app;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.easygourmet.main.R;
import com.easygourmet.ui.MyPageAdapter;
import com.easygourmet.utils.Utils;


/**
 * La clase MenuPrincipal la cual extiende de un activity,
 *  la misma contiene 3 tabs y es la pantalla principal de la aplicacion.
 */
public class MenuPrincipal extends FragmentActivity {

	/** Es el elemento que contiene a todas las tabs del menu principal */
	private TabHost tabHost;
	private ViewPager pager; 

	private MyPageAdapter mPagerAdapter;
	
	private HashMap<String, TabInfo> mapTabInfo = new HashMap<>();
    private TabInfo mCurrentTab = null;
    
    final String TAB_NAME_NEVERA = "¿Que Hay en la Nevera?";
	final String TAB_NAME_RECETAS = "Recetas";
	final String TAB_NAME_INGREDIENTES = "Ingredientes";
	
	
	/* (non-Javadoc)
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_menu_principal);
		
		//Seteo la ultima tab activa despues de la rotación
		if(savedInstanceState != null){
			Integer currentTab = savedInstanceState.getInt("currentTab");
			initTabs(currentTab);
		}else{
			initTabs(0);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		outState.putInt("currentTab", this.tabHost.getCurrentTab());
		super.onSaveInstanceState(outState);
	}
	
	/**
	 * Inicializa graficamente todas las tabs en la pantalla principal,.
	 *
	 * @param initialTab Un int entre 0- 2 el cual indica cual de las 3 tabs se debe mostrar por defecto.
	 */
	private void initTabs(final int initialTab){
		
		final int tabBackgroungColor = getResources().getColor(R.color.greenCheckbox);
        final int tabBackgroungColorActive = getResources().getColor(R.color.greenCheckboxLigth);
        final int fontSize = 14;
        final int textColor = Color.WHITE;
        final int gravity = Gravity.CENTER;
       
        TabInfo tabInfo = null;
        
        this.tabHost = (TabHost) findViewById(android.R.id.tabhost);
        this.pager = (ViewPager) findViewById(R.id.pager);  
        
        tabHost.setup();
        
        TabSpec tabNevera = tabHost.newTabSpec(TAB_NAME_NEVERA);
        tabNevera.setIndicator(
        	getTextViewForTab(TAB_NAME_NEVERA, fontSize, textColor, gravity, tabBackgroungColor)
        );

        TabSpec tabRecetas = tabHost.newTabSpec(TAB_NAME_RECETAS);
        tabRecetas.setIndicator(
        	getTextViewForTab(TAB_NAME_RECETAS, fontSize, textColor, gravity, tabBackgroungColor)
        );
        
        TabSpec tabIngredientes = tabHost.newTabSpec(TAB_NAME_INGREDIENTES);
        tabIngredientes.setIndicator(
        	getTextViewForTab(TAB_NAME_INGREDIENTES, fontSize, textColor, gravity, tabBackgroungColor)
        );
        
        addTab(this, tabHost, tabNevera, (tabInfo = new TabInfo(TAB_NAME_NEVERA)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        addTab(this, tabHost, tabRecetas, (tabInfo = new TabInfo(TAB_NAME_RECETAS)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        addTab(this, tabHost, tabIngredientes, (tabInfo = new TabInfo(TAB_NAME_INGREDIENTES)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        
        tabHost.setCurrentTab(initialTab);
		tabHost.getTabWidget().getChildAt(initialTab).setBackgroundColor(tabBackgroungColorActive);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				int pos = tabHost.getCurrentTab();
		        pager.setCurrentItem(pos);
		        Utils.setActiveTab(tabHost, tabBackgroungColor, tabBackgroungColorActive);
		        
		        TabInfo newTab = mapTabInfo.get(tabId);
		        hideShowFragment(newTab);
				
			}
		});   
        
        this.mPagerAdapter  = new MyPageAdapter(super.getSupportFragmentManager());
        this.pager = (ViewPager)super.findViewById(R.id.pager);
        this.pager.setOffscreenPageLimit(3);
        this.pager.setAdapter(this.mPagerAdapter);
        this.pager.setCurrentItem(initialTab);
        this.pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        	
            @Override
            public void onPageSelected(int position) {
                tabHost.setCurrentTab(position);
            }
            
            //DO NOTHING: INTERFACE
            @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override public void onPageScrollStateChanged(int state) {}
        });
        
	}
	
	private class TabInfo {
        private String tag;
        private Fragment fragment;
        
        TabInfo(String tag) {
            this.tag = tag;
        }

   }
	
   class TabFactory implements TabContentFactory {
		 
        private final Context mContext;
 
        /**
         * @param context
         */
        public TabFactory(Context context) {
            mContext = context;
        }
 
        /** (non-Javadoc)
         * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
         */
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
 
    }
	
	/**
	 * Adds the tab.
	 *
	 * @param activity the activity
	 * @param tabHost the tab host
	 * @param tabSpec the tab spec
	 * @param tabInfo the tab info
	 */
	private static void addTab(MenuPrincipal activity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
        // Attach a Tab view factory to the spec
        tabSpec.setContent(activity.new TabFactory(activity));
        String tag = tabSpec.getTag();
 
        // Check to see if we already have a fragment for this tab, probably
        // from a previously saved state.  If so, deactivate it, because our
        // initial state is that a tab isn't shown.
        tabInfo.fragment = activity.getSupportFragmentManager().findFragmentByTag(tag);
   	 
        if (tabInfo.fragment != null && !tabInfo.fragment.isDetached()) {
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.detach(tabInfo.fragment);
            ft.commit();
            activity.getSupportFragmentManager().executePendingTransactions();
        }
 
        tabHost.addTab(tabSpec);
    }
	
	/**
	 * Genera un textview a partir de los argumentos pasados en la funcion.
	 *
	 * @param tabName El nombre de la tab
	 * @param fontSize El tamaño de la letra
	 * @param textColor El color del texto
	 * @param gravity La aliniacion (CENTER,RIGHT,..)
	 * @param backgroundColor El background color de las tabs
	 * 
	 * @return Un nuevo objeto TextView
	 */
	private TextView getTextViewForTab(String tabName, int fontSize, int textColor, int gravity, int backgroundColor){
		TextView tv = new TextView(this);
		
		tv.setText(tabName);
        tv.setTextSize(fontSize);
        tv.setTextColor(textColor);
        tv.setGravity(gravity);
        tv.setBackgroundColor(backgroundColor);
        tv.setPadding(0, 10, 0, 10); //OJO PADDING
        
        return tv;
	}
	
	/**
	 * @param newTab
	 */
	private void hideShowFragment(TabInfo newTab){
		if (mCurrentTab != newTab) {
            FragmentTransaction ft = MenuPrincipal.this.getSupportFragmentManager().beginTransaction();
            if (mCurrentTab != null) {
                if (mCurrentTab.fragment != null) {
                    ft.hide(mCurrentTab.fragment);
                }
            }else {
            	 this.mapTabInfo.get(TAB_NAME_NEVERA).fragment = Fragment.instantiate(MenuPrincipal.this, FragmentTabNevera.class.getName(), null);
            	 this.mapTabInfo.get(TAB_NAME_RECETAS).fragment = Fragment.instantiate(MenuPrincipal.this, FragmentTabNevera.class.getName(), null);
            	 this.mapTabInfo.get(TAB_NAME_INGREDIENTES).fragment = Fragment.instantiate(MenuPrincipal.this, FragmentTabNevera.class.getName(), null);
            }
            
            if (newTab != null) {
                if (newTab.fragment == null) {
                    ft.replace(android.R.id.tabcontent, newTab.fragment, newTab.tag);
                } else {
                    ft.show(newTab.fragment);
                }
            }
 
            mCurrentTab = newTab;
            ft.commit();
            MenuPrincipal.this.getSupportFragmentManager().executePendingTransactions();
        }
	}
	
}

