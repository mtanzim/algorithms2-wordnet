import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
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
        StdOut.println(this.G);
        strRep = this.G.toString();
        StdOut.println(topo.isDAG());
        StdOut.println(topo.order());


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
        int curAncestor = -1;
        List<Integer> possibleAncestors = new Stack<Integer>();
        for (int cur : topo.order()) {
            possibleAncestors.add(cur);
            if (cur == v || cur == w) {
                break;
            }
        }
        List<Integer> curList = new ArrayList<Integer>();
        curList.add(v);
        curList.add(w);
        for (int cur : possibleAncestors) {
            curList.add(cur);
            StdOut.println(cur);
            BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G, curList);
            curList.remove(2);
        }

        return 0;
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
        sap.ancestor(8, 9);
        StdOut.println("dotfile");
        StdOut.println(sap.dotGen());
    }

}
