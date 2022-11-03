package co.empathy.academy.JavaClient.controlles;


import co.empathy.academy.JavaClient.services.SearchEngineImpl;
import co.empathy.academy.JavaClient.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController

public class SearchController {

    @Autowired
    private SearchService service;

    @GetMapping("/search")
    public int Response (@RequestParam("query") String query) throws IOException, InterruptedException {
        service= new SearchService(new SearchEngineImpl());
        return service.search(query);
    }
    @GetMapping("/search")
    public String search(@RequestParam String query) {

        String version = elasticService.performGETRequest("/");

        JSONObject json = new JSONObject(version);

        JSONObject theVersion = (JSONObject)json.get("version");

        return "{\"query\": " + query + ", \"clusterName\" : " + theVersion.get("number") + "}";
    }

}
