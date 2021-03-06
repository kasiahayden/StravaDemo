package asia.haydenk.mystravademo.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by khayden on 10/5/14.
 */
public class Map {

    private String id;
    private String polyline = null;
    private String summary_polyline = null;
    private String resource_state; //int 1 - 3


    public static Map fromJSON(JSONObject jsonObject) {
        Map map = new Map();
        try {
            map.id = jsonObject.getString("id");
            map.resource_state = jsonObject.getString("resource_state"); // 2 for activity, 3 for segment
            if (map.resource_state == "2") {
                map.summary_polyline = jsonObject.getString("summary_polyline"); // activity
            } else if (map.resource_state == "3") {
                map.polyline = jsonObject.getString("polyline"); // segment
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return map;
    }

    public static ArrayList<Map> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Map> maps = new ArrayList<Map>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject mapJson = null;
            try {
                mapJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Map map = Map.fromJSON(mapJson);
            if (map != null) {
                maps.add(map);
            }
        }
        return maps;
    }


    public String getId() {
        return id;
    }

    public String getPolyline() {
        return polyline;
    }

    public String getSummary_polyline() {
        return summary_polyline;
    }

    public String getResource_state() {
        return resource_state;
    }



}
