package com.yupi.springbootinit.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.constant.CommonConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.yupi.springbootinit.model.entity.InterfaceInfo;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.model.vo.InterfaceInfoVo;
import com.yupi.springbootinit.service.InterfaceInfoService;
import com.yupi.springbootinit.mapper.InterfaceInfoMapper;
import com.yupi.springbootinit.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
* @author sunming
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2023-03-17 00:01:16
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService{

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean b) {
         Integer id = interfaceInfo.getId();
         String name = interfaceInfo.getName();
         String description = interfaceInfo.getDescription();
         String url = interfaceInfo.getUrl();
         String requestHeader = interfaceInfo.getRequestHeader();
         String responseHeader = interfaceInfo.getResponseHeader();
         Integer status = interfaceInfo.getStatus();
         String method = interfaceInfo.getMethod();
         Long userId = interfaceInfo.getUserId();
         Date createTime = interfaceInfo.getCreateTime();
         Date updateTime = interfaceInfo.getUpdateTime();
         Integer isDelete = interfaceInfo.getIsDelete();




    }

    @Override
    public QueryWrapper getQueryWrapper(InterfaceInfoQueryRequest interfaceInfoQueryRequest) {


        if (interfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        Integer id = interfaceInfoQueryRequest.getId();
        String name = interfaceInfoQueryRequest.getName();
        String url = interfaceInfoQueryRequest.getUrl();
        String requestHeader = interfaceInfoQueryRequest.getRequestHeader();
        String responseHeader = interfaceInfoQueryRequest.getResponseHeader();
        Integer status = interfaceInfoQueryRequest.getStatus();
        String method = interfaceInfoQueryRequest.getMethod();
        Long userId = interfaceInfoQueryRequest.getUserId();
        String sortField = interfaceInfoQueryRequest.getSortField();
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(StringUtils.isNotBlank(name), "name", name);
        queryWrapper.eq(StringUtils.isNotBlank(url), "url", url);
        queryWrapper.like(StringUtils.isNotBlank(requestHeader), "requestHeader", requestHeader);
        queryWrapper.like(StringUtils.isNotBlank(responseHeader), "responseHeader", responseHeader);
        queryWrapper.like(StringUtils.isNotBlank(method), "method", method);
        queryWrapper.like(status!=null, "status", status);
        queryWrapper.like(userId!=null,"userId",userId);

        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public Object getInterfaceInfoVOPage(Page<InterfaceInfo> interfaceInfoPage, HttpServletRequest request) {
        return null;
    }


    @Override
    public InterfaceInfoVo getInterfaceInfoVo(InterfaceInfo interfaceInfo) {
        if (interfaceInfo == null) {
            return null;
        }
        InterfaceInfoVo interfaceInfoVo1 = new InterfaceInfoVo();
        BeanUtils.copyProperties(interfaceInfoVo1, interfaceInfo);
        return interfaceInfoVo1;
    }


    @Override
    public List<InterfaceInfoVo> getInterfaceInfoVo(List<InterfaceInfo> InterfaceInfoList) {
        if (CollectionUtils.isEmpty(InterfaceInfoList)) {
            return new ArrayList<>();
        }
        return InterfaceInfoList.stream().map(this::getInterfaceInfoVo).collect(Collectors.toList());
    }

    @Override
    public Object getInterfaceInfoVO(InterfaceInfo interfaceInfo, HttpServletRequest request) {
        return null;
    }


}




