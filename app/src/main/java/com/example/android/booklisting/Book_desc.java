package com.example.android.booklisting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Book_desc extends AppCompatActivity {

    Books books;
    ArrayList<String> authors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_desc);

        books=(Books) getIntent().getSerializableExtra("Book");
        authors=books.getAuthors();
        updateUI();

        Button preview=(Button) findViewById(R.id.preview);
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(books.getPreviewLink()));
                startActivity(intent);
            }
        });
    }

    public void updateUI(){
        TextView bookName=(TextView) findViewById(R.id.book_name);
        bookName.setText(books.getTitle());

        TextView publisher=(TextView) findViewById(R.id.publisher);
        publisher.setText(books.getPublisher());

        TextView publishedOn=(TextView) findViewById(R.id.publishedOn);
        publishedOn.setText(books.getPublishedDate());

        TextView description=(TextView) findViewById(R.id.Description);
        description.setText(books.getDescription());

        TextView author=(TextView) findViewById(R.id.authors);
        StringBuilder list=new StringBuilder();
        list.append("Authors :\n\n");
        if(authors!=null)
        for(int i=0;i<authors.size();i++){
            list.append(i+1+". ");
            list.append(authors.get(i));
            if(i!=authors.size()-1)
                list.append("\n");
        }
        else{
            list.append("Author not Found");
        }
        author.setText(list);
    }
}
