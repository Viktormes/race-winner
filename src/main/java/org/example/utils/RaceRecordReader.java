package org.example.utils;

import org.example.entity.Participant;
import org.example.entity.Race;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RaceRecordReader {

    private final String filePath;
    private static Logger logger = Logger.getLogger(RaceRecordReader.class.getName());

    public RaceRecordReader(String filePath) {
        this.filePath = filePath;
    }

    public Map<Integer, Participant> readRecords() {
        Map<Integer, List<Race>> racesByParticipant = new HashMap<>();
        Map<Integer, String> namesByParticipant = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(filePath))))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length > 5) {
                    logger.log(Level.WARNING, "Ignoring line with more than 5 parts: " + line);
                    continue;
                }


                String name = parts[0];
                int id = Integer.parseInt(parts[1]);
                LocalTime startingTime = LocalTime.parse(parts[2]);
                LocalTime endingTime = LocalTime.parse(parts[3]);
                String raceType = parts[4];

                namesByParticipant.putIfAbsent(id, name);
                racesByParticipant.computeIfAbsent(id, k -> new ArrayList<>()).add(new Race(raceType, endingTime.minusHours(startingTime.getHour()).minusMinutes(startingTime.getMinute())));
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
}
