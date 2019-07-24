package com.xijn.squirrel.admin.controller;

import com.xijn.squirrel.admin.controller.annotation.PermissionLimit;
import com.xijn.squirrel.admin.core.model.ReturnT;
import com.xijn.squirrel.admin.core.model.SquirrelBiz;
import com.xijn.squirrel.admin.core.model.SquirrelUser;
import com.xijn.squirrel.admin.core.util.CookieUtil;
import com.xijn.squirrel.admin.core.util.tool.StringTool;
import com.xijn.squirrel.admin.dao.ISquirrelBizDao;
import com.xijn.squirrel.admin.dao.ISquirrelUserDao;
import com.xijn.squirrel.admin.service.impl.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/user")
public class SquirrelUserController {

    private static Logger logger = LoggerFactory.getLogger(SquirrelUserController.class);

    @Resource
    private ISquirrelUserDao squirrelUserDao;
    @Resource
    private ISquirrelBizDao squirrelBizDao;
    @Resource
    private LoginService loginService;

    @RequestMapping
    @PermissionLimit(limit = true)
    public String index(Model model) {
        List<SquirrelBiz> bizList = squirrelBizDao.loadAll();
        model.addAttribute("bizList", bizList);

        return "user/user.list";
    }

    @RequestMapping("/pageList")
    @ResponseBody
    @PermissionLimit(limit = true)
    public Map<String, Object> pageList(HttpServletRequest request,
                                        HttpServletResponse response,
                                        @RequestParam(required = false, defaultValue = "0") int start,
                                        @RequestParam(required = false, defaultValue = "10") int length,
                                        String userName,
                                        int type) {
        String groupName = "";
        String excludeGroupName = "";
        List<SquirrelUser> list = new ArrayList<SquirrelUser>();
        int list_count = 0;
        SquirrelUser loginUser = loginService.ifLogin(request);
        int loginUserType = loginUser.getType();

        // 外包人员只显示自己的信息
        if (loginUserType == 0) {
            if (userName == null || userName.equals("")) {
                userName = loginUser.getUserName();
                list = squirrelUserDao.pageList(start, length, userName, groupName, excludeGroupName, type);
                list_count = squirrelUserDao.pageListCount(start, length, userName, groupName, excludeGroupName, type);
            }
            if (userName != loginUser.getUserName()) {
                list_count = 0;
            }
        }
        // 组长显示本组人员信息
        else if (loginUserType == 1) {
            String loginGroupName = loginUser.getGroupName();
            list = squirrelUserDao.pageList(start, length, userName, loginGroupName, excludeGroupName, type);
            list_count = squirrelUserDao.pageListCount(start, length, userName, loginGroupName, excludeGroupName, type);
        }
        // 经理显示除超级用户组所有人员信息
        else if (loginUserType == 2) {
            excludeGroupName = "super";
            list = squirrelUserDao.pageList(start, length, userName, groupName, excludeGroupName, type);
            list_count = squirrelUserDao.pageListCount(start, length, userName,groupName, excludeGroupName, type);
        }
        // 超级用户显示所有人员信息
        else if (loginUserType == 3) {
            list = squirrelUserDao.pageList(start, length, userName, groupName, excludeGroupName, type);
            list_count = squirrelUserDao.pageListCount(start, length, userName, groupName, excludeGroupName, type);
        }


        // package result
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("recordsTotal", list_count);		// 总记录数
        maps.put("recordsFiltered", list_count);	// 过滤后的总记录数
        maps.put("data", list);  					// 分页列表
        return maps;
    }

    @RequestMapping("/add")
    @ResponseBody
    @PermissionLimit(superUser = true)
    public ReturnT<String> add(SquirrelUser squirrelUser) {
        // valid
        if (StringTool.isBlank(squirrelUser.getUserName())) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "请输入登录账号");
        }
        if (StringTool.isBlank(squirrelUser.getPassword())) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "请输入密码");
        }

        // valid
        SquirrelUser existUser = squirrelUserDao.findByUserName(squirrelUser.getUserName());
        if (existUser != null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "“登录账号”重复，请更换");
        }

        // passowrd md5
        String md5Password = DigestUtils.md5DigestAsHex(squirrelUser.getPassword().getBytes());
        squirrelUser.setPassword(md5Password);

        int ret = squirrelUserDao.add(squirrelUser);
        return (ret>0)?ReturnT.SUCCESS:ReturnT.FAIL;
    }

    @RequestMapping("/update")
    @ResponseBody
    @PermissionLimit(limit = true)
    public ReturnT<String> update(HttpServletRequest request, HttpServletResponse response, SquirrelUser squirrelUser) {

        SquirrelUser loginUser = (SquirrelUser) request.getAttribute(LoginService.LOGIN_IDENTITY);

        // exist
        SquirrelUser existUser = squirrelUserDao.findByUserName(squirrelUser.getUserName());
        if (existUser == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "更新失败，登录账号非法");
        }

        // update param
        if (StringTool.isNotBlank(squirrelUser.getPassword())) {
            if (!(squirrelUser.getPassword().length()>=4 && squirrelUser.getPassword().length()<=50)) {
                return new ReturnT<String>(ReturnT.FAIL.getCode(), "密码长度限制为4~50");
            }
            // passowrd md5
            String md5Password = DigestUtils.md5DigestAsHex(squirrelUser.getPassword().getBytes());
            existUser.setPassword(md5Password);
        }
        existUser.setType(squirrelUser.getType());
        existUser.setPermissionBiz(squirrelUser.getPermissionBiz());
        existUser.setUserNameCn(squirrelUser.getUserNameCn());
        existUser.setResponsibleFor(squirrelUser.getResponsibleFor());
        existUser.setGroupName(squirrelUser.getGroupName());
        existUser.setGroupNameCn(squirrelUser.getGroupNameCn());
        existUser.setPrinciple(squirrelUser.getPrinciple());
        existUser.setEntryTime(squirrelUser.getEntryTime());
        existUser.setBornTime(squirrelUser.getBornTime());
        existUser.setCompany(squirrelUser.getCompany());
        existUser.setGraduateSchool(squirrelUser.getGraduateSchool());
        existUser.setEduBackground(squirrelUser.getEduBackground());
        existUser.setWorkingYears(squirrelUser.getWorkingYears());
        existUser.setEmail(squirrelUser.getEmail());
        existUser.setMobileNumber(squirrelUser.getMobileNumber());

        int ret = squirrelUserDao.update(existUser);
        return updateSquirrelUserCookie(request, response, ret, loginUser, existUser);
    }

    @RequestMapping("/delete")
    @ResponseBody
    @PermissionLimit(superUser = true)
    public ReturnT<String> delete(HttpServletRequest request, int id) {

        // valid user
        SquirrelUser delUser = squirrelUserDao.findById(id);
        if (delUser == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "拒绝删除，用户ID非法");
        }

        SquirrelUser loginUser = (SquirrelUser) request.getAttribute(LoginService.LOGIN_IDENTITY);
        if (loginUser.getUserName().equals(delUser.getUserName())) {
            return new ReturnT<String>(ReturnT.FAIL.getCode(), "禁止操作当前登录账号");
        }

        int ret = squirrelUserDao.delete(id);
        return (ret>0)?ReturnT.SUCCESS:ReturnT.FAIL;
    }

    @RequestMapping("/updatePwd")
    @ResponseBody
    public ReturnT<String> updatePwd(HttpServletRequest request, HttpServletResponse response, String password){

        // new password(md5)
        if (StringTool.isBlank(password)){
            return new ReturnT<String>(ReturnT.FAIL.getCode(), "密码不可为空");
        }
        if (!(password.length()>=4 && password.length()<=100)) {
            return new ReturnT<String>(ReturnT.FAIL.getCode(), "密码长度限制为4~50");
        }
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());

        // update pwd
        SquirrelUser loginUser = (SquirrelUser) request.getAttribute(LoginService.LOGIN_IDENTITY);

        SquirrelUser existUser = squirrelUserDao.findByUserName(loginUser.getUserName());
        existUser.setPassword(md5Password);
        int ret = squirrelUserDao.update(existUser);
        return updateSquirrelUserCookie(request, response, ret, loginUser, existUser);
    }

    /**
     * 更新用户信息后更新cookie
     * @param request
     * @param response
     * @param loginUser
     * @param existUser
     * @return
     */
    private ReturnT<String> updateSquirrelUserCookie(HttpServletRequest request, HttpServletResponse response, int ret, SquirrelUser loginUser, SquirrelUser existUser) {
        if (ret > 0) {
            if (loginUser.getUserName().equals(existUser.getUserName())) {
                Cookie cookie = CookieUtil.get(request, LoginService.LOGIN_IDENTITY);
                if (cookie != null) {
                    cookie.setPath(CookieUtil.getCookiePath());
                    cookie.setValue(loginService.makeToken(existUser));
                    CookieUtil.remove(request, response, LoginService.LOGIN_IDENTITY);
                    response.addCookie(cookie);
                }
            }
            return ReturnT.SUCCESS;
        } else {
            return ReturnT.FAIL;
        }
    }
}
