package com.raysmond.blog.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Raysmond<i@raysmond.com>
 */
public class DTOUtil {

    private static ModelMapper MAPPER = null;

    private static ModelMapper getMapper(){
        if(MAPPER == null){
            MAPPER = new ModelMapper();
            MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        }

        return MAPPER;
    }

    public static <S, T> T map(S source, Class<T> targetClass) {
        return getMapper().map(source, targetClass);
    }

    public static <S, T> void mapTo(S source, T dist) {
        getMapper().map(source, dist);
    }

    public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        List<T> list = new ArrayList<>();
        for (S s : source) {
            list.add(getMapper().map(s, targetClass));
        }
        return list;
    }
}
