package org.example.entity;

import java.util.List;

public record Participant(String name, int id, List<Race> races) {}
