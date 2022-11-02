package co.empathy.academy.JavaClient.services;


import co.empathy.academy.JavaClient.model.MyResponse;

import java.io.IOException;

public interface SearchEngine {

    int search(String query);
    MyResponse searchQuery(String query) throws IOException;
}
