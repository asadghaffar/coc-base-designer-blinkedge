package com.blinkedge.cocbasedesign.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.blinkedge.cocbasedesign.API.API;
import com.blinkedge.cocbasedesign.Modal.Modal;
import com.blinkedge.cocbasedesign.R;
import com.blinkedge.cocbasedesign.RecyclerViewAdapter.BuilderAllBasesRecyclerView;
import com.blinkedge.cocbasedesign.Singlation;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuilderBasesActivity extends AppCompatActivity {

    private ImageView backImage;
    private ShimmerRecyclerView builderAllBasesRecycler;
    private List<Modal> builderBasesmodalList = new ArrayList<>();
    private StringRequest stringRequest;
    public static List<Modal> builderFavoriteModalList = new ArrayList<>();
    int catHome_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_builder_bases);

        id();
        onClick();
        getData();
        jsonResponse();

    }

    private void getData() {
        try {
            catHome_id = this.getIntent().getExtras().getInt("catHome_id", 0);
            Log.d("cat_home_id", String.valueOf(catHome_id));
            if (catHome_id == 0)
                Toast.makeText(this, "Data is unavaliable ", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jsonResponse() {
        stringRequest = new StringRequest(Request.Method.POST, API.VILLAGE_BASES_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("builderBases_", response);
                    JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.getBoolean("status");
                    Log.d("statuts_", String.valueOf(status));
                    if (status) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject fetchBuilderBases = jsonArray.getJSONObject(i);

                            int builderBaseId = fetchBuilderBases.getInt("subcategory_id");
                            String builderBaseImage = fetchBuilderBases.getString("subcategory_image");
                            String builderBaseLink = fetchBuilderBases.getString("subcategory_link");
                            Log.d("builderBAseImage_", builderBaseImage);

                            Modal modal = new Modal();
                            modal.setBuilderBasesItemId(builderBaseId);
                            modal.setBuilderBaseLink(builderBaseLink);
                            modal.setBuilderBaseImage(builderBaseImage);

                            builderBasesmodalList.add(modal);
                            Log.d("sizeBuilderBases", builderBasesmodalList.size() + "builderbase");

                        }

                        setUpRecycler(builderBasesmodalList);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BuilderBasesActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override

            // Passing two parameters
            protected Map<String, String> getParams() {
                Map<String, String> hashmap = new HashMap<>();
                hashmap.put("subCategory_type", "builder");
                hashmap.put("catHome_id", catHome_id + "");
                return hashmap;
            }
        };

        // if server is not getting the response then it shows hits the API in every 5 seconds
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Singlation.getInstance(this).addToRequestQueue(stringRequest, "");

    }

    private void setUpRecycler(List<Modal> builderBasesmodalList) {
        BuilderAllBasesRecyclerView builderAllBasesRecyclerView = new BuilderAllBasesRecyclerView(this, this.builderBasesmodalList);
        builderAllBasesRecycler.setLayoutManager(new LinearLayoutManager(this));
        builderAllBasesRecycler.setAdapter(builderAllBasesRecyclerView);
    }

    private void onClick() {
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void id() {
        backImage = findViewById(R.id.backImage);
        builderAllBasesRecycler = findViewById(R.id.builderAllBasesRecycler);
    }
}