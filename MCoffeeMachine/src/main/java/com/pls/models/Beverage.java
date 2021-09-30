package com.pls.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class Beverage {
    private String beverageName;
    private Map<String, Double> quantities;
}
