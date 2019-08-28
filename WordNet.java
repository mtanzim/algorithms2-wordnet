import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RedBlackBST;
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

    private ArrayList<Node> synA = new ArrayList<Node>();
    private RedBlackBST<String, ArrayList<Integer>> synRBBST = new RedBlackBST<String, ArrayList<Integer>>();
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
            String curSynset[] = tokens[1].split(" ");
            String curWord = curSynset[0];
//            THIS PART IS KEY!!!!!!!!
            for (String word : curSynset) {
//                Bag curIds = synRBBST.get(word);
                ArrayList<Integer> curIds = synRBBST.get(word);
                if (curIds == null) {
                    ArrayList<Integer> newIds = new ArrayList<Integer>();
                    newIds.add(curId);
                    synRBBST.put(word, newIds);
                } else {
                    curIds.add(curId);
                    synRBBST.put(word, curIds);

                }
//                synRBBST.put(word, curId);
                if (debug) StdOut.println("id: " + curId + " word: " + word);

            }
            synA.add(new Node(curId, curSynset));
            i++;
        }

        if (debug) {
            StdOut.println("pre-sort: ");
            i = 0;
            for (Node node : synA) {
                StdOut.println(node.toString());
                if (i == 10) break;
                i++;
            }
        }

        synA.sort(new SortByWord());

        if (debug) {
            StdOut.println("post-sort: ");
            i = 0;
            for (Node node : synA) {
                StdOut.println(node.toString());
                if (i == 10) break;
                i++;
            }
        }
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
        return synRBBST.keys();
    }

    private void sizes() {
        StdOut.println("Number of synset words: ");
        StdOut.println(synRBBST.size());
        StdOut.println("Number of hyp vertices: ");
        StdOut.println(hypG.V());
        StdOut.println("Number of hyp edges: ");
        StdOut.println(hypG.E());
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return synRBBST.contains(word);
    }

    // distance between nounA and nounB
    public int distance(String nounA, String nounB) {
//        int a = synRBBST.get(nounA);
//        int b = synRBBST.get(nounB);
        ArrayList<Integer> a = synRBBST.get(nounA);
        ArrayList<Integer> b = synRBBST.get(nounB);
        return sap.length(a, b);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        boolean debug = false;
        if (!(isNoun(nounA) && isNoun(nounB))) throw new IllegalArgumentException("noun not found");
//        int indexA = Collections.binarySearch(synA, new Node(nounA), new SortByWord());
//        int indexB = Collections.binarySearch(synA, new Node(nounB), new SortByWord());
        ArrayList<Integer> a = synRBBST.get(nounA);
        ArrayList<Integer> b = synRBBST.get(nounB);
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

        return synA.get(sap.ancestor(a, b)).toString();
    }

    // do unit testing of this class
    public static void main(String[] args) {
        boolean debug = false;
        WordNet w = new WordNet("synsets.txt", "hypernyms.txt");
        for (String noun : w.nouns()) {
            if (debug) StdOut.println(noun);
        }
        StdOut.println(w.isNoun("oogla"));
        StdOut.println(w.isNoun("zygospore"));
        StdOut.println(w.distance("worm", "bird"));
        StdOut.println(w.sap("worm", "bird"));
        StdOut.println(w.distance("white_marlin", "mileage"));
        StdOut.println(w.distance("Black_Plague", "black_marlin"));
        StdOut.println(w.distance("American_water_spaniel", "histology"));
        StdOut.println(w.distance("Brown_Swiss", "barrel_roll"));
    }

}
