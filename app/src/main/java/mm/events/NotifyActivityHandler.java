package mm.events;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import mm.events.backend.FacebookAPI;
import mm.events.domain.RSVPStatus;


public class NotifyActivityHandler extends Activity {

    String accept, maybe, decline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_activity_handler);
        int i = 0;
        Log.i("here", "here");

        accept = null;
        maybe = null;
        decline = null;

        Bundle gotdata = getIntent().getExtras();
        try {
            accept = gotdata.getString("Accept");
        } catch (Exception e) {
            Log.e("",e.toString());
        }


        try {
            maybe = gotdata.getString("Maybe");
        } catch (Exception e) {
            Log.e("",e.toString());
        }


        try {
            decline = gotdata.getString("Decline");
        } catch (Exception e) {
            Log.e("",e.toString());
        }


        String action  = gotdata.getString("action");
//        String id = gotdata.getString("id");


      Log.e("accept: id ", ""+accept);
      Log.e("maybe: id ", ""+maybe);
      Log.e("decline: id ", ""+decline);


//        String action = getIntent().getExtras().getString("action");

        FacebookAPI api = FacebookAPI.getInstance(this);

        if (accept!=null){
            api.RSVPtoEvent(accept, RSVPStatus.GOING);
        }
        else if (maybe!=null){
            api.RSVPtoEvent(maybe, RSVPStatus.MAYBE);
        }
        else if (decline!=null){
            api.RSVPtoEvent(decline, RSVPStatus.DECLINED);
        }

        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notify_activity_handler, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
