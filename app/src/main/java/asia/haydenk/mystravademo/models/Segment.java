package asia.haydenk.mystravademo.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by khayden on 10/5/14.
 */
public class Segment {

    private String name;
    private String distance;
    private Map map;


    public static Segment fromJSON(JSONObject jsonObject) {
        Segment segment = new Segment();

        try {
            segment.name = jsonObject.getString("name");
            segment.distance = jsonObject.getString("distance"); //float, meters
            segment.map = Map.fromJSON(jsonObject.getJSONObject("map"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return segment;
    }

    public static ArrayList<Segment> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Segment> segments = new ArrayList<Segment>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject segmentJson = null;
            try {
                segmentJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Segment segment = Segment.fromJSON(segmentJson);
            if (segment != null) {
                segments.add(segment);
            }
        }
        return segments;
    }

    public String getName() {
        return name;
    }

    public String getDistance() {
        return distance;
    }

    public Map getMap() {
        return map;
    }

}




