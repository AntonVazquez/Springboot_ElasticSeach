package co.empathy.academy.JavaClient.services;


import co.empathy.academy.JavaClient.model.MyResponse;

import java.io.IOException;

public interface SearchService {

    int search(String query);
    MyResponse searchQuery(String query) throws IOException;

    String getElasticVersion() throws IOException;
}
