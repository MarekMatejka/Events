package mm.events;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class NotifyActivityHandler extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_activity_handler);

        Log.e("here", "here");

        String action = getIntent().getExtras().getString("action");

        Log.e("action", action);
        if (action != null) {
            switch (action) {
                case "going":
                    Toast.makeText(NotifyActivityHandler.this, "Accept", Toast.LENGTH_SHORT).show();
                    break;
                case "maybe":
                    Toast.makeText(NotifyActivityHandler.this, "Maybe", Toast.LENGTH_SHORT).show();
                    break;
                case "reject":
                    Toast.makeText(NotifyActivityHandler.this, "Reject", Toast.LENGTH_SHORT).show();
                    break;
            }
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
