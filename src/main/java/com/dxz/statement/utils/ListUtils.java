package com.dxz.statement.utils;

import java.util.*;

/**
 * @title: ListUtils
 * @Author dxz
 * @Date: 2022/4/23 0023 10:35
 */
public class ListUtils {
    /**
     * 找出list2 相对于list1 新的数据
     *
     * @author dxz
     * @date 10:37 2022/4/23 0023
     **/
    public static List<String> findListDiff(List<String> listA, List<String> listB) {
        List<String> diff = new ArrayList<>();

        Map<String, Integer> tempMap = new HashMap<>(listA.size());
        for (String A : listA) {
            tempMap.put(A, 1);
        }
        for (String B : listB) {
            if (tempMap.get(B) != null) {
                // tempMap.put(B, 2);
                continue;
            }
            diff.add(B);
        }
        return diff;
    }

}
