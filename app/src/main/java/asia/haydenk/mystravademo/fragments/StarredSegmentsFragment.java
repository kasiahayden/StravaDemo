package asia.haydenk.mystravademo.fragments;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import asia.haydenk.mystravademo.R;
import asia.haydenk.mystravademo.models.Segment;

/**
 * Created by khayden on 10/9/14.
 */
public class StarredSegmentsFragment extends SegmentsListFragment {
    private String accessToken;
    private String TAG = "StarredSegmentsFragment";
    private AsyncHttpClient stravaClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stravaClient = new AsyncHttpClient();
        accessToken = getActivity().getIntent().getStringExtra("accessToken");
        populateSegmentListActivity();
    }

    public void populateSegmentListActivity() {
        stravaClient.addHeader("Authorization", "Bearer " + accessToken);
        stravaClient.get("https://www.strava.com/api/v3/segments/starred", new JsonHttpResponseHandler() {
            public void onSuccess(JSONArray json) {
                ArrayList<Segment> segments = Segment.fromJSONArray(json);
                addAll(segments);
            }
            public void onFailure(Throwable ex) {
                Log.d(TAG, "Unsuccessful populateSegmentListActivity");
                ex.printStackTrace();
            }
        });

    }
}
