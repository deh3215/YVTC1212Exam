package com.example.a32150.yvtc1212exam;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    MyDataHandler dataHandler;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listView);

    }

    public void onClick1(View v)    {
        getData();
        dataHandler = new MyDataHandler();
        adapter = new MyAdapter(MainActivity.this, dataHandler);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener (new MyItemClick());
    }

    public void onClick2(View v)  {
        Intent it = new Intent(MainActivity.this, ChatActivity.class);
        startActivity(it);
    }

    public void onClick3(View v)  {
        Intent it = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(it);
    }


    void getData() {

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL("https://udn.com/rssfeed/news/2/6638?ch=news");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    String str;
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    while((str = br.readLine()) != null)
                    {
                        sb.append(str);
                        //sb.append("\n");
                    }
                    br.close();
                    isr.close();
                    is.close();
                    String result = sb.toString();

                    SAXParserFactory spf = SAXParserFactory.newInstance();
                    SAXParser sp = spf.newSAXParser();
                    XMLReader xr = sp.getXMLReader();
                    xr.setContentHandler(dataHandler);
                    xr.parse(new InputSource(new StringReader(result)));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private class MyItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent it = new Intent(MainActivity.this, WebViewEx.class);
            it.putExtra("url", dataHandler.links.get(position));
            startActivity(it);
        }
    }
}
