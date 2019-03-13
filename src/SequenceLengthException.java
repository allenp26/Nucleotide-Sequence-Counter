/**
 * Custom exception class to handle the
 * case where the sequence length is invalid.
 *
 * @author Allen Pan
 */
class SequenceLengthException extends Exception{
    private int seqLength;

    /**
     * Default constructor to set all values
     * to zero or null
     */
    SequenceLengthException() {
        seqLength = 0;
    }

    /**
     * Constructor to set the class variable to
     * the one that has been entered.
     *
     * @param seqLength sequence length entered by the user
     */
    SequenceLengthException(int seqLength) {
        super("Invalid Sequence Length: " +seqLength);
        this.seqLength = seqLength;
    }

    /**
     * Accessor for class variable seqLength.
     *
     * @return seqLength
     */
    int getSeqLength() {
        return seqLength;
    }
}
