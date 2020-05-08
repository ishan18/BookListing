package com.example.android.booklisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Books> {
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currView=convertView;
        Books books=getItem(position);

        if(currView==null)
            currView= LayoutInflater.from(getContext()).inflate(R.layout.booklist,parent,false);

        TextView bookname=(TextView) currView.findViewById(R.id.book_name);
        bookname.setText(books.getTitle());

        TextView publisher=(TextView) currView.findViewById(R.id.publisher);
        publisher.setText("Published by: "+books.getPublisher());

        TextView publishedOn=(TextView) currView.findViewById(R.id.publishedOn);
        publishedOn.setText("Published On: "+books.getPublishedDate());

        return currView;
    }

    public BookAdapter(@NonNull Context context, ArrayList<Books> booksArrayList) {
        super(context, 0,booksArrayList);
    }
}
