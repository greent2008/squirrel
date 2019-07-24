package com.xijn.squirrel.admin.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限控制
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionLimit {
  
  /**
   * 要求用户登录
   */
  boolean limit() default true;

  /**
   * 要求小组长权限
   */
  boolean teamLeaderUser() default false;
  
  /**
   * 要求经理权限
   */
  boolean managerUser() default false;

  /**
   * 要求超级用户权限
   */
  boolean superUser() default false;
}