package com.cse110.mybookstore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.swipe.util.Attributes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {


    public static final String LOG_TAG = "MainActivity";
    private List<Book> mBooksList = new ArrayList<Book>();

    // Create the RecyclerView and ViewAdapter bc this is where the ViewAdapter will
    // be called from and where the RecyclerView will be updated.

    private RecyclerView mRecyclerView;
    private RelativeLayout mSurfaceView;
    private BookRecyclerViewAdapter bookRecyclerViewAdapter;
    static TextView noBookToSell;

    private static Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isOnline()) {
            super.onCreateDrawer();

            noBookToSell = (TextView) findViewById(R.id.no_books_text);

            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

            mSurfaceView = (RelativeLayout) findViewById(R.id.surfaceView);

        /*
         A layout manager positions item views inside a RecyclerView and determines when to reuse
         item views that are no longer visible to the user. The LinearLayoutManager shows items in
         a vertical or horizontal scrolling list.
         */
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Retrieve the mBookList from the Add Book button action and use in RecyclerViewAdapter
            bookRecyclerViewAdapter = new BookRecyclerViewAdapter(MainActivity.this,
                    new ArrayList<Book>());


            // Set the Adapter to our RecyclerView
            mRecyclerView.setAdapter(bookRecyclerViewAdapter);

            // Setting Mode to Single to reveal bottom View for one item in List
            // Setting Mode to Multiple to reveal bottom Views for multiple items in List
            bookRecyclerViewAdapter.setMode(Attributes.Mode.Single);

        /* Scroll Listeners */
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    Log.v(LOG_TAG, "onScrollStateChanged");
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });


            addButton = (FloatingActionButton) findViewById(R.id.addButton);

            addButton.setOnClickListener(addButtonHandler);
        }

        else {
            try {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();

                alertDialog.setTitle("Info");
                alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                });

                alertDialog.show();
            }
            catch(Exception e)
            {
                Log.d(SyncStateContract.Constants.DATA, "Show Dialog: "+e.getMessage());
            }
        }

    }


    View.OnClickListener addButtonHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, AddBook.class);

            mBooksList = AddBook.getBooksList(bookRecyclerViewAdapter, noBookToSell);
            intent.putExtra(BOOK_LIST_TRANSFER, (Serializable) mBooksList);

            startActivity(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        mBooksList = AddBook.getBooksList(bookRecyclerViewAdapter, noBookToSell);

        if (mBooksList.size() > 0) {
            noBookToSell.setVisibility(View.GONE);
        }

        bookRecyclerViewAdapter.loadNewData(mBooksList);
    }


}
