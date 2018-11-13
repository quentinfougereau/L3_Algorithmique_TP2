package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpellChecker {

    private String wordToCorrect;
    private Map<String, List<String>> trigrams = new HashMap<>();
    private Map<String, Integer> commonTrigramCounter = new HashMap<String, Integer>();
    //private ArrayList<CommonTrigramCounter> commonTrigramCounters = new ArrayList<>();

    public SpellChecker(String word) {
        this.wordToCorrect = word;
    }

    public String getWordToCorrect() {
        return this.wordToCorrect;
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

    public int levenshteinDistance(String firstWord, String secondWord) {
        int[][] matrice = new int[firstWord.length()][secondWord.length()];
        matrice[0][0] = 0;
        for (int i = 1; i < firstWord.length(); i++) {
            matrice[i][0] = i;
        }
        for (int i = 1; i < secondWord.length(); i++) {
            matrice[0][i] = i;
        }
        for (int i = 1; i < firstWord.length(); i++) {
            for (int j = 1; j < secondWord.length(); j++) {

                if (firstWord.charAt(i) == secondWord.charAt(j)) {
                    //on ajoute le minimum des cases autour à la matrice[i][j]
                    matrice[i][j] = matrice[i - 1][j - 1];
                } else {
                    //on ajoute le minimum des cases autour + 1 à la matrice[i][j]
                    int min = Math.min(matrice[i - 1][j], matrice[i][j - 1]);
                    matrice[i][j] = Math.min(min, matrice[i - 1][j - 1]) + 1;
                }
            }

        }
        return matrice[firstWord.length()-1][secondWord.length()-1];
    }

}
