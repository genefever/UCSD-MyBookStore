package com.cse110.mybookstore;

import android.view.View;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parth on 11/21/15.
 */
public class SearchQuery {

    //private static List<Book> bookResults = new ArrayList<>();

    public static List<Book> searchByISBN(final BuyingRecyclerViewAdapter bookRecyclerViewAdapter, String isbn, final TextView noBookToSell) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("bookItem");
        query.whereEqualTo("isbn", isbn);
        final List<Book> bookResults = new ArrayList<>();

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    for (ParseObject a : scoreList) {
                        Book book = new Book("", "", "", "", "", 0, "", "", "");
                        book.setTitle(a.getString("title"));
                        book.setAuthor(a.getString("author"));
                        book.setImage(a.getString("image"));
                        book.setFormat(a.getString("format"));
                        book.setDescription(a.getString("description"));
                        book.setPrice(a.getInt("price"));
                        book.setSeller(a.getString("seller"));
                        book.setIsbn(a.getString("isbn"));
                        book.setObjectID(a.getObjectId());
                        book.setUsername(a.getString("username"));
                        book.setEmail(a.getString("email"));

                        bookResults.add(book);
                    }

                    bookRecyclerViewAdapter.onLoadData(bookResults);
                    if (bookResults.size() > 0) {
                        noBookToSell.setVisibility(View.GONE);
                    }
                    else {
                        noBookToSell.setVisibility(View.VISIBLE);
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
        return bookResults;
    }
}

