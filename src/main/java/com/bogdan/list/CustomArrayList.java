package com.bogdan.list;

import java.util.*;

/**
 * Представлена простая пользовательская реализация (по типу ArrayList) интерфейса SimpleList
 * с изменяемым размером массива. По мимо реализации интерфейса SimpleList, данная реализация предоставляет метод
 * для получения значения емкости внутреннего массива, что поможет при желании использовать метод trimToSize и удалить
 * выделенные ячейки. Так же представлен внутренний класс реализующий пользовательский Iterator.
 *
 * @param <T> - указывает тип элемента в списке.
 */
public class CustomArrayList<T> implements SimpleList<T> {
    /**
     * Величина внутреннего массива, при вызове конструктора без аргументов.
     */
    private static final int DEFAULT_CAPACITY = 10;
    /**
     * Пустой массив используется при использовании конструктора с передаваемой величиной емкости массива.
     * Устанавливается если емкость равняется 0.
     */
    private static final Object[] EMPTY_ELEMENTS = {};
    /**
     * Мягкая максимальная длина массива, ограничение которое накладывается для вычисления роста массива.
     * Некоторые JVM имеют ограничение реализации, которое приведет
     * к возникновению ошибки OutOfMemoryError("Запрашиваемый размер массива превышает лимит виртуальной машины"),
     * если делается запрос на выделение массива некоторой длины около Integer.MAX_VALUE,
     * даже если имеется достаточная куча. Фактическое ограничение может зависеть от некоторых
     * характеристик реализации JVM, таких как размер заголовка объекта. Мягкое максимальное
     * значение выбирается консервативно, чтобы быть меньше любого ограничения реализации,
     * которое, вероятно, встретится.
     */
    private static final int SOFT_MAX_ARRAY_LENGTH = Integer.MAX_VALUE - 8;
    /**
     * Внутренний массив для хранения списка элементов
     */
    private Object[] elements;
    /**
     * Размер коллекции с элементами, показывает количество элементов находящиеся в массиве
     */
    private int size;

    /**
     * Конструктор по умолчинию
     */
    public CustomArrayList() {
        this.elements = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Конструктор принимающий велечину внутреннего массива
     * @param initCapacity - задаваемая величина внутреннего массива
     */
    public CustomArrayList(int initCapacity) {
        if (initCapacity > 0) {
            this.elements = new Object[initCapacity];
        } else if (initCapacity == 0) {
            this.elements = EMPTY_ELEMENTS;
        } else {
            throw new IllegalArgumentException("Illegal size: " + initCapacity);
        }
    }

    /**
     * Конструктор принимающий экземпляр Collection.
     * @param collection экземпляр принимаемой коллекции
     */
    public CustomArrayList(Collection<? extends T> collection) {
        Object[] temp = collection.toArray();
        this.size = temp.length;
        if (size != 0) {
            elements = Arrays.copyOf(temp, size, Object[].class);
        } else {
            elements = EMPTY_ELEMENTS;
        }
    }

    /**
     * Метод для получения велечины емкости внутреннего массива
     * @return величину длинны внутреннего массива
     */
    public int getCapacity() {
        return elements.length;
    }

    /**
     * Метод для удаления пустых ячеек внутреннего массива
     */
    @Override
    public void trimToSize() {
        if (size < elements.length) {
            elements = (size == 0) ? EMPTY_ELEMENTS : Arrays.copyOf(elements, size);
        }
    }

    /**
     * Метод добавления елемента типа T в список.
     * При недостаточном количестве места происходит увеличение емкости внутреннего массива методом ensureCapacity.
     * @param element - элемент который добавляют в список.
     * @return true если элемент успешно добавлен
     * @throws OutOfMemoryError - метод ensureCapacity может выбросить ошибку если емкость массива превышает Integer
     * .MAX_VALUE
     */
    @Override
    public boolean add(T element) {
        ensureCapacity(this.size + 1);
        elements[this.size++] = element;

        return true;
    }

    /**
     * Метод добавления елемента типа T в список о индексу. Проверяет на валидность передаваемого индекса.
     * При недостаточном количестве места происходит увеличение емкости внутреннего массива методом ensureCapacity.
     * @param element - элемент который добавляют в список.
     * @param index - передаваемый индекс
     * @throws OutOfMemoryError - метод ensureCapacity может выбросить ошибку если емкость массива превышает Integer
     * .MAX_VALUE
     */
    @Override
    public void add(int index, T element) {
        checkRange(index);
        ensureCapacity(this.size + 1);
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        this.size++;
    }

    private void ensureCapacity(int capacity) {
        if (capacity > elements.length) {
            int oldCapacity = elements.length;
            Object[] oldElements = this.elements;
            int newSize = newLength(oldCapacity,
                    capacity - oldCapacity,
                    oldCapacity >> 1);
            this.elements = Arrays.copyOf(oldElements, newSize);
        }
    }
    private int newLength(int oldLength,int minGrowth, int prefGrowth){
        int prefLength = oldLength + Math.max(minGrowth, prefGrowth);
        if(0 < prefLength && prefLength <= SOFT_MAX_ARRAY_LENGTH){
            return prefLength;
        } else {
            return hugeLength(oldLength,minGrowth);
        }
    }

    private int hugeLength(int oldLength, int minGrowth) {
        int minLength = oldLength + minGrowth;
        if (minLength < 0) {
            throw new OutOfMemoryError(
                    "Required array length " + oldLength + " + " + minGrowth + " is too large");
        } else return Math.max(minLength, SOFT_MAX_ARRAY_LENGTH);
    }

    private void checkRange(int index) {
        if (index < 0 || index >= (this.size + 1)) {
            throw new IllegalArgumentException("Illegal index: " + index);
        }
    }

    /**
     * Заменяет элемент в указанной позиции в этом списке указанным элементом.
     * @param index - позиция в массиве для вставки
     * @param element - передаваемый элемент
     * @return возвращает элемент, ранее находившийся в указанной позиции
     * @throws IndexOutOfBoundsException – если индекс выходит за пределы диапазона
     */
    @Override
    public T set(int index, T element) {
        checkRange(index);

        @SuppressWarnings("unchecked") T oldValue = (T) elements[index];

        elements[index] = element;

        return oldValue;
    }

    /**
     * Возвращает элемент в указанной позиции в этом списке.
     * @param index - позиция элемента в массиве для возврата
     * @return возвращает элемент на указзанной позиции
     * @throws IndexOutOfBoundsException – если индекс выходит за пределы диапазона
     */
    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkRange(index);

        return (T) elements[index];
    }

    /**
     * Возвращает итератор по элементам в этом списке в правильной последовательности
     * @return Возвращает итератор по элементам в этом списке в правильной последовательности
     */
    @Override
    public Iterator<T> iterator() {
        return new CustomIterator<>();
    }

    /**
     * Удаляет элемент в указанной позиции в этом списке. Сдвигает любые последующие элементы влево.
     * @param index - позиция элемента для удалния
     * @return возвращает удаленный объект
     * @throws IndexOutOfBoundsException – если индекс выходит за пределы диапазона
     */
    @Override
    public T remove(int index) {
        checkRange(index);

        @SuppressWarnings("unchecked") T oldValue = (T) elements[index];

        fastRemove(elements, index);

        return oldValue;
    }
    /**
     * Удаляет принемаеммый элемент, если находит его в списке. Сдвигает любые последующие элементы влево.
     * @param element - элемента для удалния
     * @return возвращает true если объект успешно удален, false если такого элемента не существует в списке.
     */
    @Override
    public boolean remove(T element) {
        final Object[] temp = elements;

        int removeIndex = findElementIndex(temp, element);
        if (removeIndex == -1) {
            return false;
        }

        fastRemove(temp, removeIndex);

        return true;
    }

    private int findElementIndex(Object[] elements, T element) {
        int index = -1;
        final int tempSize = this.size;
        if (element == null) {
            for (int i = 0; i < tempSize; i++) {
                if (elements[i] == null) {
                    index = i;
                }
            }
        } else {
            for (int i = 0; i < tempSize; i++) {
                if (element.equals(elements[i])) {
                    index = i;
                }
            }
        }

        return index;
    }

    private void fastRemove(Object[] elements, int index) {
        final int newSize = size - 1;
        if (newSize > index)
            System.arraycopy(elements, index + 1, elements, index, newSize - index);
        size = newSize;
        elements[size] = null;
    }

    /**
     * Удаляет все элементы из этого списка. Список будет пуст после возврата этого вызова.
     */
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        this.size = 0;
    }

    /**
     * Метод для сравнения объектов
     * @param obj - объект для сравнения
     * @return true если объекты равны
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        CustomArrayList<?> that = (CustomArrayList<?>) obj;
        return size == that.size && Arrays.equals(elements,that.elements);
    }

    /**
     * Метод для вычислени хеш-кода объекта
     * @return возвращает хеш-код объекта
     */
    @Override
    public int hashCode(){
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(elements);
        return result;
    }

    /**
     * Проверяет является ли список пустым
     * @return true сли список пуст
     */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Возвращает количество элементов в списке
     * @return число элементов в списке
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Внутренний класс с реализацией кастомного итератора
     * @param <T>
     */
    private class CustomIterator<T> implements Iterator<T> {

        /**
         * Позиция элемента в списке
         */
        private int current = 0;

        /**
         * Проверка существует ли следующий элемент в списке
         * @return Проверка существует ли следующий элемент в списке
         */
        @Override
        public boolean hasNext() {
            return this.current < size;
        }

        /**
         * Получение следующего элемента в списке
         * @return возвращает следующий элемент в списке
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            @SuppressWarnings("unchecked") T value = (T) elements[current++];
            return value;
        }
    }
}