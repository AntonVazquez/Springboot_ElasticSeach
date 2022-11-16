package co.empathy.academy.JavaClient.connector;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;

import co.empathy.academy.JavaClient.exception.BulkIndexException;
import co.empathy.academy.JavaClient.exception.RecordNotFoundException;
import co.empathy.academy.JavaClient.model.Movie;
import co.empathy.academy.JavaClient.utils.IMDbReader;
import co.empathy.academy.JavaClient.utils.QueryBuilderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SearchEngine {

    @Value("${elastic.index}")
    private static String index;

    @Autowired
    public static ElasticsearchClient elasticsearchClient;


    public static String insertMovie(Movie movie) throws IOException {
        IndexRequest<Movie> request = IndexRequest.of(i->
                i.index(index)
                        .id(String.valueOf(movie.getTconst()))
                        .document(movie));
        IndexResponse response = elasticsearchClient.index(request);
        return response.result().toString();
    }

    public static boolean bulkInsertMovies(List<Movie> movieList) throws IOException {
        BulkRequest.Builder builder = new BulkRequest.Builder();
        movieList.stream().forEach(movie ->
                builder.operations(op->
                        op.index(i->
                                i.index(index)
                                        .id(String.valueOf(movie.getTconst()))
                                        .document(movie)))
        );
        BulkResponse bulkResponse = elasticsearchClient.bulk(builder.build());
        return !bulkResponse.errors();
    }

    public static Movie fetchMovieById(String id) throws RecordNotFoundException, IOException {
        GetResponse<Movie> response = elasticsearchClient.get(req->
                req.index(index)
                        .id(id),Movie.class);
        if(!response.found())
            throw new RecordNotFoundException("Movie with ID" + id + " not found!");

        return response.source();
    }


    public static String deleteMovieById(Long id) throws IOException {
        DeleteRequest request = DeleteRequest.of(req->
                req.index(index).id(String.valueOf(id)));
        DeleteResponse response = elasticsearchClient.delete(request);
        return response.result().toString();
    }

    public static String updateMovie(Movie movie) throws IOException {
        UpdateRequest<Movie, Movie> updateRequest = UpdateRequest.of(req->
                req.index(index)
                        .id(String.valueOf(movie.getTconst()))
                        .doc(movie));
        UpdateResponse<Movie> response = elasticsearchClient.update(updateRequest, Movie.class);
        return response.result().toString();
    }


    public void createIndex() throws IOException {
        try {
            elasticsearchClient.indices().delete(d -> d.index("imdb"));
        } catch (Exception e) {
            // Ignore
        }
        elasticsearchClient.indices().create(c -> c.index("imdb"));
    }



    public static void indexbulkMovies(List<Movie> movies) throws IOException, BulkIndexException {
        BulkRequest.Builder request = new BulkRequest.Builder();

        movies.forEach(movie -> request.operations(op -> op
                .index(i -> i
                        .index("imdb")
                        .id(movie.getTconst())
                        .document(movie))));

        BulkResponse bulkResponse = elasticsearchClient.bulk(request.build());
        if (bulkResponse.errors()) {
            throw new BulkIndexException("Error indexing bulk");
        }
    }


    public void indexDocument(Movie movie) throws IOException {
        elasticsearchClient.index(i -> i
                .index("imdb")
                .id(movie.getTconst())
                .document(movie));
    }
}