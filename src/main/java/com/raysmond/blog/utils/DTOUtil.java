package com.raysmond.blog.utils;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Raysmond<jiankunlei@gmail.com>
 */
public class DTOUtil {

    private static final ModelMapper MAPPER = new ModelMapper();

    public static <S, T> T map(S source, Class<T> targetClass) {
        return MAPPER.map(source, targetClass);
    }

    public static <S, T> void mapTo(S source, T dist) {
        MAPPER.map(source, dist);
    }

    public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        List<T> list = new ArrayList<>();
        for (S s : source) {
            list.add(MAPPER.map(s, targetClass));
        }
        return list;
    }
}
