package com.challenge.interfaces;

import java.math.BigDecimal;

public interface Calculavel {
    BigDecimal somar(Object objSum) throws IllegalAccessException, InstantiationException;
    BigDecimal subtrair(Object objSubtract);
    BigDecimal totalizar(Object objTotalize) throws InstantiationException, IllegalAccessException;
}
