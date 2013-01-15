package com.bftcom.smevunifo.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Date: 15.01.13
 * @author ikka
 */
public class UnifiedIdControlDigitCalculationRuleTest {
    @Test
    public void testCalculateControlDigit() throws Exception {
      //1-�� ������� ���������� 10, ������ 5
      Assert.assertTrue(UnifiedIdControlDigitCalculationRule.calculateControlDigit("�00007229740048364Z") == 5);
      //1-�� ������� ���������� 9
      Assert.assertTrue(UnifiedIdControlDigitCalculationRule.calculateControlDigit("M00007229730048364Z") == 9); 
    }
}
