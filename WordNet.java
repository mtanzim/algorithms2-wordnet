import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Comparator;

public class WordNet {


    //    class Node implements Comparable<Node> {
    class Node {
        int id;
        String word;
        String synsets[];
        private boolean debug = false;

        public Node(int id, String synsets[]) {
            this.id = id;
            this.synsets = synsets;
            this.word = synsets[0];
        }

        //        for searching
        public Node(String word) {
            this.word = word;
        }

        public String[] getSynsets() {
            return synsets;
        }

        public String getWord() {
            return word;
        }

        private String synsetToString() {
            String v = "";
            for (String synset : synsets) {
                v += synset + " ";
            }
            return v;
        }

        public String toString() {
            if (debug) return "id: " + id + " synsets: " + synsetToString();
            return synsetToString();
        }
    }

    class SortById implements Comparator<Node> {
        public int compare(Node a, Node b) {
            return a.id - b.id;
        }
    }

    class SortByWord implements Comparator<Node> {
        public int compare(Node a, Node b) {
            return a.word.compareTo(b.word);
        }
    }

    //    private ArrayList<Node> synA = new ArrayList<Node>();
    private LinearProbingHashST<String, ArrayList<Integer>> wordH = new LinearProbingHashST<String, ArrayList<Integer>>();
    private LinearProbingHashST<Integer, String> synA = new LinearProbingHashST<Integer, String>();
    private Digraph hypG;
    private SAP sap;


    private void makeSyn(String synsets) {
        boolean debug = false;


        In inSyn = new In(synsets);
        int i = 0;
        while (inSyn.hasNextLine()) {
            String curLine = inSyn.readLine();
            String tokens[] = curLine.split(",");
            int curId = Integer.parseInt(tokens[0]);
            assert (curId == i);
            String curSynsetWhole = tokens[1];
            String curSynset[] = curSynsetWhole.split(" ");
            for (String word : curSynset) {
                ArrayList<Integer> curIds = wordH.get(word);
                if (curIds == null) {
                    ArrayList<Integer> newIds = new ArrayList<Integer>();
                    newIds.add(curId);
                    wordH.put(word, newIds);
                } else {
                    curIds.add(curId);
                    wordH.put(word, curIds);

                }
//                wordH.put(word, curId);
                if (debug) StdOut.println("id: " + curId + " word: " + word);

            }
//            synA.add(new Node(curId, curSynset));
            synA.put(curId, curSynsetWhole);
            i++;
        }

//        if (debug) {
//            StdOut.println("pre-sort: ");
//            i = 0;
//            for (Node node : synA) {
//                StdOut.println(node.toString());
//                if (i == 10) break;
//                i++;
//            }
//        }

//        synA.sort(new SortByWord());
//
//        if (debug) {
//            StdOut.println("post-sort: ");
//            i = 0;
//            for (Node node : synA) {
//                StdOut.println(node.toString());
//                if (i == 10) break;
//                i++;
//            }
//        }
    }

    private void makeHyp(String hypernyms) {
        boolean debug = false;
        In inHyp = new In(hypernyms);
        hypG = new Digraph(synA.size());
        int i = 0;
        while (inHyp.hasNextLine()) {
            String curLine = inHyp.readLine();
            String tokens[] = curLine.split(",");
            int curId = Integer.parseInt(tokens[0]);
            assert (curId == i);
            for (int k = 1; k < tokens.length; k++) {
                hypG.addEdge(curId, Integer.parseInt(tokens[k]));
            }
            i++;
        }
        if (debug) StdOut.println(hypG.toString());
    }

    public WordNet(String synsets, String hypernyms) {
        boolean debug = false;
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("invalid arg");
        }
        // TODO:
        // Corner cases. Throw a java.lang.IllegalArgumentException in the following situations:
        // Any argument to the constructor or an instance method is null
        // The input to the constructor does not correspond to a rooted DAG.
        // Any of the noun arguments in distance() or sap() is not a WordNet noun.

        makeSyn(synsets);
        makeHyp(hypernyms);
        sap = new SAP(hypG);
        if (debug) sizes();
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return wordH.keys();
    }

    private void sizes() {
        StdOut.println("Number of synset words: ");
        StdOut.println(wordH.size());
        StdOut.println("Number of hyp vertices: ");
        StdOut.println(hypG.V());
        StdOut.println("Number of hyp edges: ");
        StdOut.println(hypG.E());
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return wordH.contains(word);
    }

    // distance between nounA and nounB
    public int distance(String nounA, String nounB) {
//        int a = wordH.get(nounA);
//        int b = wordH.get(nounB);
        ArrayList<Integer> a = wordH.get(nounA);
        ArrayList<Integer> b = wordH.get(nounB);
        return sap.length(a, b);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        boolean debug = false;
        if (!(isNoun(nounA) && isNoun(nounB))) throw new IllegalArgumentException("noun not found");
//        int indexA = Collections.binarySearch(synA, new Node(nounA), new SortByWord());
//        int indexB = Collections.binarySearch(synA, new Node(nounB), new SortByWord());
        ArrayList<Integer> a = wordH.get(nounA);
        ArrayList<Integer> b = wordH.get(nounB);
        if (debug) {
            StdOut.println("synset ids for " + nounA);
            for (Object item : a) {
                StdOut.println(item);
            }
            StdOut.println("synset ids for " + nounB);

            for (Object item : b) {
                StdOut.println(item);
            }
        }
        /*if (debug) {
            StdOut.println("found indices for " + nounA + " and " + nounB);
            StdOut.println(a);
            StdOut.println(b);
            StdOut.println(indexA);
            StdOut.println(indexB);
            StdOut.println(synA.get(a));
            StdOut.println(synA.get(b));
            StdOut.println(synA.get(indexA));
            StdOut.println(synA.get(indexB));
        }*/

//        return synA.get(sap.ancestor(a, b)).toString();
        return synA.get(sap.ancestor(a, b));
    }

    // do unit testing of this class
    public static void main(String[] args) {
        boolean debug = false;
        WordNet w = new WordNet("synsets.txt", "hypernyms.txt");
        for (String noun : w.nouns()) {
            if (debug) StdOut.println(noun);
        }
        boolean test1 = w.isNoun("oogla");
        boolean test2 = w.isNoun("zygospore");
        int test3 = w.distance("worm", "bird");
        String test4 = w.sap("worm", "bird");
        int test5 = w.distance("white_marlin", "mileage");
        int test6 = w.distance("Black_Plague", "black_marlin");
        int test7 = w.distance("American_water_spaniel", "histology");
        int test8 = w.distance("Brown_Swiss", "barrel_roll");

        assert !test1;
        assert test2;
        assert test3 == 5;
        StdOut.println(test4);
//        assert test4 == "animal animate_being beast brute creature fauna ";
        assert test5 == 23;
        assert test6 == 33;
        assert test7 == 27;
        assert test8 == 29;


    }

}
