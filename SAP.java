import edu.princeton.cs.algs4.*;

import java.util.ArrayList;

public class SAP {
    private Topological topo;  // topological order
    private Digraph G;
    private String strRep;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        boolean debug = false;
        if (G == null) {
            throw new IllegalArgumentException("invalid arg");
        }
        this.G = G;
        topo = new Topological(G);
        strRep = this.G.toString();
        if (debug) StdOut.println(dotGen());
        if (debug) StdOut.println("topological order: " + topo.order());

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
        if (debug) StdOut.println("\nv: " + v + " w: " + w);
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
        for (int cur : topo.order()) {
            if (bfsV.hasPathTo(cur) && bfsW.hasPathTo(cur)) {
                if (debug) StdOut.println(cur + " : " + bfsV.pathTo(cur));
                if (debug) StdOut.println(cur + " : " + bfsW.pathTo(cur));
                int dist = bfsW.distTo(cur) + bfsV.distTo(cur);
                if (debug) StdOut.println("dist: " + dist);
                if (isDist) return dist;
                return cur;
            }
        }
        return -1;
    }


    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        ArrayList<Integer> vIter = new ArrayList<Integer>();
        ArrayList<Integer> wIter = new ArrayList<Integer>();
        vIter.add(v);
        wIter.add(w);
        return common(vIter, wIter, true);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        ArrayList<Integer> vIter = new ArrayList<Integer>();
        ArrayList<Integer> wIter = new ArrayList<Integer>();
        vIter.add(v);
        wIter.add(w);
        return common(vIter, wIter, false);
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
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        int len;
        int ancestor;

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
