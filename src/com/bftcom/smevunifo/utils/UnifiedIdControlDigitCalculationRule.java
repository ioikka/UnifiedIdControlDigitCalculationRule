package com.bftcom.smevunifo.utils;

import java.util.HashMap;

/**
 * ������� ������� ������������ ������� ���
 * <p/>
 * ����������� �������� ����������. ��������� �������� ��������� ��������� � ����� 
 * /resources/����������� �������������� ���������� ������ ������ ������������.zip   � 2.3
 * ������� ��� ������ ����� �� ������ http://ms.znate.ru/docs/10/index-40602.html?page=8  �� 15.01.13
 * 
 * Date: 15.01.13
 * @author ikka
 */
public class UnifiedIdControlDigitCalculationRule {
  private static final HashMap<String, Integer> mapSymbolValue = new HashMap<String, Integer>();

  static {
    //Cyrillic
    mapSymbolValue.put("�", 1);
    mapSymbolValue.put("�", 2);
    mapSymbolValue.put("�", 3);
    mapSymbolValue.put("�", 4);
    mapSymbolValue.put("�", 5);
    mapSymbolValue.put("�", 6);
    mapSymbolValue.put("�", 7);
    mapSymbolValue.put("�", 8);
    mapSymbolValue.put("�", 9);
    mapSymbolValue.put("�", 10);
    mapSymbolValue.put("�", 11);
    mapSymbolValue.put("�", 12);
    mapSymbolValue.put("�", 13);
    mapSymbolValue.put("�", 14);
    mapSymbolValue.put("�", 15);
    mapSymbolValue.put("�", 16);
    mapSymbolValue.put("�", 17);
    mapSymbolValue.put("�", 18);
    mapSymbolValue.put("�", 19);
    mapSymbolValue.put("�", 20);
    mapSymbolValue.put("�", 21);
    mapSymbolValue.put("�", 22);
    mapSymbolValue.put("�", 23);
    mapSymbolValue.put("�", 24);
    mapSymbolValue.put("�", 25);
    mapSymbolValue.put("�", 26);
    mapSymbolValue.put("�", 27);
    mapSymbolValue.put("�", 28);
    mapSymbolValue.put("�", 33);
    mapSymbolValue.put("�", 36);
    mapSymbolValue.put("�", 42);
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
   * ������������ �������� ������������ ������� ��� ���
   * <p/>
   * 1. ������� ������� ����, ������� �� �������� �������, ������������� ����� �����, ��������������� ������������
   * ���� ����� �� 1 �� 10. ���� ����������� ���� ������ 10, �� ����� ����� �����������;
   * <p/>
   * 2. ���� ������ ����� � ���� ������������ �����, �� �������� ������� ������� ����������� ������ ������� ��
   * ������� ����������� ������ ����� � �������� �� 10 (��������, ��� ����� �˒ �������� ������� ����� 11 mod 10 = 1,
   * � ��� ����� �O� � ����� 14 mod 10 = 4);
   * <p/>
   * 3. ������ ����� ���� ���������� �� ��� ������� � ����������� ����� ���������� ������������;
   * <p/>
   * 4. ����������� ����� ��� ���� ������������ ����� ������� �� ������� ���������� ����� �� ������ �11�. �����������
   * ����� ������ ����� ���� ������, �������� �������� ��������� � �������� �� 0 �� 9;
   * <p/>
   * 5. ���� ���������� �������, ������ 10, �� ��� ����������� �������������� ������������ ����� ����������
   * �������� ��������� ������, �������� ������ ������������������ �����, ��������� �� ��� ������� ����� (3, 4, 5,�).
   * <p/>
   * 6. ���� � ������ ���������� ������� ������� �� ������� ����� ����������� ������ 10, �� �������� ������������
   * ����� ������������� ������ �0�.
   *
   * @param uin
   * @return �������� ������������ �������
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
   * @param leftShift 0 (��� ������ �������) ��� 2 (��� ������ �������)
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
