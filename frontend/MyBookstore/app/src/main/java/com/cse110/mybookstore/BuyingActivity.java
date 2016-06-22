package com.cse110.mybookstore;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BuyingActivity extends BaseActivity {
    public static final String LOG_TAG = "BuyingActivity";
    private List<Book> mBooksList = new ArrayList<Book>();

    // Create the RecyclerView and ViewAdapter because this is where the ViewAdapter will
    // be called from and where the RecyclerView will be updated.
    private RecyclerView mRecyclerView;
    private BuyingRecyclerViewAdapter bookRecyclerViewAdapter;
    private TextView noBookToSell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buying);
        super.onCreateDrawer();

        noBookToSell = (TextView) findViewById(R.id.no_books_text);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // A layout manager positions item views inside a RecyclerView and determines when to reuse
        // item views that are no longer visible to the user. The LinearLayoutManager shows items in
        // a vertical or horizontal scrolling list.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve the mBookList from the Add Book button action and use in RecyclerViewAdapter
        bookRecyclerViewAdapter = new BuyingRecyclerViewAdapter(BuyingActivity.this,
                new ArrayList<Book>());

        // Set the Adapter to our RecyclerView
        mRecyclerView.setAdapter(bookRecyclerViewAdapter);

        handleIntent(getIntent());

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(BuyingActivity.this, mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.v("BuyAct", "Short click position: " + position);

                Intent buyBookInfoIntent = new Intent(BuyingActivity.this, BuyBookInfo.class);
                buyBookInfoIntent.putExtra(BOOK_LIST_TRANSFER, mBooksList.get(position));
                startActivity(buyBookInfoIntent);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            //use the query to search your data somehow
            try {
                //long isbn = Long.parseLong(query);
                mBooksList = SearchQuery.searchByISBN(bookRecyclerViewAdapter, query, noBookToSell);
            } catch (Exception e) {
                mBooksList = new ArrayList<Book>();
            }

            bookRecyclerViewAdapter.onLoadData(mBooksList);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_buy, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_item_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        return true;
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector mGestureDetector;
        private ClickListener mClickListener;
        private static final int SWIPE_MIN_DISTANCE = 120;
        private static final int SWIPE_MAX_OFF_PATH = 250;
        private static final int SWIPE_THRESHOLD_VELOCITY = 200;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            mClickListener = clickListener;

            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    // Return super.onSingleTapUp(e)
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());

                    if (child != null && mClickListener != null) {
                        mClickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && mClickListener != null && mGestureDetector.onTouchEvent(e)) {
                mClickListener.onClick(child, rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {}

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
    }

    public interface ClickListener {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }
}
