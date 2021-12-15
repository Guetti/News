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
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cl.ucn.disc.dsm.gszigethi.news.model.News;

/**
 * The Adapter of News.
 * @author Gustavo Patricio Szigethi Araya.
 */
public final class NewsAdapter extends BaseAdapter {

    /**
     * The list of News.
     */
    private final List<News> news = new ArrayList<>();

    /**
     * The Inflater.
     */
    private final LayoutInflater theInflater;

    /**
     * Constructor of the NewsAdapter.
     * @param context to use
     */
    public NewsAdapter(Context context){
        // Get the inflater
        this.theInflater = LayoutInflater.from(context);

    }

    /**
     * @returnthe size of the list of {@link News}.
     */
    @Override
    public int getCount() {
        return this.news.size();
    }

    /**
     * Return rhe {@link News} at the position in the list.
     * @param position the position.
     * @return the {@link News}
     */
    @Override
    public News getItem(int position) {
        return this.news.get(position);
    }

    /**
     *
     * @param position to get.
     * @return the same position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }


    /**
     * Return a ConvertView with Holder.
     * @param position to get.
     * @param convertView to use, can be null.
     * @param parent The outer component.
     * @return The real ConvertView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // The holder.
        ViewHolder holder;

        // Inflate only the rows visibles
        if (convertView == null){
            convertView = this.theInflater.inflate(R.layout.list_element, parent, false);

            // Construct the ViewHolder
            holder = new ViewHolder(convertView);

            // Save into the convertView.
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Assign the values

        final News news = this.getItem(position);

        holder.author.setText(news.getAuthor());
        holder.title.setText(news.getTitle());

        return convertView;
    }

    /**
     * The ViewHolder.
     */
    private static class ViewHolder{
        // TODO: Add all the attributes
        TextView author;
        TextView title;

        ViewHolder(View view){
            this.author = view.findViewById(R.id.tv_author);
            this.title = view.findViewById(R.id.tv_title);
        }
    }
}
