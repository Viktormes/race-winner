package org.example.utils;

import org.example.entity.Participant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

    class RaceRecordReaderTest {

        private RaceRecordReader raceRecordReader;

        @BeforeEach
        void setUp() {
            raceRecordReader = new RaceRecordReader("/test-race-results.txt");
        }

        @Test
        void readRecordsAmountOfParticipants() {
            Map<Integer, Participant> participants = raceRecordReader.readRecords();

            assertEquals(3, participants.size());
        }

        @Test
        void readRecordsNoComma() {
            RaceRecordReader raceRecordReader = new RaceRecordReader("/test-race-results-no-comma.txt");
            Map<Integer, Participant> participants = raceRecordReader.readRecords();

            assertTrue(participants.isEmpty());
        }

        @Test
        void readRecordsLessThanFiveParts() {
            RaceRecordReader raceRecordReader = new RaceRecordReader("/test-race-results-less-than-five.txt");
            Map<Integer, Participant> participants = raceRecordReader.readRecords();

            assertTrue(participants.isEmpty());
        }

        @Test
        void readRecordsMoreThanFiveParts() {
            RaceRecordReader raceRecordReader = new RaceRecordReader("/test-race-results-more-than-five.txt");
            Map<Integer, Participant> participants = raceRecordReader.readRecords();

            assertTrue(participants.isEmpty());
        }

        @Test
        void readRecordsIdNotInteger() {
            RaceRecordReader raceRecordReader = new RaceRecordReader("/test-race-results-id-not-integer.txt");
            Map<Integer, Participant> participants = raceRecordReader.readRecords();

            assertTrue(participants.isEmpty());
        }

        @Test
        void readRecordsInvalidTimeFormat() {
            RaceRecordReader raceRecordReader = new RaceRecordReader("/test-race-results-invalid-time-format.txt");
            Map<Integer, Participant> participants = raceRecordReader.readRecords();

            assertTrue(participants.isEmpty());
        }

        @Test
        void readRecordsInvalidRaceType() {
            RaceRecordReader raceRecordReader = new RaceRecordReader("/test-race-results-invalid-race-type.txt");
            Map<Integer, Participant> participants = raceRecordReader.readRecords();

            assertTrue(participants.isEmpty());
        }
    }
