package com.blinkedge.cocbasedesign.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blinkedge.cocbasedesign.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MoreFragment extends Fragment {
    View view;
    private LinearLayout moreAppsLinear;
    private LinearLayout rateAppLinear;
    private LinearLayout shareAppLinear;
    private LinearLayout privacyPolicyLinear;
    private LinearLayout exitLinear;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_more, container, false);

        id();
        onClick();

        return view;
    }

    private void onClick() {
        rateAppLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.tencent.ig&hl=en"));
                startActivity(intent);
            }
        });

        shareAppLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" +
                                                                getActivity().getPackageName() + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        privacyPolicyLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Privacy Policy", Toast.LENGTH_SHORT).show();
            }
        });

        exitLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(getActivity()).setIcon(R.drawable.ic_exit)
                        .setTitle("Are you sure to exit")
                        .setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
            }
        });

        moreAppsLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "More Apps Shown", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void id() {
        moreAppsLinear = view.findViewById(R.id.moreAppsLinear);
        rateAppLinear = view.findViewById(R.id.rateAppLinear);
        shareAppLinear = view.findViewById(R.id.shareAppLinear);
        privacyPolicyLinear = view.findViewById(R.id.privacyPolicyLinear);
        exitLinear = view.findViewById(R.id.exitLinear);

    }
}