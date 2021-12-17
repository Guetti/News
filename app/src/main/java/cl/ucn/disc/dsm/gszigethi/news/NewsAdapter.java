/*
 * MIT License
 *
 * Copyright (c) 2021 Gustavo Patricio Szigethi Araya
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cl.ucn.disc.dsm.gszigethi.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cl.ucn.disc.dsm.gszigethi.news.model.News;

/**
 * The Adapter of News.
 * @author Gustavo Patricio Szigethi Araya.
 */
public final class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    /**
     * The list of News.
     */
    private final List<News> news = new ArrayList<>();

    /**
     * The Constructor.
     */
    public NewsAdapter(){
        // Nothing here
    }

    /**
     * Populate the {@link List} of {@link News} with new data.
     * @param news
     */
    public void setNews(final List<News> news){
        this.news.addAll(news);
    }

    /**
     * Add a news.
     * @param news the news to add.
     */
    public void addNews(News news){
        this.news.add(news);
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Step 1: Get the inflater
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Step 2: Inflate the row of News
        final View newsView = layoutInflater.inflate(R.layout.news_element, parent, false);
        // Step 3: Build the ViewHolder
        return new ViewHolder(newsView);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the news at position
        final News news = this.news.get(position);

        // Set the properties
        holder.author.setText(news.getAuthor());
        holder.title.setText(news.getTitle());
        holder.description.setText(news.getDescription());
        holder.date.setText(news.getPublishedAt().toString());

        Picasso.get().load(news.getUrlImage())
                .error(R.drawable.ic_launcher_background)
                .into(holder.image);

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return this.news.size();
    }


    /**
     * The ViewHolder.
     */
    protected static class ViewHolder extends RecyclerView.ViewHolder{

        TextView author;
        TextView title;
        TextView description;
        TextView date;
        ImageView image;


        public ViewHolder(View view){
            super(view);
            this.author = view.findViewById(R.id.cv_tv_author);
            this.title = view.findViewById(R.id.cv_tv_title);
            this.description = view.findViewById(R.id.cv_tv_description);
            this.date = view.findViewById(R.id.cv_tv_publishedAt);
            this.image = view.findViewById(R.id.cv_img);

        }
    }
}
