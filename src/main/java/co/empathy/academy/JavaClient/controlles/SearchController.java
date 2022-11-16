package co.empathy.academy.JavaClient.controlles;


import co.empathy.academy.JavaClient.exception.BulkIndexException;
import co.empathy.academy.JavaClient.exception.RecordNotFoundException;
import co.empathy.academy.JavaClient.model.Movie;
import co.empathy.academy.JavaClient.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    public ResponseEntity indexImdbData(@RequestParam("basicsFile") MultipartFile basicsFile,
                                        @RequestParam("ratingsFile") MultipartFile ratingsFile,
                                        @RequestParam("akasFile") MultipartFile akasFile,
                                        @RequestParam("crewFile") MultipartFile crewFile) {
        try {
            searchService.indexImdbData(basicsFile, ratingsFile, akasFile, crewFile);
        } catch (BulkIndexException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.accepted().build();
    }


    }
