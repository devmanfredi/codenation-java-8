package com.challenge.desafio;

import com.challenge.annotation.Somar;
import com.challenge.annotation.Subtrair;
import com.challenge.interfaces.Calculavel;
import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public class CalculadorDeClasses implements Calculavel {

    @Override
    public BigDecimal somar(Object objSum){
        return reduceValues(objSum,Somar.class);
    }
    @Override
    public BigDecimal subtrair(Object objSubtract) {
        return reduceValues(objSubtract,Subtrair.class);
    }
    private BigDecimal reduceValues(Object object, Class<? extends Annotation> clazz) {
        return Stream.of(object.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(clazz))
                .map(f -> {
                    try {
                        f.setAccessible(true);
                        Object value = f.get(object);
                        return value instanceof BigDecimal ? (BigDecimal) value : BigDecimal.ZERO;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return BigDecimal.ZERO;
                }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal totalizar(Object objTotalize){
        return somar(objTotalize).subtract(subtrair(objTotalize));
    }
}