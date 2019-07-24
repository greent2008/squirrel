package com.xijn.squirrel.admin.service.impl;

import com.xijn.squirrel.admin.core.model.ReturnT;
import com.xijn.squirrel.admin.core.model.SquirrelUser;
import com.xijn.squirrel.admin.core.util.CookieUtil;
import com.xijn.squirrel.admin.core.util.JacksonUtil;
import com.xijn.squirrel.admin.dao.ISquirrelUserDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;

@Configuration
public class LoginService {

    private static Logger logger = LoggerFactory.getLogger(LoginService.class);

    public static final String LOGIN_IDENTITY = "SQUIRREL_LOGIN_IDENTITY";

    @Resource
    private ISquirrelUserDao squirrelUserDao;

    public String makeToken(SquirrelUser squirrelUser){
        String tokenJson = JacksonUtil.writeValueAsString(squirrelUser);
        String tokenHex = new BigInteger(tokenJson.getBytes()).toString(16);
        return tokenHex;
    }
    private SquirrelUser parseToken(String tokenHex){
        SquirrelUser squirrelUser = null;
        if (tokenHex != null) {
            String tokenJson = new String(new BigInteger(tokenHex, 16).toByteArray());      // username_password(md5)
            squirrelUser = JacksonUtil.readValue(tokenJson, SquirrelUser.class);
        }
        return squirrelUser;
    }


    /**
     * login
     *
     * @param response
     * @param usernameParam
     * @param passwordParam
     * @param ifRemember
     * @return
     */
    public ReturnT<String> login(HttpServletResponse response, String usernameParam, String passwordParam, boolean ifRemember){
        SquirrelUser squirrelUser = squirrelUserDao.findByUserName(usernameParam);
        if (squirrelUser == null) {
            return new ReturnT<String>(500, "账号或密码错误");
        }

        String passwordParamMd5 = DigestUtils.md5DigestAsHex(passwordParam.getBytes());
        if (!squirrelUser.getPassword().equals(passwordParamMd5)) {
            return new ReturnT<String>(500, "账号或密码错误");
        }

        String loginToken = makeToken(squirrelUser);

        // do login
        CookieUtil.set(response, LOGIN_IDENTITY, loginToken, ifRemember);
        return ReturnT.SUCCESS;
    }

    /**
     * logout
     *
     * @param request
     * @param response
     */
    public void logout(HttpServletRequest request, HttpServletResponse response){
        CookieUtil.remove(request, response, LOGIN_IDENTITY);
    }

    /**
     * logout
     *
     * @param request
     * @return
     */
    public SquirrelUser ifLogin(HttpServletRequest request){
        String cookieToken = CookieUtil.getValue(request, LOGIN_IDENTITY);
        if (cookieToken != null) {
            SquirrelUser cookieUser = parseToken(cookieToken);
            if (cookieUser != null) {
                SquirrelUser dbUser = squirrelUserDao.findByUserName(cookieUser.getUserName());
                if (dbUser != null) {
                    if (cookieUser.getPassword().equals(dbUser.getPassword())) {
                        return dbUser;
                    }
                }
            }
        }
        return null;
    }

}
