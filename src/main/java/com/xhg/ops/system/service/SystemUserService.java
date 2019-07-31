package com.xhg.ops.system.service;

import com.xhg.core.web.vo.PagerResult;
import com.xhg.ops.system.dto.SystemUserAddDTO;
import com.xhg.ops.system.dto.SystemUserLoadDTO;
import com.xhg.ops.system.dto.SystemUserQueryDTO;
import com.xhg.ops.system.model.SystemUser;
import com.xhg.ops.system.vo.user.*;

import java.util.List;
import java.util.Map;

public interface SystemUserService {

    int deleteByPrimaryKey(Integer id);

    int insert(SystemUser record);

    int insertSelective(SystemUser record);

    SystemUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SystemUser record);

    int updateByPrimaryKey(SystemUser record);

    int queryAllCount(SystemUser record);

    List<SystemUser> queryAllList(SystemUser record);

    /**
     * 添加用户
     * @param systemUserAddDTO
     * @return
     */
    Map<String,Object> saveSystemUser(SystemUserAddDTO systemUserAddDTO);

    /**
     * 用户列表分页
     * @param systemUserQueryDTO
     * @return
     */
    PagerResult<SystemUserQueryVO> queryListPage(SystemUserQueryDTO systemUserQueryDTO);

    /**
     * 加载用户信息
     * @param id
     * @return
     */
    SystemUserEditVO loadSystemUserById(Integer id);

    /**
     * 更改用户信息
     * @param systemUserEditVO
     * @return
     */
    Map<String,Object> updateSystemUser(SystemUserEditVO systemUserEditVO,Integer currentUserId);

    /**
     * 重置用户密码
     * @param id
     * @return
     */
    Map<String,Object> updateResetSystemUserPassword(Integer id,Integer currentUserId);

    /**
     * 查看用户详细信息
     * @param id
     * @return
     */
    SystemUserViewVO viewSystemUserById(Integer id);

    /**
     * 用户登录校验
     * @param systemUserLoginVO
     * @return
     */
    SystemUser validateSystemUser(SystemUserLoginVO systemUserLoginVO);


    /**
     * app端加载用户信息
     * @param systemUser
     * @return
     */
    SystemAppUserViewVO querySystemAppUserViewVO(SystemUser systemUser);


    /**
     * app端修改用户密码
     * @param param
     * @return
     */
    Map<String,Object> updateModifySystemUserPassword(SystemUser param, String oldPassword);

    /**
     * 删除用户，及其用户角色关联表
     * @param userId
     * @return
     */
    Map<String,Object> deleteSystemUser(Integer userId);

    /**
     * 通过区域编码，角色编码获取职位用户集合对象(不分页）
     * @param areaCode
     * @param roleCode
     * @return
     */
    List<SystemUserPositionVO> loadSystemUserInfosByParams(String countryCode,String areaCode,String roleCode) throws Exception;

    /**
     * 通过区域编码，角色编码获取职位用户集合对象(分页）
     * @param systemUserLoadDTO
     * @return
     * @throws Exception
     */
    PagerResult<SystemUserPositionVO> loadSystemUserPageInfosByParams(SystemUserLoadDTO systemUserLoadDTO) throws Exception;

    /**
     * 根据区域编码，角色编码，用户名称获取职位用户集合对象（不分页）
     * @param systemUserLoadDTO
     * @return
     * @throws Exception
     */
    List<SystemUserPositionVO> querySystemUserInfosByParams(SystemUserLoadDTO systemUserLoadDTO) throws Exception;

    /**
     * 根据区域编码，角色编码，用户名称获取职位用户集合对象（分页）
     * @param systemUserLoadDTO
     * @return
     * @throws Exception
     */
    PagerResult<SystemUserPositionVO> querySystemUserPageInfosByParams(SystemUserLoadDTO systemUserLoadDTO) throws Exception;

    /**
     * 查询重复的街道code
     * @param params
     * @return
     * @throws Exception
     */
    List<String> selectCityStreet(Map<String,Object> params) throws Exception;

    /**
     * 通过positionIds获取userId集合
     * @param positionIds
     * @return
     */
    List<Integer> queryAllUserIdbyParams(List<Integer> positionIds);

    /**
     * 加载申请人信息-布点系统
     * @param systemUser
     * @return
     */
    SystemApplicantVO loadSystemApplicantVO(SystemUser systemUser);



}
