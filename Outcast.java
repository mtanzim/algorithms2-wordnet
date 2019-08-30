import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    WordNet wordNet;

    public Outcast(WordNet wordNet) {
        this.wordNet = wordNet;
    }

    public String outcast(String[] nouns) {
        return "";
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
//        WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
//            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
            StdOut.println(args[t]);
            for (String noun : nouns) {
                StdOut.print(noun + " ");
            }
            StdOut.println();
        }
    }

}
