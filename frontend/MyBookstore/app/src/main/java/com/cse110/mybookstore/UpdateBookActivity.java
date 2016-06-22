package com.cse110.mybookstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class UpdateBookActivity extends BaseActivity implements AdapterView.OnItemSelectedListener{

    private EditText titleText;
    private EditText authorText;
    private EditText isbnText;
    private EditText priceText;
    private EditText descriptionText;
    private Spinner bookFormat;
    private TextView spinnerResult;

    private static Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);
        activateToolbarWithHomeEnabled();

        titleText = (EditText) findViewById(R.id.titleEdit);
        authorText = (EditText) findViewById(R.id.authorEdit);
        isbnText = (EditText) findViewById(R.id.isbnEdit);
        priceText = (EditText) findViewById(R.id.priceEdit);
        descriptionText = (EditText) findViewById(R.id.descriptionEdit);
        bookFormat = (Spinner) findViewById(R.id.formatEdit);

        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra(BOOK_LIST_TRANSFER);

        titleText.setText(book.getTitle());
        if(book.getAuthor().length() > 0) {
            authorText.setText(book.getAuthor());
        }

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.book_format, android.R.layout.simple_spinner_item);
        bookFormat.setAdapter(adapter);
        bookFormat.setOnItemSelectedListener(this);

        isbnText.setText(book.getIsbn());
        priceText.setText(Integer.toString(book.getPrice()));
        descriptionText.setText(book.getDescription());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        // For this case it's to edit the current book content
        int id = item.getItemId();

        // If the done "checkmark" icon is clicked, then update the book and
        // go back to the SellBookInfo Activity with the updated Book information.
        if (id == R.id.action_done) {

            book.setTitle(titleText.getText().toString());
            book.setAuthor(authorText.getText().toString());
            book.setFormat(spinnerResult.getText().toString());
            book.setDescription(descriptionText.getText().toString());

            if (priceText.getText().length() > 0) {

                String inputPrice = new String (priceText.getText().toString());
                int price = 0;

                if(!inputPrice.equalsIgnoreCase("")) {
                    price = Integer.parseInt(priceText.getText().toString());
                }

                book.setPrice(price);
            }

            else {
                book.setPrice(0);
            }

            if (isbnText.getText().length() == 13) {
                long isbn = Long.parseLong(isbnText.getText().toString());

                book.setIsbn(isbnText.getText().toString());

                ParseQuery<ParseObject> query = ParseQuery.getQuery("bookItem");
                query.getInBackground(book.getObjectID(), new GetCallback<ParseObject>()
                {
                    public void done(ParseObject bookUpdate, ParseException e) {
                        if (e==null) {
                            bookUpdate.put("title", book.getTitle());
                            bookUpdate.put("author", book.getAuthor());
                            bookUpdate.put("format", book.getFormat());
                            bookUpdate.put("description", book.getDescription());
                            bookUpdate.put("price", book.getPrice());
                            bookUpdate.put("isbn", book.getIsbn());
                            if (!User.isNull()) bookUpdate.put("username", User.username);
                            bookUpdate.saveInBackground();
                        }
                    }});

                Intent sellBookInfoIntent = new Intent(UpdateBookActivity.this, SellBookInfo.class);
                sellBookInfoIntent.putExtra(BOOK_LIST_TRANSFER, book);
                Toast.makeText(UpdateBookActivity.this, "Updated book.", Toast.LENGTH_SHORT).show();
                startActivity(sellBookInfoIntent);
            }

            else {
                Toast.makeText(this, "Please enter a 13 digit ISBN number.", Toast.LENGTH_SHORT).show();
            }

        }

        else if(id == android.R.id.home) {
            Intent sellBookInfoIntent = new Intent(UpdateBookActivity.this, SellBookInfo.class);
            sellBookInfoIntent.putExtra(BOOK_LIST_TRANSFER, book);
            startActivity(sellBookInfoIntent);
        }

        return super.onOptionsItemSelected(item);
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
