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


import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cl.ucn.disc.dsm.gszigethi.news.model.News;

import cl.ucn.disc.dsm.gszigethi.news.services.retrofit.JsonPlaceHolderApi;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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



        // TODO: Remove this.
        // Create news for testing
        // Create the zoned date time as now
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("-4"));

        // Create the list of news
        List<News> newsList = new ArrayList<>();

        // Create some news
        News news1 = new News("ABCDE", "ABCDE", "ABCDE", "ABCDE", "ABCDE", "ABCDE", "ABCDE", zonedDateTime);
        News news2 = new News(
                "Windows 11: Windows Terminal wird neuer Standard für Kommandozeilen - ComputerBase",
                "ComputerBase",
                "Nicolas La Rocco",
                "https://www.computerbase.de/2021-12/windows-11-windows-terminal-wird-neuer-standard-fuer-kommandozeilen/",
                "https://pics.computerbase.de/1/0/1/7/0/7-576982f7519a42a4/article-1280x720.32d2af39.jpg",
                "Microsoft will kommendes Jahr das Windows Terminal zum neuen Standard in Windows 11 für Kommandozeilenbefehle und Scripts machen.",
                "Microsoft will kommendes Jahr das Windows Terminal zum neuen Standard in Windows 11 für Kommandozeilenbefehle und Scripts machen.",
                zonedDateTime
        );
        News news3 = new News(
                "Disney Plus adds support for Apple’s new SharePlay feature - The Siasat Daily",
                "The Siasat Daily",
                "IANS",
                "https://www.siasat.com/disney-plus-adds-support-for-apples-new-shareplay-feature-2242300/",
                "https://cdn.siasat.com/wp-content/uploads/2021/12/Disney-1.jpg",
                "Disney has updated its subscription video on demand (SVOD) mobile app Disney Plus streaming with support for Apple's SharePlay feature.",
                "Disney has updated its subscription video on demand (SVOD) mobile app Disney Plus streaming with support for Apple's SharePlay feature.",
                zonedDateTime
        );

        // Add the news to the list of news
        newsList.add(news1);
        newsList.add(news2);
        newsList.add(news3);

        // Set the news into the adapter
        this.newsAdapter.setNews(newsList);

        // Union of Adapter + RecyclerView
        recyclerView.setAdapter(this.newsAdapter);
    }

    /**
     * Get the news from NewsApi.org
     */
    // TODO: Get the news from news api
    private void getNews(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<News>> call = jsonPlaceHolderApi.getNews();
        call.enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {

            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {

            }
        });
    }

    // FIXME: Error when load from json file
    /*
    protected void onStart(){
        super.onStart();
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

            // Populate the adapter
            this.newsAdapter.setNews(theNews);

            // Notify / Update the GUI
            runOnUiThread(() -> {
                // Run in UI thread
                this.newsAdapter.notifyDataSetChanged();
            });
        });
    }
    */
}