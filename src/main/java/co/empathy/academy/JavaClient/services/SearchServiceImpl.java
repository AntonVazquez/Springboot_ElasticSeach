package co.empathy.academy.JavaClient.services;

import co.empathy.academy.JavaClient.connector.SearchEngine;
import co.empathy.academy.JavaClient.exception.BulkIndexException;
import co.empathy.academy.JavaClient.model.Movie;
import co.empathy.academy.JavaClient.model.Response;
import co.empathy.academy.JavaClient.utils.IMDbReader;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class SearchServiceImpl implements SearchService {

    @Autowired
    private final SearchEngine searchEngine;




    public SearchServiceImpl(SearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }


    @Override
    public void createIndex() throws IOException {
        searchEngine.createIndex();
        searchEngine.putSettings();
        searchEngine.putMapping();

    }

    @Override
    public void indexImdbData(MultipartFile basicsFile, MultipartFile ratingsFile, MultipartFile akasFile, MultipartFile crewFile, MultipartFile principalsFile) throws IOException, BulkIndexException {
        IMDbReader reader = new IMDbReader(basicsFile, ratingsFile,akasFile, crewFile, principalsFile );
    }


    @Override
    public void indexDocument(Movie movie) throws IOException {
        searchEngine.indexDocument(movie);
    }

    @Override
    public Response searchQuery(String query) throws IOException {
        return null;
    }
    /*
    @Override
    public Object filterQuery(String titleType, String type) {
        return null;
    }

     */










    /**
     * Makes a query to a specific index
     * @param indexName : index where the search is going to take place
     * @param body : settings of the query
     * @return : SimpleResponse
     */
    @Override
    public List<Movie> searchIndex(String indexName, String body) throws JSONException {
        return searchEngine.searchIndex(indexName, body);
    }

    @Override
    public List<Movie> search(Optional<String> genre, Optional<Integer> maxYear, Optional<Integer> minYear, Optional<Integer> maxMinutes, Optional<Integer> minMinutes, Optional<Double> maxScore, Optional<Double> minScore, Optional<String> type) {
        return null;
    }

    /**
     * Custom search that filters with given parameters
     * @param genre : genre(s) of the movie
     * @param maxYear : upper range of the year interval
     * @param minYear : lower range of the year interval
     * @param maxMinutes : upper range of the runtimeMinutes interval
     * @param minMinutes : lower range of the runtimeMinutes interval
     * @param maxScore : upper range of the rate interval
     * @param minScore : lower range of the rate interval
     * @param type : if it's a movie, short or tv-series
     * @return : List of movies that satisfy the custom search
     */
    @Override
    public List<Movie> search(Optional<String> genre, Optional<Integer> maxYear, Optional<Integer> minYear,
                              Optional<Integer> maxMinutes, Optional<Integer> minMinutes,
                              Optional<Double> maxScore, Optional<Double> minScore, Optional<Integer> maxNHits, Optional<String> type) {
        return searchEngine.search(genre, maxYear, minYear, maxMinutes, minMinutes, maxScore, minScore, maxNHits, type);
    }




}




