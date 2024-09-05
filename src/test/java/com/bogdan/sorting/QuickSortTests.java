package com.bogdan.sorting;

import com.bogdan.list.CustomArrayList;
import com.bogdan.list.SimpleList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

public class QuickSortTests {
    private Sorter sorter;

    @Before
    public void init(){
        this.sorter = new QuickSorter();
    }

    @Test
    public void quickSortIntegerTest(){
        List<Integer> sortList = List.of(-24,-2,0,3,18,99);
        SimpleList<Integer> testSortList = new CustomArrayList<>(sortList);
        SimpleList<Integer> list = new CustomArrayList<>();
        list.add(18);
        list.add(-2);
        list.add(99);
        list.add(-24);
        list.add(0);
        list.add(3);
        sorter.sort(list, Comparator.naturalOrder());
        for (int i = 0; i < list.size(); i++) {
            Assert.assertEquals(list.get(i),testSortList.get(i));
        }
    }

    @Test
    public void quickSortStringTest(){
        SimpleList<String> comparisonList = new CustomArrayList<>();
        comparisonList.add("tomato");
        comparisonList.add("melon");
        comparisonList.add("garlic");
        comparisonList.add("banana");
        comparisonList.add("apple");
        SimpleList<String> testSortList = new CustomArrayList<>();
        testSortList.add("banana");
        testSortList.add("apple");
        testSortList.add("melon");
        testSortList.add("garlic");
        testSortList.add("tomato");
        sorter.sort(testSortList,Comparator.reverseOrder());

        Assert.assertEquals(comparisonList,testSortList);
        for (int i = 0; i < testSortList.size(); i++) {
            Assert.assertEquals(comparisonList.get(i),testSortList.get(i));
        }
    }

    @Test
    public void quickSortPersonByAgeTest(){
        SimpleList<Person> sortPeople = new CustomArrayList<>();
        sortPeople.add(new Person(2, "Sam"));
        sortPeople.add(new Person(5, "Anny"));
        sortPeople.add(new Person(25, "John"));
        sortPeople.add(new Person(45, "Bob"));
        sortPeople.add(new Person(99, "Mike"));
        SimpleList<Person> people = new CustomArrayList<>();
        people.add(new Person(25, "John"));
        people.add(new Person(2, "Sam"));
        people.add(new Person(99, "Mike"));
        people.add(new Person(45, "Bob"));
        people.add(new Person(5, "Anny"));

        sorter.sort(people,Comparator.comparing(Person::getAge));

        Assert.assertEquals(people,sortPeople);

    }

    private static class Person{
        int age;
        String name;

        public Person(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person person)) return false;

            if (getAge() != person.getAge()) return false;
            return getName() != null ? getName().equals(person.getName()) : person.getName() == null;
        }

        @Override
        public int hashCode() {
            int result = getAge();
            result = 31 * result + (getName() != null ? getName().hashCode() : 0);
            return result;
        }
    }
}
