package org.example;

import org.example.entity.Participant;
import org.example.utils.RaceRecordReader;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        RaceRecordReader reader = new RaceRecordReader("/race-results.txt");
        Map<Integer, Participant> participants = reader.readRecords();

        Optional<Duration> minDuration = participants.values().stream()
                .filter(p -> p.races().size() == 3)
                .map(p -> p.races().stream()
                        .map(race -> Duration.between(LocalTime.MIN, race.time()))
                        .reduce(Duration::plus)
                        .orElseThrow())
                .min(Duration::compareTo);

        if (minDuration.isPresent()) {
            participants.values().stream()
                    .filter(p -> p.races().size() == 3)
                    .filter(p -> p.races().stream()
                            .map(race -> Duration.between(LocalTime.MIN, race.time()))
                            .reduce(Duration::plus)
                            .orElseThrow()
                            .equals(minDuration.get()))
                    .forEach(winnerParticipant -> {
                        System.out.println("The winner is " + winnerParticipant.name() + " with id " + winnerParticipant.id());

                        Duration totalDuration = winnerParticipant.races().stream()
                                .map(race -> Duration.between(LocalTime.MIN, race.time()))
                                .reduce(Duration::plus)
                                .orElseThrow();

                        long averageSeconds = totalDuration.getSeconds() / winnerParticipant.races().size();
                        long minutes = averageSeconds / 60;
                        long seconds = averageSeconds % 60;

                        System.out.println("The average race time for " + winnerParticipant.name() + " is: " + minutes + " minutes and " + seconds + " seconds");
                    });
        } else {
            System.out.println("No winner found");
        }
    }
}