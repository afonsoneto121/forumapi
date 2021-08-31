package com.br.dio.forumapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ParamsType {
    Q("q","none"),
    TITLE("title","none"),
    DESCRIPTION("description","none"),
    ORDER("order","desc"),
    SORT("sort",""),
    PAGE("page","0"),
    LIMIT("limit","10");
    private final String description;
    private final String defaultValue;
}
