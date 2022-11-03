package co.empathy.academy.JavaClient.services;

import co.empathy.academy.JavaClient.model.MyResponse;


import java.io.IOException;

public class SearchServiceImpl implements SearchService {

    private final SearchEngine searchEngine;

    public SearchServiceImpl(SearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }

    @Override
    public int search(String query) {
        return searchEngine.search(query);
    }

    @Override
    public MyResponse searchQuery(String query) throws IOException {
        return searchEngine.searchQuery(query);
    }

    @Override
    public String getElasticVersion() throws IOException {
        return searchEngine.getElasticVersion();
    }


}
