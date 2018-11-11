package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        List<String> words = new ArrayList();
        //Map<K, V>
        Map<String, List<String>> trigrams = new HashMap<>();
        SpellChecker spellChecker = new SpellChecker("<ADN>");
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
                if (counter == 9)
                    break;
                counter++;
            }

            for (Map.Entry<String, List<String>> entry : spellChecker.getTrigrams().entrySet()) {
                System.out.println(entry);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        /********* Distance de Levenshtein *********/
        String firstWord = " logarytmique";
        String secondWord = " algorithmique";
        //String firstWord = " bruhh";
        //String secondWord = " brah";

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
                    matrice[i][j] = matrice[i-1][j-1];
                } else {
                    //on ajoute le minimum des cases autour + 1 à la matrice[i][j]
                    int min = Math.min(matrice[i-1][j], matrice[i][j-1]);
                    matrice[i][j] = Math.min(min, matrice[i-1][j-1]) + 1;
                }

            }

        }
        System.out.println("Result : " + matrice[firstWord.length()-1][secondWord.length()-1]);
        /*************************************************************************/


        /* Sélectionner les mots du dictionnaire qui ont le plus de trigrammes communs avec un mot "myWord" */

        /*
        Map<String, Integer> commonTrigramCounter = new HashMap<String, Integer>();
        String myWord = "<ADN>";

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
        */

        spellChecker.fillCommonTrigramCounter();

        for (Map.Entry<String, Integer> entry : spellChecker.getCommonTrigramCounter().entrySet()) {
            System.out.println(entry);
        }

        /**************************************************************************************************/


    }
}
