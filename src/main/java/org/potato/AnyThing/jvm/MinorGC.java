package org.potato.AnyThing.jvm;

import java.util.HashMap;

/**
 * Created by potato on 2018/4/17.
 */
public class MinorGC {
    private static final int _1MB = 1024*1024;
    public static void main(String[] args) {
        testPretenureSizeThreshold();
    }

    /**
     * 示范 MinorCG的产生
     * VM参数： -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     * 堆大小固定20M，新生代10M，Eden区与一个Survivor区空间比例为8:1.
     */
    public static void testAllocation(){
        byte[] allocation1,allocation2,allocation3,allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];
    }
    /**
     *  大对象直接进入老年代
     * VM参数： -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     * -XX:PretenureSizeThreshold=3145728
     * 堆大小固定20M，新生代10M，Eden区与一个Survivor区空间比例为8:1.
     * 对于大于3M的对象直接在老年代分配
     */
    public static void testPretenureSizeThreshold(){
        byte[] allocation;
        allocation = new byte[3* _1MB];
    }
}
