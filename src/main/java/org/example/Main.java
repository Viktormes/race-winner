package org.example;

import org.example.utils.RaceAnalyzer;

public class Main {
    public static void main(String[] args) {
        RaceAnalyzer raceAnalyzer = new RaceAnalyzer();
        raceAnalyzer.analyzeRaces("/race-results.txt");
    }
}