package co.empathy.academy.JavaClient.controlles;


import co.empathy.academy.JavaClient.exception.RecordNotFoundException;
import co.empathy.academy.JavaClient.model.Movie;
import co.empathy.academy.JavaClient.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController

public class SearchController {

        @Autowired
        private SearchService searchService;

        @GetMapping("/index/{id}")
        public ResponseEntity<Movie> fetchMovieById(@PathVariable("id") String id) throws RecordNotFoundException, IOException {
            Movie movie = searchService.fetchMovieById(id);
            return ResponseEntity.ok(movie);
        }

        @PostMapping("/index/fetchWithMust")
        public ResponseEntity<List<Movie>> fetchMoviesWithMustQuery(@RequestBody Movie movieSearchRequest) throws IOException {
            List<Movie> movies = searchService.fetchMoviesWithMustQuery(movieSearchRequest);
            return ResponseEntity.ok(movies);
        }

        @PostMapping("/index/fetchWithShould")
        public ResponseEntity<List<Movie>> fetchMoviesWithShouldQuery(@RequestBody Movie movieSearchRequest) throws IOException {
            List<Movie> movies = searchService.fetchMoviesWithShouldQuery(movieSearchRequest);
            return ResponseEntity.ok(movies);
        }

        @PostMapping("/index")
        public ResponseEntity<String> insertRecords(@RequestBody Movie movie) throws IOException {
            String status = searchService.insertMovie(movie);
            return ResponseEntity.ok(status);
        }

        @PostMapping("/index/bulk")
        public ResponseEntity<String> bulkInsertMovies(@RequestBody List<Movie> movies) throws IOException {
                boolean isSuccess = searchService.bulkInsertMovies(movies);
            if(isSuccess) {
                return ResponseEntity.ok("Records successfully ingested!");
            } else {
                return ResponseEntity.internalServerError().body("Oops! unable to ingest data");
            }
        }

        @DeleteMapping("/index/{id}")
        public ResponseEntity<String> deleteMovies(@PathVariable("id") Long id) throws IOException {
            String status = searchService.deleteMovieById(id);
            return ResponseEntity.ok(status);
        }

        @PutMapping("/index")
        public ResponseEntity<String> updateMovies(@RequestBody Movie movie) throws IOException {
            String status = searchService.updateMovie(movie);
            return ResponseEntity.ok(status);
        }
    }
