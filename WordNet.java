import edu.princeton.cs.algs4.StdOut;

public class WordNet {

    private String synsets;
    private String hypernyms;

    public WordNet(String _synsets, String _hypernyms) {
        if (_synsets == null || _hypernyms == null) {
            throw new IllegalArgumentException("invalid arg");
        }
        // TODO:
        // Corner cases. Throw a java.lang.IllegalArgumentException in the following situations:
        // Any argument to the constructor or an instance method is null
        // The input to the constructor does not correspond to a rooted DAG.
        // Any of the noun arguments in distance() or sap() is not a WordNet noun.

        synsets = _synsets;
        hypernyms = _hypernyms;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return null;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return false;
    }

    // distance between nounA and nounB
    public int distance(String nounA, String nounB) {
        return 0;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        return "None";
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet w = new WordNet("asdasd", "asdasd");
        w.sap("a", "b");
        StdOut.println("Going bottom");
    }

}
