package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpellChecker {

    private String wordToCorrect;
    private Map<String, List<String>> trigrams = new HashMap<>();
    private Map<String, Integer> commonTrigramCounter = new HashMap<String, Integer>();

    public SpellChecker(String word) {
        this.wordToCorrect = word;
    }

    public void fillTrigrams(String currentWord) {
        if (currentWord.length() < 3)
            return;

        currentWord = "<" + currentWord + ">";
        for (int i = 2; i < currentWord.length(); i++) {
            String trigram = "" + currentWord.charAt(i - 2) + currentWord.charAt(i - 1) + currentWord.charAt(i);
            if (!trigrams.containsKey(trigram)) {
                List<String> wordsList = new ArrayList<>();
                wordsList.add(currentWord);
                trigrams.put(trigram, wordsList);
            } else {
                trigrams.get(trigram).add(currentWord);
            }
        }
    }

    public Map<String, List<String>> getTrigrams() {
        return trigrams;
    }

    public void fillCommonTrigramCounter() {
        for (Map.Entry<String, List<String>> entry : trigrams.entrySet()) {
            if (entry.getValue().contains(wordToCorrect)) {
                for(String word : entry.getValue()) {
                    if (commonTrigramCounter.containsKey(word)) {
                        Integer counter = commonTrigramCounter.get(word);
                        counter++;
                        commonTrigramCounter.put(word, counter);
                    } else if (!word.equals(wordToCorrect)) {
                        commonTrigramCounter.put(word, 1);
                    }
                }
            }
        }
    }

    public Map<String, Integer> getCommonTrigramCounter() {
        return commonTrigramCounter;
    }
}
