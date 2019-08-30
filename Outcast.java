import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    WordNet wordNet;

    public Outcast(WordNet wordNet) {
        this.wordNet = wordNet;
    }

    public String outcast(String[] nouns) {
        int maxD = -1;
        int xt = -1;
        int distances[] = new int[nouns.length];
        for (int i = 0; i < nouns.length; i++) {
            int curSum = 0;
            for (int k = 0; k < nouns.length; k++) {
                if (k == i) continue;
                curSum += wordNet.distance(nouns[i], nouns[k]);
            }
            if (curSum > maxD) {
                maxD = curSum;
                xt = i;
            }
        }

        return nouns[xt];
    }

    public static void main(String[] args) {
        boolean debug = false;
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            if (debug) {
                StdOut.println(args[t]);
                for (String noun : nouns) {
                    StdOut.print(noun + " ");
                }
                StdOut.println();
            }

            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }

}
