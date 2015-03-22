package mm.events.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import mm.events.R;
import mm.events.domain.FBEvent;
import mm.events.domain.RSVPStatus;
import mm.events.views.LogoView;

import static mm.events.domain.RSVPStatus.GOING;

public class EventsListAdapter extends ArrayAdapter<FBEvent> {

    private Context context;
    private List<FBEvent> events;

    public EventsListAdapter(Context context, List<FBEvent> events) {
        super(context, R.layout.row_events_list, events);
        this.context = context;
        this.events = events;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_events_list, parent, false);

        FBEvent event = events.get(position);

        TextView eventName = (TextView) convertView.findViewById(R.id.event_name);
        eventName.setText(event.getName());

        TextView eventDetails = (TextView) convertView.findViewById(R.id.event_details);
        eventDetails.setText(event.getFormattedStartDate() + " @ "+event.getLocation());

        LogoView eventStatus = (LogoView) convertView.findViewById(R.id.status);
        eventStatus.setLogo(getStatus(event));
        eventStatus.setClickable(false);
        eventStatus.setFocusable(false);

        return convertView;
    }

    private String getStatus(FBEvent event) {
        switch (event.getStatus()) {
            case GOING: return "f00c";
            case DECLINED: return "f00d";
            default: return "f128";
        }
    }
}
