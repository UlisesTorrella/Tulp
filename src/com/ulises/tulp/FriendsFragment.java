package com.ulises.tulp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ulises.tulp.ProfileFragment.GetFromTulpServer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class FriendsFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private User[] friends;
    private ListView friendsLv;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FriendsFragment newInstance(int sectionNumber) {
    	FriendsFragment fragment = new FriendsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public FriendsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all, container, false);
        
        
        
        friendsLv = (ListView) rootView.findViewById(R.id.AllListView);
        friendsLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				((MainActivity)getActivity()).initiatePopupWindow(arg2,friends);
			}
		});
        friendsLv.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				final int selected = arg2;
                AlertDialog.Builder cartel = new AlertDialog.Builder(getActivity());
                RelativeLayout linearLayout=new RelativeLayout(getActivity());

                final NumberPicker picker = new NumberPicker(getActivity());
                picker.setMinValue(0);
                picker.setMaxValue(10);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(50,50);
                RelativeLayout.LayoutParams numPicerParams =
         new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
                numPicerParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                linearLayout.addView(picker,numPicerParams);
                linearLayout.setLayoutParams(params);
                linearLayout.isClickable();
				
                
				cartel.setPositiveButton("Send", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   System.out.println("SDFBOFSDFBOSFBOSDBFOSDBFIDSFBSDIBFOS");
	                	   new SendPoints().execute(friends[selected].getMail(),picker.getValue()+"",((MainActivity)getActivity()).getUserMail());
	                   }
	               });
				cartel.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   
	                   }
	               });

				cartel.setView(linearLayout);
                //Dialog dialog = cartel.create();
				cartel.show();  
				return true;

			}

		});
        
        new GetFriends().execute("");
        
        
        
        
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
 
    
    
    
    
    public void refrescarLista() {
    	// TODO Refrescar lista
    		List<String> nada = new ArrayList<String>(0);
    		
    		ArrayList<Model> nombres = new ArrayList<Model>();
    		
    		for(int i = 0; i<friends.length;i++) {
    			nombres.add(new Model("  "+friends[i].getName(),
    					Long.toString(friends[i].getPoints())));
    			}
    		
    		MyAdapter adapter = new MyAdapter(getActivity(), nombres);
    		friendsLv.setAdapter(adapter);
    		
    	
    }
    
    
    
   
    
    public class GetFriends extends AsyncTask<String, Void, String> {


		@Override
		protected String doInBackground(String... empty) {
			String result = "";
    		ServiceCall srvc = new ServiceCall("http://1-dot-tulp-project.appspot.com");
    		//ServiceCall srvc = new ServiceCall("http://localhost:8888");

			String data;
			try {
				data = srvc.get("/tulpfriends?user="+((MainActivity)getActivity()).getUserMail());
	    		if(data != ""){
	    			String[] amigos = data.split("%");
		    		friends = new User[amigos.length];
		    		for(int i=0; i<amigos.length;i++){
		    			String cuenta = amigos[i];
		    			String[] datos = cuenta.split("#");

		    			User aux = new User(datos[0]);
		    			aux.setName(datos[1]);
		    			aux.setPoints(Long.parseLong(datos[2]));
		    			friends[i]=aux;
		    			
		    		}
		    		result = data;
	    		}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		
			return result;
		}
		
		protected void onPostExecute(String result) {
			if(result!=""){
				refrescarLista();
			}
			else{
				String mail = ((MainActivity) getActivity()).getUserMail();
	    		new GetFriends().execute(mail);
			}
				     }
	 
		
	   
	}
    
    public class SendPoints extends AsyncTask<String, Void, String> {


		@Override
		protected String doInBackground(String... data) {
			String result = "";
    		ServiceCall srvc = new ServiceCall("http://1-dot-tulp-project.appspot.com");
    		//ServiceCall srvc = new ServiceCall("http://localhost:8888");
    		
			String nombre = data[0];
    		String puntos = data[1];
    		String emitente = data[2];
    		String devolucion="Try again";
			try {
				devolucion = srvc.post("/tulpserver?user="+nombre+"&addPoints="+puntos+"&emitente="+emitente);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return devolucion;
		}
		
		@SuppressLint("ShowToast")
		protected void onPostExecute(String result) {
			Toast.makeText(getActivity(), result, 234232).show();
			
			new GetFriends().execute("");
			refrescarLista();	

			}
	 
		
	   
	}
    
    
    
    
    
    
    
    
    
    
    
}

