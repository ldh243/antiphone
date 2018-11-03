package com.example.jun.antiphone.singleton;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import entity.ActivitiesLogDTO;
import entity.StoreDTO;
import util.Constants;

public class RestfulAPIManager {
    private static final String TAG = RestfulAPIManager.class.toString();

    private static RestfulAPIManager mInstance;

    private RestfulAPIManager() {
    }

    public static RestfulAPIManager getInstancẹ̣̣̣() {
        if (mInstance == null) {
            mInstance = new RestfulAPIManager();
        }
        return mInstance;
    }

    public void postActivityLog(Context context, final ActivitiesLogDTO activitiesLogDTO) {
        Log.d(TAG, String.format("postActivityLog: %s", activitiesLogDTO));
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String URL = Constants.API_PATH+ "/api/activities-logs";

        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("activitiesLogDate", activitiesLogDTO.getActivitiesLogDate());
            jsonRequest.put("activitiesLogPointReceived", activitiesLogDTO.getActivitiesLogPointReceived());
            jsonRequest.put("activitiesLogAchievedTime", activitiesLogDTO.getActivitiesLogAchievedTime());
            jsonRequest.put("username", activitiesLogDTO.getUsername());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                jsonRequest,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String json = response.toString();
                            ObjectMapper om = new ObjectMapper();
                            JsonNode jsonNode = om.readTree(json);
                            int status = jsonNode.get("status").asInt();
                            if (status == 200) {
                                //success
                                Log.d(TAG, String.format("postActivityLog: onResponse: success | %s", activitiesLogDTO));

                            } else {
                                // failed
                                Log.d(TAG, String.format("postActivityLog: onResponse: failded to| %s", activitiesLogDTO));

                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Log.d(TAG, "onResponse: " + ex.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, String.format("postActivityLog: onResponse: failded to| %s", activitiesLogDTO));

                    }
                }
        );
        requestQueue.add(objectRequest);
    }

    public void getStoresInStoreGroup(final Context context, int storeGroupID, final VolleyCallback callback) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String URL = Constants.API_PATH+ "/api/stores/store-groups/" + storeGroupID;

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int status = response.getInt("status");
                            if (status == 200) {
                                callback.onSuccess(response.getJSONArray("data"));
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Log.d(TAG, "onResponse: " + ex.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, String.format("postActivityLog: onResponse: failded to| %s", error));
                    }
                }
        );
        requestQueue.add(objectRequest);
    }

//    public void callApi() {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        String URL = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=10.7994789,106.6114475" +
//                "&destinations=10.8538493,106.6261721&key=AIzaSyBsY-26loYcr2kpIARp5wTmbExsf-BWC7M";
//
//
//        JsonObjectRequest objectRequest = new JsonObjectRequest(
//                Request.Method.GET,
//                URL,
//                null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            String json = response.toString();
//                            ObjectMapper om = new ObjectMapper();
//                            JsonNode jsonNode = om.readTree(json);
//                            Log.d(TAG, "onResponse: " + jsonNode.get("rows").get(0).get("elements").get(0).get("distance").get("text"));
////                            String distance = jsonNode.get("rows").get("elements").get("distance").get("text").asText();
////                            Log.d(TAG, "onResponseeeeeeeeeeeeeeeee: " + distance);
//
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                            Log.d(TAG, "onResponse: " + ex.getMessage());
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d(TAG, "onErrorResponse: " + error.toString());
//                    }
//                }
//        );
//        requestQueue.add(objectRequest);
//    }

}
