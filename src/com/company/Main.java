package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        long startedTime0 = System.nanoTime();
        String pathName = "/home/quentin/IdeaProjects/Algorithmique_TP2/src/com/company/dico.txt";

        SpellChecker spellChecker = new SpellChecker();


        try {
            long startedTime = System.nanoTime();
            Scanner scanner = new Scanner(new File(pathName));
            while (scanner.hasNextLine()) {
                String currentWord = scanner.nextLine();
                spellChecker.fillTrigrams(currentWord);
            }
            System.out.println("TIME TO FILL TRIGRAMS LIST : " + (System.nanoTime() - startedTime) / 1000000000.0 + " secondes");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Scanner scanner0 = new Scanner(new File("src/com/company/fautes.txt"));

            while (scanner0.hasNextLine()) {

                long startedTime = System.nanoTime();
                String wrongWord = scanner0.nextLine();

                spellChecker.setWordToCorrect("<" + wrongWord + ">");
                spellChecker.fillWtcTrigrams();

                spellChecker.fillCommonTrigramCounter();


                int size = 100;
                if (spellChecker.getCommonTrigramCounter().size() < 100) {
                    size = spellChecker.getCommonTrigramCounter().size();
                }

                Map<String, Integer> wordsDistances = new HashMap<>();
                int distance = 0;

                for (int i = 0; i < size; i++) {
                    Map.Entry<String, Integer> maxEntry = null;

                    for (Map.Entry<String, Integer> entry : spellChecker.getCommonTrigramCounter().entrySet()) {
                        if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                            maxEntry = entry;
                        }
                    }

                    spellChecker.getCommonTrigramCounter().remove(maxEntry.getKey());
                    distance = spellChecker.levenshteinDistance(spellChecker.getWordToCorrect(), maxEntry.getKey());
                    wordsDistances.put(maxEntry.getKey(), distance);
                }
                spellChecker.getCommonTrigramCounter().clear();


                ArrayList<String> nearestWords = new ArrayList<>();

                int limit = 5;
                if (wordsDistances.size() < 5) {
                    limit = wordsDistances.size();
                }

                System.out.println("********* " + spellChecker.getWordToCorrect() + "*********");
                //System.out.println("Nearest words : ");
                for (int i = 0; i < limit; i++) {
                    Map.Entry<String, Integer> minEntry = null;
                    for (Map.Entry<String, Integer> entry : wordsDistances.entrySet()) {
                        if (minEntry == null || entry.getValue().compareTo(minEntry.getValue()) < 0) {
                            minEntry = entry;
                        }
                    }
                    wordsDistances.remove(minEntry.getKey());
                    nearestWords.add(minEntry.getKey());
                    System.out.println(minEntry.getKey());
                }
                wordsDistances.clear();
                nearestWords.clear();
                System.out.println("TIME : " + (System.nanoTime() - startedTime) / 1000000000.0 + " secondes");

                spellChecker.clearCommonTrigramCounter();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("TOTAL TIME : " + (System.nanoTime() - startedTime0) / 1000000000.0 + " secondes");

    }
}
