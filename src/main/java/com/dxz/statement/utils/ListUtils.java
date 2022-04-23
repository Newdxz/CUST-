package com.dxz.statement.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public static List<String> findListDiff(List<String> list1, List<String> list2) {
        List<String> diff = new ArrayList<>();
        List<String> maxList = list1;
        List<String> minList = list2;
        if (list2.size() > list1.size()) {
            minList = list1;
            maxList = list2;
        }
        Map<String, Integer> tempMap = new HashMap<>(maxList.size());
        for (String max : maxList) {
            tempMap.put(max, 1);
        }
        for (String min : minList) {
            if (tempMap.get(min) != null) {
                tempMap.put(min, 2);
                continue;
            }
            diff.add(min);
        }
        return diff;
    }

}
