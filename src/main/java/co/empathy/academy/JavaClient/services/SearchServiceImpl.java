package co.empathy.academy.JavaClient.services;

import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.empathy.academy.JavaClient.exception.BulkIndexException;
import co.empathy.academy.JavaClient.exception.RecordNotFoundException;
import co.empathy.academy.JavaClient.model.Movie;
import co.empathy.academy.JavaClient.utils.IMDbReader;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

import static co.empathy.academy.JavaClient.services.SearchEngine.elasticsearchClient;

public class SearchServiceImpl implements SearchService {

    private final SearchEngine searchEngine;

    public SearchServiceImpl(SearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }

    @Override
    public Movie fetchMovieById(String id) throws RecordNotFoundException, IOException {
        return SearchEngine.fetchMovieById(id);
    }



    @Override
    public String insertMovie(Movie employee) throws IOException {
        return SearchEngine.insertMovie(employee);
    }

    @Override
    public boolean bulkInsertMovies(List<Movie> movies) throws IOException {
        return SearchEngine.bulkInsertMovies(movies);
    }

    @Override
    public List<Movie> fetchMoviesWithMustQuery(Movie movie) throws IOException {
        return SearchEngine.fetchMoviesWithMustQuery(movie);
    }

    @Override
    public List<Movie> fetchMoviesWithShouldQuery(Movie movie) throws IOException {
        return SearchEngine.fetchEMoviesWithShouldQuery(movie);
    }

    @Override
    public String deleteMovieById(Long id) throws IOException {
        return SearchEngine.deleteMovieById(id);
    }

    @Override
    public String updateMovie(Movie movie) throws IOException {
        return SearchEngine.updateMovie(movie);
    }
    @Override
    public void indexBulk(List<Movie> movies) throws IOException {
        return SearchEngine.indexbulk;



    @Override
    public void indexImdbData
        }
    }
}



