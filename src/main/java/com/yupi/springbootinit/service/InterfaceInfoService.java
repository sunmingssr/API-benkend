package com.yupi.springbootinit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.yupi.springbootinit.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.model.vo.InterfaceInfoVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author sunming
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-03-17 00:01:16
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    /**
     *
     * @param interfaceInfo
     * @param b
     */
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean b);

    QueryWrapper<InterfaceInfo> getQueryWrapper(InterfaceInfoQueryRequest interfaceInfoQueryRequest);

    Object getInterfaceInfoVOPage(Page<InterfaceInfo> interfaceInfoPage, HttpServletRequest request);


    InterfaceInfoVo getInterfaceInfoVo(InterfaceInfo interfaceInfo);

    List<InterfaceInfoVo> getInterfaceInfoVo(List<InterfaceInfo> InterfaceInfo);

    Object getInterfaceInfoVO(InterfaceInfo interfaceInfo, HttpServletRequest request);

}
