package co.empathy.academy.JavaClient.model;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Aka {

    String title;
    String region;
    String language;
    Boolean isOriginalTitle;
}


