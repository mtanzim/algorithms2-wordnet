import edu.princeton.cs.algs4.StdOut;

public class WordNet {

    private String synsets;
    private String hypernyms;

    public WordNet(String _synsets, String _hypernyms) {
        if (_synsets == null || _hypernyms == null) {
            throw new IllegalArgumentException("invalid arg");
        }
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

    // distance between nounA and nounB (defpublic int distance(String nounA, String
    // a synset (second field of synsets.txt// in a shortest ancestral path (defined
    // below)
    public String sap(String nounA, String nounB) {
        return "None";
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet w = new WordNet("asdad", "asdasd");
        w.sap("a", "b");
        StdOut.println("Going bottom");
    }

}
