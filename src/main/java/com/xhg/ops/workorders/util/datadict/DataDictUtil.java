package com.xhg.ops.workorders.util.datadict;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 数据字典工具类
 * @author 刘涛
 * @date 2018年7月23日
 */
public class DataDictUtil {

	/**
	 * 转换对象为字典对象
	 * @param list
	 * @return
	 */
	public static List<DataDict> covertDataDict(Collection<? extends IDataDict> list){
		List<DataDict> dataDictList = new ArrayList<>();
		if(list == null || list.isEmpty()) {
			return dataDictList;
		}
		list.forEach(e -> {
			dataDictList.add(DataDictUtil.covertDataDict(e));
		});
		return dataDictList;
	}
	
	/**
	 *  转换对象为字典对象
	 * @param dict
	 * @return
	 */
	public static DataDict covertDataDict(IDataDict dict){
		if(dict == null) {
			return null;
		}
		DataDict dataDict = new DataDict();
		dataDict.setCode(dict.getDictCode());
		dataDict.setName(dict.getDictName());
		return dataDict;
	}
}
