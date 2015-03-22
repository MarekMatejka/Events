package mm.events;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class NotifyActivityHandler extends Activity {

    public static final String PERFORM_NOTIFICATION_BUTTON = "perform_notification_button";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_activity_handler);

//        Intent intent = this.getIntent();

       /* Bundle gotdata = getIntent().getExtras();
        String action = gotdata.getString("data");

//        String action = (String) getIntent().getExtras().get("data");
        if (action != null) {
            if (action.equals("Accept")) {
                Toast.makeText(NotifyActivityHandler.this, "Accept", Toast.LENGTH_SHORT).show();
            } else if (action.equals("close")) {
                // close current notification
            }
        }*/

//        finish();

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
