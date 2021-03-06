package com.example.sofewarefinal;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.system.StructPollfd;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 立淳 on 2016/6/21.
 */
public class StockNameActivity extends Fragment {

    private ArrayAdapter<String> myArrayAdapter;

    public StockNameActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.main, menu);
    }*/

    @Override public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_hello){
            update();
            Log.i("YOU PRESS YOYOYOYOYO", "YOYOYOYOYO");
            return true;
        }
        else if(id == R.id.action_settings){
            Log.i("you press setting", "you press SETTINGGGGGGGGGGGGG");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        Log.d("onCreateView", "onCreateView");
        myArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.content_text, R.id.my_text, new ArrayList<String>());

        View rootView = inflater.inflate(R.layout.content_main, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.my_list_view);
        listView.setAdapter(myArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String content = myArrayAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, content);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void update(){
        ParsePage parse = new ParsePage();
        parse.execute("https://tw.stock.yahoo.com/s/list.php?c=%B3n%C5%E9%B7%7E&rr=0.96335300%201466436405");
    }

    @Override
    public void onStart(){
        super.onStart();
        update();
    }

    public class ParsePage extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String... params){
            Document doc;
            try{
                doc = Jsoup.connect(params[0]).get();
                Elements title = doc.select("table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr[height=30] > td > a");
                //String logs = elements.toString();
                String logs = title.text();
                String[] stocks = logs.split(" ");
                for (String tmp : stocks)
                    Log.d("See Stock", tmp);
                return stocks;
            }
            catch (IOException e){
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String[] result){
            if(result != null){
                myArrayAdapter.clear();
                int cnt = 0;
                for (String tmp : result) {

                    if(!StringUtil.isNumeric(tmp) && !tmp.equals("零股交易")){
                        //cnt = cnt + 1;
                        myArrayAdapter.add(tmp);
                    }
                    Log.i("WOWOWOWOWOW", tmp);

                }
                //text = (TextView) findViewById(R.id.my_text);
                //text.setText(result.toString());
                //text.setMovementMethod(new ScrollingMovementMethod());


            }
        }
    }
}
