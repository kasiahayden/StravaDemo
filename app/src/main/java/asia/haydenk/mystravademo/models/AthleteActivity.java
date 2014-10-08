package asia.haydenk.mystravademo.models;

import android.text.format.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by khayden on 10/5/14.
 */

public class AthleteActivity implements Serializable {

    private String name;
    private String type;
    private String distance;
    private String elapsed_time;
    private String start_date_local;
    private String total_elevation_gain;
    private ArrayList<Segment> segments;
    private Map map;

    public static AthleteActivity fromJSON(JSONObject jsonObject) {
        AthleteActivity athleteActivity = new AthleteActivity();

        try {
            athleteActivity.name = jsonObject.getString("name");
            athleteActivity.type = jsonObject.getString("type"); //activity type, ie. ride, run, swim, etc.
            athleteActivity.distance = jsonObject.getString("distance"); //float, meters
            athleteActivity.elapsed_time = jsonObject.getString("elapsed_time"); //int, seconds
            athleteActivity.start_date_local = jsonObject.getString("start_date_local"); //time
            athleteActivity.total_elevation_gain = jsonObject.getString("total_elevation_gain"); //float, meters
            //athleteActivity.segments = new ArrayList<Segment>(Segment.fromJSONArray(jsonObject.getJSONArray("segment_efforts"))); //TODO is this correct?
            athleteActivity.map = Map.fromJSON(jsonObject.getJSONObject("map"));

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return athleteActivity;
    }

    /*public static ArrayList<AthleteActivity> fromJSONArray(JSONArray jsonArray) {
        ArrayList<AthleteActivity> athleteActivities = new ArrayList<AthleteActivity>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject athleteActivityJson = null;
            try {
                athleteActivityJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            AthleteActivity athleteActivity = AthleteActivity.fromJSON(athleteActivityJson);
            if (athleteActivity != null) {
                athleteActivities.add(athleteActivity);
            }
        }
        return athleteActivities;
    }
*/
    public static ArrayList<AthleteActivity> fromJSONArray(JSONArray jsonArray) {
        ArrayList<AthleteActivity> athleteActivities = new ArrayList<AthleteActivity>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject athleteActivityJson = null;
            try {
                athleteActivityJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            AthleteActivity athleteActivity = AthleteActivity.fromJSON(athleteActivityJson);
            if (athleteActivity != null) {
                athleteActivities.add(athleteActivity);
            }
        }
        return athleteActivities;
    }


    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        String formattedDate = "";
        String[] dateArr;
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateArr = relativeDate.split("\\s+");
        try {
            formattedDate = dateArr[0];
            //Log.d("tweet relative1", dateArr[1]);
            if (dateArr[1].contains("second")) {
                //Log.d("tweet relative", dateArr[1]);
                formattedDate += "s";
            } else if (dateArr[1].contains("minute")) {
                //Log.d("tweet relative", dateArr[1]);
                formattedDate += "m";
            } else if (dateArr[1].contains("hour")) {
                formattedDate += "h";
            } else if (dateArr[1].contains("day")) {
                formattedDate += "d";
            } else if (dateArr[1].contains("week")) {
                formattedDate += "w";
            } else if (dateArr[1].contains("month")) {
                formattedDate += "mo";
            } else if (dateArr[1].contains("year")) {
                formattedDate += "y";
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        //return relativeDate;
        return formattedDate;
    }

    public static String getSimpleDate(String rawJsonDate) { //TODO make new getSimpleDate for Strava's date string

        final String OLD_FORMAT = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        final String NEW_FORMAT = "EEE MMM dd HH:mm yyyy";

        String oldDateString = rawJsonDate;
        String newDateString;
        Date d = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        try {
            d = sdf.parse(oldDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        sdf.applyPattern(NEW_FORMAT);
        newDateString = sdf.format(d);
        return newDateString;
    }


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDistance() {
        return distance;
    }

    public String getElapsed_time() {
        return elapsed_time;
    }

    public String getStart_date_local() {
        return start_date_local;
    }

    public String getTotal_elevation_gain() {
        return total_elevation_gain;
    }

    public ArrayList<Segment> getSegments() {
        return segments;
    }

    public Map getMap() {
        return map;
    }

    @Override
    public String toString(){
        return getName() + " :: " + getType() + " :: " + getDistance();
    }

}