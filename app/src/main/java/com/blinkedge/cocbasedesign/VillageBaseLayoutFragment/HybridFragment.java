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
import com.blinkedge.cocbasedesign.RecyclerViewAdapter.HybridAllBasesRecyclerView;
import com.blinkedge.cocbasedesign.Singlation;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HybridFragment extends Fragment {

    private LinearLayout hybridNoImage;
    private Context context;
    private ShimmerRecyclerView hybridRecycler;
    private List<Modal> hybridModalList = new ArrayList<>();
    private View view;
    private StringRequest stringRequest;
    int catHome_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_hybrid, container, false);

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
                    Log.d("hrbridResponse_", response);

                    boolean status = jsonObject.getBoolean("status");
                    if (status) {

                        hybridRecycler.setVisibility(View.VISIBLE);
                        hybridNoImage.setVisibility(View.INVISIBLE);

                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject fetchHybridData = jsonArray.getJSONObject(i);

                            int hybridId = fetchHybridData.getInt("subcategory_id");
                            String hybridImage = fetchHybridData.getString("subcategory_image");
                            String warBaseLink = fetchHybridData.getString("subcategory_link");
                            Log.d("hybridImage_", hybridImage);

                            Modal modal = new Modal();

                            modal.setVillageAllBases(hybridImage);
                            modal.setHybridItemId(hybridId);
                            modal.setvillageBaseLink(warBaseLink);

                            hybridModalList.add(modal);

                        }

                        setUpRecycler(hybridModalList);


                    } else {
                        if (!status) {
                            hybridRecycler.setVisibility(View.INVISIBLE);
                            hybridNoImage.setVisibility(View.VISIBLE);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override

            // Passing two parameters
            protected Map<String, String> getParams() {
                Map<String, String> hashmap = new HashMap<>();
                hashmap.put("subCategory_type", "hybrid");
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

    private void setUpRecycler(List<Modal> hybridModalList) {
        Log.d("sizeChecker__", hybridModalList.size() + "=hybrid");

        HybridAllBasesRecyclerView villageAllBasesRecyclerView = new HybridAllBasesRecyclerView(getActivity(),
                hybridModalList);
        hybridRecycler.setLayoutManager(new LinearLayoutManager(context));
        hybridRecycler.setAdapter(villageAllBasesRecyclerView);
    }

    private void id() {
        hybridNoImage = view.findViewById(R.id.hybridNoImage);
        hybridRecycler = view.findViewById(R.id.hybridRecycler);
    }
}