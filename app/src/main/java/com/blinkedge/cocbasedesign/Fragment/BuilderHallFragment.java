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
import com.blinkedge.cocbasedesign.RecyclerViewAdapter.BuilderHallRecyclerView;
import com.blinkedge.cocbasedesign.Singlation;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuilderHallFragment extends Fragment {

    private View view;
    private ShimmerRecyclerView recyclerView;
    private List<Modal> builderHallDataFetchModal = new ArrayList<>();
    public StringRequest stringRequestBuilder;
    int catHome_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_builder_hall, container, false);

        id();
        jsonResponse();

        return view;
    }

    private void jsonResponse() {
        stringRequestBuilder = new StringRequest(Request.Method.POST, API.VILLAGE_HALL_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("builder_Response_", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.getBoolean("status");
                    if (status) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonFetchBuilderBaseData = jsonArray.getJSONObject(i);

                            int builderHallId = jsonFetchBuilderBaseData.getInt("catHome_id");
                            String builderHallImage = jsonFetchBuilderBaseData.getString("home_image");
                            Log.d("builder_image", builderHallImage);
                            String builderHallNumber = jsonFetchBuilderBaseData.getString("home_description");

                            Modal modal = new Modal();
                            modal.setBuilderHallId(builderHallId);
                            modal.setBuilderBaseTitle(builderHallNumber);
                            modal.setBuilderBaseImage(builderHallImage);

                            builderHallDataFetchModal.add(modal);
                        }

                        setUpRecyclerView(builderHallDataFetchModal);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override

            // Used For one or more parameters in API or if getting 2 response in 1 API
            protected Map<String, String> getParams() {
                Map<String, String> hashmap = new HashMap<>();
                hashmap.put("category_type", "builder");
                return hashmap;
            }
        };

        // if server is not getting the response then it shows hits the API in every 5 seconds
        stringRequestBuilder.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Singlation.getInstance(getContext()).addToRequestQueue(stringRequestBuilder, "");

    }

    public void setUpRecyclerView(List<Modal> builderHallDataFetchModal) {
        BuilderHallRecyclerView builderBaseRecyclerView = new BuilderHallRecyclerView(getActivity(), builderHallDataFetchModal);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(builderBaseRecyclerView);

    }

    private void id() {
        recyclerView = view.findViewById(R.id.builderHallRecyclerView);
    }
}