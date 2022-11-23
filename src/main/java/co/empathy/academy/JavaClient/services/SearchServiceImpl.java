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
    public void createIndex() throws IOException {
        searchEngine.createIndex();
        searchEngine.putSettings();
        searchEngine.putMapping();

    }

    @Override
    public void indexImdbData(MultipartFile basicsFile, MultipartFile ratingsFile, MultipartFile akasFile, MultipartFile crewFile) throws IOException, BulkIndexException {

    }


    @Override
    public void indexDocument(Movie movie) throws IOException {
        searchEngine.indexDocument(movie);
    }
    /*
    @Override
    public Object filterQuery(String titleType, String type) {
        return null;
    }

     */


}



