package org.example.utils;

import org.example.entity.Participant;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

    class RaceRecordReaderTest {

        private RaceRecordReader raceRecordReader;

      public void setUp() {
            raceRecordReader = new RaceRecordReader("/test-race-results.txt");
        }

        public static void main(String[] args) {
            RaceRecordReaderTest test = new RaceRecordReaderTest();
            test.readRecordsAmountOfParticipants();
            test.readRecordsNoComma();
            test.readRecordsLessThanFiveParts();
            test.readRecordsMoreThanFiveParts();
            test.readRecordsIdNotInteger();
            test.readRecordsInvalidTimeFormat();
            test.readRecordsInvalidRaceType();

        }

        public void readRecordsAmountOfParticipants() {
            setUp();
            Map<Integer, Participant> participants = raceRecordReader.readRecords();

            assert participants.size() == 3 : "Expected 3 participants, but got " + participants.size();
        }

        public void readRecordsNoComma() {
            RaceRecordReader raceRecordReader = new RaceRecordReader("/test-race-results-no-comma.txt");
            Map<Integer, Participant> participants = raceRecordReader.readRecords();

            assert participants.isEmpty() : "Expected no participants, but got " + participants.size();
        }


        public void readRecordsLessThanFiveParts() {
            RaceRecordReader raceRecordReader = new RaceRecordReader("/test-race-results-less-than-five.txt");
            Map<Integer, Participant> participants = raceRecordReader.readRecords();

            assert participants.isEmpty() : "Expected no participants, but got " + participants.size();
        }


        public void readRecordsMoreThanFiveParts() {
            RaceRecordReader raceRecordReader = new RaceRecordReader("/test-race-results-more-than-five.txt");
            Map<Integer, Participant> participants = raceRecordReader.readRecords();

            assert participants.isEmpty() : "Expected no participants, but got " + participants.size();
        }


        public void readRecordsIdNotInteger() {
            RaceRecordReader raceRecordReader = new RaceRecordReader("/test-race-results-id-not-integer.txt");
            Map<Integer, Participant> participants = raceRecordReader.readRecords();

            assert participants.isEmpty() : "Expected no participants, but got " + participants.size();
        }


        public void readRecordsInvalidTimeFormat() {
            RaceRecordReader raceRecordReader = new RaceRecordReader("/test-race-results-invalid-time-format.txt");
            Map<Integer, Participant> participants = raceRecordReader.readRecords();

            assert participants.isEmpty() : "Expected no participants, but got " + participants.size();
        }


        public void readRecordsInvalidRaceType() {
            RaceRecordReader raceRecordReader = new RaceRecordReader("/test-race-results-invalid-race-type.txt");
            Map<Integer, Participant> participants = raceRecordReader.readRecords();

            assert participants.isEmpty() : "Expected no participants, but got " + participants.size();
        }
    }
