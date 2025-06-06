package io.github.xqplus.leetcode;

import java.util.Arrays;

public class UnionFindSet {
    private final int[] set;

    public UnionFindSet(int size) {
        set = new int[size];
        Arrays.fill(set, -1);
    }

    public int findRoot(int e) {
        while (set[e] > -1) {
            e = set[e];
        }
        return e;
    }

    public void union(int e1, int e2) {
        int root1 = findRoot(e1);
        int root2 = findRoot(e2);
        if (root1 == root2) {
            return;
        }
        if (root1 < root2) {
            set[root1] += set[root2];
            set[root2] = root1;
        } else {
            set[root2] += set[root1];
            set[root1] = root2;
        }
    }
}
