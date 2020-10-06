package com.blinkedge.cocbasedesign.VillageBaseLayoutFragment;

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
import com.blinkedge.cocbasedesign.RecyclerViewAdapter.WarAllBasesRecyclerView;
import com.blinkedge.cocbasedesign.Singlation;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarFragment extends Fragment {

    private LinearLayout warNoImage;
    private View view;
    private ShimmerRecyclerView warRecycler;
    private List<Modal> fetchAllBasesModal = new ArrayList<>();
    private StringRequest stringRequest;
    int catHome_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_war, container, false);

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

                    Log.d("village_bases_response_", response);
                    JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.getBoolean("status");
                    if (status) {

                        warRecycler.setVisibility(View.VISIBLE);
                        warNoImage.setVisibility(View.INVISIBLE);

                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject fetchWarBaseImage = jsonArray.getJSONObject(i);
                            Log.d("valueI_", String.valueOf(i));

                            int warBaseId = fetchWarBaseImage.getInt("subcategory_id");
                            String warBaseImage = fetchWarBaseImage.getString("subcategory_image");
                            String warBaseLink = fetchWarBaseImage.getString("subcategory_link");
                            Log.d("builderImage_", warBaseImage);

                            Modal modal = new Modal();
                            modal.setItemId(warBaseId);
                            modal.setVillageAllBases(warBaseImage);
                            modal.setvillageBaseLink(warBaseLink);
                            Log.d("warLink__", warBaseLink);

                            fetchAllBasesModal.add(modal);
                            Log.d("sizeChecker__", fetchAllBasesModal.size() + "=war");

                        }

                        setUpRecyclerViewVillageBases(fetchAllBasesModal);

                    } else {
                        if (!status) {
                            warRecycler.setVisibility(View.INVISIBLE);
                            warNoImage.setVisibility(View.VISIBLE);
                        }
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

            // Passing two parameters
            protected Map<String, String> getParams() {
                Map<String, String> hashmap = new HashMap<>();
                hashmap.put("subCategory_type", "war");
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

    private void setUpRecyclerViewVillageBases(List<Modal> fetchAllBasesModal) {
        WarAllBasesRecyclerView villageAllBasesRecyclerView = new WarAllBasesRecyclerView(getActivity(), fetchAllBasesModal);
        warRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        warRecycler.setAdapter(villageAllBasesRecyclerView);
    }

    private void id() {
        warNoImage = view.findViewById(R.id.warNoImage);
        warRecycler = view.findViewById(R.id.warRecycler);
    }
}