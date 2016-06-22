package com.cse110.mybookstore;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BuyingRecyclerViewAdapter extends RecyclerView.Adapter<BuyingRecyclerViewAdapter.BookImageViewHolder2> {

    private List<Book> mBooksList;
    private Context mContext;

    public BuyingRecyclerViewAdapter(Context mContext, List<Book> mBooksList) {

        this.mBooksList = mBooksList;
        this.mContext = mContext;
    }

    /* Inflate our view holder layout. Create our object, get data and put into image holder.
        Creates a BookImageViewHolder object that will be returned.
     */
    @Override
    public BookImageViewHolder2 onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.browse2, null);
        BookImageViewHolder2 bookImageViewHolder = new BookImageViewHolder2(view);

        return bookImageViewHolder;
    }

    /*
        Anytime there’s an object (view) that is on screen that needs to be updated, this
        will be called automatically by the layout manager.
     */
    @Override
    public void onBindViewHolder(BookImageViewHolder2 bookImageViewHolder, int i) {

        Book bookItem = mBooksList.get(i);

        // We got to set the title and author.
        bookImageViewHolder.title.setText(bookItem.getTitle());
        bookImageViewHolder.author.setText(bookItem.getAuthor());
    }

    @Override
    public int getItemCount() {

        return ( (mBooksList != null) ? mBooksList.size() : 0 );
    }


    /* More efficient way of letting RecyclerViewAdapter to reprocess data and display the new
        contents of that data.
     */
    public void onLoadData(List<Book> newBooks) {

        mBooksList = newBooks;
        notifyDataSetChanged();
    }

    class BookImageViewHolder2 extends RecyclerView.ViewHolder{

        protected ImageView thumbnail;
        protected TextView title;
        protected TextView author;
        protected TextView format;

        public BookImageViewHolder2(View view) {

            super(view);
            this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail2);
            this.title = (TextView) view.findViewById(R.id.title2);
            this.author = (TextView) view.findViewById(R.id.author2);
        }
    }
}
