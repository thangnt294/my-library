package com.example.mylibrary;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;
import com.example.mylibrary.constants.ActivityType;

import java.util.ArrayList;

import static com.example.mylibrary.BookActivity.BOOK_ID_KEY;

public class BooksRecViewAdapter extends RecyclerView.Adapter<BooksRecViewAdapter.ViewHolder> implements IBookCallback {

    public static final String TAG = "BooksRecViewAdapter";

    private ArrayList<Book> bookList = new ArrayList<>();
    private Context mContext;
    private ActivityType parentActivity;

    public BooksRecViewAdapter(Context mContext, ActivityType parentActivity) {
        this.mContext = mContext;
        this.parentActivity = parentActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book currentBook = bookList.get(position);
        Log.d(TAG, "onBindViewHolder: called");
        holder.bookName.setText(bookList.get(position).getName());
        Glide.with(mContext)
                .asBitmap()
                .load(currentBook.getImageUrl())
                .into(holder.imgBook);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BookActivity.class);
                intent.putExtra(BOOK_ID_KEY, currentBook.getId());
                mContext.startActivity(intent);
            }
        });

        if (currentBook.isExpanded()) {
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedLayout.setVisibility(View.VISIBLE);
            holder.downArrow.setVisibility(View.GONE);
            holder.authorName.setText(currentBook.getAuthor());
            holder.shortDesc.setText(currentBook.getShortDesc());

            setRemoveLogic(holder, currentBook, position);

        } else {
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedLayout.setVisibility(View.GONE);
            holder.downArrow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    /**
     * Set the logic for the remove button
     * @param holder The current ViewHolder
     * @param currentBook The current book
     * @param position The position of the current book
     */
    private void setRemoveLogic(ViewHolder holder, Book currentBook, int position) {
        if (parentActivity.equals(ActivityType.AllBooksActivity)) {
            holder.removeTextBtn.setVisibility(View.GONE);
        } else {
            holder.removeTextBtn.setVisibility(View.VISIBLE);
            holder.removeTextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Are you sure you want to remove " + currentBook.getName() + " from this list?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    boolean removedSuccessfully = removeBookFromParentActivity(currentBook);

                                    if (removedSuccessfully) {
                                        Toast.makeText(mContext, currentBook.getName() + " has been removed", Toast.LENGTH_SHORT).show();
                                        notifyItemChanged(position);
                                    } else {
                                        Toast.makeText(mContext, "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Auto-dismiss the dialog
                        }
                    }).create().show();
                }
            });
        }
    }

    private boolean removeBookFromParentActivity(Book currentBook) {
        boolean result = false;

        if (parentActivity.equals(ActivityType.ReadingBooksActivity)) {
            result = Utils.getInstance(mContext).removeFromReadingBooks(currentBook);
        } else if (parentActivity.equals(ActivityType.FinishedBooksActivity)) {
            result = Utils.getInstance(mContext).removeFromFinishedBooks(currentBook);
        } else if (parentActivity.equals(ActivityType.FavoriteBooksActivity)) {
            result = Utils.getInstance(mContext).removeFromFavoriteBooks(currentBook);
        } else if (parentActivity.equals(ActivityType.WishListBooksActivity)) {
            result = Utils.getInstance(mContext).removeFromWishListBooks(currentBook);
        }

        return result;
    }

    public void setBookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
        notifyDataSetChanged();
    }

    @Override
    public void callback() {
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView parent;
        private final ImageView imgBook;
        private final TextView bookName;

        private final ImageView downArrow;
        private final ImageView upArrow;
        private final RelativeLayout expandedLayout;
        private final TextView authorName;
        private final TextView shortDesc;
        private final TextView removeTextBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            imgBook = itemView.findViewById(R.id.imgBook);
            bookName = itemView.findViewById(R.id.txtBookName);

            downArrow = itemView.findViewById(R.id.btnDownArrow);
            upArrow = itemView.findViewById(R.id.btnUpArrow);
            expandedLayout = itemView.findViewById(R.id.expandedRelLayout);
            authorName = itemView.findViewById(R.id.authorName);
            shortDesc = itemView.findViewById(R.id.shortDesc);
            removeTextBtn = itemView.findViewById(R.id.btnRemove);

            downArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Book book = bookList.get(getAdapterPosition());
                    book.setExpanded(!book.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            upArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Book book = bookList.get(getAdapterPosition());
                    book.setExpanded(!book.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
