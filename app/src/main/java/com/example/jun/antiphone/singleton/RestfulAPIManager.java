package com.example.jun.antiphone.singleton;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entity.ActivitiesLogDTO;
import entity.UserDTO;
import model.ProfileUserDAO;
import util.Constants;
import util.DateTimeUtils;

public class RestfulAPIManager {
    private static final String TAG = "RESTFUL";
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

        String URL = Constants.API_PATH + "/api/activities-logs";
        Log.d(TAG, "postActivityLog: " + URL);
        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("activitiesLogDate", activitiesLogDTO.getActivitiesLogDate());
            jsonRequest.put("activitiesLogPointReceived", activitiesLogDTO.getActivitiesLogPointReceived());
            jsonRequest.put("activitiesLogAchievedTime", activitiesLogDTO.getActivitiesLogAchievedTime());
            JSONObject array = new JSONObject();
            array.put("username", activitiesLogDTO.getUsername());
            jsonRequest.put("userDTO", array);
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

        String URL = Constants.API_PATH + "/api/stores/store-groups/" + storeGroupID;

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

    public void getStoreDistance(Context context, String origins, String destination, final VolleyCallback callback) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String URL = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + origins +
                "&destinations=" + destination + "&key=AIzaSyBsY-26loYcr2kpIARp5wTmbExsf-BWC7M";

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray rows = response.getJSONArray("rows");
                            JSONObject row = (JSONObject) rows.get(0);
                            JSONArray elements = row.getJSONArray("elements");

                            List<String> distances = new ArrayList<>();

                            for (int i = 0; i < elements.length(); i++) {

                                JSONObject element = (JSONObject) elements.get(i);
                                JSONObject distance = (JSONObject) element.get("distance");
                                distances.add(distance.getString("text"));
                            }

                            callback.onSuccess(distances);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Log.d(TAG, "onResponse: " + ex.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error.toString());
                    }
                }
        );
        requestQueue.add(objectRequest);
    }

    private List<Integer> processDataActivityLog(String date, JsonNode jsonNode) {
        ProfileUserDAO dao = new ProfileUserDAO();
        Date[] listDay = dao.get7DaysBefore(DateTimeUtils.parseStringToDate(date));
        String[] listDayStr = new String[11];

        for (int i = 0; i < listDay.length; i++) {
            listDayStr[i] = DateTimeUtils.parseDateToString(listDay[i]);
        }

        List<Integer> result = new ArrayList<>();

        for (int j = 0; j < listDay.length; j++) {
            String dayStr = listDayStr[j];
            int points = 0;
            for (int i = 0; i < jsonNode.size(); i++) {
                String day = jsonNode.get(i).get("activitiesLogDate").asText();
                if (dayStr.equals(day)) {
                    points = jsonNode.get(i).get("activitiesLogAchievedTime").asInt();
                    break;
                }
            }
            result.add(points / 60);
        }
        return result;
    }

    public void getActivityLogNearby(Context context, String userId, final String date, final VolleyCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String URL = Constants.API_PATH + "/api/activities-logs/near-date";

        URL += "?user=" + userId;
        URL += "&date=" + date;
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
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
                                List<Integer> result = processDataActivityLog(date, jsonNode.get("data"));
                                callback.onSuccess(result);
                            } else {
                                // failed

                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }
        );
        requestQueue.add(objectRequest);
    }

    public void getUserInformation(Context context, String user, final VolleyCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String URL = Constants.API_PATH + "/api/users/" + user;

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
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

                                String name = jsonNode.get("data").get("firstName").asText();
                                String dob = jsonNode.get("data").get("dateOfBirth").asText();
                                Long phone = Long.parseLong(jsonNode.get("data").get("phone").asText());
                                String location = jsonNode.get("data").get("location").asText();
                                boolean gender = jsonNode.get("data").get("gender").asBoolean();
                                UserDTO dto = new UserDTO();
                                dto.setFirstName(name);
                                dto.setDob(dob);
                                dto.setPhone(phone);
                                dto.setLocation(location);
                                dto.setGender(gender);
                                callback.onSuccess(dto);
                            } else {
                                // failed

                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }
        );
        requestQueue.add(objectRequest);

    }

    public void saveUserInformation(Context context, UserDTO userInfo) {
        ObjectMapper om = new ObjectMapper();
        Log.d(TAG, "saveUserInformation: " + userInfo.toString());

        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("gender", userInfo.isGender());
            jsonRequest.put("username", userInfo.getUsername());
            jsonRequest.put("dateOfBirth", userInfo.getDob());
            jsonRequest.put("phone", userInfo.getPhone());
            jsonRequest.put("firstName", userInfo.getFirstName());
            jsonRequest.put("location", userInfo.getLocation());
        } catch (JSONException e) {
            e.printStackTrace();
        }

            Log.d(TAG, "saveUserInformation: " +  jsonRequest.toString());
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String URL = Constants.API_PATH + "/api/users";

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.PUT,
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
                                Log.d(TAG, "onResponse: ");
                            } else {
                                // failed

                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        requestQueue.add(objectRequest);

    }

    public void apiRegisterWithoutPassword(Context context, String uid, String firstName, String email) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String URL = Constants.API_PATH + "/api/users/register-without-password";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", uid);
            jsonObject.put("firstName", firstName);
            jsonObject.put("email", email);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String json = response.toString();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        requestQueue.add(objectRequest);
    }

    public void getAllFoodPost(Context context, final VolleyCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String URL = Constants.API_PATH + "/api/posts/store-groups/store-type/FOO";
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
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
                                callback.onSuccess(jsonNode.get("data"));
                            } else {
                                // failed

                            }
                        } catch (Exception ex) {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }
        );
        requestQueue.add(objectRequest);
    }

    public void getAllClothesPost(Context context, final VolleyCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String URL = Constants.API_PATH + "/api/posts/store-groups/store-type/CLT";
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
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
                                callback.onSuccess(jsonNode.get("data"));
                            } else {
                                // failed

                            }
                        } catch (Exception ex) {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }
        );
        requestQueue.add(objectRequest);
    }

    public void getAllEntertainmentPost(Context context, final VolleyCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String URL = Constants.API_PATH + "/api/posts/store-groups/store-type/ETM";
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
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
                                callback.onSuccess(jsonNode.get("data"));
                            } else {
                                // failed

                            }
                        } catch (Exception ex) {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }
        );
        requestQueue.add(objectRequest);
    }

    public void getPostById(Context context, int postId, final VolleyCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String URL = Constants.API_PATH + "/api/posts/" + postId;

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
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
                                callback.onSuccess(jsonNode.get("data"));
                            } else {
                                // failed

                            }
                        } catch (Exception ex) {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }
        );
        requestQueue.add(objectRequest);
    }

    public void getTotalPointEarned(Context context, String uid, final VolleyCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String URL = Constants.API_PATH + "/api/activities-logs/users/total-point";
        URL += "/" + uid;

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
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
                                callback.onSuccess(jsonNode.get("data"));
                            } else {
                                // failed

                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }
        );
        requestQueue.add(objectRequest);
    }

    public void getTotalPointSpent(Context context, String uid, final VolleyCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String URL = Constants.API_PATH + "/api/point-logs/users/total-point";
        URL += "/" + uid;

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
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
                                callback.onSuccess(jsonNode.get("data"));
                            } else {
                                // failed

                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }
        );
        requestQueue.add(objectRequest);
    }

    public void getTotalPointRemain(Context context, String uid, final VolleyCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String URL = Constants.API_PATH + "/api/users/point";
        URL += "/" + uid;

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
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
                                callback.onSuccess(jsonNode.get("data"));
                            } else {
                                // failed
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }
        );
        requestQueue.add(objectRequest);
    }

    public void getVoucherByPostIdUsername(Context context, String uid, int postId, final VolleyCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String URL = Constants.API_PATH + "/api/vouchers/get-voucher-number/";
        URL += "?username=" + uid;
        URL += "&postID=" + postId;

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String json = response.toString();
                            ObjectMapper om = new ObjectMapper();
                            JsonNode jsonNode = om.readTree(json);
                            int status = jsonNode.get("status").asInt();
                            if (status == 200) {
                                callback.onSuccess(jsonNode.get("data"));
                            } else {
                                // failed
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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
