package com.company;

public class CommonTrigramCounter implements Comparable<CommonTrigramCounter> {

    private String word;
    private int occurrenceCounter;

    public CommonTrigramCounter(String word, int occurrenceCounter) {
        this.word = word;
        this.occurrenceCounter = occurrenceCounter;
    }

    @Override
    public int compareTo(CommonTrigramCounter ctc1) {
        return this.occurrenceCounter - ctc1.occurrenceCounter;
    }

    public void incrementOccurrenceCounter() {
        this.occurrenceCounter++;
    }

}
