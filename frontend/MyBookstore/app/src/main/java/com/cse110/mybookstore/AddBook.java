package com.cse110.mybookstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class AddBook extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private EditText titleText;
    private EditText authorText;
    private EditText isbnText;
    private EditText priceText;
    private EditText descriptionText;
    private Spinner bookFormat;
    private Button addBookButton;
    private TextView spinnerResult;
    Book book;

    private static List<Book> mBooksList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        titleText = (EditText) findViewById(R.id.title_input);
        authorText = (EditText) findViewById(R.id.author_input);
        isbnText = (EditText) findViewById(R.id.isbn_input);
        priceText = (EditText) findViewById(R.id.price_input);
        descriptionText = (EditText) findViewById(R.id.description_input);
        bookFormat = (Spinner) findViewById(R.id.spinner);

        addBookButton = (Button) findViewById(R.id.add_book_button);

        Toolbar toolbar = activateToolbarWithHomeEnabled();
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.book_format, android.R.layout.simple_spinner_item);
        bookFormat.setAdapter(adapter);
        bookFormat.setOnItemSelectedListener(this);

        Intent intent = getIntent();
        mBooksList = (List<Book>) intent.getSerializableExtra(BOOK_LIST_TRANSFER);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                book = new Book("", "", "", "", "", 0, "", "", "");

                book.setTitle(titleText.getText().toString());
                book.setAuthor(authorText.getText().toString());
                book.setFormat(spinnerResult.getText().toString());
                book.setDescription(descriptionText.getText().toString());
                book.setPrice(Integer.parseInt(priceText.getText().toString()));

                if (isbnText.getText().length() == 13) {
                    long isbn = Long.parseLong(isbnText.getText().toString());

                    book.setIsbn(isbnText.getText().toString());

                    mBooksList.add(book);
                    book.saveToCloud();

                    // Return back to MyBookstore after successful adding a book.
                    startActivity(new Intent(AddBook.this, MainActivity.class));
                }

                else {
                    Toast.makeText(AddBook.this, "Please enter a 13 digit ISBN number.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    /* Used to update the book listing from the cloud */
    public static List<Book> updateListing(final BookRecyclerViewAdapter bookRecyclerViewAdapter, final TextView noBookToSell) {

        final List<Book> cloudListing = new ArrayList<>();
        //ParseQuery<bookItem> query = ParseQuery.getQuery(bookItem.class);
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("bookItem");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> results, ParseException e) {
                if (e == null) {
                    for (ParseObject a : results) {
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


                        if (a.getString("username") != null && a.getString("username").equals(User.username)) {
                            cloudListing.add(book);
                        }
                    }
                    bookRecyclerViewAdapter.loadNewData(cloudListing);
                    if (cloudListing.size() > 0) {
                        noBookToSell.setVisibility(View.GONE);
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });


        return cloudListing;
    }

    // Used in Main Activity in conjunction with the RecyclerView
    public static List<Book> getBooksList(BookRecyclerViewAdapter bookRecyclerViewAdapter, TextView noBookToSell) {
        //return mBooksList;
        List<Book> newListing = updateListing(bookRecyclerViewAdapter, noBookToSell);
        bookRecyclerViewAdapter.loadNewData(newListing);

        return newListing;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerResult = (TextView) view;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }
}
