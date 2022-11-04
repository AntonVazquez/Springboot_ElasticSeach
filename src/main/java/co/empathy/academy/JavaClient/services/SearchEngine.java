package co.empathy.academy.JavaClient.services;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;

import co.empathy.academy.JavaClient.exception.RecordNotFoundException;
import co.empathy.academy.JavaClient.model.Movie;
import co.empathy.academy.JavaClient.utils.QueryBuilderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
    private static ElasticsearchClient elasticsearchClient;

    public static String insertMovie(Movie movie) throws IOException {
        IndexRequest<Movie> request = IndexRequest.of(i->
                i.index(index)
                        .id(String.valueOf(movie.getId()))
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
                                        .id(String.valueOf(movie.getId()))
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

    public static List<Movie> fetchMoviesWithMustQuery(Movie movie) throws IOException {
        List<Query> queries = prepareQueryList(movie);
        SearchResponse<Movie> employeeSearchResponse = elasticsearchClient.search(req->
                        req.index(index)
                                .size(movie.getSize())
                                .query(query->
                                        query.bool(bool->
                                                bool.must(queries))),
                Movie.class);

        return employeeSearchResponse.hits().hits().stream()
                .map(Hit::source).collect(Collectors.toList());
    }

    public static List<Movie> fetchEMoviesWithShouldQuery(Movie movie) throws IOException {
        List<Query> queries = prepareQueryList(movie);
        SearchResponse<Movie> employeeSearchResponse = elasticsearchClient.search(req->
                        req.index(index)
                                .size(movie.getSize())
                                .query(query->
                                        query.bool(bool->
                                                bool.should(queries))),
                Movie.class);

        return employeeSearchResponse.hits().hits().stream()
                .map(Hit::source).collect(Collectors.toList());
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
                        .id(String.valueOf(movie.getId()))
                        .doc(movie));
        UpdateResponse<Movie> response = elasticsearchClient.update(updateRequest, Movie.class);
        return response.result().toString();
    }


    private static List<Query> prepareQueryList(Movie movie) {
        Map<String, String> conditionMap = new HashMap<>();
        conditionMap.put("tcost.keyword", movie.getTconst());
        conditionMap.put("titleTipe.keyword", movie.getTitleType());
        conditionMap.put("primaryTitle.keyword", movie.getPrimaryTitle());
        conditionMap.put("originalTitle.keyword", movie.getOrigilTtle());
        conditionMap.put("startYear.keyword", movie.getStartYear());
        conditionMap.put("endYear.keyword", movie.getEndYear());
        conditionMap.put("runtimeMinutes.Keyword", String.valueOf(movie.getRunTimeMinutes()));
        conditionMap.put("genres.keyword", movie.getGeneres());

        return conditionMap.entrySet().stream()
                .filter(entry->!ObjectUtils.isEmpty(entry.getValue()))
                .map(entry-> QueryBuilderUtils.termQuery(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }



}