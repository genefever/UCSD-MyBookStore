package com.cse110.mybookstore;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by genehorecka on 10/30/15.
 * <p/>
 * The adapter provides access to the items in your data set, creates views for items,
 * and replaces the content of some of the views with new data items when the original
 * item is no longer visible.
 */
public class BookRecyclerViewAdapter extends RecyclerSwipeAdapter<BookRecyclerViewAdapter.BookImageViewHolder> {

    private List<Book> mBooksList;
    private Context mContext;
    private static Book book;

    public BookRecyclerViewAdapter(Context mContext, List<Book> mBooksList) {
        this.mBooksList = mBooksList;
        this.mContext = mContext;
    }

    /* Inflate our view holder layout. Create our object, get data and put into image holder.
        Creates a BookImageViewHolder object that will be returned.
     */
    @Override
    public BookImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.browse, viewGroup, false);
        BookImageViewHolder bookImageViewHolder = new BookImageViewHolder(view);

        return bookImageViewHolder;
    }

    /*
        Anytime there’s an object (view) that is on screen that needs to be updated, this
        will be called automatically by the layout manager.
        It uses Picasso to download the image we want.
        It tries to download only the images that are on screen.
     */
    @Override
    public void onBindViewHolder(final BookImageViewHolder bookImageViewHolder, int i) {

        Book bookItem = mBooksList.get(i);

        // TODO : Somehow find a way to let Picasso get access to the book image
        // This is where we will draw the object and thumbnail
//        Picasso.with(mContext).load(bookItem.getmImage())
//                .error(R.drawable.book_placeholder)
//                .placeholder(R.drawable.book_placeholder)
//                .into(bookImageViewHolder.thumbnail);

        // We got image to be downloaded, now we need to set the title and author.
        bookImageViewHolder.title.setText(bookItem.getTitle());
        bookImageViewHolder.author.setText(bookItem.getAuthor());
        bookImageViewHolder.price.setText("$" + String.valueOf(bookItem.getPrice()));
        bookImageViewHolder.format.setText(bookItem.getFormat());

//        bookImageViewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
//
//        // Drag From Left
//        bookImageViewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, bookImageViewHolder.swipeLayout.findViewById(R.id.bottom_wrapper));

        bookImageViewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        // Drag From Right
        bookImageViewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, bookImageViewHolder.swipeLayout.findViewById(R.id.bottom_wrapper));

        // Handling different events when swiping
        bookImageViewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });

        bookImageViewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(bookImageViewHolder.getAdapterPosition());
                Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();
                if (mBooksList.size() == 0) {
                    MainActivity.noBookToSell.setVisibility(View.VISIBLE);
                }

            }
        });

        bookImageViewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UpdateBookActivity.class);

                book = mBooksList.get(bookImageViewHolder.getAdapterPosition());

                intent.putExtra(BaseActivity.BOOK_LIST_TRANSFER, book);

                mContext.startActivity(intent);

            }
        });

        bookImageViewHolder.swipeLayout.findViewById(R.id.surfaceView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sellBookInfoIntent = new Intent(mContext, SellBookInfo.class);
                sellBookInfoIntent.putExtra(BaseActivity.BOOK_LIST_TRANSFER, mBooksList.get(bookImageViewHolder.getAdapterPosition()));
                mContext.startActivity(sellBookInfoIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ((mBooksList != null) ? mBooksList.size() : 0);
    }


    /*
        * A method to delete an item from the RecyclerView.
        * Also notifies the adapter.*/
    public void delete(int position) {

        String objectID = mBooksList.get(position).getObjectID();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("bookItem");
        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    object.deleteInBackground();
                } else {
                    e.printStackTrace();
                }
            }
        });
        mBooksList.remove(position);

        notifyItemRemoved(position);
    }

    /* More efficient way of letting RecyclerViewAdapter to reprocess data and display the new
        contents of that data.
     */

    public void loadNewData(List<Book> newBooks) {
        mBooksList = newBooks;
        // Notifies Recycler adapter to reprocess new data files and
        // redisplay new data on screen.
        notifyDataSetChanged();
    }

    // Swipe to delete functionality
    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    class BookImageViewHolder extends RecyclerView.ViewHolder {

        protected SwipeLayout swipeLayout;
        protected ImageView deleteButton;
        protected ImageView editButton;
        protected ImageView thumbnail;
        protected TextView title;
        protected TextView author;
        protected TextView format;
        protected TextView price;

        public BookImageViewHolder(View view) {
            super(view);
            this.swipeLayout = (SwipeLayout) view.findViewById(R.id.swipe);
            this.deleteButton = (ImageView) view.findViewById(R.id.deleteButton);
            this.editButton = (ImageView) view.findViewById(R.id.editButton);
            this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            this.title = (TextView) view.findViewById(R.id.title);
            this.author = (TextView) view.findViewById(R.id.author);
            this.format = (TextView) view.findViewById(R.id.format);
            this.price = (TextView) view.findViewById(R.id.book_price);
        }

    }

}
