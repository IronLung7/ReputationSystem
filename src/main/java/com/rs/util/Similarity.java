package com.rs.util;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Similarity
{
    public static void main(String[] args)
    {
        Scanner sca = new Scanner(System.in);
        String array1 = sca.nextLine();
        String array2 = sca.nextLine();

        String[] a = array1.split(",");
        String[] b = array2.split(",");

        int reversions = reversions(binarySet(a), binarySet(b));
        System.out.format(("%.3f/n"), (1 - 2.0 * reversions / (a.length * (a.length - 1))));
    }

    private static Set<String> binarySet(String[] a)
    {
        Set<String> set = new HashSet<String>();
        for (int i = 0; i < a.length - 1; i++)
            for (int j = i + 1; j < a.length; j++)
                set.add(a[i] + "," + a[j]);
        return set;
    }

    private static int reversions(Set<String> a, Set<String> b)
    {
        int reversions = 0;
        for (String aString : a)
            if (!b.contains(aString))
                reversions++;
        return reversions;
    }

}
