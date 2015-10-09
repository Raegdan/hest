package org.raegdan.herzenstreetcodec;

import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        ///////////////////////////////////////
        // ULTIMATE BYDLOCODE PROTOTYPE !!!

//        final String dictFile = "/home/raegdan/WnP-UTF8.txt";
//        final String validWordRegex = "[А-Яа-яЁё]*";
//
//        List<String> words = new ArrayList<>();
//
//        System.out.println("Started reading file: " + dictFile);
//
//        FileReader fileReader = null;
//        try {
//            fileReader = new FileReader(dictFile);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        BufferedReader bufferedReader = new BufferedReader(fileReader);
//        String bufLine = null;
//        try {
//            while ((bufLine = bufferedReader.readLine()) != null) {
//                String[] lineWords = bufLine.split(" ");
//                words.addAll(Arrays.asList(lineWords));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            bufferedReader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            fileReader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(Integer.toString(words.size()) + " words read.");
//
//        ////////////////////////////
//
//        Set<String> processedWordsSet = new LinkedHashSet<>();
//        int i = 0; int j = 0;
//        for (String word : words) {
//            if (word.matches(validWordRegex)) {
//                processedWordsSet.add(word);
//            }
//
//            i++;
//            if (i - j >= 10000) {
//                j = i;
//                System.out.println(Integer.toString(i) + " words processed.");
//            }
//        }
//
//        ArrayList<String> processedWordsList = new ArrayList<>(processedWordsSet);
//        //Collections.sort(processedWordsList);
//        Collections.shuffle(processedWordsList);
//
//        ArrayList<ArrayList<String>> encodingDict = new ArrayList<>(0xFF + 1);
//        for (int k = 0; k <= 0xFF; k++) encodingDict.add(k, new ArrayList<String>());
//
//        int byteIter = 0x00;
//        for (String word : processedWordsList) {
//            byteIter++;
//            if (byteIter > 0xFF) byteIter = 0x00;
//            encodingDict.get(byteIter).add(word);
//        }
//
//        ObjectMapper om = new ObjectMapper()
//                .enable(SerializationFeature.INDENT_OUTPUT);
//
//        try {
//            om.writeValue(new File("/home/raegdan/WnP-hest-dict.txt"), encodingDict);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        ObjectMapper om = new ObjectMapper();

        ArrayList<ArrayList<String>> encodingDict = null;
        try {
            encodingDict = om.readValue(new File("/home/raegdan/WnP-hest-dict.txt"), ArrayList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            int[] buffer = new int[20];
            Random rng = new Random();

            System.out.print("Source data: ");
            for (int k = 0; k < 20; k++) {
                buffer[k] = Math.abs(rng.nextInt()) % (0xFF + 1);
                System.out.print(Integer.toHexString(buffer[k]) + " ");
            }
            System.out.println("");

            System.out.print("HESTed data: ");
            for (int k = 0; k < 20; k++) {
                ArrayList<String> wordsForByte = encodingDict.get(buffer[k]);
                int wordsCount = wordsForByte.size();
                String word = wordsForByte.get(Math.abs(rng.nextInt()) % (wordsCount)) + " ";
                System.out.print(word);
            }
            System.out.println("");

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("");

        }
    }
}
