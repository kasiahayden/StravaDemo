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
import asia.haydenk.mystravademo.models.Segment;

/**
 * Created by khayden on 10/8/14.
 */
public class SegmentArrayAdapter extends ArrayAdapter<Segment>{


    public SegmentArrayAdapter(Context context, List<Segment> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        Segment segment = getItem(position);
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
        tvActivityName.setText(segment.getName());
        //tvTime.setText(segment.getStart_date_local());
        tvDistance.setText(segment.getDistance());
        //tvPaceOrElevation.setText(segment.getTotal_elevation_gain()); //TODO add pace to model for runs

        return v;
    }
}
