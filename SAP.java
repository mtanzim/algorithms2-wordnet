import edu.princeton.cs.algs4.*;

import java.util.List;
import java.util.Stack;

public class SAP {
    private Topological topo;  // topological order

    private Digraph G;
    private String strRep;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException("invalid arg");
        }
        this.G = G;
        topo = new Topological(G);

        strRep = this.G.toString();
        StdOut.println(this.G);
//        StdOut.println(topo.isDAG());
//        StdOut.println(topo.order());
    }

    public String dotGen() {
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


    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return 0;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
//        int curAncestor = -1;
        List<Integer> possibleAncestors = new Stack<Integer>();

        StdOut.println("\nv: " + v + " w: " + w);
        StdOut.println("Topo order: " + topo.order());
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
        boolean vMarked = false;
        boolean wMarked = false;
        for (int cur : topo.order()) {
            if (cur == v) vMarked = true;
            if (cur == w) wMarked = true;
            if (vMarked && wMarked) {
                if (bfsV.hasPathTo(cur) && bfsW.hasPathTo(cur)) {
                    StdOut.println(cur + " : " + bfsV.pathTo(cur));
                    StdOut.println(cur + " : " + bfsW.pathTo(cur));
                    return cur;

                }
            }

        }
        return -1;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return 0;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return 0;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
//        StdOut.println("dotfile");
//        StdOut.println(sap.dotGen());
        StdOut.println("ancestor: " + sap.ancestor(3, 11));
        StdOut.println("ancestor: " + sap.ancestor(9, 12));
        StdOut.println("ancestor: " + sap.ancestor(7, 2));
        StdOut.println("ancestor: " + sap.ancestor(1, 6));
    }

}
