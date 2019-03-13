import java.util.Objects;

/**
 * Class to store each individual sequence, which
 * overrides toString, equals, and hashCode methods
 * to suit the use of this program.
 *
 * @author Allen Pan
 */
public class Kmer {
    private String sequence;

    /**
     * Default constructor to set all values
     * to zero or null
     */
    Kmer() {
        sequence = "";
    }

    /**
     * Constructor to take in the k-mer sequence and
     * store it.
     */
    Kmer(String sequence) {
        this.sequence = sequence;
    }

    /**
     * accessor method for sequence
     *
     * @return sequence
     */
    public String getSequence() {
        return sequence;
    }

    /**
     * mutator method to set the sequence
     *
     * @param s k-mer sequence
     */
    public void setSequence(String s) {
        this.sequence = s;
    }

    /**
     * Return variable, sequence, in string type
     */
    @Override
    public String toString() {
        return sequence;
    }

    /**
     * Checks to see if the two sequences have the same combination.
     *
     * @param o object of kmer
     * @return true if the sequences are the same, vice versa
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Kmer)) return false;
        if (o == this) return true;

        return this.sequence.equals(((Kmer)o).sequence);
    }

    /**
     * Acts as an accessor for the hashCode of a sequence
     *
     * @return hashCode of the sequence
     */
    @Override
    public int hashCode() {
        return sequence.hashCode();

        /*
        return Objects.hash(sequence);
        This hashCode function has the search time of 19ms, sequence of CCAC
        and the file is yeast1.fasta, which is slightly slower than the one in use
        because it requires the creation of an array, which requires extra time.
         */


        /*
        return 0;
        This hashCode function has the search time of 1096ms, sequence of CCAC
        and the file is yeast1.fasta, which is way slower than the one is user because
        each sequence will have the same hash code, which results in a linear scan of the list
        each time it is called.
         */


        /*
        int hash = 7;
        hash = 31 * hash + (sequence == null ? 0: sequence.hashCode());
        return hash;
        This hashCode function has the search time of 18ms, sequence of CCAC
        and the file is yeast1.fasta, which is the same as the one in use because they are
        equally the same, but written out differently.
         */
    }
}
