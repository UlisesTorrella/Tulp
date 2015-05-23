package com.ulises.tulp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class ProfileFragment extends Fragment {

	        /**
	         * The fragment argument representing the section number for this
	         * fragment.
	         */
	        private static final String ARG_SECTION_NUMBER = "section_number";

	        /**
	         * Returns a new instance of this fragment for the given section
	         * number.
	         */
	        TextView nombre;
	        TextView puntos;
	        ImageView imgRango;
	    	private ProgressDialog progress;


	        public static ProfileFragment newInstance(int sectionNumber) {
	        	ProfileFragment fragment = new ProfileFragment();
	            Bundle args = new Bundle();
	            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
	            fragment.setArguments(args);
	            
	            return fragment;
	        }

	        public ProfileFragment() {
	        	
	        }
	        

	        @Override
	        public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                Bundle savedInstanceState) {
	            View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
	    		
		        nombre =(TextView) rootView.findViewById(R.id.txvProfileName);
	    		puntos = (TextView) rootView.findViewById(R.id.txvProfilePoints);
	    		imgRango = (ImageView) rootView.findViewById(R.id.profileRangeImg);
	    		
	    		progress = new ProgressDialog(getActivity());
	            progress.setTitle("Please Wait!!");
	            progress.setMessage("Wait!!");
	            progress.setCancelable(false);
	            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	            progress.show();
	    		String mail = ((MainActivity) getActivity()).getUserMail();
	    		new GetFromTulpServer().execute(mail);
	    		
	            return rootView;
	        }

	        @Override
	        public void onAttach(Activity activity) {
	            super.onAttach(activity);
	            ((MainActivity) activity).onSectionAttached(
	                    getArguments().getInt(ARG_SECTION_NUMBER));
	            
	        }

	     
	        
	        
	    	public void setImgRange(String puntos){
	    		int points = Integer.parseInt(puntos);
				int rangoint = 0;
				if(points<50){
					rangoint = 1;//pebete
				}
				else{
					if(points<100){
						rangoint = 2;//mostro
					}
					else{
						if(points<150){
							rangoint = 3;//Maquinola
						}
						else{
							if(points<200){
								rangoint = 4;//Troesma
							}
							else{
								if(points>300){
									rangoint = 5;//Lince
								}
							}
						}
					}
				}
				
				
				switch (rangoint) {
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
	    	
	    	
	    	
	    	
	    	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    		String mail = ((MainActivity) getActivity()).getUserMail();
	    		new GetFromTulpServer().execute(mail);
	    	    
	    	}
	    	
	    	
	    	
	    	
	    	
	    	public class GetFromTulpServer extends AsyncTask<String, Void, String> {

	    		
	    		@Override
	    		protected String doInBackground(String... user) {
	    			String result = "";
		    		ServiceCall srvc = new ServiceCall("http://1-dot-tulp-project.appspot.com");
		    		//ServiceCall srvc = new ServiceCall("http://localhost:8888");
		    		try {
						String data = srvc.get("/tulpserver?user="+user[0]);
						System.out.println(data);
			    		result = data;

					} catch (IOException e) {
						// TODO Auto-generated catch block
						
						e.printStackTrace();
					}

	    			return result;
	    		}
	    		
	    		protected void onPostExecute(String result) {
	    			if(result != ""){
	    				if(result.startsWith("FAIL")){
	    					Intent intent = new Intent(getActivity(), NewAcc.class);
	    					intent.putExtra("user_mail", ((MainActivity) getActivity()).getUserMail());
	    					startActivityForResult(intent, 1);
	    					progress.dismiss();
	    				}
	    				else{
		    				String[] parts = result.split("-");
		    				String name = parts[0]; 
		    				String points = parts[1]; 
			    			setImgRange(points);
			    			nombre.setText("Nombre:  "+name);
			    			puntos.setText("Puntos:  "+points);
			    			progress.dismiss();
	    				}
	    			}
	    			else{
	    				String mail = ((MainActivity) getActivity()).getUserMail();
	    	    		new GetFromTulpServer().execute(mail);
	    			}
	    	     }
	    	 
	    		
	    		
	    	}
	        
}


