package com.example.ezcamp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ezcamp.NATIONAL_PARK_SERVICE_API_PACKAGE.Campgrounds.CampgroundsResponse;
import com.example.ezcamp.RECYCLE_VIEW_CLASS_PACKAGE.PostsAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentYourCamps extends Fragment implements YourCampsAdapter.YourCampsAdapterListener {

    private RecyclerView Camps;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<YourCampsItem> arrayYourCamps;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yourcamps, container, false);

        Camps = view.findViewById(R.id.RecycleViewCamps);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.isAutoMeasureEnabled();
        arrayYourCamps = new ArrayList<>();
        arrayYourCamps.add(new YourCampsItem("https://cdn.vox-cdn.com/thumbor/wNCd1cBf7MrId4a_2IT-XmcfygY=/0x0:5114x3414/1200x800/filters:focal(2148x1298:2966x2116)/cdn.vox-cdn.com/uploads/chorus_image/image/64713096/shutterstock_1106746100.0.jpg","Long Board","Weather: 87","Alerts: none"));
        arrayYourCamps.add(new YourCampsItem("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSPsz47dbVnMxuOb55TTXBxid3NOGkxd5304hkzoVXWOgRNXX3S&usqp=CAU","North Port","Weather: 89","Alerts: none"));
        arrayYourCamps.add(new YourCampsItem("https://media.tegna-media.com/assets/KFSM/images/417fe596-ffcd-457c-a66c-41bf04c9d777/417fe596-ffcd-457c-a66c-41bf04c9d777_750x422.jpg","RV Gold Trails","Weather: 89","Alerts: windy"));
        mAdapter = new YourCampsAdapter(arrayYourCamps, this, getActivity());
        Camps.setAdapter(mAdapter);
        Camps.setLayoutManager(mLayoutManager);
        Log.d("F", "onCreateView: "+arrayYourCamps.size());
        return view;
    }

    @Override
    public void webGoTo(String url) {

    }

    @Override
    public void removeCamp(int position) {
        //arrayYourCamps.remove(0);
        mLayoutManager.removeViewAt(position);

    }
}