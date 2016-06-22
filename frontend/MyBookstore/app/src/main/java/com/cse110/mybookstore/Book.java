package com.cse110.mybookstore;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.Serializable;

/**
 * Created by genehorecka on 10/30/15.
 */
public class Book implements Serializable {

    private String mTitle;
    private String mAuthor;
    private String mImage;
    private String mFormat;
    private String mDescription;
    private int mPrice;
    private String mSeller;
    private String isbn;
    private String mObjectID;
    private String username;
    private String email;

    public Book(String mTitle, String mAuthor, String mImage, String mFormat, String mDescription, int mPrice, String mSeller, String isbn, String mObjectID) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mImage = mImage;
        this.mFormat = mFormat;
        this.mDescription = mDescription;
        this.mPrice = mPrice;
        this.mSeller = mSeller;
        this.isbn = isbn;
        this.mObjectID = mObjectID;
        if (!User.isNull()) this.username = User.username;
        if (!User.isNull()) this.email = User.email;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getFormat() {
        return mFormat;
    }

    public String getImage() {
        return mImage;
    }

    public int getPrice() {
        return mPrice;
    }

    public String getSeller() {
        return mSeller;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setFormat(String format) {
        mFormat = format;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    public void setSeller(String seller) {
        mSeller = seller;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getObjectID() { return mObjectID; }

    public void setObjectID(String objectID) { mObjectID = objectID; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public void saveToCloud() {
        //ParseObject bookItem=new ParseObject("bookItem");
        final ParseObject bookItem = ParseObject.create("bookItem");
        bookItem.put("title",mTitle);
        bookItem.put("author", mAuthor);
        bookItem.put("image", mImage);
        bookItem.put("format", mFormat);
        bookItem.put("description", mDescription);
        bookItem.put("price", mPrice);
        bookItem.put("seller", mSeller);
        bookItem.put("isbn", isbn);
        bookItem.put("username", User.username);
        bookItem.put("email", User.email);
        //bookItem.saveInBackground();
        bookItem.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    mObjectID = bookItem.getObjectId();
                }
            }
        });
    }


}
