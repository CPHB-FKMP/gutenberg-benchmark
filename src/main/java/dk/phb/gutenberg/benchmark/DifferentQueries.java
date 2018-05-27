package dk.phb.gutenberg.benchmark;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DifferentQueries {
    public static void main(String[] args) throws SQLException {
         NEO4JImpl neo = new NEO4JImpl();
        PostgresqlImpl pos = new PostgresqlImpl();
        
        int times = 10;
        
        String[] cities = {
            "Copenhagen", 
            "Greve", 
            "London", 
            "Milton", 
            "Venice"};
        String[] titles = {
            "War in the Garden of Eden",
            "La Boheme",
            "Social Value A Study in Economic Theory Critical and Constructive",
            "North America",
            "Hesiod, The Homeric Hymns, and Homerica"
        };
        String[] authors = {
            "Georgiana Cavendish",
            "Lilian Garis",
            "Maxime Provost",
            "James Cook",
            "Kermit Roosevelt"
        };
        
        String[][] locations = {
            {"34.366500", "-89.519250"},
            {"42.440630", "-76.496610"},
            {"34.802430", "-86.972190"},
            {"43.983330", "25.333330"},
            {"42.728410", "-73.691790"}
        };
        
        System.out.println("NEO4J QUERIES");
        System.out.println("----------------------------------------------------------------");
        System.out.println("Neo4J Cities");
        for (String city : cities) {
            List<Double> results = new ArrayList<>();
            for (int i = 0; i < times; i++) {
                neo.getBooksByCity(city, results);  
            }
            System.out.println("Get all books that mention " + city);
            System.out.println("Average time in milliseconds for this Query: " + neo.getAverage(results));
            System.out.println("Median time in milliseconds for this Query: " + neo.getMedian(results));
            System.out.println("----------------------------------------------------------------");
        }
        System.out.println("Neo4J Titles");
        for (String title : titles) {
            List<Double> results = new ArrayList<>();
            for (int i = 0; i < times; i++) {
                neo.getAllCitiesInBook(title, results);  
            }
            System.out.println("get all cities mentioned in " + title);
            System.out.println("Average time in milliseconds for this Query: " + neo.getAverage(results));
            System.out.println("Median time in milliseconds for this Query: " + neo.getMedian(results));
            System.out.println("----------------------------------------------------------------");
        }
        System.out.println("Neo4J Authors");     
        for (String author : authors) {
            List<Double> results = new ArrayList<>();
            for (int i = 0; i < times; i++) {
                neo.getBooksAndCitiesByAuthor(author, results);  
            }
            System.out.println("Get all books and the cities they mention by author " + author);
            System.out.println("Average time in milliseconds for this Query: " + neo.getAverage(results));
            System.out.println("Median time in milliseconds for this Query: " + neo.getMedian(results));
            System.out.println("----------------------------------------------------------------");
        }
        System.out.println("Neo4J Locations");
        for (String[] location : locations) {
            List<Double> results = new ArrayList<>();
            for (int i = 0; i < times; i++) {
                neo.getAllBooksWrittenNearby(location[0], location[1], results);
            }
            System.out.println("Get all books that mention a city Nearby latitude : " + location[0] + " Longitude : " + location[1] );
            System.out.println("Average time in milliseconds for this Query: " + neo.getAverage(results));
            System.out.println("Median time in milliseconds for this Query: " + neo.getMedian(results));
            System.out.println("----------------------------------------------------------------");
            
        }

        System.out.println("POSTGRESQL QUERIES");
        System.out.println("----------------------------------------------------------------");
        System.out.println("Postgres Cities");
        for (String city : cities) {
            List<Double> results = new ArrayList<>();
            for (int i = 0; i < times; i++) {
                pos.GetBooksByCity(city, results);  
            }
            System.out.println("Get all books that mention " + city);
            System.out.println("Average time in milliseconds for this Query: " + neo.getAverage(results));
            System.out.println("Median time in milliseconds for this Query: " + neo.getMedian(results));
            System.out.println("----------------------------------------------------------------");
        }
        System.out.println("Postgres Titles");
        for (String title : titles) {
            List<Double> results = new ArrayList<>();
            for (int i = 0; i < times; i++) {
                pos.GetAllcitiesInBook(title, results);  
            }
            System.out.println("get all cities mentioned in " + title);
            System.out.println("Average time in milliseconds for this Query: " + neo.getAverage(results));
            System.out.println("Median time in milliseconds for this Query: " + neo.getMedian(results));
            System.out.println("----------------------------------------------------------------");
        }
        System.out.println("Postgres Authors");     
        for (String author : authors) {
            List<Double> results = new ArrayList<>();
            for (int i = 0; i < times; i++) {
                pos.GetBooksAndCitiesByAuthor(author, results);  
            }
            System.out.println("Get all books and the cities they mention by author name");
            System.out.println("Average time in milliseconds for this Query: " + neo.getAverage(results));
            System.out.println("Median time in milliseconds for this Query: " + neo.getMedian(results));
            System.out.println("----------------------------------------------------------------");
        }
        System.out.println("Postgres Locations");
        for (String[] location : locations) {
            List<Double> results = new ArrayList<>();
            for (int i = 0; i < times; i++) {
                pos.GetAllBooksWrittenNearby(location[0], location[1], results);
            }
            System.out.println("Get all books that mention a city Nearby latitude : " + location[0] + " Longitude : " + location[1] );
            System.out.println("Average time in milliseconds for this Query: " + neo.getAverage(results));
            System.out.println("Median time in milliseconds for this Query: " + neo.getMedian(results));
            System.out.println("----------------------------------------------------------------");
            
        }
    }

}

