package com.basuhampali.indiavotes;


import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	Log.i(TAG, "Intent recieved: " + intent.getAction());
        	 if (intent.getAction().equals(SMS_RECEIVED)) {
                 Bundle bundle = intent.getExtras();
                 try {
					if (bundle != null) {
					     Object[] pdus = (Object[])bundle.get("pdus");
					     final SmsMessage[] messages = new SmsMessage[pdus.length];
					     for (int i = 0; i < pdus.length; i++) {
					         messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
					     }
					     if(messages[0].getOriginatingAddress().equals("TD-CEOKAR")){
					    	 Toast.makeText(MainActivity.this, "mssage recived", 10).show();
					    	 String strFull="";
					    	 for(int i=0;i<messages.length;i++){
					    		 strFull+= messages[i].getMessageBody();
					    	 }
					    	 Log.i(TAG, "Message recieved full First: " + strFull  );
					    	 if (messages.length > -1) {
					    		 	String str[]= strFull.split(";");
					    	
					    		 	String constNoAndName[]=str[0].split(":");
					    		 	String address[]=str[1].split(":");
					    		 	String serialNo[]=str[2].split(":");
					    		 	String name[]=str[3].split(":");
					    		 	String careOf[]=str[4].split(" ");
					    		 	saveVoterInfo(name[1],str[4],constNoAndName[1],address[1],serialNo[1]);
					    	/*((TextView) findViewById(R.id.textView2)).setText(name[1]);
					    	((TextView) findViewById(R.id.textView4)).setText(constNoAndName[1]);
					    	((TextView) findViewById(R.id.textView6)).setText(address[1]);
					    	((TextView) findViewById(R.id.textView8)).setText(serialNo[1]);
					    	((TextView) findViewById(R.id.textView10)).setText(str[4]);*/
					        Log.i(TAG, "Message recieved full: " + strFull  );
					        // Toast.makeText(MainActivity.this, "Message recieved: " + messages[0].getMessageBody() + "oriAddress:" +messages[0].getOriginatingAddress(), 10).show();
					    	 }
					     }
					 }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					unExpectedError("Errror","There is some error while reading SMS. It may be due to wrong entry of Voter card No.For details read the SMS from TD-CEOKAR in your inbox");
				}
             }
            
        }
   };
   
   
   private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
   private static final String TAG = "SMSBroadcastReceiver1";
	EditText voterId_ET=null;
	int MAX_SMS_MESSAGE_LENGTH=160;
	LinearLayout appInfo_LL,voterInfo_LL;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		voterId_ET = (EditText)findViewById(R.id.editText1);
		appInfo_LL=(LinearLayout) findViewById(R.id.appinfo_linear_layout);
		voterInfo_LL=(LinearLayout) findViewById(R.id.voterinfo_linear_layout);
		setVoterID_ET_Text();
		
		/*String strName = PreferenceConnector.readString(this,
				PreferenceConnector.NAME, null);
		if(strName!=null && (!strName.equals(""))){
			setView();
		}
		else
		{
			voterInfo_LL.setVisibility(View.GONE);
			appInfo_LL.setVisibility(View.VISIBLE);
		}*/
	}
	
	
	public void setVoterID_ET_Text(){
		String voterID = PreferenceConnector.readString(this,
				PreferenceConnector.VOTER_ID, null);
		if(voterID!=null && (!voterID.equals(""))){
			voterId_ET.setText(voterID);
		}
		else
		{
			voterId_ET.setHint("Ex: NNA0727214");
		}
	}
   
	public void saveVoterInfo(String name,String careOf,String assemblyInfo,String address,String serialno){
		if (name != null && careOf != null && assemblyInfo!=null && address!=null && serialno!=null){
			PreferenceConnector.writeString(this, PreferenceConnector.NAME,	name);
			PreferenceConnector.writeString(this, PreferenceConnector.CARE_OF,	careOf);
			PreferenceConnector.writeString(this, PreferenceConnector.ASSEMBLY_NO_AND_NAME,	assemblyInfo);
			PreferenceConnector.writeString(this, PreferenceConnector.POLLING_BOTH_ADDRESS,	address);
			PreferenceConnector.writeString(this, PreferenceConnector.SERIAL_NO,	serialno);
			//setView(name,careOf,assemblyInfo,address,serialno);
			setView();
		}
		else{
			
		}
	}
	
	public void setView(String name,String careOf,String assemblyInfo,String address,String serialno){
		voterInfo_LL.setVisibility(View.VISIBLE);
		appInfo_LL.setVisibility(View.GONE);
		((TextView) findViewById(R.id.textView2)).setText(name);
    	((TextView) findViewById(R.id.textView4)).setText(assemblyInfo);
    	((TextView) findViewById(R.id.textView6)).setText(address);
    	((TextView) findViewById(R.id.textView8)).setText(serialno);
    	((TextView) findViewById(R.id.textView10)).setText(careOf);
	}
	
public void setView(){
	voterInfo_LL.setVisibility(View.VISIBLE);
	appInfo_LL.setVisibility(View.GONE);
	String name=PreferenceConnector.readString(this,
			PreferenceConnector.NAME, null);
	String assemblyInfo=PreferenceConnector.readString(this,
			PreferenceConnector.ASSEMBLY_NO_AND_NAME, null);
    String address=PreferenceConnector.readString(this,
			PreferenceConnector.POLLING_BOTH_ADDRESS, null);
    String serialno=PreferenceConnector.readString(this,
			PreferenceConnector.SERIAL_NO, null);
    String careOf=PreferenceConnector.readString(this,
			PreferenceConnector.CARE_OF, null);
		((TextView) findViewById(R.id.textView2)).setText(name);
    	((TextView) findViewById(R.id.textView4)).setText(assemblyInfo);
    	((TextView) findViewById(R.id.textView6)).setText(address);
    	((TextView) findViewById(R.id.textView8)).setText(serialno);
    	((TextView) findViewById(R.id.textView10)).setText(careOf);
	}


public void moreButtonOnClick(View V){
	Intent intent = new Intent(this, MoreActivity.class);
	startActivity(intent);
}
	public void unExpectedError(String tittle,String message){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);
 
			// set title
			alertDialogBuilder.setTitle(tittle);
 
			// set dialog message
			alertDialogBuilder
				.setMessage(message)
				.setCancelable(true)
				.setPositiveButton("OK",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						dialog.cancel();
					}
				  });
				/*.setNegativeButton("No",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				})*/
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	
	public void SaveOnClick(View view) {
		unExpectedError("Hi", "This app is growing up, if you face isuue regaing data non-availabilty,please check your message inbox");
		voterInfo_LL.setVisibility(View.VISIBLE);
		appInfo_LL.setVisibility(View.GONE);
		String nameText = voterId_ET.getText().toString();
		
		
		if (nameText != null && (!nameText.equals("")) && (nameText.length()>8)){
			PreferenceConnector.writeString(this, PreferenceConnector.VOTER_ID,
					nameText);
			String str = PreferenceConnector.readString(this,
					PreferenceConnector.VOTER_ID, null);
			Toast.makeText(this, "Sending SMS", 10).show();
		
			sendSMSPRogramatically("09243355223", "KAEPIC " + str);
		}
		else
		{
			unExpectedError("Errors", "Please enter proper Card No.");
		}
		
		
		/*Intent smsIntent = new Intent(Intent.ACTION_VIEW);
		smsIntent.setType("vnd.android-dir/mms-sms");
		smsIntent.putExtra("address", "09243355223");
		smsIntent.putExtra("sms_body","KAEPIC " + str);
		startActivity(smsIntent);
		*/
	}
	
	public void sendSMSPRogramatically(String phonenumber,String message){
		PendingIntent piSend = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT"), 0);
	    PendingIntent piDelivered = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED"), 0);

		SmsManager manager = SmsManager.getDefault();
		manager.sendTextMessage(phonenumber, null, message, null, null);
	}
	 
	/*private void sendSms(String phonenumber,String message, boolean isBinary)
	{
	    SmsManager manager = SmsManager.getDefault();

	    PendingIntent piSend = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
	    PendingIntent piDelivered = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);

	    if(isBinary)
	    {
	            byte[] data = new byte[message.length()];

	            for(int index=0; index<message.length() && index < MAX_SMS_MESSAGE_LENGTH; ++index)
	            {
	                    data[index] = (byte)message.charAt(index);
	            }

	            manager.sendDataMessage(phonenumber, null, (short) SMS_PORT, data,piSend, piDelivered);
	    }
	    else
	    {
	            int length = message.length();

	            if(length > MAX_SMS_MESSAGE_LENGTH)
	            {
	                    ArrayList<String> messagelist = manager.divideMessage(message);

	                    manager.sendMultipartTextMessage(phonenumber, null, messagelist, null, null);
	            }
	            else
	            {
	                    manager.sendTextMessage(phonenumber, null, message, piSend, piDelivered);
	            }
	    }
	}*/
	@Override
	 public void onResume() {
	        super.onResume();
	        setVoterID_ET_Text();
			String strName = PreferenceConnector.readString(this,
					PreferenceConnector.NAME, null);
			if(strName!=null && (!strName.equals(""))){
				setView();
			}
			else
			{
				voterInfo_LL.setVisibility(View.GONE);
				appInfo_LL.setVisibility(View.VISIBLE);
			}
	        IntentFilter filter = new IntentFilter();
	        filter.addAction("android.provider.Telephony.SMS_RECEIVED");

	        this.registerReceiver(this.receiver, filter);
	    }

	@Override    
	public void onPause() {
	        super.onPause();

	        this.unregisterReceiver(this.receiver);
	    }
}
