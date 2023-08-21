package com.wangan.usercenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangan.usercenter.common.BaseResponse;
import com.wangan.usercenter.common.ErrorCode;
import com.wangan.usercenter.common.ResultUtils;
import com.wangan.usercenter.exception.BusinessException;
import com.wangan.usercenter.model.domain.User;
import com.wangan.usercenter.model.domain.request.UserLoginRequest;
import com.wangan.usercenter.model.domain.request.UserRegisterRequest;
import com.wangan.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.wangan.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.wangan.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 */
@RestController
@RequestMapping("/user")  //定义请求的路径
public class UserController {
    @Resource
    private UserService userService;


    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody  UserRegisterRequest userRegisterRequest){
        if(userRegisterRequest == null){
//            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,planetCode))
        {
            return null;
        }
        long result =  userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
        return ResultUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if(userLoginRequest == null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if(StringUtils.isAnyBlank(userAccount,userPassword))
        {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        User user =  userService.userLogin(userAccount,userPassword,request);
        return ResultUtils.success(user);
    }


    @PostMapping("/logout")
    public BaseResponse<Integer>  userLogout(HttpServletRequest request){
        if(request == null){//返回为空的话也可以返回一个null
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }



    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if(currentUser == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        //从数据库中获取数据，实时更新数据信息；
        long userId = currentUser.getId();
        //todo 校验用户是否合法
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username,HttpServletRequest request){
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "缺少管理员权限");
        }
        QueryWrapper<User>queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isAnyBlank(username)){
            queryWrapper.like("username",username);//默认就是模糊查询；
        }
        List<User>userList =  userService.list(queryWrapper);
        List<User> list = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(list);
    }
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id,HttpServletRequest request){
        if(isAdmin(request)){
            return null;
        }
        if(id <= 0){
            return null;
        }
        boolean b = userService.removeById(id);//逻辑删除
        return ResultUtils.success(b);
    }

    /**
     * 是否为管理员
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request){
        //仅管理员可供查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);//静态方法使用类就行；
        User user = (User)userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }
}
