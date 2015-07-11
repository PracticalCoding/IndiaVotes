package com.basuhampali.indiavotes;

import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MoreActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more);
	}

	public void helpOnClick(View V){
		Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "indiavotesapp@gmail.com"));
		intent.putExtra(Intent.EXTRA_SUBJECT, "IndiaVotes App Feedback/help");
		startActivity(intent);
	}
	
	public void twitterOnClick(View V){
		Intent intent = findTwitterClient();
		if(intent!=null){
			intent.putExtra(Intent.EXTRA_TEXT, "check this IndiaVote android app bit.ly/voteindia2013 it lets me see my polling booth adddress");
			startActivity(intent);
		}
		else
		{
			Toast.makeText(this, "you dont have twitter app", 10).show();
			
		}
	}
	
public void facebookOnClick(View V){
	Intent intent = findfacebookClient();
	if(intent!=null){
		intent.putExtra(Intent.EXTRA_TEXT, "check this IndiaVote android app bit.ly/voteindia2013 it lets me see my polling booth adddress");
		startActivity(intent);
	}
	else
	{
		Toast.makeText(this, "you dont have facebook app", 10).show();
		
	}
	}
public void otherShareOnClick(View V){
	
}
public void rateAppOnClick(View V){
	final String appName = "com.basuhampali.indiavotes";
	try {
	    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+appName)));
	} catch (android.content.ActivityNotFoundException anfe) {
	    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+appName)));
	}
	
}
public void aboutAppOnClick(View V){
	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
			this);

		// set title
		alertDialogBuilder.setTitle("About IndiaVotes");

		// set dialog message
		alertDialogBuilder
			.setMessage("\t*Presently this app only supports Karnataka Assembly Election 2013\n\t* This application is based on messsage service provided by cheif election commission of India(ECI) \n \t*The app formats the message to match the format of ECI and sends it and on reciving message from ECI , data is shown in clean view \n \t*Nominal SMS charges apply(This app doesn&apos;t charge you any money) \n\t*Application doesn&apos;t use any private data, As you have seen we dont use internet permission \n \t*Finally : The app moto is to help find polling booth without any confusion")
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

public Intent findTwitterClient() {
	final String[] twitterApps = {
			// package // name - nb installs (thousands)
			"com.twitter.android", // official - 10 000
			"com.twidroid", // twidroyd - 5 000
			"com.handmark.tweetcaster", // Tweecaster - 5 000
			"com.thedeck.android"  };
	Intent tweetIntent = new Intent();
	tweetIntent.setType("text/plain");
	final PackageManager packageManager = getPackageManager();
	List<ResolveInfo> list = packageManager.queryIntentActivities(
			tweetIntent, PackageManager.MATCH_DEFAULT_ONLY);

	for (int i = 0; i < twitterApps.length; i++) {
		for (ResolveInfo resolveInfo : list) {
			String p = resolveInfo.activityInfo.packageName;
			if (p != null && p.startsWith(twitterApps[i])) {
				tweetIntent.setPackage(p);
				return tweetIntent;
			}
		}
	}
	return null;
}

public Intent findfacebookClient() {
	final String[] twitterApps = {
			// package // name - nb installs (thousands)
			"com.facebook.katana"};
	Intent tweetIntent = new Intent();
	tweetIntent.setType("text/plain");
	final PackageManager packageManager = getPackageManager();
	List<ResolveInfo> list = packageManager.queryIntentActivities(
			tweetIntent, PackageManager.MATCH_DEFAULT_ONLY);

	for (int i = 0; i < twitterApps.length; i++) {
		for (ResolveInfo resolveInfo : list) {
			String p = resolveInfo.activityInfo.packageName;
			if (p != null && p.startsWith(twitterApps[i])) {
				tweetIntent.setPackage(p);
				return tweetIntent;
			}
		}
	}
	return null;
}
}
