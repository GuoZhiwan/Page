package com.swufe.page;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {
    TextView show;
    public String updateDate="";
    public String TAG="FirstFragment";
    int count=1;


    public FirstFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);
        show=rootView.findViewById(R.id.showView);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myapp", Activity.MODE_PRIVATE);
        //获取sp里的时间
        updateDate=sharedPreferences.getString("update_date","");
        //获取当前时间
        count=sharedPreferences.getInt("count",count);
        Date today= Calendar.getInstance().getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        final String todayStr=sdf.format(today);
        Log.i(TAG, "onCreate: sp updateDate=" + updateDate);
        Log.i(TAG, "onCreate: sp todayStr=" + todayStr);
        Log.i(TAG, "onCreate: count:"+count);
        if(!todayStr.equals(updateDate)){
            Log.i(TAG, "onCreate: 需要更新");

            SharedPreferences sp = getActivity().getSharedPreferences("myapp", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            count++;
            editor.putString("update_date",todayStr);
            editor.putInt("count",count);
            editor.apply();
            Log.i(TAG, "onCreate: count"+count);

        }else{
            Log.i(TAG, "onCreate: 不需要更新");
            Log.i(TAG, "onCreate: count:"+count);
        }
        show.setText(count+"");
        return rootView;

    }

}
