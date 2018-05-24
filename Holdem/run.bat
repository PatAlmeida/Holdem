@ECHO OFF
cls
javac -d bin src/*.java
java -cp bin Game
