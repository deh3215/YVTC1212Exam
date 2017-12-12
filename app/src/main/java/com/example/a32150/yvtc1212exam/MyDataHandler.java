package com.example.a32150.yvtc1212exam;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by yvtc on 2017/10/30.
 */

public class MyDataHandler extends DefaultHandler {
    boolean isTitle = false;
    boolean isLink = false;
    boolean isItem = false;
    boolean isImg = false;
    public ArrayList<String> titles = new ArrayList();
    public ArrayList<String> links = new ArrayList();
    public ArrayList<String> imgs = new ArrayList();
    StringBuilder titleTemp = new StringBuilder();
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (qName.equals("title"))
        {
            isTitle = true;
        }
        if (qName.equals("item"))
        {
            isItem = true;
        }
        if (qName.equals("link"))
        {
            isLink = true;
        }
        if (qName.equals("description"))
        {
            isImg = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.equals("title"))
        {
            if(isItem) {
                titles.add(titleTemp.toString());
                titleTemp = new StringBuilder();
            }
                isTitle = false;
        }
        if (qName.equals("item"))
        {
            isItem = false;
        }
        if (qName.equals("link"))
        {
            isLink = false;
        }
        if (qName.equals("description"))
        {
            isImg = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if (isTitle && isItem)
        {
            String data = new String(ch, start, length);
            Log.d("MyTitle", data);
            titleTemp.append(data);
//            titles.add(data);
        }
        if (isLink && isItem)
        {
            String data = new String(ch, start, length);
            Log.d("MyLink", data);
            links.add(data);
        }

//        String imgData;
        if (isImg && isItem)
        {
            String data = new String(ch, start, length);
            int indexStart = data.indexOf("='");
            int indexEnd = data.indexOf("'>");
            Log.d("MyImg", indexStart+", "+indexEnd);
            if (indexStart==-1 || indexEnd==-1) {
                data = "R.drawable/mipmap/ic_launcher";
            } else {
                data = data.substring(indexStart+2, indexEnd - indexStart+2);
                Log.d("MyImg", data);
            }
            imgs.add(data);
        }
    }
}
