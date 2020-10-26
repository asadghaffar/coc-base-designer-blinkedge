package com.blinkedge.cocbasedesign.VillageBaseLayoutFragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.blinkedge.cocbasedesign.API.API;
import com.blinkedge.cocbasedesign.Modal.Modal;
import com.blinkedge.cocbasedesign.R;
import com.blinkedge.cocbasedesign.RecyclerViewAdapter.FarmingAllBasesRecyclerView;
import com.blinkedge.cocbasedesign.Singlation;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FarmingFragment extends Fragment {

    private LinearLayout farmingNoImage;
    private ShimmerRecyclerView farmingRecycler;
    private Context context;
    private List<Modal> farmingFetchDataModal = new ArrayList<>();
    private View view;
    private StringRequest stringRequest;
    int catHome_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_farming, container, false);

        id();
        getData();
        jsonResoponse();


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

    private void jsonResoponse() {
        stringRequest = new StringRequest(Request.Method.POST, API.VILLAGE_BASES_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("farmingVillage_", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.getBoolean("status");
                    if (status) {

                        farmingRecycler.setVisibility(View.VISIBLE);
                        farmingNoImage.setVisibility(View.INVISIBLE);

                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject fetchFarmBases = jsonArray.getJSONObject(i);

                            int farmBaseId = fetchFarmBases.getInt("subcategory_id");
                            String farmBaseImage = fetchFarmBases.getString("subcategory_image");
                            String warBaseLink = fetchFarmBases.getString("subcategory_link");
                            Log.d("builderImage_", farmBaseImage);

                            Modal modal = new Modal();

                            modal.setVillageAllBases(farmBaseImage);
                            modal.setFarmingItemId(farmBaseId);
                            modal.setvillageBaseLink(warBaseLink);

                            farmingFetchDataModal.add(modal);

                        }

                        setUPRecyclerFarming(farmingFetchDataModal);


                    }
                    else {
                        farmingRecycler.setVisibility(View.INVISIBLE);
                        farmingNoImage.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Server error in farming", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override

            // Passing two parameters
            protected Map<String, String> getParams() {
                Map<String, String> hashmap = new HashMap<>();
                hashmap.put("subCategory_type", "farming");
                hashmap.put("catHome_id", catHome_id+"");
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

    private void setUPRecyclerFarming(List<Modal> farmingFetchData) {
        Log.d("sizeChecker__",farmingFetchDataModal.size()+"=Framing");

        FarmingAllBasesRecyclerView villageAllBasesRecyclerView = new FarmingAllBasesRecyclerView(getContext(),
                                                                                farmingFetchData);
        farmingRecycler.setLayoutManager(new LinearLayoutManager(context));
        farmingRecycler.setAdapter(villageAllBasesRecyclerView);
    }


    private void id() {
        farmingNoImage = view.findViewById(R.id.farmingNoImage);
        farmingRecycler = view.findViewById(R.id.farmingRecycler);
    }
}