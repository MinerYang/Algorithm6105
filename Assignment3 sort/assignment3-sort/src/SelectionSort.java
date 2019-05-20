//package edu.neu.coe.info6205.sort.simple;

//import static edu.neu.coe.info6205.sort.simple.Helper.less;
//import static edu.neu.coe.info6205.sort.simple.Helper.swap;

public class SelectionSort<X extends Comparable<X>> implements Sort<X> {

    @Override
    public void sort(X[] xs, int from, int to) {
        // TODO implement selection sort
        int N=xs.length;
        for(int i=0;i<N;i++){
            int min=i;
            for(int j=i+1;j<N;j++){
                if(Helper.less(xs[j] , xs[min]))
                    min=j;
                Helper.swap(xs,from,to,i,min);
            }
        }
    }
}
