package co.empathy.academy.JavaClient.services;

import co.empathy.academy.JavaClient.model.MyResponse;
import net.minidev.json.JSONObject;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;

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
        // Creates GET request to elasticSearch
        MyResponse response = client.performRequest(new Request("GET","/"));
        // Retrieves info from cluster
        String responseBody = EntityUtils.toString(response.getEntity());
        JSONObject json = new JSONObject(responseBody);
        JSONObject version = json.getJSONObject("version");
        // Gets current version from JSON response
        String numberVersion = version.getString("number");

        return numberVersion;
    }

}
