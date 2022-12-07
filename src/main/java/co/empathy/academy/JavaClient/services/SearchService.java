package co.empathy.academy.JavaClient.services;


import co.empathy.academy.JavaClient.exception.BulkIndexException;
import co.empathy.academy.JavaClient.model.Movie;
import co.empathy.academy.JavaClient.model.Response;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@Service
public interface SearchService {





       void createIndex() throws IOException;



    void indexImdbData(MultipartFile basicsFile, MultipartFile ratingsFile,
                       MultipartFile akasFile, MultipartFile crewFile) throws IOException, BulkIndexException;

    void indexDocument(Movie movie) throws IOException;

    Response searchQuery(String query) throws IOException;



    List<Movie> searchIndex(String indexName, String body) throws JSONException;


    List<Movie> search(Optional<String> genre, Optional<Integer> maxYear, Optional<Integer> minYear, Optional<Integer> maxMinutes, Optional<Integer> minMinutes, Optional<Double> maxScore, Optional<Double> minScore, Optional<String> type);

    List<Movie> search(Optional<String> genre, Optional<Integer> maxYear, Optional<Integer> minYear,
                       Optional<Integer> maxMinutes, Optional<Integer> minMinutes,
                       Optional<Double> maxScore, Optional<Double> minScore, Optional<Integer> maxNHits, Optional<String> type);


    /*
    @Override
    public Object filterQuery(String titleType, String type) {
        return null;
    }

     */






}
