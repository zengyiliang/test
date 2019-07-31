package com.xhg.ops.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhg.core.util.crypto.MD5Utils;
import com.xhg.core.web.vo.PagerResult;
import com.xhg.ops.system.dao.SystemUserDao;
import com.xhg.ops.system.dto.SystemUserAddDTO;
import com.xhg.ops.system.dto.SystemUserLoadDTO;
import com.xhg.ops.system.dto.SystemUserQueryDTO;
import com.xhg.ops.system.entity.OpsSystemRoleDO;
import com.xhg.ops.system.enums.SystemUserEnum;
import com.xhg.ops.system.model.*;
import com.xhg.ops.system.service.*;
import com.xhg.ops.system.vo.user.*;
import com.xhg.ops.utils.GenerateNumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SystemUserServiceImpl implements SystemUserService {

    @Autowired
    private SystemUserDao systemUserDao;

    @Autowired
    private SystemUserOperateRecordService systemUserOperateRecordService;

    @Autowired
    private SystemAreaZoneService systemAreaZoneService;

    @Autowired
    private SystemDeptService systemDeptService;

    @Autowired
    private SystemAreaDistrictService systemAreaDistrictService;

    @Autowired
    private OpsSystemUserRoleService opsSystemUserRoleService;

    @Autowired
    private OpsSystemRoleService opsSystemRoleService;





    @Override
    public int deleteByPrimaryKey(Integer id) {
        return systemUserDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SystemUser record) {
        return systemUserDao.insert(record);
    }

    @Override
    public int insertSelective(SystemUser record) {
        return systemUserDao.insertSelective(record);
    }

    @Override
    public SystemUser selectByPrimaryKey(Integer id) {
        return systemUserDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SystemUser record) {
        return systemUserDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SystemUser record) {
        return systemUserDao.updateByPrimaryKey(record);
    }

    @Override
    public int queryAllCount(SystemUser record) {
        return systemUserDao.queryAllCount(record);
    }

    @Override
    public List<SystemUser> queryAllList(SystemUser record) {
        return systemUserDao.queryAllList(record);
    }



    @Override
    public Map<String, Object> saveSystemUser(SystemUserAddDTO systemUserAddDTO){
        Map<String, Object> map = new HashMap<>();
        if(!validateUserNo(systemUserAddDTO.getUserNo(),0)){
            map.put("message","已有该工号信息，不可重复创建！");
        }else if(!validatePhone(systemUserAddDTO.getPhone(),0)){
            map.put("message","已有该手机号信息，不可重复创建！");
        }else {
            //生成8位数随机密码
            String passwordStr = GenerateNumUtil.getRandomCharAndNumr(8);
            SystemUser systemUser = new SystemUser();
            systemUser.setEnable(1);
            systemUser.setPassword(MD5Utils.md5(passwordStr));
            systemUser.setName(systemUserAddDTO.getName());
            systemUser.setPhone(systemUserAddDTO.getPhone());
            systemUser.setUserNo(systemUserAddDTO.getUserNo());
            systemUser.setCreatedTime(new Date());
            systemUser.setUpdatedTime(new Date());
            systemUser.setRefAreaId(Integer.parseInt(systemUserAddDTO.getAreaZoneId()));
            systemUser.setDeptId(Integer.parseInt(systemUserAddDTO.getDeptId()));
            //职位
            systemUser.setPositionId(Integer.parseInt(systemUserAddDTO.getPositionId()));
            //add 省市区街道,========================布点系统===============================
            //街道的判断
            String cityStreetCodes = systemUserAddDTO.getCityStreet();
            if(cityStreetCodes!=null&&!"".equals(cityStreetCodes)) {
                if (cityStreetCodes.contains(",")) {
                    String[] codes = cityStreetCodes.split(",");
                    for (String cityStreetCode : codes) {
                        if (validateCityStreet(cityStreetCode, null)) {
                            map.put("message", "同一个街道不可分配给多人！");
                            return map;
                        }
                    }
                } else {
                    if (validateCityStreet(cityStreetCodes, null)) {
                        map.put("message", "同一个街道不可分配给多人！");
                        return map;
                    }
                }
            }
            systemUser.setProvince(systemUserAddDTO.getProvince());
            systemUser.setCity(systemUserAddDTO.getCity());
            systemUser.setCityArea(systemUserAddDTO.getCityArea());
            systemUser.setCityStreet(systemUserAddDTO.getCityStreet());

            //add 省市区街道,========================布点系统===============================
            systemUser.setUpdatedUserId(systemUserAddDTO.getId()==null?1:systemUserAddDTO.getId());
            systemUser.setCreatedUserId(systemUserAddDTO.getId()==null?1:systemUserAddDTO.getId());
            OpsSystemRoleDO opsSystemRoleDO = opsSystemRoleService.queryById(Integer.parseInt(systemUserAddDTO.getRoleId()));
            if(opsSystemRoleDO!=null)
                systemUser.setRoleNames(opsSystemRoleDO.getName());
            //默认普通用户
            //systemUser.setUserType(Integer.parseInt(systemUserAddDTO.getBackRoleId()));
            systemUser.setUserType(1);
            systemUserDao.insertSelective(systemUser);

            //用户角色关联表
            OpsSystemUserRole opsSystemUserRole = new OpsSystemUserRole();
            opsSystemUserRole.setUserId(systemUser.getId());
            opsSystemUserRole.setCreatedTime(new Date());
            opsSystemUserRole.setCreatedUserId(systemUserAddDTO.getId()==null?1:systemUserAddDTO.getId());
            opsSystemUserRole.setUpdatedTime(new Date());
            opsSystemUserRole.setUpdatedUserId(systemUserAddDTO.getId()==null?1:systemUserAddDTO.getId());
            opsSystemUserRole.setEnable(1);
            opsSystemUserRole.setRoleId(Integer.parseInt(systemUserAddDTO.getRoleId()));

            opsSystemUserRoleService.insertSelective(opsSystemUserRole);
            //添加用户操作日志
            SystemUserOperateRecord systemUserOperateRecord = new SystemUserOperateRecord();
            systemUserOperateRecord.setOperateType(SystemUserEnum.CREATE_USER.getCode());
            systemUserOperateRecord.setRemark(SystemUserEnum.CREATE_USER.getValue());
            systemUserOperateRecord.setUserId(systemUser.getId());
            systemUserOperateRecord.setEnable(1);
            systemUserOperateRecord.setCreatedUserId(systemUserAddDTO.getId()==null?1:systemUserAddDTO.getId());
            systemUserOperateRecord.setCreatedTime(new Date());
            systemUserOperateRecord.setUpdatedUserId(systemUserAddDTO.getId()==null?1:systemUserAddDTO.getId());
            systemUserOperateRecord.setUpdatedTime(new Date());
            systemUserOperateRecordService.insertSelective(systemUserOperateRecord);
            //返回一个生成的密码
            map.put("password", passwordStr);
        }
        opsSystemUserRoleService.queryByUserRoleList();
        return map;
    }


    @Override
    public PagerResult<SystemUserQueryVO> queryListPage(SystemUserQueryDTO systemUserQueryDTO) {
        PagerResult<SystemUserQueryVO> result = new PagerResult<>();
        SystemUser systemUser = new SystemUser();
        systemUser.setName(systemUserQueryDTO.getName());
        systemUser.setUserNo(systemUserQueryDTO.getUserNo());
        systemUser.setPhone(systemUserQueryDTO.getPhone());

        String tempAreaZoneId = systemUserQueryDTO.getAreaZoneId();
        if(tempAreaZoneId !=null&&!"".equals(tempAreaZoneId)){
            systemUser.setRefAreaId(Integer.valueOf(tempAreaZoneId));
        }
        //分页
        PageHelper.startPage(systemUserQueryDTO.getCurrentPage(),systemUserQueryDTO.getPageSize());
        PageInfo<SystemUser> pageInfo = new PageInfo<>(systemUserDao.queryAllList(systemUser));

        List<SystemUserQueryVO> vos = new ArrayList<>();
        if(pageInfo.getList()!=null&&pageInfo.getList().size()>0){

            pageInfo.getList().stream().forEach(user->{
                SystemUserQueryVO vo = new SystemUserQueryVO();
                vo.setId(user.getId());
                vo.setName(user.getName());
                vo.setUserNo(user.getUserNo());
                vo.setPhone(user.getPhone());
                vo.setAreaZone(systemAreaZoneService.selectByPrimaryKey(user.getRefAreaId()).getName());
                vo.setDeptName(systemDeptService.selectByPrimaryKey(user.getDeptId()).getName());
                vo.setRoleName(user.getRoleNames());
                vos.add(vo);
            });
        }
        result.setList(vos);
        result.setCurrentPage(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotal(pageInfo.getTotal());
        result.setTotalPages(pageInfo.getPages());

        return result;
    }

    @Override
    public SystemUserEditVO loadSystemUserById(Integer id) {
        SystemUserEditVO vo = new SystemUserEditVO();
        SystemUser systemUser = systemUserDao.selectByPrimaryKey(id);
        if(systemUser!=null){
            vo.setAreaZoneId(systemUser.getRefAreaId().toString());
            vo.setPositionId(systemUser.getPositionId().toString());//职位
            vo.setDeptId(systemUser.getDeptId().toString());
            vo.setId(systemUser.getId());
            vo.setName(systemUser.getName());
            vo.setPhone(systemUser.getPhone());
            vo.setUserNo(systemUser.getUserNo());
            vo.setProvince(systemUser.getProvince());
            vo.setCity(systemUser.getCity());
            vo.setCityArea(systemUser.getCityArea());
            vo.setCityStreet(systemUser.getCityStreet());
            //vo.setBackRoleId(systemUser.getUserType().toString());
            Map<String,Object> map = new HashMap<>();
            map.put("name",systemUser.getRoleNames());
            List<OpsSystemRoleDO> list = opsSystemRoleService.queryAllList(map);
            if(list!=null&&list.size()>0){
                OpsSystemRoleDO opsSystemRoleDO = list.get(0);
                vo.setRoleId(opsSystemRoleDO.getId().toString());
            }

        }
        return vo;
    }

    @Override
    public Map<String, Object> updateSystemUser(SystemUserEditVO systemUserEditVO,Integer currentUserId) {
        Map<String, Object> map = new HashMap<>();
        if(!validateUserNo(systemUserEditVO.getUserNo(),systemUserEditVO.getId())){
            map.put("message","已有该工号信息，不可重复创建！");
        }else if(!validatePhone(systemUserEditVO.getUserNo(),systemUserEditVO.getId())) {
            map.put("message","已有该手机号信息，不可重复创建！");
        }else{

            SystemUser systemUser = new SystemUser();
            systemUser.setId(systemUserEditVO.getId());
            systemUser.setPhone(systemUserEditVO.getPhone());
            systemUser.setUserNo(systemUserEditVO.getUserNo());
            systemUser.setName(systemUserEditVO.getName());
            //add 省市区街道
            //街道的判断
            String cityStreetCodes = systemUserEditVO.getCityStreet();
            if(cityStreetCodes!=null&&!"".equals(cityStreetCodes)) {
                if (cityStreetCodes.contains(",")) {
                    String[] codes = cityStreetCodes.split(",");
                    for (String cityStreetCode : codes) {
                        if (validateCityStreet(cityStreetCode, systemUserEditVO.getId())) {
                            map.put("message", "同一个街道不可分配给多人！");
                            return map;
                        }
                    }
                } else {
                    if (validateCityStreet(cityStreetCodes, systemUserEditVO.getId())) {
                        map.put("message", "同一个街道不可分配给多人！");
                        return map;
                    }
                }
            }
            systemUser.setProvince(systemUserEditVO.getProvince());
            systemUser.setCity(systemUserEditVO.getCity());
            systemUser.setCityArea(systemUserEditVO.getCityArea());
            systemUser.setCityStreet(systemUserEditVO.getCityStreet());
            //
            systemUser.setRefAreaId(Integer.parseInt(systemUserEditVO.getAreaZoneId()));
            systemUser.setDeptId(Integer.parseInt(systemUserEditVO.getDeptId()));


            systemUser.setPositionId(Integer.parseInt(systemUserEditVO.getPositionId()));
            systemUser.setUpdatedTime(new Date());
            systemUser.setUpdatedUserId(currentUserId==null?1:currentUserId);
            OpsSystemRoleDO opsSystemRoleDO = opsSystemRoleService.queryById(Integer.parseInt(systemUserEditVO.getRoleId()));
            if(opsSystemRoleDO!=null)
                systemUser.setRoleNames(opsSystemRoleDO.getName());
            //systemUser.setUserType(Integer.parseInt(systemUserEditVO.getBackRoleId()));
            systemUserDao.updateByPrimaryKeySelective(systemUser);
            //修改用户角色关联表
            OpsSystemUserRole opsSystemUserRole = new OpsSystemUserRole();
            opsSystemUserRole.setRoleId(Integer.parseInt(systemUserEditVO.getRoleId()));
            opsSystemUserRole.setUserId(systemUserEditVO.getId());
            opsSystemUserRoleService.updateRoleByUserId(opsSystemUserRole);

            //添加用户操作日志
            SystemUserOperateRecord systemUserOperateRecord = new SystemUserOperateRecord();
            systemUserOperateRecord.setOperateType(SystemUserEnum.EDIT_USER.getCode());
            systemUserOperateRecord.setRemark(SystemUserEnum.EDIT_USER.getValue());
            systemUserOperateRecord.setUserId(systemUser.getId());
            systemUserOperateRecord.setEnable(1);
            systemUserOperateRecord.setCreatedUserId(currentUserId==null?1:currentUserId);
            systemUserOperateRecord.setCreatedTime(new Date());
            systemUserOperateRecord.setUpdatedUserId(currentUserId==null?1:currentUserId);
            systemUserOperateRecord.setUpdatedTime(new Date());
            systemUserOperateRecordService.insertSelective(systemUserOperateRecord);

        }
        //
        opsSystemUserRoleService.queryByUserRoleList();
        return map;
    }

    @Override
    public Map<String, Object> updateResetSystemUserPassword(Integer id,Integer currentUserId) {
        Map<String,Object> map = new HashMap<>();
        String passwordStr = GenerateNumUtil.getRandomCharAndNumr(8);
        map.put("password",MD5Utils.md5(passwordStr));
        map.put("id",id);
        map.put("updatedTime",new Date());
        map.put("updatedUserId",currentUserId==null?1:currentUserId);
        systemUserDao.updateResetSystemUserPassword(map);
        //用户操作记录添加
        SystemUserOperateRecord systemUserOperateRecord = new SystemUserOperateRecord();
        systemUserOperateRecord.setOperateType(SystemUserEnum.RESET_USER_PASSWORD.getCode());
        systemUserOperateRecord.setRemark(SystemUserEnum.RESET_USER_PASSWORD.getValue());
        systemUserOperateRecord.setUserId(id);
        systemUserOperateRecord.setEnable(1);
        systemUserOperateRecord.setCreatedUserId(currentUserId==null?1:currentUserId);
        systemUserOperateRecord.setCreatedTime(new Date());
        systemUserOperateRecord.setUpdatedUserId(currentUserId==null?1:currentUserId);
        systemUserOperateRecord.setUpdatedTime(new Date());
        systemUserOperateRecordService.insertSelective(systemUserOperateRecord);

        map.put("password",passwordStr);
        map.remove("id");
        map.remove("updatedTime");
        map.remove("updatedUserId");
        return map;

    }

    @Override
    public SystemUserViewVO viewSystemUserById(Integer id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SystemUserViewVO systemUserViewVO = new SystemUserViewVO();
        List<SystemUserOperateVO> vos = new ArrayList<>();

        SystemUser systemUser = systemUserDao.selectByPrimaryKey(id);
        if(systemUser!=null){
            systemUserViewVO.setAreaZone(systemAreaZoneService.selectByPrimaryKey(systemUser.getRefAreaId()).getName());
            systemUserViewVO.setDeptName(systemDeptService.selectByPrimaryKey(systemUser.getDeptId()).getName());
            //???
            //systemUserViewVO.setPositionName(systemUser.getRoleNames());
            systemUserViewVO.setRoleName(systemUser.getRoleNames());
            systemUserViewVO.setId(systemUser.getId());
            systemUserViewVO.setName(systemUser.getName());
            systemUserViewVO.setPhone(systemUser.getPhone());
            systemUserViewVO.setUserNo(systemUser.getUserNo());
        }

        //查询用户操作日志
        List<SystemUserOperateRecord> systemUserOperateRecordList = systemUserOperateRecordService.selectByUserId(id);
        if(systemUserOperateRecordList!=null&&systemUserOperateRecordList.size()>0){
            systemUserOperateRecordList.forEach(operate->{
                SystemUserOperateVO vo = new SystemUserOperateVO();
                vo.setContent(operate.getRemark());
                vo.setOperator(systemUserDao.selectByPrimaryKey(operate.getUpdatedUserId()).getName());
                vo.setOperateTime(sdf.format(operate.getCreatedTime()));
                vos.add(vo);
            });
        }
        systemUserViewVO.setUserOperateList(vos);
        return systemUserViewVO;
    }

    @Override
    public SystemUser validateSystemUser(SystemUserLoginVO systemUserLoginVO) {

        SystemUser systemUser = new SystemUser();
        systemUser.setName(systemUserLoginVO.getPhone());
        String password = MD5Utils.md5(systemUserLoginVO.getPassword());
        systemUser.setPassword(password);
        List<SystemUser> systemUsers = systemUserDao.queryAllList(systemUser);
        if(systemUsers!=null&&systemUsers.size()>0){
            return systemUsers.get(0);
        }
        return null;
    }

    @Override
    public SystemAppUserViewVO querySystemAppUserViewVO(SystemUser param) {
        SystemAppUserViewVO vo = new SystemAppUserViewVO();
        SystemUser systemUser = systemUserDao.selectByPrimaryKey(param.getId());
        String deptName = systemDeptService.selectByPrimaryKey(systemUser.getDeptId()).getName();
        String areaZoneName = systemAreaZoneService.selectByPrimaryKey(systemUser.getRefAreaId()).getName();
        String roleName = systemUser.getRoleNames();
        String userInfo = deptName+"-"+areaZoneName+"-"+roleName;
        String phone = systemUser.getPhone();
        phone = phone.substring(0,3)+"****"+phone.substring(phone.length()-4);
        vo.setId(systemUser.getId());
        vo.setName(systemUser.getName());
        vo.setUserInfo(userInfo);
        vo.setPhone(phone);

        return vo;
    }

    @Override
    public Map<String, Object> updateModifySystemUserPassword(SystemUser param,String oldPassword) {
        Map<String, Object> map = new HashMap<>();
        //查询旧的密码
        SystemUser systemUser = new SystemUser();
        systemUser.setPassword(MD5Utils.md5(oldPassword));
        systemUser.setId(param.getId());
        List<SystemUser> list = systemUserDao.queryAllList(systemUser);
        if(list!=null&&list.size()>0){
            map.put("id",param.getId());
            map.put("password",MD5Utils.md5(param.getPassword()));
            map.put("updatedTime",new Date());
            map.put("updatedUserId",param.getId());
            systemUserDao.updateResetSystemUserPassword(map);
            //添加用户操作日志
            SystemUserOperateRecord systemUserOperateRecord = new SystemUserOperateRecord();
            systemUserOperateRecord.setOperateType(SystemUserEnum.UPDATE_USER_PASSWORD.getCode());
            systemUserOperateRecord.setRemark(SystemUserEnum.UPDATE_USER_PASSWORD.getValue());
            systemUserOperateRecord.setUserId(param.getId());
            systemUserOperateRecord.setEnable(1);
            //操作用户是他自己
            systemUserOperateRecord.setCreatedUserId(param.getId());
            systemUserOperateRecord.setCreatedTime(new Date());
            systemUserOperateRecord.setUpdatedUserId(param.getId());
            systemUserOperateRecord.setUpdatedTime(new Date());
            systemUserOperateRecordService.insertSelective(systemUserOperateRecord);
            //修改密码成功
            map.put("code","1");
        }else {
            map.put("code","-1");
        }
        return map;
    }

    @Override
    public Map<String, Object> deleteSystemUser(Integer userId) {
        Map<String, Object> map = new HashMap<>();
        systemUserDao.deleteByPrimaryKey(userId);
        opsSystemUserRoleService.deleteByUserId(userId);
        map.put("code","1");
        map.put("message","删除成功");

        return map;
    }

    @Override
    public List<SystemUserPositionVO> loadSystemUserInfosByParams(String countryCode,String areaCode, String roleCode)throws Exception {
        Map<String,Object> params = new HashMap<>();
        params.put("areaCode",areaCode);
        params.put("areaCode1",countryCode);
        params.put("roleCode",roleCode);
        List<SystemUser> list = systemUserDao.loadSystemUserInfosByParams(params);

        params.remove("areaCode1");
        params.remove("areaCode");
        List<OpsSystemRoleDO> opsSystemRoleDOList = opsSystemRoleService.queryAllList(params);
        String roleName="";
        if(opsSystemRoleDOList!=null&&opsSystemRoleDOList.size()>0){
            roleName = opsSystemRoleDOList.get(0).getName();
        }

        if(list!=null&&list.size()>0){
            List<SystemUserPositionVO> systemUserPositionVOList = new ArrayList<>();
            for(SystemUser systemUser:list){
                SystemUserPositionVO vo = new SystemUserPositionVO();
                vo.setUserId(systemUser.getId().toString());
                vo.setUserNo(systemUser.getUserNo());
                vo.setName(systemUser.getName());
                vo.setPhone(systemUser.getPhone());
                vo.setRoleName(roleName);
                systemUserPositionVOList.add(vo);
            }
            return systemUserPositionVOList;
        }

        return null;
    }

    @Override
    public PagerResult<SystemUserPositionVO> loadSystemUserPageInfosByParams(SystemUserLoadDTO systemUserLoadDTO) throws Exception {

        PagerResult<SystemUserPositionVO> result = new PagerResult<>();

        Map<String,Object> params = new HashMap<>();
        params.put("areaCode",systemUserLoadDTO.getAreaCode());
        params.put("areaCode1",systemUserLoadDTO.getCountryCode());
        params.put("roleCode",systemUserLoadDTO.getRoleCode());
        List<SystemUser> list = systemUserDao.loadSystemUserInfosByParams(params);

        PageHelper.startPage(systemUserLoadDTO.getCurrentPage(),systemUserLoadDTO.getPageSize());
        PageInfo<SystemUser> pageInfo = new PageInfo<SystemUser>(list);
        List<SystemUserPositionVO> vos = null;
        if(pageInfo.getList()!=null&&pageInfo.getList().size()>0){

            params.remove("areaCode1");
            params.remove("areaCode");
            List<OpsSystemRoleDO> opsSystemRoleDOList = opsSystemRoleService.queryAllList(params);
            String roleName="";
            if(opsSystemRoleDOList!=null&&opsSystemRoleDOList.size()>0){
                roleName = opsSystemRoleDOList.get(0).getName();
            }
            String finalRoleName = roleName;

            vos = list.stream().map(systemUser -> {
                SystemUserPositionVO vo = new SystemUserPositionVO();
                vo.setUserId(systemUser.getId().toString());
                vo.setUserNo(systemUser.getUserNo());
                vo.setName(systemUser.getName());
                vo.setPhone(systemUser.getPhone());
                vo.setRoleName(finalRoleName);
                return vo;
            }).collect(Collectors.toList());
        }

        result.setList(vos);
        result.setCurrentPage(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotal(pageInfo.getTotal());
        result.setTotalPages(pageInfo.getPages());
        return result;
    }

    @Override
    public List<SystemUserPositionVO> querySystemUserInfosByParams(SystemUserLoadDTO systemUserLoadDTO) throws Exception {
        Map<String,Object> params = new HashMap<>();
        params.put("areaCode",systemUserLoadDTO.getAreaCode());
        params.put("areaCode1",systemUserLoadDTO.getCountryCode());
        params.put("roleCode",systemUserLoadDTO.getRoleCode());
        params.put("name",systemUserLoadDTO.getName());
        List<SystemUser> list = systemUserDao.loadSystemUserInfosByParams(params);

        params.remove("areaCode1");
        params.remove("areaCode");
        params.remove("name");
        List<OpsSystemRoleDO> opsSystemRoleDOList = opsSystemRoleService.queryAllList(params);
        String roleName="";
        if(opsSystemRoleDOList!=null&&opsSystemRoleDOList.size()>0){
            roleName = opsSystemRoleDOList.get(0).getName();
        }

        if(list!=null&&list.size()>0){
            List<SystemUserPositionVO> systemUserPositionVOList = new ArrayList<>();
            for(SystemUser systemUser:list){
                SystemUserPositionVO vo = new SystemUserPositionVO();
                vo.setUserId(systemUser.getId().toString());
                vo.setUserNo(systemUser.getUserNo());
                vo.setName(systemUser.getName());
                vo.setPhone(systemUser.getPhone());
                vo.setRoleName(roleName);
                systemUserPositionVOList.add(vo);
            }
            return systemUserPositionVOList;
        }

        return null;
    }

    @Override
    public PagerResult<SystemUserPositionVO> querySystemUserPageInfosByParams(SystemUserLoadDTO systemUserLoadDTO) throws Exception {
        PagerResult<SystemUserPositionVO> result = new PagerResult<>();

        Map<String,Object> params = new HashMap<>();
        params.put("areaCode",systemUserLoadDTO.getAreaCode());
        params.put("areaCode1",systemUserLoadDTO.getCountryCode());
        params.put("roleCode",systemUserLoadDTO.getRoleCode());
        params.put("name",systemUserLoadDTO.getName());
        List<SystemUser> list = systemUserDao.loadSystemUserInfosByParams(params);

        PageHelper.startPage(systemUserLoadDTO.getCurrentPage(),systemUserLoadDTO.getPageSize());
        PageInfo<SystemUser> pageInfo = new PageInfo<SystemUser>(list);
        List<SystemUserPositionVO> vos = null;
        if(pageInfo.getList()!=null&&pageInfo.getList().size()>0){

            params.remove("areaCode1");
            params.remove("areaCode");
            params.remove("name");
            List<OpsSystemRoleDO> opsSystemRoleDOList = opsSystemRoleService.queryAllList(params);
            String roleName="";
            if(opsSystemRoleDOList!=null&&opsSystemRoleDOList.size()>0){
                roleName = opsSystemRoleDOList.get(0).getName();
            }
            String finalRoleName = roleName;

            vos = list.stream().map(systemUser -> {
                SystemUserPositionVO vo = new SystemUserPositionVO();
                vo.setUserId(systemUser.getId().toString());
                vo.setUserNo(systemUser.getUserNo());
                vo.setName(systemUser.getName());
                vo.setPhone(systemUser.getPhone());
                vo.setRoleName(finalRoleName);
                return vo;
            }).collect(Collectors.toList());
        }

        result.setList(vos);
        result.setCurrentPage(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotal(pageInfo.getTotal());
        result.setTotalPages(pageInfo.getPages());
        return result;
    }

    @Override
    public List<String> selectCityStreet(Map<String,Object> params) throws Exception {
        return systemUserDao.selectCityStreet(params);
    }

    @Override
    public List<Integer> queryAllUserIdbyParams(List<Integer> positionIds) {
        return systemUserDao.queryAllUserIdbyParams(positionIds);
    }



    @Override
    public SystemApplicantVO loadSystemApplicantVO(SystemUser systemUser) {
        CyclingAreaDO cyclingAreaDO = null;
        SystemApplicantVO systemApplicantVO =new SystemApplicantVO();
        systemApplicantVO.setId(systemUser.getId());
        systemApplicantVO.setName(systemUser.getName());
        systemApplicantVO.setProvince(systemUser.getProvince());
        if(systemUser.getProvince()!=null) {
            cyclingAreaDO = systemAreaDistrictService.queryByCode(systemUser.getProvince());
            systemApplicantVO.setProvinceName(cyclingAreaDO == null?null:cyclingAreaDO.getAreaName());
        }
        systemApplicantVO.setCity(systemUser.getCity());
        if(systemUser.getCity()!=null) {
            cyclingAreaDO = systemAreaDistrictService.queryByCode(systemUser.getCity());
            systemApplicantVO.setCityName(cyclingAreaDO == null?null:cyclingAreaDO.getAreaName());
        }
        systemApplicantVO.setCityArea(systemUser.getCityArea());
        if(systemUser.getCityArea()!=null) {
            cyclingAreaDO = systemAreaDistrictService.queryByCode(systemUser.getCityArea());
            systemApplicantVO.setCityAreaName(cyclingAreaDO ==null?null:cyclingAreaDO.getAreaName());
        }
        systemApplicantVO.setPhone(systemUser.getPhone());
        //所属分区
        SystemAreaZone record = new SystemAreaZone();
        record.setId(systemUser.getRefAreaId());
        List<SystemAreaZone> list = systemAreaZoneService.queryAllList(record);
        if(list!=null&&list.size()>0){
            systemApplicantVO.setAreaZone(list.get(0).getName());
        }
        //点位街道位置集合
        String street = systemUser.getCityStreet();
        if(street!=null&&!"".equals(street.trim())) {
            List<SystemApplicantVO.AreaCode> cityStreetList = getCityStreetList(street.trim());
            if(cityStreetList!=null && cityStreetList.size()>0)
                systemApplicantVO.setCityStreetList(cityStreetList);
        }

        return systemApplicantVO;
    }

    private  List<SystemApplicantVO.AreaCode> getCityStreetList(String street){
        List<SystemApplicantVO.AreaCode> cityStreetList = new ArrayList<>();
        SystemApplicantVO.AreaCode areaCode = null;
        if(street.contains(",")){
            String[] streets = street.split(",");
            for(String str:streets){
                areaCode= new SystemApplicantVO().new AreaCode();
                CyclingAreaDO cyclingAreaDO = systemAreaDistrictService.queryByCode(str);
                if(cyclingAreaDO!=null) {
                    areaCode.setAreaName(cyclingAreaDO.getAreaName());
                    areaCode.setAreaCode(str);
                    cityStreetList.add(areaCode);
                }
            }

        }else{
            areaCode = new SystemApplicantVO().new AreaCode();
            CyclingAreaDO cyclingAreaDO = systemAreaDistrictService.queryByCode(street);
            if(cyclingAreaDO!=null) {
                areaCode.setAreaName(cyclingAreaDO.getAreaName());
                areaCode.setAreaCode(street);
                cityStreetList.add(areaCode);
            }
        }

        return cityStreetList;
    }

    /**
     * 校验是否是重复工号
     * @return
     */
    private boolean validateUserNo(String userNo,Integer validId){

        SystemUser systemUser = new SystemUser();
        //判断工号的重复可能
        systemUser.setUserNo(userNo);
        List<SystemUser> list = null;
        if(validId==0) {
            list = systemUserDao.queryAllList(systemUser);
        } else {
            systemUser.setId(validId);
            list = systemUserDao.queryAllListWithoutId(systemUser);
        }
        if(list!=null&&list.size()>0)
            return false;

        return true;
    }

    /**
     * 校验是否是重复手机号
     * @return
     */
    private boolean validatePhone(String phone,Integer validId){
        SystemUser systemUser = new SystemUser();
        systemUser.setPhone(phone);
        List<SystemUser> list = null;
        if(validId==0){
            list = systemUserDao.queryAllList(systemUser);
        }else {
            systemUser.setId(validId);
            list = systemUserDao.queryAllListWithoutId(systemUser);
        }
        if(list!=null&&list.size()>0)
            return false;

        return true;
    }

    /**
     *
     * 查询街道是否有重复
     * 有重复的返回true
     *
     */
    private boolean validateCityStreet(String cityStreetCode,Integer validId){

        Map<String,Object> params = new HashMap<>();
        if(validId != null)
            params.put("id",validId);

        List<String> list = systemUserDao.selectCityStreet(params);
        List<String> result = new ArrayList<>();
        list.stream().forEach(str->{
            if(str.contains(",")){
                String[] strs = str.split(",");
                for(String code:strs)
                    result.add(code);
            } else {
                result.add(str);
            }
        });
        return result.contains(cityStreetCode);
    }
}
