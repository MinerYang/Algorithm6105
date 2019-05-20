//package edu.neu.coe.info6205.sort.simple;

//import static edu.neu.coe.info6205.sort.simple.Helper.less;
//import static edu.neu.coe.info6205.sort.simple.Helper.swap;

public class InsertionSort<X extends Comparable<X>> implements Sort<X> {
    @Override
    public void sort(X[] xs, int from, int to) {
        // TODO implement insertionSort
        int N=xs.length;
        for(int i=0;i<N;i++){
            for(int j=i;j>0;j--){
                if(Helper.less(xs[j],xs[j-1]))
                {
                    Helper.swap(xs,from,to,j,j-1);}
                else break;
            }
        }
    }
}
