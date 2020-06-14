package com.swufe.page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MyListActivity extends ListActivity implements Runnable,AdapterView.OnItemClickListener {
    String data[]={"wait......"};
    Handler handler;
    private int count=0;
    ArrayList words=new ArrayList();
    ArrayList means=new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp=getSharedPreferences("mycount", Context.MODE_PRIVATE);
        count=sp.getInt("db_count",0);
        final ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,data);
        setListAdapter(adapter);
        Thread t=new Thread(this);
        t.start();
        handler=new Handler(){
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==5){
                    List<String> list2=(List<String>)msg.obj;
                    ListAdapter adapter=new ArrayAdapter<String>(MyListActivity.this,android.R.layout.simple_expandable_list_item_1,list2);
                    setListAdapter(adapter);

                }
                super.handleMessage(msg);
            }
        };
        getListView().setOnItemClickListener(this);

    }
    public void run() {
        List<String> retlist = new ArrayList<String>();
        //获取网络数据，放入list带回到主线程中
        Log.i(TAG, "run: count:"+count);
        if(count!=0){
            WordManager manager = new WordManager(MyListActivity.this);
            Log.i(TAG, "run: 数据库里有数据");
            for(WordItem item:manager.listall()){
                retlist.add(item.getCurWord()+"-->"+item.getCurMean());
                words.add(item.getCurWord());
                means.add(item.getCurMean());
            }
            Log.i(TAG, "onCreate: words:"+words);


        } else {
        Document doc = null;
        try {
                List<WordItem> wordList = new ArrayList<WordItem>();
                    doc = Jsoup.connect("https://github.com/GuoZhiwan/Page/blob/master/app/src/main/assets/A.txt").get();
                    Elements tables = doc.getElementsByTag("table");
                    Element table0 = tables.get(0);
                    //获取td中的数据
                    Elements tds = table0.getElementsByTag("td");

                    for (int i =1; i < tds.size(); i += 4) {
                        Element td1 = tds.get(i);
                        Element td2 = tds.get(i + 2);
                        String str1 = td1.text();
                        String val = td2.text();
                        Log.i(TAG, "run: " + td1.text() + "==>" + td2.text());
                        retlist.add(str1 + "==>" + val);
                        wordList.add(new WordItem(str1, val));
                    }
            //把数据写入数据库中
            WordManager manager = new WordManager(MyListActivity.this);
            manager.deleteAll();
            Log.i(TAG, "run: 删除所有记录");
            words.clear();
            means.clear();
            manager.addAll(wordList);
            for(WordItem item:manager.listall()){
                retlist.add(item.getCurWord()+"-->"+item.getCurMean());
                words.add(item.getCurWord());
                means.add(item.getCurMean());
            }

            Log.i(TAG, "run: 添加所有记录");
            count++;

        } catch (IOException e) {
            e.printStackTrace();
        }
            SharedPreferences sp = getSharedPreferences("mycount", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putInt("db_count",count);
            Log.i(TAG, "run: count:"+count);
            edit.commit();
        }
        Message msg = handler.obtainMessage(5);
        msg.obj = retlist;
        handler.sendMessage(msg);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent =new Intent(this,Main2Activity.class);
        Bundle bundle=new Bundle();
        bundle.putStringArrayList("CURWORDS",words);
        bundle.putStringArrayList("CURMEANS",means);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}

