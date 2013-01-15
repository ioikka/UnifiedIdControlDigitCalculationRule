package com.bftcom.smevunifo.utils;

import java.util.HashMap;

/**
 * Правила расчета контрольного разряда УИН
 * <p/>
 * Реализовано согласно постановке. Подробное описание алгоритма находится в файле 
 * /resources/Департамент информационных технологий города москвы информационн.zip   п 2.3
 * примеры для тестов взяты по адресу http://ms.znate.ru/docs/10/index-40602.html?page=8  на 15.01.13
 * 
 * Date: 15.01.13
 * @author ikka
 */
public class UnifiedIdControlDigitCalculationRule {
  private static final HashMap<String, Integer> mapSymbolValue = new HashMap<String, Integer>();

  static {
    //Cyrillic
    mapSymbolValue.put("А", 1);
    mapSymbolValue.put("Б", 2);
    mapSymbolValue.put("В", 3);
    mapSymbolValue.put("Г", 4);
    mapSymbolValue.put("Д", 5);
    mapSymbolValue.put("Е", 6);
    mapSymbolValue.put("Ж", 7);
    mapSymbolValue.put("З", 8);
    mapSymbolValue.put("И", 9);
    mapSymbolValue.put("К", 10);
    mapSymbolValue.put("Л", 11);
    mapSymbolValue.put("М", 12);
    mapSymbolValue.put("Н", 13);
    mapSymbolValue.put("О", 14);
    mapSymbolValue.put("П", 15);
    mapSymbolValue.put("Р", 16);
    mapSymbolValue.put("С", 17);
    mapSymbolValue.put("Т", 18);
    mapSymbolValue.put("У", 19);
    mapSymbolValue.put("Ф", 20);
    mapSymbolValue.put("Х", 21);
    mapSymbolValue.put("Ц", 22);
    mapSymbolValue.put("Ч", 23);
    mapSymbolValue.put("Ш", 24);
    mapSymbolValue.put("Щ", 25);
    mapSymbolValue.put("Э", 26);
    mapSymbolValue.put("Ю", 27);
    mapSymbolValue.put("Я", 28);
    mapSymbolValue.put("Ъ", 33);
    mapSymbolValue.put("Ы", 36);
    mapSymbolValue.put("Ь", 42);
    //Latin
    mapSymbolValue.put("A", 1);
    mapSymbolValue.put("B", 3);
    mapSymbolValue.put("E", 6);
    mapSymbolValue.put("K", 10);
    mapSymbolValue.put("M", 12);
    mapSymbolValue.put("H", 13);
    mapSymbolValue.put("O", 14);
    mapSymbolValue.put("P", 16);
    mapSymbolValue.put("C", 17);
    mapSymbolValue.put("T", 18);
    mapSymbolValue.put("Y", 19);
    mapSymbolValue.put("X", 21);
    mapSymbolValue.put("D", 29);
    mapSymbolValue.put("F", 30);
    mapSymbolValue.put("G", 31);
    mapSymbolValue.put("I", 32);
    mapSymbolValue.put("J", 33);
    mapSymbolValue.put("L", 34);
    mapSymbolValue.put("N", 35);
    mapSymbolValue.put("Q", 36);
    mapSymbolValue.put("R", 37);
    mapSymbolValue.put("S", 38);
    mapSymbolValue.put("U", 39);
    mapSymbolValue.put("V", 40);
    mapSymbolValue.put("W", 41);
    mapSymbolValue.put("Z", 42);
    //digits
    for (int i = 0; i < 10; i++) {
      mapSymbolValue.put(String.valueOf(i), i);
    }
  }

  /**
   * Рассчитывает значение контрольного разряда для УИН
   * <p/>
   * 1. каждому разряду кода, начиная со старшего разряда, присваивается набор весов, соответствующий натуральному
   * ряду чисел от 1 до 10. Если разрядность кода больше 10, то набор весов повторяется;
   * <p/>
   * 2. если вместо цифры в коде присутствует буква, то значение данного разряда принимается равным остатку от
   * деления порядкового номера буквы в алфавите на 10 (например, для буквы ‘Л’ значение разряда равно 11 mod 10 = 1,
   * а для буквы ‘O’ – равно 14 mod 10 = 4);
   * <p/>
   * 3. каждая цифра кода умножается на вес разряда и вычисляется сумма полученных произведений;
   * <p/>
   * 4. контрольное число для кода представляет собой остаток от деления полученной суммы на модуль «11». Контрольное
   * число должно иметь один разряд, значение которого находится в пределах от 0 до 9;
   * <p/>
   * 5. если получается остаток, равный 10, то для обеспечения одноразрядного контрольного числа необходимо
   * провести повторный расчет, применяя вторую последовательность весов, сдвинутую на два разряда влево (3, 4, 5,…).
   * <p/>
   * 6. Если в случае повторного расчета остаток от деления вновь сохраняется равным 10, то значение контрольного
   * числа проставляется равным «0».
   *
   * @param uin
   * @return значение контрольного разряда
   */
  public static int calculateControlDigit(String uin) {
    String[] strings = uin.split("(?!^)");
    int result;
    try {
      result = run(strings, 0);
    } catch (ArithmeticException e) {
      try {
        result = run(strings, 2);
      } catch (ArithmeticException ex) {
        result = 0;
      }
    }
    return result;
  }

  /**
   * @param strings
   * @param leftShift 0 (для первой попытки) или 2 (для второй попытки)
   * @return
   */
  static private int run(String[] strings, int leftShift) {
    int controlSum = 0;
    for (int i = 0; i < strings.length; i++) {
      controlSum += (mapSymbolValue.get(strings[i]) % 10) * (((i + leftShift) % 10) + 1);
    }
    if ((controlSum % 11) == 10) {
      throw new ArithmeticException();
    }
    return controlSum % 11;
  }
}
