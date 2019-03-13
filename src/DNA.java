import java.util.*;
import java.io.*;

/**
 * Processing class to build the HashMap and searching
 * through the HashMap. As well as the printing of
 * results.
 *
 * @author Allen Pan
 */
class DNA {
    private File dataFile;
    private int kmerLength;
    private int numOfSeq = 0;
    private HashMap<Kmer, ArrayList<Integer>> genSequence;

    /**
     * Default constructor to set all values
     * to zero or null
     */
    DNA() {
        dataFile = null;
    }

    /**
     * constructor to set the variable values to
     * the ones that the user has entered
     *
     * @param dataFile file name containing DNA sequence
     * @param kmerLength desired k-mer sequence length
     * @throws IOException
     */
    DNA(File dataFile, int kmerLength) throws IOException {
        this.dataFile = dataFile;
        this.kmerLength = kmerLength;

        loadSequence();
    }

    /**
     * Read in DNA sequences from the file provided. Creates
     * the HashMap based on the k-mer sequence length entered.
     * Uses BufferedReader to read through the file character by
     * character. StringBuilder is used to create k-mer sequences
     * based on the length the user desires. Stores the sequence
     * and location of the sequence in the HashMap.
     * Calls calcTotalSeqNum method to calculate the total amount
     * of sequences with the k-mer sequence that the user has entered.
     *
     * @throws IOException
     */
    private void loadSequence() throws IOException{
        BufferedReader inp;
        String line;
        Kmer kSeq = new Kmer();
        int keyPosition = 0;
        int mapSize;
        StringBuilder nextKmer = new StringBuilder();

        genSequence = new HashMap<>((int)(1.5 * (4 * kmerLength)));
        inp = new BufferedReader(new FileReader(dataFile));
        line = inp.readLine();

        if (line.contains(">")) {
            System.out.println("Reading genome sequence from file with description: " + line);
        }

        while (inp.ready()) {
            line = inp.readLine();

            for(int i = 0; i < line.length(); i++) {

                nextKmer.append(line.charAt(i));

                if (nextKmer.length() == kmerLength) {
                    kSeq = new Kmer(nextKmer.toString());
                }
                else if (nextKmer.length() > kmerLength) {
                    nextKmer.deleteCharAt(0);
                    kSeq = new Kmer(nextKmer.toString());
                }

                if (nextKmer.length() == kmerLength) {
                    ArrayList<Integer> keyPosList = new ArrayList<>();
                    if (genSequence.containsKey(kSeq)) {
                        keyPosList.addAll(genSequence.get(kSeq));
                        keyPosList.add(keyPosition);
                        genSequence.put(kSeq, keyPosList);
                    }
                    else {
                        keyPosList.add(keyPosition);
                        genSequence.put(kSeq, keyPosList);
                    }
                    keyPosition++;
                }
            }
        }

        genSequence.forEach((k, v) -> calcTotalSeqNum(v.size()));
        mapSize = genSequence.size();
        System.out.println("Sequence of size " +kmerLength+ " count: " +numOfSeq);
        System.out.println("Map size: " +mapSize);
    }

    /**
     * Validates the string to be searched based on
     * the length of the string. If the string length
     * is the same as the k-mer sequence length, search
     * through the HashMap. Else print an error message.
     *
     * @param s k-mer sequence entered by the user
     */
    void validSeq(String s) {
        if (s.length() == kmerLength) {
            searchMap(s);
        }
        else {
            System.out.println("Invalid sequence entered: " +s);
        }
    }

    /**
     * Search through the HashMap 1 million times to
     * see if the k-mer sequence entered is in the HashMap.
     * The time that the program takes to find the sequence
     * is calculated and stored.
     * If the sequence is found, call printGoodResult method to
     * display all necessary information.
     * If the sequence is not found, call printNullResult method
     * to display a message to the user.
     *
     * @param kmerSeq validated k-mer sequence
     */
    private void searchMap(String kmerSeq) {
        Kmer kSeq = new Kmer(kmerSeq);
        long startTime;
        long endTime;
        long searchDur;
        boolean foundKey = false;

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            if (genSequence.containsKey(kSeq)) {
                foundKey = true;
            }
        }
        endTime = System.currentTimeMillis();
        searchDur = endTime - startTime;

        if(foundKey) {
            printGoodResult(kSeq, searchDur);
        }
        else {
            printNullResult(kSeq, searchDur);
        }

    }

    /**
     * Calculates the total number of sequences with
     * the k-mer sequence length that the user has entered.
     *
     * @param v amount for each combination of sequence
     */
    private void calcTotalSeqNum(int v) {
        numOfSeq += v;
    }

    /**
     * Prints the result of the search, which includes
     * the sequence that the user has entered, all the locations
     * of the sequence, number of locations, and the search time.
     *
     * @param k k-mer sequence entered by the user
     * @param time search time of the sequence
     */
    private void printGoodResult(Kmer k, long time) {
        int location = genSequence.get(k).size();

        System.out.println("Sequence: " +k.toString());
        System.out.println(genSequence.get(k));
        System.out.println("There are " +location+ " locations with the sequence: " +k.toString());
        System.out.println("Search time: " +time + " ms");

    }

    /**
     * Display a message to the user, which the sequence
     * that he/she has entered cannot be found within
     * the HashMap or the file. The time it has taken
     * to search is also displayed.
     *
     * @param k k-mer sequence entered by the user
     * @param time search time of the sequence
     */
    private void printNullResult(Kmer k, long time) {
        System.out.println("Sequence: " +k.toString()+ " not found.");
        System.out.println("Search time: " +time);
    }

}
