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

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.Source;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.List;

import cl.ucn.disc.dsm.gszigethi.news.model.News;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainActivity extends AppCompatActivity {

    /**
     * The API KEY from newsapi.org.
     */
    String API_KEY = "a80f0e7d2efb4580b1b049815f8456ac";

    /**
     * The News Api Client.
     */
    NewsApiClient newsApiClient = new NewsApiClient(API_KEY);

    /**
     * The News Adapter
     */
    protected NewsAdapter newsAdapter;

    List<Article> articles = new ArrayList<>();
    /**
     *
     * @param savedInstanceState the state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        // Get the List (RecyclerView).
        final RecyclerView recyclerView = findViewById(R.id.rv_news_list);
        // The type of layout of RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        // Build the Adapter
        this.newsAdapter = new NewsAdapter();

        // Union of Adapter + RecyclerView
        recyclerView.setAdapter(this.newsAdapter);

        //AsyncTask.execute(this::loadNews);



    }

    @Override
    protected void onStart() {
        super.onStart();
        loadNews();
    }

    /**
     * Get the news from newsapi.org
     */
    private void loadNews(){
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .language("es")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        System.out.println(response.getArticles());
                        articles = response.getArticles();


                        for (int i = 0; i < articles.size(); i++){
                            // Validate Author
                            if (articles.get(i).getAuthor() == null || articles.get(i).getAuthor().length() < 3){
                                articles.get(i).setAuthor("Autor desconocido");
                            }

                            // Validate Title
                            if (articles.get(i).getTitle() == null || articles.get(i).getTitle().length() < 2){
                                articles.get(i).setTitle("Titulo desconocido");
                            }

                            // Validate Source
                            if (articles.get(i).getSource() == null){
                                Source source  = new Source();
                                source.setName("Fuente desconocida");
                                articles.get(i).setSource(source);
                            }

                            // Validate Url
                            if (articles.get(i).getUrl() == null){
                                articles.get(i).setUrl("Url desonocida");
                            }

                            // Validate Url Image
                            if (articles.get(i).getUrlToImage() == null){
                                articles.get(i).setUrlToImage("Url de imagen desconocida");
                            }

                            // Validate Description
                            if (articles.get(i).getDescription() == null){
                                articles.get(i).setDescription("Descripcion desconocida");
                            }

                            ZonedDateTime zonedDateTime = ZonedDateTime.parse(articles.get(i).getPublishedAt()).withZoneSameInstant(ZoneId.of("-4"));
                            News theNews = new News(
                                    articles.get(i).getTitle(),
                                    articles.get(i).getSource().getName(),
                                    articles.get(i).getAuthor(),
                                    articles.get(i).getUrl(),
                                    articles.get(i).getUrlToImage(),
                                    articles.get(i).getDescription(),
                                    "No Content",
                                    zonedDateTime
                            );
                            newsAdapter.addNews(theNews);
                        }

                        newsAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println("Error al recuperar noticias: ");
                        System.out.println(throwable.getMessage());

                    }
                }
        );



    }
}