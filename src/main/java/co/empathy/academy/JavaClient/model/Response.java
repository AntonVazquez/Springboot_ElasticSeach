package co.empathy.academy.JavaClient.model;

import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@NoArgsConstructor(force = true)
public class Response {

    List<Movie> hits;

}