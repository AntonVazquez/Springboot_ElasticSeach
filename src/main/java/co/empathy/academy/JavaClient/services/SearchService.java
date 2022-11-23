package co.empathy.academy.JavaClient.services;


import co.empathy.academy.JavaClient.exception.BulkIndexException;
import co.empathy.academy.JavaClient.exception.RecordNotFoundException;
import co.empathy.academy.JavaClient.model.Movie;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SearchService {





       void createIndex() throws IOException;

        void indexImdbData(MultipartFile basicsFile, MultipartFile ratingsFile,
                           MultipartFile akasFile, MultipartFile crewFile) throws IOException, BulkIndexException;

        void indexDocument(Movie movie) throws IOException;

/*
    Object getQuery(Filters filter);

    Object getSearchQuery(String searchText);

 */
}
