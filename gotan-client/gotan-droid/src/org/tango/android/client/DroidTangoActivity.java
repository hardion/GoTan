package org.tango.android.client;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class DroidTangoActivity extends Activity {
	
	TextView tv;
	RestClient client = new RestClient();
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tv = (TextView)findViewById(R.id.textView1);
		
		new ValueThread().start();
	}
	
	
	// Define the Handler that receives messages from the thread and update the progress
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
        	
			String value = msg.obj.toString();
			tv.setText(value);
        }
    };

	private class ValueThread extends Thread {

		@Override
		public void run() {
			
			while(true){
				try {
					sleep(1000);
					
					String response = client.queryRESTurl("http://192.168.0.28:8182/devices/sys/tg_test/1/string_scalar");
					try {
						JSONObject object = new JSONObject(response);
						String value = object.getString("value");
						
						Message msg = handler.obtainMessage();
						msg.obj=value;
						handler.sendMessage(msg);
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	

}