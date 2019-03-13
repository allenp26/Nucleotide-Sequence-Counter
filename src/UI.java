import java.io.*;
import java.util.*;

/**
 * User interface class, which prompts the user for file name
 * and the sequence length for k-mer. Implemented adequate
 * error handling method to handle cases where file cannot be found,
 * wrong type input for the sequence length, and wrong sequence length.
 *
 * @author Allen Pan
 */
public class UI {
    private Scanner in = new Scanner(System.in);
    private static final int MIN_SEQ_LENGTH = 0;
    private static final int MAX_SEQ_LENGTH = 5;

    /**
     * Instantiate the class and calls the promptUser method
     * to ask user for input file and sequence length.
     *
     * @param args string argument to be entered
     * @throws IOException
     */
    public static void main(String args[]) throws IOException{
        UI run = new UI();
        run.promptUser();
    }

    /**
     * Prompts the user for the file name and the sequence length
     * they wish to have. If the sequence length is below or exceeds
     * the minimum or maximum, throw an exception to notify the user.
     * If the file cannot be found, throw an exception to notify the
     * user. If the input type of sequence length is wrong, not an integer,
     * display a message.
     *
     * Prompt user to enter a sequence to search within the HashMap. The
     * sequence is verified based on the length of the string, if the length
     * are the same then it is a valid sequence.
     *
     * Note: in.nextLine() in all the catch() is to avoid the design flaw of
     * scanner which sees enter as a line.
     *
     * @throws IOException
     */
    private void promptUser() throws IOException {
        DNA dna = new DNA();
        String fileName = "";
        String sequence;
        int kmerLength = 0;
        boolean fileCheck = false;
        boolean done = false;

        do {
            try{
                System.out.println("Please enter the data filename: ");
                fileName = in.nextLine();

                System.out.println("Please enter the sequence length:");
                kmerLength = in.nextInt();

                if (fileName.isEmpty()) {
                    System.out.println("The file name has been set to \"yeast1Sample.fasta\" by default.");
                    fileName = "yeast1Sample.fasta";
                }
                else if (kmerLength > MAX_SEQ_LENGTH || kmerLength <= MIN_SEQ_LENGTH) {
                    throw new SequenceLengthException(kmerLength);
                }

                dna = new DNA(new File(fileName), kmerLength);
                fileCheck = true;

            }catch (FileNotFoundException f) {
                if (fileName.equalsIgnoreCase("q")) {
                    done = true;
                    break;
                }
                System.out.println("Not able to open file " + fileName);
                System.out.println("Please try again or enter \"Q\" to exit the program \n");
                in.nextLine();
            }
            catch (InputMismatchException i) {
                System.out.println("You have entered an invalid sequence length, Please try again \n");
                in.nextLine();
            }
            catch (SequenceLengthException s) {
                System.out.println("Sequence entered: " +kmerLength);
                System.out.println(s.getMessage());
                if (s.getSeqLength() <= MIN_SEQ_LENGTH) {
                    System.out.println("Minimum Sequence Length: 1");
                }
                else if(s.getSeqLength() > MAX_SEQ_LENGTH) {
                    System.out.println("Maximum Sequence Length: 5");
                }
                in.nextLine();
            }

        }while(!fileCheck);

        while (!done) {
            while (!done) {
                in = new Scanner(System.in);
                System.out.println("Enter new sequence. Enter \"Q\" to exit.");
                sequence = in.nextLine();

                if (sequence.compareToIgnoreCase("q") == 0) {
                    in.close();
                    done = true;

                }
                else {
                    dna.validSeq(sequence);
                }
            }
        }
    }
}
