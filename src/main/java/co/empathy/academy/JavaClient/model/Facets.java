package co.empathy.academy.JavaClient.model;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.ArrayList;

@Value
@AllArgsConstructor
public class Facets {
    String type;
    String facet;
    ArrayList<FacetsValue> values;
}
