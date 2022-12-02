package co.empathy.academy.JavaClient.model;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class FacetsValue {
    String id;
    String value;
    Long count;
    String filter;
}