package asia.haydenk.mystravademo;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import asia.haydenk.mystravademo.fragments.NearbySegmentsFragment;
import asia.haydenk.mystravademo.fragments.SegmentsListFragment;
import asia.haydenk.mystravademo.fragments.StarredSegmentsFragment;
import asia.haydenk.mystravademo.listeners.FragmentTabListener;


public class SegmentListActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segment_list);
        setupTabs();
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
