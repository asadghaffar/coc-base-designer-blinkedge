package com.blinkedge.cocbasedesign.VillageBaseLayoutFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    private View view;
    private ImageView fvrtBase;
    private ImageView linkBase;
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
        onClick();


        return view;
    }

    private void onClick() {
        fvrtBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Added to favort", Toast.LENGTH_SHORT).show();
            }
        });

        linkBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://link.clashofclans.com/en?action=OpenLayout&id=TH12%3AWB%3AAAAAMAAAAAGM3GAShmuShcHGigknb71S";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
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

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Server error in war", Toast.LENGTH_SHORT).show();
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
        Context context;
        fvrtBase = view.findViewById(R.id.warFavoriteImage);
        linkBase = view.findViewById(R.id.copyWarBase);
        warRecycler = view.findViewById(R.id.warRecycler);
    }
}