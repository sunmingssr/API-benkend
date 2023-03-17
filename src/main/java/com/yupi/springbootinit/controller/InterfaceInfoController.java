package com.yupi.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.yupi.springbootinit.annotation.AuthCheck;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.DeleteRequest;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.constant.UserConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.model.dto.interfaceInfo.InterfaceInfoAddRequest;
import com.yupi.springbootinit.model.dto.interfaceInfo.InterfaceInfoEditRequest;
import com.yupi.springbootinit.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.yupi.springbootinit.model.dto.interfaceInfo.InterfaceInfoUpdateRequest;
import com.yupi.springbootinit.model.entity.InterfaceInfo;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.service.InterfaceInfoService;
import com.yupi.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 帖子接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/InterfaceInfo")
@Slf4j
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService InterfaceInfoService;

    @Resource
    private UserService userService;

    private final static Gson GSON = new Gson();

    // region 增删改查

    /**
     * 创建
     *
     * @param InterfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest InterfaceInfoAddRequest, HttpServletRequest request) {
        //校验数据是否为空
        if (InterfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //创建对象
        InterfaceInfo InterfaceInfo = new InterfaceInfo();
        //拷贝对象
        BeanUtils.copyProperties(InterfaceInfoAddRequest, InterfaceInfo);
        //校验对象数据是否正确
        InterfaceInfoService.validInterfaceInfo(InterfaceInfo, true);
        //设置用户登录态
        User loginUser = userService.getLoginUser(request);
        //设置创建人id
        InterfaceInfo.setUserId(loginUser.getId());
        //保存接口信息
        boolean result = InterfaceInfoService.save(InterfaceInfo);
        //判断如果保存不成功返回错误状态码
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        //获取当前接口信息id
        long newInterfaceInfoId = InterfaceInfo.getId();

        return ResultUtils.success(newInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = InterfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = InterfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param InterfaceInfoUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest InterfaceInfoUpdateRequest) {
        if (InterfaceInfoUpdateRequest == null || InterfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }


        InterfaceInfo InterfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(InterfaceInfoUpdateRequest, InterfaceInfo);


        // 参数校验
        InterfaceInfoService.validInterfaceInfo(InterfaceInfo, false);
        long id = InterfaceInfoUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = InterfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = InterfaceInfoService.updateById(InterfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<InterfaceInfoVO> getInterfaceInfoVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo InterfaceInfo = InterfaceInfoService.getById(id);
        if (InterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(InterfaceInfoService.getInterfaceInfoVO(InterfaceInfo, request));
    }

    /**
     * 分页获取列表（封装类）
     *
     * @param InterfaceInfoQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<InterfaceInfoVO>> listInterfaceInfoVOByPage(@RequestBody InterfaceInfoQueryRequest InterfaceInfoQueryRequest,
            HttpServletRequest request) {
        long current = InterfaceInfoQueryRequest.getCurrent();
        long size = InterfaceInfoQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<InterfaceInfo> InterfaceInfoPage = InterfaceInfoService.page(new Page<>(current, size),
                InterfaceInfoService.getQueryWrapper(InterfaceInfoQueryRequest));
        return ResultUtils.success(InterfaceInfoService.getInterfaceInfoVOPage(InterfaceInfoPage, request));
    }

    /**
     * 分页获取当前用户创建的资源列表
     *
     * @param InterfaceInfoQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<InterfaceInfoVO>> listMyInterfaceInfoVOByPage(@RequestBody InterfaceInfoQueryRequest InterfaceInfoQueryRequest,
            HttpServletRequest request) {
        if (InterfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        InterfaceInfoQueryRequest.setUserId(loginUser.getId());
        long current = InterfaceInfoQueryRequest.getCurrent();
        long size = InterfaceInfoQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<InterfaceInfo> InterfaceInfoPage = InterfaceInfoService.page(new Page<>(current, size),
                InterfaceInfoService.getQueryWrapper(InterfaceInfoQueryRequest));
        return ResultUtils.success(InterfaceInfoService.getInterfaceInfoVOPage(InterfaceInfoPage, request));
    }

    // endregion



    /**
     * 编辑（用户）
     *
     * @param InterfaceInfoEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editInterfaceInfo(@RequestBody InterfaceInfoEditRequest InterfaceInfoEditRequest, HttpServletRequest request) {
        if (InterfaceInfoEditRequest == null || InterfaceInfoEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo InterfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(InterfaceInfoEditRequest, InterfaceInfo);
        List<String> tags = InterfaceInfoEditRequest.getTags();
        if (tags != null) {
            InterfaceInfo.setTags(GSON.toJson(tags));
        }
        // 参数校验
        InterfaceInfoService.validInterfaceInfo(InterfaceInfo, false);
        User loginUser = userService.getLoginUser(request);
        long id = InterfaceInfoEditRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = InterfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldInterfaceInfo.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = InterfaceInfoService.updateById(InterfaceInfo);
        return ResultUtils.success(result);
    }

}
