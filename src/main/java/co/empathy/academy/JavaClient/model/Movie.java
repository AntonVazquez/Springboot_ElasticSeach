package co.empathy.academy.JavaClient.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Movie {

        private Long id;
        private String tconst;
        private String titleType;
        private String primaryTitle;
        private String origilTtle;
        private boolean isAdult;
        private String startYear;
        private String endYear;
        private int runTimeMinutes;
        private String generes;
        private Integer size;



}
