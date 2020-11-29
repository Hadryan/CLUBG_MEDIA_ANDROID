package com.gtechnologies.clubg.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gtechnologies.clubg.Library.Utility;
import com.gtechnologies.clubg.R;

/**
 * Created by Hp on 9/11/2017.
 */

public class Hits extends Fragment {

    Context context;
    Utility utility;
    TextView hitsTitle;
    String value;

    public Hits() {
    }

    @SuppressLint("ValidFragment")
    public Hits(Context context, String value) {
        this.context = context;
        this.value = value;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hits_layout, null);
        hitsTitle = (TextView) view.findViewById(R.id.hitsTitle);
        hitsTitle.setText(value);
        return view;
    }
}
