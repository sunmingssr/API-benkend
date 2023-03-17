package com.yupi.springbootinit.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.model.entity.InterfaceInfo;
import com.yupi.springbootinit.service.InterfaceInfoService;
import com.yupi.springbootinit.mapper.InterfaceInfoMapper;
import org.springframework.stereotype.Service;

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
}




