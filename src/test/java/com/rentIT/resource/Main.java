package com.rentIT.resource;


import java.util.List;

public class Main {

    public int[] multiplication(int[] initial) {

        int [] result = new int[initial.length];

        for(int i =0; i<initial.length; i++) {
            int newElement = 1;
            for(int j = 0; j<initial.length; j++){
                if(j != i)
                newElement = newElement* initial[j];

            }
            result[i] = newElement;
        }
        return result;
    }

    public int sum (List<Integer> integers) {
        int sum = integers.stream()
                            .filter(x-> x > 0)
                            .filter(x -> x%2 ==1)
                            .reduce(0,(x,y)-> (x+y));
        return sum;
    }
}
