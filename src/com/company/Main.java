package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        List<String> words = new ArrayList();
        //Map<K, V>
        Map<String, List<String>> trigrams = new HashMap<>();
        SpellChecker spellChecker = new SpellChecker("<acalmie>");
        spellChecker.fillTrigrams("acalmie");
        String pathName = "/home/quentin/IdeaProjects/Algorithmique_TP2/src/com/company/dico.txt";

        int counter = 0;
        try {
            Scanner scanner = new Scanner(new File(pathName));
            while (scanner.hasNextLine()) {
                String currentWord = scanner.nextLine();
                /*
                if (word.length() >= 3) {
                    word = "<" + word + ">";
                    for (int i = 2; i < word.length(); i++) {
                        //Création du dictionnaire trigrammes <trigramme, <mots>>
                        String trigram = "" + word.charAt(i - 2) + word.charAt(i - 1) + word.charAt(i);
                        if (!trigrams.containsKey(trigram)) {
                            List<String> wordsList = new ArrayList<>();
                            wordsList.add(word);
                            trigrams.put(trigram, wordsList);
                        } else {
                            trigrams.get(trigram).add(word);
                        }
                    }
                }
                */
                spellChecker.fillTrigrams(currentWord);

                /*
                if (counter == 9)
                    break;
                counter++;
                */

            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        /*
        for (Map.Entry<String, List<String>> entry : spellChecker.getTrigrams().entrySet()) {
            System.out.println(entry);
        }
        */

        /* Sélectionner les mots du dictionnaire qui ont le plus de trigrammes communs avec un mot "myWord" */


        Map<String, Integer> commonTrigramCounter = new HashMap<String, Integer>();
        String myWord = "<" + spellChecker.getWordToCorrect() + ">";

        for (Map.Entry<String, List<String>> entry : trigrams.entrySet()) {
            if (entry.getValue().contains(myWord)) {
                for(String word : entry.getValue()) {
                    if (commonTrigramCounter.containsKey(word)) {
                        Integer secondCounter = commonTrigramCounter.get(word);
                        secondCounter++;
                        commonTrigramCounter.put(word, secondCounter);
                    } else if (!word.equals(myWord)) {
                        commonTrigramCounter.put(word, 1);
                    }
                }
            }
        }

        spellChecker.fillCommonTrigramCounter();

        System.out.println("SIZE : " + spellChecker.getCommonTrigramCounter().size());
        int size = 100;
        if (spellChecker.getCommonTrigramCounter().size() < 100) {
            size = spellChecker.getCommonTrigramCounter().size();
        }
        String[] greatestNumberCommonTrigrams = new String[size];
        for (int i = 0; i < greatestNumberCommonTrigrams.length; i++) {
            Map.Entry<String, Integer> maxEntry = null;
            for (Map.Entry<String, Integer> entry : spellChecker.getCommonTrigramCounter().entrySet()) {
                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                    maxEntry = entry;
                }
            }
            if (maxEntry != null) {
                spellChecker.getCommonTrigramCounter().remove(maxEntry.getKey());
                greatestNumberCommonTrigrams[i] = maxEntry.getKey();
            }
        }
        /**************************************************************************************************/

        ArrayList<String> nearestWords = new ArrayList<>();
        Map<String, Integer> wordsDistances = new HashMap<>();
        int distance = 0;
        for (String word : greatestNumberCommonTrigrams) {
            distance = spellChecker.levenshteinDistance(spellChecker.getWordToCorrect(), word);
            wordsDistances.put(word, distance);
        }

        int limit = 5;
        if (wordsDistances.size() < 5) {
            limit = wordsDistances.size();
        }
        System.out.println("Nearest words : ");
        for (int i = 0; i < 5; i++) {
            Map.Entry<String, Integer> minEntry = null;
            for (Map.Entry<String, Integer> entry : wordsDistances.entrySet()) {
                if (minEntry == null || entry.getValue().compareTo(minEntry.getValue()) < 0) {
                    minEntry = entry;
                }
            }
            if (minEntry != null) {
                wordsDistances.remove(minEntry.getKey());
                nearestWords.add(minEntry.getKey());
                System.out.println(minEntry.getKey());
            }
        }

        System.out.println("TIME : " + System.nanoTime()/1000000000 + " secondes");
    }
}
