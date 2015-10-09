package org.raegdan.herzenstreetcodec;

import java.io.*;
import java.util.*;

/**
 * Created by raegdan on 30.05.15.
 */
public class HerzenStreet {
    // Filter out everything but Russian words.
    final private String VALID_ALPHABET_REGEXP = "[А-Яа-яЁё]*";
    private String dictFile;
    private String sourceFile;

    // Basic Latin (English, Latin, ...)
//    final private String VALID_ALPHABET_REGEXP = "[A-Za-z]*";

    // Greek

    /*
        M.b. it can be specified as a range, like "α-ω"?
        Russian Cyrillic definitely have an exception for "ё" in such case, so I don't want to take risk.
        Need advice from Greek coders.
        -- Raegdan
     */

//    final private String VALID_ALPHABET_REGEXP = "[ΑαΒβΓγΔδΕεΖζΗηΘθΙιΚκΛλΜμΝνΞξΟοΠπΡρΣσΤτΥυΦφΧχΨψΩω]*";

    public void generateHestDictionary() throws IOException {
        File src = new File(sourceFile);
        File dict = new File(dictFile);

        if (!src.exists() ||
                !src.isFile() ||
                (dict.exists() && !dict.isFile())) {
            // TODO throw error
        }

        FileReader frSrc = new FileReader(src);
        FileWriter fwDict = new FileWriter(dict);


    }


    ////////////
    // Private methods
    ////////////

    private void checkFile(String fileName, boolean checkExistence, boolean checkIsFile, boolean checkReadability, boolean checkWritability) throws IOException {
        File f = new File(fileName);
        if (checkExistence && !f.exists()) throw new IOException("File: " + fileName + "does not exist.");
        if (checkIsFile && !f.isFile()) throw new IOException("File: " + fileName + "is not a file (dir? socket?)");
        if (checkReadability && !f.canRead())
            throw new IOException("File: " + fileName + "is not readable (permissions?)");
        if (checkWritability && !f.canWrite())
            throw new IOException("File: " + fileName + "is not writable (permissions?)");
    }

    private List<String> readTextFile(String fileName) throws IOException {
        checkFile(fileName, true, true, true, false);

        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);

        String buffer;
        ArrayList<String> result = new ArrayList<>();
        final String crlf = System.getProperty("line.separator");

        while ((buffer = br.readLine()) != null) {
            result.add(buffer);
        }

        br.close();
        fr.close();

        return result;
    }

    private void writeTextFile(String fileName, List<String> lines) throws IOException {
        checkFile(fileName, false, true, false, true);

        FileWriter fw = new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(fw);

        for (String line : lines) {
            bw.write(line);
            bw.newLine();
        }

        bw.close();
        fw.close();
    }

    private List<String> explodeToWords(List<String> rawLines) {
        ArrayList<String> result = new ArrayList<>();
        for (String line : rawLines) {
            result.addAll(Arrays.asList(line.split("[ ]+")));
        }

        return result;
    }

    private List<String> filterWords(List<String> wordsList) {
        List<String> buffer = new ArrayList<>();

        for (String word : wordsList) {
            if (word.matches(VALID_ALPHABET_REGEXP)) buffer.add(word);
        }

        return buffer;
    }

    private List<String> dedupList(List<String> nonDistinctList) {
        Set<String> deduper = new HashSet<>(nonDistinctList);
        List<String> deduped = new ArrayList<>(deduper);

        return deduped;
    }

    private List<String> shuffleList(List<String> unarrangedList, long rngSeed) {
        Random prng = new Random(rngSeed);

        return realShuffler(unarrangedList, prng);
    }

    private List<String> shuffleList(List<String> unarrangedList) {
        Random prng = new Random();

        return realShuffler(unarrangedList, prng);
    }

    private List<String> realShuffler(List<String> unarrangedList, Random prng) {
        List<String> clone = new ArrayList<>(unarrangedList);
        Collections.shuffle(clone, prng);

        return clone;
    }

    private List<String> sortList(List<String> unarrangedList) {
        List<String> clone = new ArrayList<>(unarrangedList);
        Collections.sort(clone);

        return clone;
    }
}
