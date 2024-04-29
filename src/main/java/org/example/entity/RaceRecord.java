package org.example.entity;

import java.time.LocalTime;

public record RaceRecord(String name, int id, LocalTime startingTime, LocalTime endingTime, String raceType) {}
