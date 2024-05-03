package org.example.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class RaceAnalyzerTest {

    private RaceAnalyzer raceAnalyzerMock;

    public void setUp() {
        raceAnalyzerMock = new RaceAnalyzer();
    }

    public static void main(String[] args) {
        RaceAnalyzerTest test = new RaceAnalyzerTest();
        test.analyzeRaces();
        test.analyzeRacesNoWinner();


}

public void analyzeRaces() {
    setUp();
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    raceAnalyzerMock.analyzeRaces("/race-results.txt");

    String expectedOutput = "The winner is Marilyn Monroe with id 624586\n" +
            "The average race time for the Marilyn Monroe is: 5 minutes and 18 seconds";
    assert outContent.toString().equals(expectedOutput) : "Expected output: " + expectedOutput + ", but got: " + outContent.toString();
}

public void analyzeRacesNoWinner() {
    setUp();
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    raceAnalyzerMock.analyzeRaces("/test-race-results-no-comma.txt");

    String expectedOutput = "No winner found";
    assert outContent.toString().equals(expectedOutput) : "Expected output: " + expectedOutput + ", but got: " + outContent.toString();

}
    static {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }
}

