package com.deepblue.library.adapter;

import java.util.HashMap;

/**
 * @author liumq
 * @date 2015/8/29
 */
public class ItemTypeUtil {

    private HashMap<Object, Integer> typePool;

    void setTypePool(HashMap<Object, Integer> typePool) {
        this.typePool = typePool;
    }

    /**
     * @param type item的类型
     * @return 通过object类型的type来得到int类型的type
     */
    public int getIntType(Object type) {
        if (typePool == null) {
            typePool = new HashMap<>();
        }
        
        if (!typePool.containsKey(type)) {
            typePool.put(type, typePool.size());
        }
        return typePool.get(type);
    }
}
