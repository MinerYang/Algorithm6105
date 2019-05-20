/*
 * Copyright (c) 2018. Phasmid Software
 */

//package edu.neu.coe.info6205.util;
//import edu.neu.coe.info6205.sort.simple.InsertionSort;
//import edu.neu.coe.info6205.sort.simple.SelectionSort;
//import edu.neu.coe.info6205.sort.simple.ShellSort;

import java.util.Random;
import java.util.function.Function;

/**
 * @param <T> The generic type T is that of the input to the function f which you will pass in to the constructor.
 */
public class Benchmark<T> {

    /**
     * Constructor for a Benchmark.
     * @param f a function of T => Void.
     * Function f is the function whose timing you want to measure. For example, you might create a function which sorts an array.
     * When you create a lambda defining f, you must return "null."
     */
    public Benchmark(Function<T, Void> f) {
        this.f = f;
    }

    /**
     * Run function f m times and return the average time in milliseconds.
     * @param t the value that will in turn be passed to function f.
     * @param m the number of times the function f will be called.
     * @return the average number of milliseconds taken for each run of function f.
     */
    public double run(T t, int m) {
        long time1=System.nanoTime();
        for(int i=0;i<=m;i++){
            f.apply(t);
        }
        long time2= System.nanoTime();
        return ((double)(time2-time1)/m);  // TODO

    }

    private final Function<T, Void> f;

    /**
     * Everything below this point has to do with a particular example of running a Benchmark.
     * In this case, we time three types of simple sort on a randome integer array of length 1000.
     * Each test is run 200 times.
     * @param args the command-line arguments, of which none are significant.
     */
    public static void main(String[] args) {
        Random random = new Random();
        int m = 100; // This is the number of repetitions: sufficient to give a good mean value of timing
        // TODO You need to apply doubling to n
       // int n = 1000; // This is the size of the array
        //
        System.out.println("random order");
        int n = 1000;
        for (int k = 0; k< 5; k++) {
            Integer[] array = new Integer[n];

            for (int i = 0; i < n; i++) array[i] = random.nextInt();
            benchmarkSort(array, "InsertionSort: "+n, new InsertionSort<>(), m);
            benchmarkSort(array, "SelectionSort: "+n, new SelectionSort<>(), m);
            benchmarkSort(array, "ShellSort h=2h "+n, new ShellSort<>(1), m);
            benchmarkSort(array, "ShellSort h=2h+1 "+n, new ShellSort<>(2), m);
            benchmarkSort(array, "ShellSort h=3h+1 "+n, new ShellSort<>(3), m);
            n = n * 2;
        }
        System.out.println("******************************************");

        //
        System.out.println("ordered order");
        n = 1000;
        for (int k = 0; k< 5; k++) {
            Integer[] array = new Integer[n];

            for (int i = 0; i < n; i++) array[i] = i;
            benchmarkSort(array, "InsertionSort: "+n, new InsertionSort<>(), m);
            benchmarkSort(array, "SelectionSort: "+n, new SelectionSort<>(), m);
            benchmarkSort(array, "ShellSort h=2h "+n, new ShellSort<>(1), m);
            benchmarkSort(array, "ShellSort h=2h+1 "+n, new ShellSort<>(2), m);
            benchmarkSort(array, "ShellSort h=3h+1 "+n, new ShellSort<>(3), m);
            n = n * 2;
        }
        System.out.println("******************************************");

        //
        System.out.println("partial order");
        n = 1000;
        for (int k = 0; k< 5; k++) {
            Integer[] array = new Integer[n];

            for (int i = 0; i < n; i++) array[i] = i%10;
            benchmarkSort(array, "InsertionSort: "+n, new InsertionSort<>(), m);
            benchmarkSort(array, "SelectionSort: "+n, new SelectionSort<>(), m);
            benchmarkSort(array, "ShellSort h=2h "+n, new ShellSort<>(1), m);
            benchmarkSort(array, "ShellSort h=2h+1 "+n, new ShellSort<>(2), m);
            benchmarkSort(array, "ShellSort h=3h+1 "+n, new ShellSort<>(3), m);
            n = n * 2;
        }
        System.out.println("******************************************");

        //
        System.out.println("reverse order");
        n = 1000;
        for (int k = 0; k< 5; k++) {
            Integer[] array = new Integer[n];

            for (int i = 0; i < n; i++) array[i] = n-i;
            benchmarkSort(array, "InsertionSort: "+n, new InsertionSort<>(), m);
            benchmarkSort(array, "SelectionSort: "+n, new SelectionSort<>(), m);
            benchmarkSort(array, "ShellSort h=2h "+n, new ShellSort<>(1), m);
            benchmarkSort(array, "ShellSort h=2h+1 "+n, new ShellSort<>(2), m);
            benchmarkSort(array, "ShellSort h=3h+1 "+n, new ShellSort<>(3), m);
            n = n * 2;
        }
        System.out.println("******************************************");


    }

    private static void benchmarkSort(Integer[] array, String name, Sort<Integer> sorter, int m) {
        Function<Integer[], Void> sortFunction = (xs) -> {
            sorter.sort(xs);
            return null;
        };
        Benchmark<Integer[]> bm = new Benchmark<>(sortFunction);
        double x = bm.run(array, m);
        System.out.println(name + ": " + x + " nanosecs");
    }

    //

}