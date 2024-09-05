package com.bogdan.list;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CustomArrayListTestCase {

    @Test
    public void createDefaultConstructor_shouldCreatedEmptyList() {
        SimpleList<Integer> list = new CustomArrayList<>();
        Assert.assertEquals(0, list.size());
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void createConstructorWithInitialCapacity_shouldCreatedListWithGivenCapacity() {
        final int capacity = 5;
        CustomArrayList<Integer> list1 = new CustomArrayList<>(capacity);

        Assert.assertEquals(capacity, list1.getCapacity());
    }

    @Test
    public void createConstructorWithGivenOtherList() {
        List<Integer> testList = new ArrayList<>();
        testList.add(1);
        testList.add(1);
        testList.add(1);
        SimpleList<Integer> list = new CustomArrayList<>(testList);

        Assert.assertEquals(list.size(), testList.size());
    }

    @Test
    public void addElementsToList() {
        final int numberElements = 100;
        SimpleList<Integer> list = new CustomArrayList<>();
        fill(list, numberElements);

        Assert.assertEquals(list.size(), numberElements);
    }

    private void fill(SimpleList<Integer> list, int size) {
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
    }

    @Test
    public void addElementsToListByIndex() {
        final int numberElements = 10;
        SimpleList<Integer> list = new CustomArrayList<>();
        fill(list, numberElements);
        final int position = 1;
        Integer integer = list.get(position);
        list.add(position, 40);

        Assert.assertNotEquals(integer, list.get(position));
    }

    @Test
    public void addElementsToListByIncorrectIndex() {
        final int numberElements = 10;
        SimpleList<Integer> list = new CustomArrayList<>();
        fill(list, numberElements);
        final int incorrectPosition = -1;

        Assert.assertThrows(IllegalArgumentException.class, () -> list.add(incorrectPosition, 40));
    }

    @Test
    public void setElementToListByIndex() {
        final int numberElements = 10;
        SimpleList<Integer> list = new CustomArrayList<>();
        fill(list, numberElements);
        final int position = 5;
        Integer oldValue = list.get(position);
        final Integer newValue = 10;
        list.set(position, newValue);

        Assert.assertNotEquals(oldValue, list.get(position));
        Assert.assertEquals(newValue, list.get(position));
    }

    @Test
    public void getElementToList() {
        final int numberElements = 10;
        SimpleList<Integer> list = new CustomArrayList<>();
        Assert.assertEquals(0, list.size());

        fill(list, numberElements);

        Integer value = 0;
        for (int i = 0; i < numberElements; i++) {
            Assert.assertEquals(list.get(i), value++);
        }
    }

    @Test
    public void removeElementByIndex() {
        final int numberElements = 10;
        SimpleList<Integer> list = new CustomArrayList<>();
        fill(list, numberElements);
        final int oldSize = list.size();
        final int removeIndex = 5;
        Integer removedValue = list.remove(removeIndex);

        Assert.assertNotEquals(removedValue, list.get(removeIndex));
        Assert.assertNotEquals(oldSize, list.size());
    }

    @Test
    public void removeByElement() {
        final int numberElements = 10;
        SimpleList<Integer> list = new CustomArrayList<>();
        fill(list, numberElements);
        final Integer elementForRemoved = 8;
        Assert.assertEquals(list.size(), numberElements);


        Assert.assertTrue(list.remove(elementForRemoved));
        for (Integer integer : list) {
            Assert.assertNotEquals(integer, elementForRemoved);
        }
    }

    @Test
    public void removeByElementIfElementNotExist() {
        final int numberElements = 10;
        SimpleList<Integer> list = new CustomArrayList<>();
        fill(list, numberElements);
        final int sizeBeforeRemoved = list.size();
        final Integer elementForRemoved = 20;

        Assert.assertFalse(list.remove(elementForRemoved));
        Assert.assertEquals(list.size(), sizeBeforeRemoved);
    }

    @Test
    public void trimToSizeTest() {
        final int numberElements = 100;
        CustomArrayList<Integer> list = new CustomArrayList<>();
        fill(list, numberElements);


        Assert.assertNotEquals(list.getCapacity(), list.size());

        list.trimToSize();
        Assert.assertEquals(list.getCapacity(), list.size());
    }

    @Test
    public void clearList_ShouldBeEmpty() {
        final int numberElements = 100;
        CustomArrayList<Integer> list = new CustomArrayList<>();
        fill(list, numberElements);

        list.clear();
        Assert.assertEquals(0, list.size());
    }
}
