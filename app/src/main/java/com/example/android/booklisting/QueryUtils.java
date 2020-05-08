package com.example.android.booklisting;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class QueryUtils {

    public static ArrayList<Books> fetchData(String url1){

        Log.i("FetchData","Fetchdata called");
        if(url1==null)
            return null;
        URL url=null;
        try {
            url=new URL(url1);
        }catch (MalformedURLException e){}

        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;
        String jsonResponse="";

        if(url==null)
            return null;
        try{
            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(1000);
            urlConnection.setReadTimeout(1500);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            inputStream=urlConnection.getInputStream();
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

            StringBuilder output=new StringBuilder();
            String line=bufferedReader.readLine();
            while (line!=null)
            {
                output.append(line);
                line=bufferedReader.readLine();
            }
            jsonResponse=output.toString();
        }catch (IOException e){}
        finally {
            if(urlConnection!=null)
                urlConnection.disconnect();
            if(inputStream!=null)
                try {
                    inputStream.close();
                }catch (IOException e){}
        }

        return extractJson(jsonResponse);
    }

    public static ArrayList<Books> extractJson(String jsonResponse){
        if(jsonResponse==null)
            return null;
        ArrayList<Books> booksArrayList=new ArrayList<>();
        JSONObject root=null;
        try{
            root=new JSONObject(jsonResponse);
            JSONArray items=root.getJSONArray("items");
            for(int i=0;i<items.length();i++) {
                Books books = extractBooks(items.getJSONObject(i));
                if(books!=null)
                    booksArrayList.add(books);
            }
        }catch (JSONException e){}
        return booksArrayList;
    }

    public static Books extractBooks(JSONObject jsonObject){
        String title;
        ArrayList<String> authors;
        String publisher;
        String publishedDate;
        String description;
        String previewLink;
        Books books=null;

        try {
            JSONObject volumeInfo=jsonObject.getJSONObject("volumeInfo");
            title=volumeInfo.getString("title");
            JSONArray array=volumeInfo.getJSONArray("authors");
            authors=new ArrayList<>();
            for(int i=0;i<array.length();i++)
                authors.add(array.getString(i));
            publisher=volumeInfo.getString("publisher");
            publishedDate=volumeInfo.getString("publishedDate");
            description=volumeInfo.getString("description");
            previewLink=volumeInfo.getString("previewLink");
            books=new Books(title,authors,publisher,publishedDate,description,previewLink);
        }catch (JSONException e){}

        return books;
    }
}
