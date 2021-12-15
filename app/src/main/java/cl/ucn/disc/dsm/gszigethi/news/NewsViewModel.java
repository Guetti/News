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

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

import cl.ucn.disc.dsm.gszigethi.news.model.News;

/**
 * The ViewModel of News.
 * @author Gustavo Patricio Szigethi Araya.
 */
public final class NewsViewModel extends AndroidViewModel {

    /**
     * The List of {@link News},
     */
    private MutableLiveData<List<News>> news;

    /**
     * The Constructor.
     * @param application to use.
     */
    public NewsViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     *
     * @return the LiveData of List of News.
     */
    public LiveData<List<News>> getNews(){
        if (this.news == null){

            this.news = new MutableLiveData<>();
            this.loadNews();
        }
        return this.news;
    }

    /**
     * Read the News from news.json.
     */
    private void loadNews(){
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

            this.news.postValue(theNews);
        });
    }
}
