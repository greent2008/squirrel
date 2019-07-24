package com.xijn.squirrel.admin.controller.interceptor;

import com.xijn.squirrel.admin.controller.annotation.PermissionLimit;
import com.xijn.squirrel.admin.core.model.SquirrelUser;
import com.xijn.squirrel.admin.service.impl.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截
 */
@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class);

    @Resource
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return super.preHandle(request, response, handler);
        }

        // if need login
        boolean needLogin = true;
        boolean needAdminuser = false;
        HandlerMethod method = (HandlerMethod)handler;
        PermissionLimit permission = method.getMethodAnnotation(PermissionLimit.class);
        if (permission!=null) {
            needLogin = permission.limit();
            needAdminuser = permission.superUser();
        }

        // if pass
        if (needLogin) {
            SquirrelUser loginUser = loginService.ifLogin(request);
            if (loginUser == null) {
                response.sendRedirect(request.getContextPath() + "/toLogin");	//request.getRequestDispatcher("/toLogin").forward(request, response);
                return false;
            }
            if (needAdminuser && loginUser.getType()!=3) {
                throw new RuntimeException("权限拦截");
            }
            request.setAttribute(LoginService.LOGIN_IDENTITY, loginUser);
        }

        return super.preHandle(request, response, handler);
    }

}

