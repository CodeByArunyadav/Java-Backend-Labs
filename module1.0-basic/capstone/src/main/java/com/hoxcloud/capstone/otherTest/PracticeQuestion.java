package com.hoxcloud.capstone.otherTest;

import java.text.StringCharacterIterator;

public class PracticeQuestion {

    public static void main(String[] args) {

        //Question 1 : truncate the given string at K size with keeping in mind their should no space or incomplete word at end.
        // so i used to store the string inside and array of word and on second i loop and check the nextlength of word compare with K size in it found < K then i append it with
        // space and word at the end otherwise i break the loop and return the stored buffered new string.

            /*String s="a quick fox jump over the lazzy dog";
            String result=truncateString(s,20);*/

        //Question 2: n is given number as sum of the digit of n number should be twice as of sum of generated smallest positive number.
          /*  int n=15;
            int result=2*sumOfDigit(n);
            int sumOf=0;
            int num=0;
            for (int i=n; ; i++)
            {   num=i;
                sumOf=sumOfDigit(i);
                if(result==sumOf)
                   break;
            }
            System.out.println(num);*/
       //Question 3: N is the integer negative or positive(-/+) , remove digit 5 from the number N and obtain maximum Number;

    int result1= maxValueAfterRemovalOccurance(345653);

        System.out.println(result1);


    }

    private static int maxValueAfterRemovalOccurance(int n) {
        String s= String.valueOf(n);
        int start=s.charAt(0)=='-'? 1:0;
        String t;
        int bestvalue=Integer.MIN_VALUE;
        for(int i=start; i<s.length(); i++)
        {
            if(s.charAt(i)=='5') {
                //.out.println(s.charAt(i));
                t = s.substring(0, i) + s.substring(i + 1, s.length());
                int value=(t.isEmpty() || t.equals('-'))? 0:Integer.parseInt(t);
                if(bestvalue<value)
                    bestvalue=value;
                //System.out.println(t.toString());

            }
        }
        return bestvalue;
    }

    private static int sumOfDigit(int n) {
        int sum=0;
        while(n>0)
        {
        sum+=n%10;
        n=n/10;
        }
        return sum;
    }

    private static String truncateString(String s, int K) {
        if(s.length()<K)
            return s;
        StringBuilder sb= new StringBuilder();
        String [] word=s.split(" ",s.length());

        for (String words : word) {
           int nextLength= sb.length()<=0 ? words.length() : sb.length()+1+words.length();

           if(nextLength<=K)
           {  if(sb.isEmpty())
                   sb.append(words);
               else {
                   sb.append(" ");
                   sb.append(words);
               }
               }
           else
               break;
        }

        return sb.toString();

    }
}
