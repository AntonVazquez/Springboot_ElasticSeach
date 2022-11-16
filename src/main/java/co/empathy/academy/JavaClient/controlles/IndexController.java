package co.empathy.academy.JavaClient.controlles;

import co.empathy.academy.JavaClient.exception.BulkIndexException;
import co.empathy.academy.JavaClient.model.Movie;
import co.empathy.academy.JavaClient.services.SearchService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/index")
public class IndexController {

    private final SearchService searchService;

    public IndexController(SearchService searchService) {
        this.searchService = searchService;
    }


    @PutMapping("/create")
    public ResponseEntity<String> createIndex() {
        try {
            searchService.createIndex();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error creating index");
        }

        return ResponseEntity.ok("Index created");
    }


    @PostMapping("/{indexName}")
    public ResponseEntity<Movie> indexDocument(@RequestBody Movie movie) {
        try {
            searchService.indexDocument(movie);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.created(null).body(movie);
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