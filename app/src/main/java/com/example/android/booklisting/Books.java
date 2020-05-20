package com.example.android.booklisting;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Books implements Serializable {

    String title;
    ArrayList<String> authors;
    String publisher;
    String publishedDate;
    String description;
    String previewLink;

    public Books(String title,ArrayList<String> authors,String publisher,String publishedDate,String description,String previewLink)
    {
        this.authors=authors;
        this.title=title;
        this.publishedDate=publishedDate;
        this.publisher=publisher;
        this.description=description;
        this.previewLink=previewLink;
    }

//    public Books(Parcel parcel)
//    {
//        String data[]=new String[5];
//        parcel.readStringArray(data);
//
//        this.title=data[0];
//        this.publisher=data[2];
//        this.publishedDate=data[1];
//        this.previewLink=data[3];
//        this.description=data[4];
//    }

//    public static final Parcelable.Creator<Books> CREATOR = new Parcelable.Creator<Books>() {
//        @Override
//        public Books createFromParcel(Parcel in) {
//            return new Books(in);
//        }
//
//        @Override
//        public Books[] newArray(int size) {
//            return new Books[size];
//        }
//    };

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public String getDescription() {
        return description;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getTitle() {
        return title;
    }

//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeArray(new String[]{title,publishedDate,publisher,previewLink,description});
//    }
//
//    @Override
//    public int describeContents() {
//                return 0;
//    }
}
