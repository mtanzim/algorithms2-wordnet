import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class WordNet {
    private ArrayList<String> synA = new ArrayList<String>();
    private ArrayList<Bag> hypA = new ArrayList<Bag>();

    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("invalid arg");
        }
        // TODO:
        // Corner cases. Throw a java.lang.IllegalArgumentException in the following situations:
        // Any argument to the constructor or an instance method is null
        // The input to the constructor does not correspond to a rooted DAG.
        // Any of the noun arguments in distance() or sap() is not a WordNet noun.


        StdOut.println(synsets);
        StdOut.println(hypernyms);
        In inSyn = new In(synsets);
        In inHyp = new In(hypernyms);
//        StdOut.println(inSyn.readAll());
//        StdOut.println(inHyp.readAll());


        int i = 0;
        while (inSyn.hasNextLine()) {
            String curLine = inSyn.readLine();
//            StdOut.println(curLine);
            String tokens[] = curLine.split(",");
            int curId = Integer.parseInt(tokens[0]);
            assert (curId == i);
            String curSyn = tokens[1];
            StdOut.println("id: " + curId + " word: " + curSyn);
            synA.add(curSyn);
            i++;
        }
        i = 0;
        while (inHyp.hasNextLine()) {
            String curLine = inHyp.readLine();
//            StdOut.println(curLine);
            String tokens[] = curLine.split(",");
            int curId = Integer.parseInt(tokens[0]);
            assert (curId == i);
            Bag curHyps = new Bag();
            for (int k = 1; k < tokens.length; k++) {
                curHyps.add(tokens[k]);
            }
            hypA.add(curHyps);
            i++;
        }
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
        WordNet w = new WordNet("synsets.txt", "hypernyms.txt");
    }

}
