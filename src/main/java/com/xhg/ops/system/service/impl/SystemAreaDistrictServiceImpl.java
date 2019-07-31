package com.xhg.ops.system.service.impl;

import com.xhg.ops.common.CommonArea;
import com.xhg.ops.system.dao.CyclingAreaDao;
import com.xhg.ops.system.model.CyclingAreaDO;
import com.xhg.ops.system.service.SystemAreaDistrictService;
import com.xhg.ops.system.vo.areazone.SystemAreaDistrictListVO;
import com.xhg.ops.system.vo.areazone.SystemAreaListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SystemAreaDistrictServiceImpl implements SystemAreaDistrictService {

    @Autowired
    private CyclingAreaDao cyclingAreaDao;

    @Override
    public Integer insert(CyclingAreaDO cyclingArea) {
        return cyclingAreaDao.insert(cyclingArea);
    }

    @Override
    public Integer insertSelective(CyclingAreaDO cyclingArea) {
        return cyclingAreaDao.insertSelective(cyclingArea);
    }

    @Override
    public void update(CyclingAreaDO cyclingArea) {
        cyclingAreaDao.update(cyclingArea);
    }

    @Override
    public void updateBySelective(CyclingAreaDO cyclingArea) {
        cyclingAreaDao.updateBySelective(cyclingArea);
    }

    @Override
    public void delete(Integer id) {
        cyclingAreaDao.delete(id);
    }

    @Override
    public CyclingAreaDO queryById(Integer id) {
        return cyclingAreaDao.queryById(id);
    }

    @Override
    public Integer queryAllCount(CyclingAreaDO record) {
        return cyclingAreaDao.queryAllCount(record);
    }


    @Override
    public List<CyclingAreaDO> queryAllList(CyclingAreaDO record) {
        return cyclingAreaDao.queryAllList(record);

    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        cyclingAreaDao.deleteBatch(ids);
    }

    @Override
    public void insertByBatch(List<CyclingAreaDO> cyclingAreaDoList) {
        cyclingAreaDao.insertByBatch(cyclingAreaDoList);
    }

    @Override
    public List<SystemAreaDistrictListVO> loadSystemAreaDistrictList(Integer parentId,Integer noteLevel) {
        List<SystemAreaDistrictListVO> list = new ArrayList<>();
        CyclingAreaDO record = new CyclingAreaDO();
        record.setParentId(parentId);
        record.setNodeLevel(noteLevel);
        List<CyclingAreaDO> cyclingAreaDOList = cyclingAreaDao.queryAllListInfo(record);
        cyclingAreaDOList.stream().forEach(area->{
            SystemAreaDistrictListVO vo = new SystemAreaDistrictListVO();
            vo.setAreaCode(area.getAreaCode());
            vo.setAreaName(area.getAreaName());
            vo.setNodeLevel(noteLevel.toString());
            List<SystemAreaDistrictListVO> systemAreaDistrictListVOList = loadSystemAreaDistrictList(area.getId(),noteLevel+1);
            if(systemAreaDistrictListVOList!=null&&systemAreaDistrictListVOList.size()>0)
                vo.setChildrent(systemAreaDistrictListVOList);

            list.add(vo);
        });

        return list;
    }

    @Override
    public CyclingAreaDO queryByCode(String areaCode) {
        return cyclingAreaDao.queryByCode(areaCode);
    }

    @Override
    public List<CyclingAreaDO> queryNameByCode(String code) {
        return cyclingAreaDao.queryNameByCode(code);
    }

    @Override
    public List<String> queryAreaCodeListByParent(String code) {
        return cyclingAreaDao.queryAreaCodeListByParent(code);
    }

    @Override
    public List<CyclingAreaDO> queryListByParam(CyclingAreaDO cyclingArea) {
        return cyclingAreaDao.queryListByParam(cyclingArea);
    }

    @Override
    public List<CyclingAreaDO> queryListByAreaCodeList(List<String> areaCodeList) {
        return cyclingAreaDao.queryListByAreaCodeList(areaCodeList);
    }

    @Override
    public CyclingAreaDO queryCityByCode(String areaCode) {
        return cyclingAreaDao.queryCityByCode(areaCode);
    }

    @Override
    public CyclingAreaDO queryCityAreaInfo(String areaCode) {
        return cyclingAreaDao.queryCityAreaInfo(areaCode);
    }



    @Override
    public List<SystemAreaListVO> queryAllByParentId(String areaCode) {
        return cyclingAreaDao.queryAllByParentId(areaCode);
    }


    public List<SystemAreaDistrictListVO> loadSystemAreaDistrictList22(Integer parentId,Integer noteLevel) {
        List<SystemAreaDistrictListVO> list = new ArrayList<>();
        CyclingAreaDO record = new CyclingAreaDO();
        List<CyclingAreaDO> cyclingAreaDOList = cyclingAreaDao.queryAllListInfo(record);


        cyclingAreaDOList.stream().forEach(area->{
            SystemAreaDistrictListVO vo = new SystemAreaDistrictListVO();
            if(area.getNodeLevel()==0) {
                vo.setNodeLevel("0");
                vo.setAreaCode(area.getAreaCode());
                vo.setAreaName(area.getAreaName());
            }
            list.add(vo);
        });

        return list;
    }


    private List<SystemAreaDistrictListVO> getTest(List<CyclingAreaDO> cyclingAreaDOList,Integer note) {


        List<SystemAreaDistrictListVO> systemAreaDistrictListVOList = new ArrayList<>();

        cyclingAreaDOList.stream().forEach(area -> {
            SystemAreaDistrictListVO vo = new SystemAreaDistrictListVO();
            if (area.getNodeLevel() == note) {
                vo.setNodeLevel(note.toString());
                vo.setAreaCode(area.getAreaCode());
                vo.setAreaName(area.getAreaName());
                Integer nextOne = note + 1;
                vo.setChildrent(getTest(cyclingAreaDOList, nextOne));
            }
            systemAreaDistrictListVOList.add(vo);
        });
        return systemAreaDistrictListVOList;
    }
}
