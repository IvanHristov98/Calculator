package com.calculator.core;

import com.calculator.core.operator.DivisionOperator;

import org.junit.Test;

public class DivisionOperatorTest
{
    @Test
    public void test()
    {
        DivisionOperator dv = new DivisionOperator();
        System.out.println(dv.getSymbolicRepresentation());
    }
}
