package com.blinkedge.cocbasedesign.VillageBaseLayoutFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.blinkedge.cocbasedesign.RecyclerViewAdapter.TrophyAllBasesRecyclerView;
import com.blinkedge.cocbasedesign.Singlation;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrophyFragment extends Fragment {

    private LinearLayout trophyNoImage;
    private ShimmerRecyclerView trophyRecyclerView;
    private List<Modal> trophyModalList = new ArrayList<>();
    private StringRequest stringRequest;
    private Context context;
    private View view;
    int catHome_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_trophy, container, false);
        id();
        getData();
        jsonResponse();

        return view;
    }

    private void getData() {
        try {
            catHome_id = getActivity().getIntent().getExtras().getInt("catHome_id", 0);
            Log.d("cat_home_id", String.valueOf(catHome_id));
            if (catHome_id == 0)
                Toast.makeText(getActivity(), "Data is unavaliable ", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void jsonResponse() {
        stringRequest = new StringRequest(Request.Method.POST, API.VILLAGE_BASES_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("trophyResponse_", response);

                    boolean status = jsonObject.getBoolean("status");
                    if (status) {

                        trophyRecyclerView.setVisibility(View.VISIBLE);
                        trophyNoImage.setVisibility(View.INVISIBLE);

                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject fetchTroJsonObject1 = jsonArray.getJSONObject(i);

                            int trophyId = fetchTroJsonObject1.getInt("subcategory_id");
                            String trophyImage = fetchTroJsonObject1.getString("subcategory_image");
                            String warBaseLink = fetchTroJsonObject1.getString("subcategory_link");

                            Modal modal = new Modal();

                            modal.setTrophyItemId(trophyId);
                            modal.setVillageAllBases(trophyImage);
                            modal.setvillageBaseLink(warBaseLink);

                            trophyModalList.add(modal);

                        }

                        setUpRecyclerView(trophyModalList);

                    } else {
                        if (!status) {
                            trophyRecyclerView.setVisibility(View.INVISIBLE);
                            trophyNoImage.setVisibility(View.VISIBLE);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Server Error in trophy", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override

            // Passing two parameters
            protected Map<String, String> getParams() {
                Map<String, String> hashmap = new HashMap<>();
                hashmap.put("subCategory_type", "trophy");
                hashmap.put("catHome_id", catHome_id + "");
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

    private void setUpRecyclerView(List<Modal> trophyModalList) {
        TrophyAllBasesRecyclerView villageAllBasesRecyclerView = new TrophyAllBasesRecyclerView(getActivity(), trophyModalList);
        trophyRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        trophyRecyclerView.setAdapter(villageAllBasesRecyclerView);

    }

    private void id() {
        trophyRecyclerView = view.findViewById(R.id.trophyRecyclerView);
        trophyNoImage = view.findViewById(R.id.trophyNoImage);


    }


}