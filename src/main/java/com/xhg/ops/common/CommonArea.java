package com.xhg.ops.common;

import com.xhg.ops.system.model.CyclingAreaDO;

import java.util.ArrayList;
import java.util.List;

/**
 * 全国区域
 */
public class CommonArea {

    public static List<CyclingAreaDO> COMMON_AREA_LIST = new ArrayList<>();


    public static void initAreaList(List<CyclingAreaDO> list){
        COMMON_AREA_LIST = list;
    }
}
