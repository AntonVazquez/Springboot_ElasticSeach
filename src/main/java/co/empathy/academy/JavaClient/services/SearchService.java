package co.empathy.academy.JavaClient.services;


import co.empathy.academy.JavaClient.exception.BulkIndexException;
import co.empathy.academy.JavaClient.exception.RecordNotFoundException;
import co.empathy.academy.JavaClient.model.Movie;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SearchService {


        Movie fetchMovieById(String id) throws RecordNotFoundException, IOException;


        String insertMovie(Movie employee) throws IOException;

        boolean bulkInsertMovies(List<Movie> movies) throws IOException;

        public List<Movie> fetchMoviesWithMustQuery(Movie movie) throws IOException;
        public List<Movie> fetchMoviesWithShouldQuery(Movie movie) throws IOException;

        public String deleteMovieById(Long id) throws IOException;

        public String updateMovie(Movie movie) throws IOException;

        void indexBulk(List<Movie> movies) throws IOException, BulkIndexException;

        void indexImdbData(MultipartFile basicsFile, MultipartFile ratingsFile) throws IOException, BulkIndexException;
}
