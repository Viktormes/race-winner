package org.example.utils;

import org.example.entity.Participant;
import org.example.entity.Race;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RaceRecordReader {

    private final String filePath;
    private static final Logger logger = Logger.getLogger(RaceRecordReader.class.getName());

    public RaceRecordReader(String filePath) {
        this.filePath = filePath;
    }

    public Map<Integer, Participant> readRecords() {
        Map<Integer, List<Race>> racesByParticipant = new HashMap<>();
        Map<Integer, String> namesByParticipant = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(filePath))))) {
            String line;
            while ((line = br.readLine()) != null) {
                parseLine(line, racesByParticipant, namesByParticipant);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file", e);
        }

        Map<Integer, Participant> participants = new HashMap<>();
        for (var entry : racesByParticipant.entrySet()) {
            participants.put(entry.getKey(), new Participant(namesByParticipant.get(entry.getKey()), entry.getKey(), entry.getValue()));
        }

        return participants;
    }

    private void parseLine(String line, Map<Integer, List<Race>> racesByParticipant, Map<Integer, String> namesByParticipant) {
        if (!line.contains(",")) {
            logger.log(Level.WARNING, "Ignoring line without commas: " + line);
            return;
        }
        String[] parts = line.split(",");

        if (parts.length <= 4) {
            logger.log(Level.WARNING, "Ignoring line with less than 5 parts: " + line);
            return;
        }

        if (parts.length > 5) {
            logger.log(Level.WARNING, "Ignoring line with more than 5 parts: " + line);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Ignoring line with invalid id: " + line);
            return;
        }

        String name = parts[0];

        LocalTime startingTime;
        LocalTime endingTime;
        try {
            startingTime = LocalTime.parse(parts[2]);
            endingTime = LocalTime.parse(parts[3]);
        } catch (DateTimeParseException e) {
            logger.log(Level.WARNING, "Ignoring line with invalid time format: " + line);
            return;
        }

        String raceType = parts[4];

        if (!raceType.equals("eggRace") && !raceType.equals("sackRace") && !raceType.equals("1000m")) {
            logger.log(Level.WARNING, "Ignoring line with invalid race type: " + line);
            return;
        }

        namesByParticipant.putIfAbsent(id, name);
        racesByParticipant.computeIfAbsent(id, k -> new ArrayList<>()).add(createRace(raceType, startingTime, endingTime));
    }

    private Race createRace(String raceType, LocalTime startingTime, LocalTime endingTime) {
        return new Race(raceType, endingTime.minusHours(startingTime.getHour()).minusMinutes(startingTime.getMinute()));
    }
}