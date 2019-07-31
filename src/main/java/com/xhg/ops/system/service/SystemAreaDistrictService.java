package com.xhg.ops.system.service;

import com.xhg.ops.system.model.CyclingAreaDO;
import com.xhg.ops.system.vo.areazone.SystemAreaDistrictListVO;
import com.xhg.ops.system.vo.areazone.SystemAreaListVO;

import java.util.List;

public interface SystemAreaDistrictService {

    Integer insert(CyclingAreaDO cyclingArea);

    Integer insertSelective(CyclingAreaDO cyclingArea);

    void update(CyclingAreaDO cyclingArea);

    void updateBySelective(CyclingAreaDO cyclingArea);

    void delete(Integer id);

    CyclingAreaDO queryById(Integer id);

    Integer queryAllCount(CyclingAreaDO record);

    List<CyclingAreaDO> queryAllList(CyclingAreaDO record);

    void deleteBatch(List<Integer> ids);

    void insertByBatch(List<CyclingAreaDO> cyclingAreaDoList);

    List<SystemAreaDistrictListVO> loadSystemAreaDistrictList(Integer parentId,Integer noteLevel);

    /**
     * 通过areacode获取当前地区信息
     * @param areaCode
     * @return
     */
    CyclingAreaDO queryByCode(String areaCode);

    /**
     * 根据areaCode获取（包括本身）上级集合数据
     * @param code
     * @return
     */
    List<CyclingAreaDO> queryNameByCode(String code);

    /**
     * 根据省级code获取所有市级code
     * @param code
     * @return
     */
    List<String> queryAreaCodeListByParent(String code);

    /**
     * @author wanglijuan
     * @param cyclingArea
     * @return
     */
    List<CyclingAreaDO> queryListByParam(CyclingAreaDO cyclingArea);

    /**
     * 根据地区编码,返回地区List
     * @param areaCodeList
     * @return  list
     */
    List<CyclingAreaDO> queryListByAreaCodeList(List<String> areaCodeList);

    /**
     * @Title:
     * @Description:
     * 	根据区县code编码查询市级城市
     * 	根据市级code查询省级
     * @param areaCode
     * @return CyclingAreaDO
     */
    CyclingAreaDO queryCityByCode(String areaCode);


    /**
     * 根据区县编码查询城市和区县信息
     * 根据市级编码查询城市和省级信息
     * @param areaCode
     * @return
     */
    CyclingAreaDO queryCityAreaInfo(String areaCode);


    /**
     * 通过省级areaCode获取省的所有子集合
     * @param areaCode
     * @return
     */
    List<SystemAreaListVO> queryAllByParentId(String areaCode);

}
