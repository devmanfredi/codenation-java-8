package com.challenge.desafio;

import com.challenge.annotation.Somar;
import com.challenge.annotation.Subtrair;
import com.challenge.interfaces.Calculavel;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public class CalculadorDeClasses implements Calculavel {

    @Override
    public BigDecimal somar(Object objSum) throws IllegalAccessException, InstantiationException {
        if (objSum == null) {
            return BigDecimal.ZERO;
        }
        Field[] declaredFields = objSum.getClass().getDeclaredFields();
        List<Field> fieldList = Stream.of(declaredFields)
                .filter(field -> field.getType().equals(BigDecimal.class))
                .filter(field -> field.isAnnotationPresent(Somar.class))
                .collect(Collectors.toList());
        List<BigDecimal> bigDecimalList = fieldList.stream()
                .map(field -> {
                    try {
                        field.setAccessible(true);
                        BigDecimal obj = field.get(objSum) instanceof BigDecimal ? (BigDecimal) field.get(objSum) : BigDecimal.ZERO;
                        return obj;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return BigDecimal.ZERO;

                }).collect(Collectors.toList());
        return bigDecimalList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal subtrair(Object objSubtract) {
        if (objSubtract == null) {
            return BigDecimal.ZERO;
        }
        Field[] declaredFields = objSubtract.getClass().getDeclaredFields();
        List<Field> fieldList = Stream.of(declaredFields)
                .filter(field -> field.getType().equals(BigDecimal.class))
                .filter(field -> field.isAnnotationPresent(Subtrair.class))
                .collect(Collectors.toList());
        List<BigDecimal> bigDecimalList = fieldList.stream()
                .map(field -> {
                    try {
                        field.setAccessible(true);
                        BigDecimal obj = field.get(objSubtract) instanceof BigDecimal ? (BigDecimal) field.get(objSubtract) : BigDecimal.ZERO;
                        return obj;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return BigDecimal.ZERO;
                }).collect(Collectors.toList());
        return bigDecimalList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal totalizar(Object objTotalize) throws InstantiationException, IllegalAccessException {
        if(objTotalize == null){return BigDecimal.ZERO;}
        //Passo o objeto para a função de somar e também para a de subtrair.
        return somar(objTotalize).subtract(subtrair(objTotalize));
    }
}