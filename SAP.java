import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class SAP {
    //    private Topological topo;  // topological order, need this?
    private Digraph G;
    private String strRep;
    private static final int INFINITY = Integer.MAX_VALUE;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        boolean debug = false;
        if (G == null) {
            throw new IllegalArgumentException("invalid arg");
        }
        this.G = G;
//        topo = new Topological(G);
        strRep = this.G.toString();
        if (debug) StdOut.println(dotGen());
//        if (debug) StdOut.println("topological order: " + topo.order());

    }

    private String dotGen() {
        String lines[] = strRep.split("\\r?\\n");
        StringBuilder s = new StringBuilder();
        s.append("Digraph G {\n");
        for (int i = 1; i < lines.length; i++) {
            String temp = lines[i].replaceAll(": ", "->");
            temp = temp.replaceAll(" ", "->");
            temp = temp.replaceAll("->(?!\\d)", "");
            s.append("\t" + temp);
            s.append("\n");
        }
        s.append("}\n");
        return s.toString();
    }

    private int common(Iterable<Integer> v, Iterable<Integer> w, boolean isDist) {
        boolean debug = false;
        int minDist = INFINITY;
        int curMinAnc = -1;
        if (debug) StdOut.println("\nv: " + v + " w: " + w);
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
//        for (int cur : topo.order()) {
        for (int cur = 0; cur < G.V(); cur++) {
            if (bfsV.hasPathTo(cur) && bfsW.hasPathTo(cur)) {
                int dist = bfsW.distTo(cur) + bfsV.distTo(cur);
                if (debug) StdOut.println("\ndist: " + dist);
                if (debug) StdOut.println(cur + " : " + bfsV.pathTo(cur));
                if (debug) StdOut.println(cur + " : " + bfsW.pathTo(cur));
                if (dist < minDist) {
                    if (debug) StdOut.println("changing min dist to: " + dist);
                    if (debug) StdOut.println("changing min anc to: " + cur);
                    curMinAnc = cur;
                    minDist = dist;
                }
            }
        }
        if (isDist) {
            if (minDist == INFINITY) return -1;
            return minDist;
        }
        return curMinAnc;
    }


    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        ArrayList<Integer> vI = new ArrayList<Integer>();
        ArrayList<Integer> wI = new ArrayList<Integer>();
        vI.add(v);
        wI.add(w);
        return common(vI, wI, true);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        ArrayList<Integer> vI = new ArrayList<Integer>();
        ArrayList<Integer> wI = new ArrayList<Integer>();
        vI.add(v);
        wI.add(w);
        return common(vI, wI, false);
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return common(v, w, true);
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return common(v, w, false);
    }

    public static void main(String[] args) {
//        In in = new In(args[0]);
        String filename = "digraph25.txt";
        StdOut.println(filename);
        In in = new In(filename);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        // tests with digraph25.txt
        ArrayList<Integer> vI = new ArrayList<Integer>();
        ArrayList<Integer> wI = new ArrayList<Integer>();
        vI.addAll(Arrays.asList(13, 23, 24));
        wI.addAll(Arrays.asList(6, 16, 17));

        int len;
        int ancestor;
        len = sap.length(vI, wI);
        ancestor = sap.ancestor(vI, wI);
        StdOut.println("len: " + len);
        StdOut.println("ancestor: " + ancestor);
        assert ancestor == 3;
        assert len == 4;

        // tests with digraph1.txt
        filename = "digraph1.txt";
        StdOut.println(filename);
        in = new In(filename);
        G = new Digraph(in);
        sap = new SAP(G);
        len = sap.length(3, 11);
        ancestor = sap.ancestor(3, 11);
        StdOut.println("len: " + len);
        StdOut.println("ancestor: " + ancestor);
        assert ancestor == 1;
        assert len == 4;

        len = sap.length(9, 12);
        ancestor = sap.ancestor(9, 12);
        StdOut.println("len: " + len);
        StdOut.println("ancestor: " + ancestor);
        assert ancestor == 5;
        assert len == 3;

        len = sap.length(7, 2);
        ancestor = sap.ancestor(7, 2);
        StdOut.println("len: " + len);
        StdOut.println("ancestor: " + ancestor);
        assert ancestor == 0;
        assert len == 4;

        len = sap.length(1, 6);
        ancestor = sap.ancestor(1, 6);
        StdOut.println("len: " + len);
        StdOut.println("ancestor: " + ancestor);
        assert ancestor == -1;
        assert len == -1;
    }

}
