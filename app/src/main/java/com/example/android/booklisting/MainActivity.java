package com.example.android.booklisting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    SearchView searchBar;
    Button searchButton;
    StringBuilder BOOKS_URL=new StringBuilder();
    String HOME_URL="https://www.googleapis.com/books/v1/volumes?q=android";
    ListView listView;
    ArrayList<Books> booksList;
    BookAdapter bookAdapter;
    AsyncBooks asyncBooks;
    ProgressBar progressBar;
    TextView noItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        searchBar=(SearchView) findViewById(R.id.search_bar);
        searchButton=(Button) findViewById(R.id.search_button);
        listView=(ListView) findViewById(R.id.list_item);
        progressBar=findViewById(R.id.progress_circular);
        noItem=(TextView)findViewById(R.id.noItem);
        noItem.setVisibility(View.INVISIBLE);

        asyncBooks=new AsyncBooks();
        asyncBooks.execute(HOME_URL);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchBar.getQuery().toString().isEmpty())
                    return;
                noItem.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                listView.setAdapter(new BookAdapter(MainActivity.this,new ArrayList<Books>()));
                BOOKS_URL=new StringBuilder();
                BOOKS_URL.append("https://www.googleapis.com/books/v1/volumes?q=");
                BOOKS_URL.append(searchBar.getQuery());
                asyncBooks=new AsyncBooks();
                asyncBooks.execute(BOOKS_URL.toString());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(booksList==null)
                    return;
                Books books=booksList.get(position);
                Intent intent=new Intent(MainActivity.this,Book_desc.class);
                intent.putExtra("Book",books);
                startActivity(intent);

//                Intent intent=new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(books.getPreviewLink()));
//                startActivity(intent);
            }
        });
    }

    private class AsyncBooks extends AsyncTask<String,Void,ArrayList<Books>>{
        @Override
        protected void onPostExecute(ArrayList<Books> booksArrayList) {
            super.onPostExecute(booksArrayList);
            progressBar.setVisibility(View.INVISIBLE);
            booksList=booksArrayList;
            if(booksArrayList.isEmpty()){
                noItem.setVisibility(View.VISIBLE);
                return;
            }
            bookAdapter=new BookAdapter(MainActivity.this,booksArrayList);
            listView.setAdapter(bookAdapter);
        }

        @Override
        protected ArrayList<Books> doInBackground(String... strings) {
            if(strings.length<1 || strings[0]==null)
                    return null;
            return QueryUtils.fetchData(strings[0]);
        }
    }
}
