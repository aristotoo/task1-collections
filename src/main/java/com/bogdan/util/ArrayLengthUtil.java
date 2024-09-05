package com.bogdan.util;

/**
 * Утилитный класс для расчета величины роста нового массива.
 */
public final class ArrayLengthUtil {
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

    private ArrayLengthUtil(){}

    /**
     * Вычисляет новую длину массива с учетом текущей длины массива,
     * минимальной величины роста и предпочтительной величины роста.
     * Вычисление выполняется безопасным для переполнения способом.
     * Возвращаемая длина обычно ограничивается мягкой максимальной длиной, чтобы избежать
     * достижения предела реализации JVM. Однако мягкий максимум будет превышен, если этого требует минимальная
     * величина роста. Если минимальная требуемая длина превышает Integer.MAX_VALUE,
     * то этот метод выдает OutOfMemoryError. Независимо от значения длины, возвращаемого этим методом,
     * вызывающий может столкнуться с OutOfMemoryError, если кучи недостаточно для выполнения запроса.
     * @param oldLength - старая длинна массива
     * @param minGrowth - минимальная величина прироста длинны массива
     * @param prefGrowth - предпочитаемая величина прироста длинны массива
     * @return новую длинну массива.
     * @throws OutOfMemoryError - если новая величина массива выходит за границы Integer.MAX_VALUE или лимит кучи
     * превышен
     */
    public static int newLength(int oldLength,int minGrowth, int prefGrowth){
        int prefLength = oldLength + Math.max(minGrowth, prefGrowth);
        if(0 < prefLength && prefLength <= SOFT_MAX_ARRAY_LENGTH){
            return prefLength;
        } else {
            return hugeLength(oldLength,minGrowth);
        }
    }

    private static int hugeLength(int oldLength, int minGrowth) {
        int minLength = oldLength + minGrowth;
        if (minLength < 0) {
            throw new OutOfMemoryError(
                    "Required array length " + oldLength + " + " + minGrowth + " is too large");
        } else return Math.max(minLength, SOFT_MAX_ARRAY_LENGTH);
    }
}