package com.ulises.tulp;


import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks{

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	String userMail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
			setContentView(R.layout.activity_main);

			android.app.ActionBar actionBar = getActionBar();
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
			
			mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
					.findFragmentById(R.id.navigation_drawer);
			mTitle = getTitle();

			// Set up the drawer.
			mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
					(DrawerLayout) findViewById(R.id.drawer_layout));
			Intent intent = getIntent();
			userMail = intent.getExtras().getString("user_mail");
		}


	public String getUserMail(){
		return userMail;
	}
	public void setUserMail(String mail){
		userMail = mail;
	}

	
	@Override
	public void onNavigationDrawerItemSelected(int position) {
		
		Fragment fragment = null;
        switch (position){
            case 0:
                fragment = ProfileFragment.newInstance(position + 1);
                break;
            case 1:
            	fragment = FriendsFragment.newInstance(position + 1);
                break;

        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
		/* update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						ProfileFragment.newInstance(position + 1)).commit();*/
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
			}

	}
	
	
	public void volver(View v){
    	pwindo.dismiss();
    }
    
    PopupWindow pwindo;
  //Este metodo inica el popUpWindow
  	void initiatePopupWindow(int p, User[] friends) {

		Display display = this.getWindowManager().getDefaultDisplay(); 

		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.friends_pop_up,(ViewGroup) (this.findViewById(R.id.popup_element)));
		pwindo = new PopupWindow(layout, display.getWidth()-100, display.getHeight()-200, true);
		pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
		pwindo.setBackgroundDrawable(getWallpaper());
		TextView nombre= (TextView) pwindo.getContentView().findViewById(R.id.txvFriendName);
		TextView mail=(TextView)pwindo.getContentView().findViewById(R.id.txvFriendMail);
		TextView points=(TextView)pwindo.getContentView().findViewById(R.id.txvFriendPoints);
		ImageView img = (ImageView)pwindo.getContentView().findViewById(R.id.imgFriend);

		String modMail = friends[p].getMail();
		String[] aux = modMail.split("@");
		modMail = aux[0];
		nombre.setText(friends[p].getName());
		mail.setText(modMail);
		points.setText(friends[p].getPoints()+"");
		
		setImgRange((int)friends[p].getPoints(),img);
		
  	}
    
  	public void setImgRange(int points, ImageView imgRango){

 		int rango = 0;
 		if(points<100){
 			rango = 1;//pebete
 		}
 		else{
 			if(points<200){
 				rango = 2;//mostro
 			}
 			else{
 				if(points<300){
 					rango = 3;//Maquinola
 				}
 				else{
 					if(points<400){
 						rango = 4;//Troesma
 					}
 					else{
 						if(points>500){
 							rango = 5;//Lince
 						}
 					}
 				}
 			}
 		}
 		
 		
 		switch (rango) {
 		case 1:
 			imgRango.setImageResource(R.drawable.pebete);
 			break;
 		case 2:
 			imgRango.setImageResource(R.drawable.mostro);
 			break;
 		case 3:
 			imgRango.setImageResource(R.drawable.maquinola);
 			break;
 		case 4:
 			imgRango.setImageResource(R.drawable.troesma);
 			break;
 		case 5:
 			imgRango.setImageResource(R.drawable.lince);
 			break;
 		}
 		
 	}
 	
     
	
	
	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

		
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	
	TextView nombre;
	TextView puntos;
	ImageView imgRango;

	
}
