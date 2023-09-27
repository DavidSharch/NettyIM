package com.im.common.router.hash;

import com.im.common.enums.UserErrorCode;
import lombok.extern.slf4j.Slf4j;

import java.util.SortedMap;
import java.util.TreeMap;

@Slf4j
public class TreeMapConsistentHash extends AbstractConsistentHash {

    private TreeMap<Long,String> treeMap = new TreeMap<>();

    /**
     * 虚拟节点数量
     */
    private static final int VIRTUAL_NODE_SIZE = 2;

    @Override
    protected void add(long key, String value) {
        for (int i = 0; i < VIRTUAL_NODE_SIZE; i++) {
            // 创建虚拟节点
            treeMap.put(super.hash("virtual_node" + key + i), value);
        }
        treeMap.put(key, value);
    }

    
    @Override
    protected String getFirstNodeValue(String value) {
        Long hash = super.hash(value);
        // 返回map中的下一个节点
        SortedMap<Long, String> last = treeMap.tailMap(hash);
        if (!last.isEmpty()) {
            return last.get(last.firstKey());
        }
        if (treeMap.size() == 0){
            UserErrorCode err = UserErrorCode.SERVER_NOT_AVAILABLE;
            log.error(
                    "一致性hash均衡错误,{},{}", err.getCode(), err.getError()
            );
        }
        return treeMap.firstEntry().getValue();
    }

    @Override
    protected void processBefore() {
        treeMap.clear();
    }
}