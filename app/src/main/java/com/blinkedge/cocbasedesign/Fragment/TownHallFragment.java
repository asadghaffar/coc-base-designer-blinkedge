package com.blinkedge.cocbasedesign.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.blinkedge.cocbasedesign.API.API;
import com.blinkedge.cocbasedesign.Modal.Modal;
import com.blinkedge.cocbasedesign.R;
import com.blinkedge.cocbasedesign.RecyclerViewAdapter.VillageHallRecyclerView;
import com.blinkedge.cocbasedesign.Singlation;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TownHallFragment extends Fragment {

    public ShimmerRecyclerView hallRecyclerView;
    public StringRequest stringRequest;
    public View view;
    private LinearLayout townHallNoImage;
    public List<Modal> dataFetchModal = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_town_hall, container, false);

        id();
        jsonResponse();

        return view;
    }


    private void jsonResponse() {
        stringRequest = new StringRequest(Request.Method.POST, API.VILLAGE_HALL_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d("village_response_", response);
                    JSONObject jsonObject = new JSONObject(response);
                    // Fetching Status
                    boolean status = jsonObject.getBoolean("status");
                    if (status) {

                        hallRecyclerView.setVisibility(View.VISIBLE);
                        townHallNoImage.setVisibility(View.INVISIBLE);

                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonFetchTownHallImages = jsonArray.getJSONObject(i);
                            // Getting Response
                            Log.d("data_response", response);

                            int villageHomeID = jsonFetchTownHallImages.getInt("catHome_id");
                            String villageTownHallIMage = jsonFetchTownHallImages.getString("home_image");
                            Log.d("image_", villageHomeID+"api");
                            String villageTownHallNumber = jsonFetchTownHallImages.getString("home_description");

                            Modal modal = new Modal();
                            modal.setTownHallId(villageHomeID);
                            modal.setTownHallImage(villageTownHallIMage);
                            modal.setTownHallNumber(villageTownHallNumber);

                            dataFetchModal.add(modal);

                        }
                        setUpRecyclerView(dataFetchModal);

                    }
                    else{
                        hallRecyclerView.setVisibility(View.INVISIBLE);
                        townHallNoImage.setVisibility(View.VISIBLE);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            //Passing app_id 1
            // If there is one or more app on admin panel then we have to pass the app_id so the API will hit the specific app
            protected Map<String, String> getParams() {
                Map<String, String> hashmap = new HashMap<>();
                hashmap.put("category_type", "village");
                return hashmap;
            }
        };

        // if server is not getting the response then it shows hits the API in every 5 seconds
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Singlation.getInstance(getContext()).addToRequestQueue(stringRequest, "");

    }

    private void id() {
        townHallNoImage = view.findViewById(R.id.townHallNoImage);
        hallRecyclerView = view.findViewById(R.id.hallRecyclerView);
    }

    public void setUpRecyclerView(List<Modal> dataFetchModal) {
        VillageHallRecyclerView hallRecyclerViewAdapter = new VillageHallRecyclerView(getActivity(), dataFetchModal);
        hallRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        hallRecyclerView.setAdapter(hallRecyclerViewAdapter);
    }

}