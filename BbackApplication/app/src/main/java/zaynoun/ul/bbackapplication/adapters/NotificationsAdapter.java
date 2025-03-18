package zaynoun.ul.bbackapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class NotificationsAdapter extends ArrayAdapter<String> {

    public NotificationsAdapter(Context context, ArrayList<String> notifications) {
        super(context, 0, notifications);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView notificationText = convertView.findViewById(android.R.id.text1);
        notificationText.setText(getItem(position));

        return convertView;
    }
}
