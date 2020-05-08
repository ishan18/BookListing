package com.example.android.booklisting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    SearchView searchBar;
    Button searchButton;
    StringBuilder BOOKS_URL=new StringBuilder();
    String HOME_URL="https://www.googleapis.com/books/v1/volumes?q=android";
    ListView listView;
    ArrayList<Books> booksArrayList;
    BookAdapter bookAdapter;
    AsyncBooks asyncBooks;
    ProgressBar progressBar;
    TextView noItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBar=(SearchView) findViewById(R.id.search_bar);
        searchButton=(Button) findViewById(R.id.search_button);
        listView=(ListView) findViewById(R.id.list_item);
        progressBar=findViewById(R.id.progress_circular);
        noItem=(TextView)findViewById(R.id.noItem);
        noItem.setVisibility(View.INVISIBLE);

//        getLoaderManager().initLoader(1,null,this);
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
//                getLoaderManager().initLoader(1,null,MainActivity.this);
            }
        });
    }

    private class AsyncBooks extends AsyncTask<String,Void,ArrayList<Books>>{
        @Override
        protected void onPostExecute(ArrayList<Books> booksArrayList) {
            super.onPostExecute(booksArrayList);
            progressBar.setVisibility(View.INVISIBLE);
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
            Log.i("Async Called","Asynctask called");
            return QueryUtils.fetchData(strings[0]);
        }
    }

//    @Override
//    public void onLoaderReset(@NonNull Loader<ArrayList<Books>> loader) {
//        loader.reset();
//    }
//
//    @Override
//    public void onLoadFinished(@NonNull Loader<ArrayList<Books>> loader, ArrayList<Books> data) {
//        bookAdapter=new BookAdapter(MainActivity.this,data);
//        listView.setAdapter(bookAdapter);
//    }
//
//    @NonNull
//    @Override
//    public Loader<ArrayList<Books>> onCreateLoader(int id, @Nullable Bundle args) {
//        return new BookLoader(MainActivity.this,HOME_URL);
//    }
}
