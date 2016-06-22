package com.cse110.mybookstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class BuyBookInfo extends BaseActivity {

    private TextView titleText;
    private TextView authorText;
    private TextView isbnText;
    private TextView formatText;
    private TextView priceText;
    private TextView descriptionText;
    private TextView showMore;
    private TextView emailText;

    private boolean toggleShow = false;

    private static Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_book_info);
        activateToolbarWithHomeEnabled();

        titleText = (TextView) findViewById(R.id.titleInfo);
        authorText = (TextView) findViewById(R.id.authorInfo);
        isbnText = (TextView) findViewById(R.id.isbnInfo);
        formatText = (TextView) findViewById(R.id.formatInfo);
        priceText = (TextView) findViewById(R.id.priceInfo);
        descriptionText = (TextView) findViewById(R.id.descriptionInfo);
        showMore = (TextView) findViewById(R.id.showMore);
        emailText = (TextView) findViewById(R.id.emailInfo);

        Intent intent = getIntent();

        // This is where we get the book item that has been passed through the activities
        // Sets the book variable to the passed book object
        book = (Book) intent.getSerializableExtra(BOOK_LIST_TRANSFER);

        titleText.setText(book.getTitle());
        if(book.getAuthor().length() > 0) {
            authorText.setText("by " + book.getAuthor());
        }

        formatText.setText(book.getFormat());
        isbnText.setText(book.getIsbn());
        priceText.setText("$ " + Integer.toString(book.getPrice()));
        descriptionText.setText(book.getDescription());
        emailText.setText("\nContact Seller:\n" + book.getEmail());

        // Toggle showing more information about the book when "Show More" is clicked
        showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!toggleShow) {
                    showMore.setText("Show Less");
                    formatText.setVisibility(View.VISIBLE);
                    isbnText.setVisibility(View.VISIBLE);
                    priceText.setVisibility(View.VISIBLE);
                    descriptionText.setVisibility(View.VISIBLE);
                    emailText.setVisibility(View.VISIBLE);
                    toggleShow = true;
                }
                else {
                    showMore.setText("Show More");
                    formatText.setVisibility(View.INVISIBLE);
                    isbnText.setVisibility(View.INVISIBLE);
                    priceText.setVisibility(View.INVISIBLE);
                    descriptionText.setVisibility(View.INVISIBLE);
                    emailText.setVisibility(View.INVISIBLE);
                    toggleShow = false;
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return true;
    }
}