package co.empathy.academy.JavaClient.model;

import co.empathy.academy.JavaClient.model.Name;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Principals {
    Name name;
    String characters;
}
