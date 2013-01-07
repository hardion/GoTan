package org.gotan.client.android;

import java.util.StringTokenizer;

import org.gotan.client.GotanAttribute;
import org.gotan.client.GotanObject;
import org.gotan.client.GotanRestClient;
import org.gotan.client.GotanServer;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

public class GotanDroidActivity extends Activity {
	
	GotanServer server;
	GotanObject object;
	GotanAttribute attribute;
	
	ValueThread pollingThread=null;
	
	private TextView valueText;
	private TextView serverText;
	private AutoCompleteTextView objectText;
	private TextView attributeText;
	private TextView setValueText;
//	RestClient client = new RestClient();
//    ClientResource resource = new ClientResource("http://192.168.56.1:8080/gotan/objects/sys/tg_test/1/attributes/string_scalar");
//    ClientResource resource = new ClientResource("http://10.0.1.7:8080/gotan/objects/sys/tg_test/1/attributes/string_scalar");
    
    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		serverText = ((TextView)findViewById(R.id.ServerText));		
		((Button)findViewById(R.id.ServerButton)).setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				String value = serverText.getText().toString();
				setServer(value);
			}

		});
		
		objectText = ((AutoCompleteTextView)findViewById(R.id.ObjectTextView));
		((Button)findViewById(R.id.ObjectButton)).setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				String value = objectText.getText().toString();
				setObject(value);
			}

		});
		
		attributeText = ((TextView)findViewById(R.id.AttributeTextView));
		((Button)findViewById(R.id.AttributeButton)).setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				String value = attributeText.getText().toString();
				setAttribute(value);
			}

		});
		
		setValueText = ((TextView)findViewById(R.id.SetValueEditText));
		((Button)findViewById(R.id.SetValueButton)).setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				String value = setValueText.getText().toString();
				setAttributeValue(value);
			}

		});
		
		valueText = (TextView)findViewById(R.id.ValueTextView);
	}
	
	private void setServer(String value) {
		if( value==null ){
			server = null;
			serverText.setText("Server");
			this.setObject(null);

		}else if( server==null || !value.equals(server.getElementName() ) ){
			this.setObject(null);
			String[] tokens = value.split(":");
			server = GotanRestClient.createServerConnection(tokens[0], tokens[1]);
			
			// Fill the autocompletion for objects
			new CreateObjectListTask().execute(R.id.ObjectTextView, 1);
		}
	}
	
	
	private void setObject(String value) {
		if( value==null ){
			object = null;
			objectText.setText("Object");
			this.setAttribute(null);

		}else if( server!=null && (object==null || !value.equals(object.getElementName()) ) ){
			killPollingThread();
			object = server.getObject(value);
			
			// Fill the autocompletion for attributes
			new CreateObjectListTask().execute(R.id.AttributeTextView, 2);
		}
	}
	
	private void setAttribute(String value) {
		if( value==null ){
			killPollingThread();
			attribute = null;
			attributeText.setText("Attribute");
			setValueText.setText("Value");
		}else if( object!=null && (attribute==null || !value.equals(attribute.getElementName()) ) ){
			attribute = object.getAttribute(value);
			runPollingThread();
		}
	}
	
	private void setAttributeValue(String value) {
		if( attribute!=null ){
			attribute.setValue(value);
		}
	}


	
	// Define the Handler that receives messages from the thread and update the progress
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
        	
			String value = msg.obj.toString();
			valueText.setText(value);
        }
    };

	private void killPollingThread(){
		if(pollingThread!=null){
			pollingThread.enable=false;
			try {
				pollingThread.join(2000);
				pollingThread = null;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
		}
	}
	
	private void runPollingThread(){
		if(pollingThread != null){
			killPollingThread();
		}
		
		pollingThread = new ValueThread();
		pollingThread.start();
	}
	
	public class CreateObjectListTask extends AsyncTask<Integer, Void, String[]> {

		int viewId;
		
		@Override
		protected String[] doInBackground(Integer... args) {
			String[] result=null;
			viewId = args[0];
			
			switch(args[1]){
			case 1:
				result=server.getObjectsNames();
				break;
			case 2:
				result=object.getAttributesNames();
				break;
			default:
				result = new String[]{"No children"};
				break;
			}
			return result;
		}

	    protected void onPostExecute(String[] result) 
	    {
	    	AutoCompleteTextView actv = ((AutoCompleteTextView) GotanDroidActivity.this.findViewById(viewId));
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(GotanDroidActivity.this, android.R.layout.simple_dropdown_item_1line, result);
			actv.setThreshold(1);
			actv.setAdapter(adapter);			
	        super.onPostExecute(result);
	    }
	}

	private class ValueThread extends Thread {

		boolean enable = true;
		
		@Override
		public void run() {
			
			while(enable){
				try {
					String value = attribute.getValue();
					
					Message msg = handler.obtainMessage();
					msg.obj=value;
					handler.sendMessage(msg);

					sleep(1000);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	

}