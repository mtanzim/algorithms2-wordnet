import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class WordNet {
    private ArrayList<String> synA = new ArrayList<String>();
    private RedBlackBST<String, Integer> synRBBST = new RedBlackBST<String, Integer>();
    private ArrayList<Bag> hypA = new ArrayList<Bag>();

    private void makeSyn(String synsets) {
        boolean debug = false;
        In inSyn = new In(synsets);
        int i = 0;
        while (inSyn.hasNextLine()) {
            String curLine = inSyn.readLine();
            String tokens[] = curLine.split(",");
            int curId = Integer.parseInt(tokens[0]);
            assert (curId == i);
            String curSyn = tokens[1];
            if (debug) StdOut.println("id: " + curId + " word: " + curSyn);
            synRBBST.put(curSyn, curId);
            synA.add(curSyn);
            i++;
        }
    }

    private void makeHyp(String hypernyms) {
        boolean debug = true;
        In inHyp = new In(hypernyms);
        int i = 0;
        while (inHyp.hasNextLine()) {
            String curLine = inHyp.readLine();
            String tokens[] = curLine.split(",");
            int curId = Integer.parseInt(tokens[0]);
            assert (curId == i);
            Bag curHyps = new Bag();
            for (int k = 1; k < tokens.length; k++) {
                curHyps.add(tokens[k]);
                if (debug) StdOut.println(tokens[k]);
            }
            hypA.add(curHyps);
            i++;
        }
    }

    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("invalid arg");
        }
        // TODO:
        // Corner cases. Throw a java.lang.IllegalArgumentException in the following situations:
        // Any argument to the constructor or an instance method is null
        // The input to the constructor does not correspond to a rooted DAG.
        // Any of the noun arguments in distance() or sap() is not a WordNet noun.

        makeSyn(synsets);
//        makeHyp(hypernyms);

    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synRBBST.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return synRBBST.contains(word);
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
        for (String noun : w.nouns()) {
            StdOut.println(noun);
        }
        StdOut.println(w.isNoun("oogla"));
        StdOut.println(w.isNoun("zygospore"));
    }

}
