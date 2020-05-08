package com.example.android.booklisting;

import java.util.ArrayList;

public class Books {

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
}
