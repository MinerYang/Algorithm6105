/*
 * Copyright (c) 2017. Phasmid Software
 */

/**
 * Weighted Quick Union with Path Compression
 */
import java.util.*;

public class WQUPC {
    private  final int[] parent;   // parent[i] = parent of i
    private  final int[] size;   // size[i] = size of subtree rooted at i
    private int count;  // number of components

    /**
     * Initializes an empty union–find data structure with {@code n} sites
     * {@code 0} through {@code n-1}. Each site is initially in its own
     * component.
     *
     * @param  n the number of sites
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public WQUPC(int n) {
        count = n;
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public void show() {
        for (int i=0; i<parent.length; i++) {
            System.out.printf("%d: %d, %d\n", i, parent[i], size[i]);
        }
    }

    /**
     * Returns the number of components.
     *
     * @return the number of components (between {@code 1} and {@code n})
     */
    public int count() {
        return count;
    }

    /**
     * Returns the component identifier for the component containing site {@code p}.
     *
     * @param  p the integer representing one site
     * @return the component identifier for the component containing site {@code p}
     * @throws IllegalArgumentException unless {@code 0 <= p < n}
     */
    public int find(int p) {
        validate(p);
        int root = p;
        while (root != parent[root]) {
            root = parent[root];
        }
        while (p != root) {
            int newp = parent[p];
            parent[p] = root;
            p = newp;
        }
        return root;
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
        }
    }

    /**
     * Returns true if the the two sites are in the same component.
     *
     * @param  p the integer representing one site
     * @param  q the integer representing the other site
     * @return {@code true} if the two sites {@code p} and {@code q} are in the same component;
     *         {@code false} otherwise
     * @throws IllegalArgumentException unless
     *         both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * Merges the component containing site {@code p} with the
     * the component containing site {@code q}.
     *
     * @param  p the integer representing one site
     * @param  q the integer representing the other site
     * @throws IllegalArgumentException unless
     *         both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;
        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }
        else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }
    //
    public static void main(String[] args) {
        //boolean[][] test_map = new boolean[2100][2100];
        Scanner sc = new Scanner(System.in);
        //System.out.println("Please input the number of the sites");
        //int n = sc.nextInt();
        System.out.println("Please input the times you want to test");
        int t = sc.nextInt();
        //int sum = 0;
        int k = 2;
        while (k <=2000) {//在1-300的整数中连续取值为sites,依次执行
            long sum=0; // 完成次数和
            int tc = t;
            while (tc > 0) {
                //for (int i=0; i<=2000;i++)
                   // for (int j=0;j<=2000;j++)
                        //test_map[i][j] = false;
                WQUPC w = new WQUPC(k);
                int num = 0;
                while (w.count() > 1) {
                    //System.out.println("Please input p and q");
                    Random random = new Random();
                    int p = random.nextInt(k);
                    int q = random.nextInt(k);
                    if (p == q) continue;
                   // if (test_map[p][q]) continue; //边是否重复
                    //test_map[p][q] = true;
                    num++;
                    if (w.connected(p, q)) continue;
                    w.union(p, q);
                    // w.show();
                }
                tc--;
                sum = sum + num;
                //System.out.printf("number of pairs genertaed is:%d\n", num);
            }
            double avgnum = (double)sum /t;
            System.out.printf("the average number of %d sites to generate pairs is:%f\n", k, avgnum);
            //System.out.printf("%f\n",avgnum);
            if(k<500)k++;
            else if(k>=500) k=k+100;
        }
    }


}