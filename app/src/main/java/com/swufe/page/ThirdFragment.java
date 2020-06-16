package com.swufe.page;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdFragment extends Fragment {
    EditText editText;
    ListView lv;
    DBHelper dbHelper;
    Handler handler;
    WordManager manager;
    ArrayList words=new ArrayList();
    ArrayList means=new ArrayList();
    private List<HashMap<String,String>> listItems;
    List<HashMap<String, String>> retlist = new ArrayList<HashMap<String, String>>();
    SimpleAdapter SimpleAdapter;
    public final String TAG = "ThirdFragment";
    public ThirdFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view=inflater.inflate(R.layout.fragment_third, container, false);

        return view;

    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListView();
        lv=getActivity().findViewById(R.id.list);
        handler=new Handler(){
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==5){
                    listItems=(List<HashMap<String,String>>)msg.obj;
                    SimpleAdapter=new SimpleAdapter(getActivity(),listItems,
                            R.layout.list_item,
                            new String[]{"ItemWord","ItemMean"},
                            new int[]{R.id.itemWord,R.id.itemMean}
                    );

                    lv.setAdapter(SimpleAdapter);

                }
                super.handleMessage(msg);
            }
        };

        editText=getActivity().findViewById(R.id.edittext);
        dbHelper = new DBHelper(getActivity(),"myword.db",null,1);
        manager=new WordManager(getActivity());
        for(WordItem item:manager.listall()){
            words.add(item.getCurWord());
            means.add(item.getCurMean());
        }

        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.setCursorVisible(true);
        editText.requestFocus();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str=editText.getText().toString();
                retlist.clear();
                for(int i=0;i<words.size();i++) {
                    if (str!=null&&words.get(i).toString().equals(str)) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("ItemWord", words.get(i).toString());
                        map.put("ItemMean", means.get(i).toString());
                        retlist.add(map);
                    }
                    Message msg = handler.obtainMessage(5);
                    msg.obj = retlist;
                    handler.sendMessage(msg);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void initListView() {
        listItems = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemWord", "word= " + i);
            map.put("ItemMean", "mean" + i);
            listItems.add(map);
        }
        SimpleAdapter=new SimpleAdapter(getActivity(),listItems,
                R.layout.list_item,
                new String[]{"ItemWord","ItemMean"},
                new int[]{R.id.itemWord,R.id.itemMean}
        );

    }
}
