package asia.haydenk.mystravademo;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import asia.haydenk.mystravademo.fragments.NearbySegmentsFragment;
import asia.haydenk.mystravademo.fragments.SegmentsListFragment;
import asia.haydenk.mystravademo.fragments.StarredSegmentsFragment;
import asia.haydenk.mystravademo.listeners.FragmentTabListener;
import asia.haydenk.mystravademo.models.AthleteActivity;
import asia.haydenk.mystravademo.models.Segment;


public class SegmentListActivity extends FragmentActivity {
    //private AsyncHttpClient stravaClient;
    /*private String accessToken;
    private String TAG = "SegmentListActivity";*/
    private SegmentsListFragment fragmentSegmentsList;
    /*private ArrayList<Segment> segments;
    private ArrayAdapter<Segment> aSegments;
    private ListView lvSegments;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segment_list);
        setupTabs();


        /*
        accessToken = getIntent().getStringExtra("accessToken");
        Log.d(TAG, "getStringExtra accessToken: " + accessToken);*/
        //stravaClient = new AsyncHttpClient();

        //fragmentSegmentsList = (SegmentsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_segments_list); //

        //populateSegmentListActivity();
        //lvSegments = (ListView) findViewById(R.id.lvSegments);
        /*segments = new ArrayList<Segment>();
        aSegments = new ArrayAdapter<Segment>(this, android.R.layout.simple_list_item_1, segments);*/
        //aSegments = new SegmentArrayAdapter(this, segments);
        //lvSegments.setAdapter(aSegments);




    }



    private void setupTabs() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        ActionBar.Tab tab1 = actionBar
                .newTab()
                .setText("Starred")
                .setIcon(R.drawable.ic_star)
                .setTag("StarredSegments")
                .setTabListener(
                        new FragmentTabListener<StarredSegmentsFragment>(R.id.flContainer, this, "starred",
                                StarredSegmentsFragment.class));

        actionBar.addTab(tab1);
        actionBar.selectTab(tab1);

        ActionBar.Tab tab2 = actionBar
                .newTab()
                .setText("Nearby")
                .setIcon(R.drawable.ic_explore)
                .setTag("NearbySegments")
                .setTabListener(
                        new FragmentTabListener<NearbySegmentsFragment>(R.id.flContainer, this, "nearby",
                                NearbySegmentsFragment.class));

        actionBar.addTab(tab2);
    }



}
