package co.empathy.academy.JavaClient.utils;


import co.empathy.academy.JavaClient.model.Movie;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class IMDbReader {

    private final BufferedReader basicsReader;
    private final BufferedReader ratingsReader;

    private final int documentsSize = 20000;

    private boolean hasDocuments = true;


    public IMDbReader(MultipartFile basicsFile, MultipartFile ratingsFile) {
        try {
            this.basicsReader = new BufferedReader(new InputStreamReader(basicsFile.getInputStream()));
            this.ratingsReader = new BufferedReader(new InputStreamReader(ratingsFile.getInputStream()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        readHeaders();
    }

    private void readHeaders() {
        try {
            basicsReader.readLine();
            ratingsReader.readLine();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Movie> readDocuments() {
        List<Movie> result = new ArrayList<>();
        int currentLinesRead = 0;

        String ratingsLine = null;
        try {
            ratingsLine = ratingsReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (currentLinesRead < documentsSize) {
            try {
                String basicsLine = basicsReader.readLine();

                if (basicsLine == null) {
                    this.hasDocuments = false;
                    return result;
                }

                String[] basics = basicsLine.split("\t");
                String[] ratings = null;
                if (ratingsLine != null) {
                    ratings = ratingsLine.split("\t");
                }
                double averageRating = 0.0;
                int numVotes = 0;

                if (!ratings[0].contentEquals(basics[0])) {
                    //Do nothing
                } else {
                    averageRating = StringDoubleConversion.toDouble(ratings[1]);
                    numVotes = StringIntegerConversion.toInt(ratings[2]);
                    ratingsLine = ratingsReader.readLine();
                }



                Movie movie = new Movie(basics[0], basics[1], basics[2], basics[3], basics[4].contentEquals("1"),
                        StringIntegerConversion.toInt(basics[5]), StringIntegerConversion.toInt(basics[6]),
                        StringIntegerConversion.toInt(basics[7]), StringArrayConversion.toArray(basics[8]),
                        averageRating, numVotes, akas, directors);

                result.add(movie);

                currentLinesRead++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }


    public boolean hasDocuments() {
        return hasDocuments;
    }
}
