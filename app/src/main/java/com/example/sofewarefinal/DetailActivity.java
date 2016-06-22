package com.example.sofewarefinal;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 立淳 on 2016/6/21.
 */
public class DetailActivity extends ActionBarActivity{



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_layout, new DetailFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public static class DetailFragment extends Fragment {

        private ArrayAdapter<String> myArrayAdapter;
        private String myContentStr;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

            myArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.content_detail, R.id.text_detail, new ArrayList<String>());

            View rootView= inflater.inflate(R.layout.content_detail, container, false);

            ListView listView = (ListView) rootView.findViewById(R.id.list_view_detail);
            listView.setAdapter(myArrayAdapter);


            Intent intent = getActivity().getIntent();
            if(intent != null && intent.hasExtra(intent.EXTRA_TEXT)){
                myContentStr = intent.getStringExtra(intent.EXTRA_TEXT);
                ((TextView) rootView.findViewById(R.id.text_detail)).setText(myContentStr);

            }

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

            private String[] nameStr= {
                "名稱", "時間",  "成交", "買進", "賣出", "漲跌", "張數", "昨收", "開盤", "最高", "最低"
            };


            @Override
            protected String[] doInBackground(String... params){
                Document doc;
                try{
                    doc = Jsoup.connect(params[0]).get();
                    Elements title = doc.select("table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td");
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
                    int flag = 0;
                    int cnt = 0;
                    for (String tmp : result) {
                        //if(!StringUtil.isNumeric(tmp) && !tmp.equals("零股交易"))
                        if (tmp.equals(myContentStr))
                            flag = 1;
                        else if (flag == 1 && tmp.equals("買"))
                            break;

                        if (flag == 1) {
                            myArrayAdapter.add(nameStr[cnt] +":\t\t"+ tmp);
                            cnt ++;
                            Log.i("WOWOWOWOWOW", tmp);
                        }
                    }


                    //text = (TextView) findViewById(R.id.my_text);
                    //text.setText(result.toString());
                    //text.setMovementMethod(new ScrollingMovementMethod());


                }
            }
        }
    }

}
