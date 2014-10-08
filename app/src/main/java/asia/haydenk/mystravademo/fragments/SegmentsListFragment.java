package asia.haydenk.mystravademo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import asia.haydenk.mystravademo.R;
import asia.haydenk.mystravademo.SegmentArrayAdapter;
import asia.haydenk.mystravademo.models.Segment;

/**
 * Created by khayden on 10/8/14.
 */


public class SegmentsListFragment extends Fragment {
    private ArrayList<Segment> segments;
    private ArrayAdapter<Segment> aSegments;
    private ListView lvSegments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        segments = new ArrayList<Segment>();
        //aSegments = new ArrayAdapter<Segment>(this, android.R.layout.simple_list_item_1, segments);
        aSegments = new SegmentArrayAdapter(getActivity(), segments); //necessary b/c fragment is not a context
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_segments_list, container, false);

        lvSegments = (ListView) v.findViewById(R.id.lvSegments);
        lvSegments.setAdapter(aSegments);

        return v;
    }

    // Delegate adding to internal adapter
    public void addAll(ArrayList<Segment> segments) {
        aSegments.addAll(segments);
    }

}
