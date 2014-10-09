package asia.haydenk.mystravademo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import asia.haydenk.mystravademo.models.AthleteActivity;

/**
 * Created by khayden on 10/7/14.
 */
public class AthleteActivityArrayAdapter extends ArrayAdapter<AthleteActivity> {

    public AthleteActivityArrayAdapter(Context context, List<AthleteActivity> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AthleteActivity athleteActivity = getItem(position);
        View v;
        if (convertView == null) {
            LayoutInflater inflator = LayoutInflater.from(getContext());
            v = inflator.inflate(R.layout.athlete_activity_item, parent, false);
        } else {
            v = convertView;
        }

        ImageView ivMap = (ImageView) v.findViewById(R.id.ivMap);
        TextView tvActivityName = (TextView) v.findViewById(R.id.tvActivityName);
        TextView tvTime = (TextView) v.findViewById(R.id.tvTime);
        TextView tvDistance = (TextView) v.findViewById(R.id.tvDistance);
        TextView tvPaceOrElevation = (TextView) v.findViewById(R.id.tvPaceOrElevation);
        int resId = android.R.color.transparent;

        ivMap.setImageResource(resId);
        ImageLoader imageLoader = ImageLoader.getInstance();

        // Populate views with tweet data
        //imageLoader.displayImage(athleteActivity.getUser().getProfileImageUrl(), ivProfileImage); //TODO populate map view
        tvActivityName.setText(athleteActivity.getName());
        tvTime.setText(athleteActivity.getStart_date_local());
        tvDistance.setText(athleteActivity.getDistance());
        tvPaceOrElevation.setText(athleteActivity.getTotal_elevation_gain()); //TODO add pace to model for runs

        return v;
    }
}
