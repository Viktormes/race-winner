@echo off
call mvn clean install
call mvn exec:java