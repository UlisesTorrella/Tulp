package com.ulises.tulp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;








import android.support.v7.app.ActionBarActivity;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SignIn extends ActionBarActivity  {
	
	private ProgressDialog progress;
	Account [] acc ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		android.app.ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
		
		AccountManager am = AccountManager.get(this);
		acc = am.getAccountsByType("com.google");
		
		progress = new ProgressDialog(this);
        progress.setTitle("Please Wait!!");
        progress.setMessage("Wait!!");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        
        if(acc.length!=1){
        	progress.dismiss();
        	setContentView(R.layout.select_acc_list);
    		final String[] cuentas = new String[acc.length];

    		if (acc.length > 0){
    		    for (int i=0; i<acc.length; i++){
    		    	cuentas[i]=acc[i].name;
    		    }
    		}
    		ListView lv = (ListView) findViewById(R.id.AccList);
    				
    		List<String> nombres = new ArrayList<String>();


    		for(int i = 0; i<cuentas.length;i++) {
    			nombres.add(cuentas[i]);
    		}
    		@SuppressWarnings("unchecked")	
    		ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nombres.toArray());
    		lv.setAdapter(adapter);
    		
    		
    		lv.setOnItemClickListener(new OnItemClickListener() {

    			@Override
    			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
    					long arg3) {
    				// TODO Auto-generated method stub
    				Intent mIntent = new Intent(getApplicationContext(),MainActivity.class);
    				
    				mIntent.putExtra("user_mail", cuentas[arg2]);
    				//mIntent.putExtra("user_name", "ulises.torrella@gmail.com");
    				startActivity(mIntent);
    			}
    		});
        }
        else{
        	progress.dismiss();
        	Intent mIntent = new Intent(getApplicationContext(),MainActivity.class);
			
			mIntent.putExtra("user_mail", acc[0].name);
			//mIntent.putExtra("user_name", "ulises.torrella@gmail.com");
			startActivity(mIntent);
			
        }
		
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
	
	


}
