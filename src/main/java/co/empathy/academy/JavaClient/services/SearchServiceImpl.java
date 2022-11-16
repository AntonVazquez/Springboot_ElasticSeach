package co.empathy.academy.JavaClient.services;

import co.empathy.academy.JavaClient.exception.BulkIndexException;
import co.empathy.academy.JavaClient.exception.RecordNotFoundException;
import co.empathy.academy.JavaClient.model.Movie;
import co.empathy.academy.JavaClient.connector.SearchEngine;
import co.empathy.academy.JavaClient.utils.IMDbReader;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

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
    public String deleteMovieById(Long id) throws IOException {
        return SearchEngine.deleteMovieById(id);
    }

    @Override
    public String updateMovie(Movie movie) throws IOException {
        return SearchEngine.updateMovie(movie);
    }

    @Override
    public void createIndex() throws IOException {
        searchEngine.createIndex();
        /*
        searchEngine.putSettings();
        searchEngine.putMapping();
        */
    }

    @Override
    public void indexImdbData(MultipartFile basicsFile, MultipartFile ratingsFile,
                              MultipartFile akasFile, MultipartFile crewFile) throws IOException, BulkIndexException {
        IMDbReader reader = new IMDbReader(basicsFile, ratingsFile, akasFile, crewFile);

        while (reader.hasDocuments()) {
            List<Movie> movies = reader.readDocuments();
            SearchEngine.indexbulkMovies(movies);
        }
    }

    @Override
    public void indexDocument(Movie movie) throws IOException {
        searchEngine.indexDocument(movie);
    }


}



