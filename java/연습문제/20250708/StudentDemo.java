package ch15;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class StudentDemo {
    public static void main(String[] args) {
        Student[]students ={new Student("100","홍길동",90,77,88)
        ,new Student("101","이순신",88,94,90)
        ,new Student("102","타이거",78,88,99)
        ,new Student("103","라이온",85,90,100)};
        
    Arrays.sort(students);
    Arrays.sort(students,Collections.reverseOrder());

    for(Student r : students)
        System.out.println(r);
    }

}
