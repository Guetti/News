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

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import org.threeten.bp.ZonedDateTime;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

import cl.ucn.disc.dsm.gszigethi.news.model.News;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainActivity extends AppCompatActivity {

    /**
     * The News Adapter
     */
    protected NewsAdapter newsAdapter;

    /**
     *
     * @param savedInstanceState the state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the List (RecyclerView).
        final RecyclerView recyclerView = findViewById(R.id.rv_news_list);
        // The type of layout of RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        // Build the Adapter
        this.newsAdapter = new NewsAdapter();
        // Union of Adapter + RecyclerView
        recyclerView.setAdapter(this.newsAdapter);
    }

    /**
     * Create a new fake news using Faker
     * @return The fake news
     */
    public News CreateFakeNews(){ // Only for testing.
        Faker faker = new Faker();
        String title = faker.university().name();
        String source = faker.superhero().name();
        String author = faker.book().author();
        String url = faker.internet().url();
        String urlImage = faker.internet().image();
        String description = faker.superhero().descriptor();
        String content = faker.shakespeare().hamletQuote();
        ZonedDateTime time = ZonedDateTime.now();
        News news = new News(title, source, author, url, urlImage, description, content, time);
        return news;
    }

    /**
     *
     */
    @Override
    protected void onStart() {
        super.onStart();

        // Run in the background
        AsyncTask.execute(() -> {
            List<News> theNews;
            try (final InputStream is = super.getApplication().getAssets()
                    .open("news.json")) {

                // Get the Type of List<News> with reflection
                final Type newsListType = new TypeToken<List<News>>(){}.getType();

                // The Reader
                final Reader reader = new InputStreamReader(is);

                // The json to object converter
                final Gson gson = new GsonBuilder().create();

                // Google Gson Black magic.
                theNews = gson.fromJson(reader, newsListType);

            } catch (IOException e){
                e.printStackTrace();
                return;
            }

            // Populate the Adapter
            this.newsAdapter.setNews(theNews);

            // Notify / Update the GUI
            runOnUiThread(() -> {
                this.newsAdapter.notifyDataSetChanged();
            });
        });
    }
}