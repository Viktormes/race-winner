package org.example;

import org.example.entity.Participant;
import org.example.entity.Race;
import org.example.utils.RaceRecordReader;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        RaceRecordReader reader = new RaceRecordReader("/race-results.txt");
        Map<Integer, Participant> participants = reader.readRecords();

        Optional<Participant> winner = participants.values().stream()
                .filter(p -> p.races().size() == 3)
                .min((p1, p2) -> p1.races().stream()
                        .map(race -> Duration.between(LocalTime.MIN, race.time()))
                        .reduce(Duration::plus)
                        .orElseThrow()
                        .compareTo(
                                p2.races().stream()
                                        .map(race -> Duration.between(LocalTime.MIN, race.time()))
                                        .reduce(Duration::plus)
                                        .orElseThrow()
                        ));

        if (winner.isPresent()) {
            System.out.println("The winner is " + winner.get().name() + " with id " + winner.get().id());
        } else {
            System.out.println("No winner found");
        }
    }
}