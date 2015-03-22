package mm.events;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import mm.events.backend.FacebookAPI;
import mm.events.domain.RSVPStatus;


public class NotifyActivityHandler extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_activity_handler);
        int i = 0;
        Log.i("here", "here");

        Bundle gotdata = getIntent().getExtras();
        String action  = gotdata.getString("action");
        String id = gotdata.getString("id");


//        String action = getIntent().getExtras().getString("action");

        FacebookAPI api = FacebookAPI.getInstance(this);

        Log.i("action", i + action);
        i++;
        if (action != null) {
            switch (action) {
                case "going":
                    api.RSVPtoEvent(id, RSVPStatus.GOING);
                    Toast.makeText(NotifyActivityHandler.this, "Accept", Toast.LENGTH_SHORT).show();
                    break;
                case "maybe":
                    api.RSVPtoEvent(id, RSVPStatus.MAYBE);
                    Toast.makeText(NotifyActivityHandler.this, "maybe", Toast.LENGTH_SHORT).show();
                    break;
                case "reject":
                    api.RSVPtoEvent(id, RSVPStatus.DECLINED);
                    Toast.makeText(NotifyActivityHandler.this, "Reject!!!!!!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
      /*  if (action != null) {
            if (action.equals("going")) {
                Toast.makeText(NotifyActivityHandler.this, "Accept", Toast.LENGTH_SHORT).show();

            }

            if (action.equals("maybe")) {
                Toast.makeText(NotifyActivityHandler.this, "maybe", Toast.LENGTH_SHORT).show();
            }
        }*/

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
