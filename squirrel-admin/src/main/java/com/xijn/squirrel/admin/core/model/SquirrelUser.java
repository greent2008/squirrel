package com.xijn.squirrel.admin.core.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class SquirrelUser {

  private int id; // 用户ID
  private String userName; // 账号
  private String password; // 密码
  private int type; // 用户类型: 0-外包员工 1-组长 2-经理 3-超级管理员
  private String permissionBiz; // 业务线权限，多个逗号分隔
  private String userNameCn; // 中文名
  private String responsibleFor; // 负责内容
  private String groupName; // 小组名称
  private String groupNameCn; // 小组中文名称
  private String principle; // 汇报人
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date entryTime; // 入职时间
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date bornTime; // 出生年月
  private String company; // 所属公司
  private String graduateSchool; // 毕业院校
  private String eduBackground; // 学历
  private int workingYears; // 工作年限
  private String email; // 工作邮箱
  private String mobileNumber; // 移动电话

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getPermissionBiz() {
    return permissionBiz;
  }

  public void setPermissionBiz(String permissionBiz) {
    this.permissionBiz = permissionBiz;
  }

  public String getUserNameCn() {
    return userNameCn;
  }

  public void setUserNameCn(String userNameCn) {
    this.userNameCn = userNameCn;
  }

  public String getResponsibleFor() {
    return responsibleFor;
  }

  public void setResponsibleFor(String responsibleFor) {
    this.responsibleFor = responsibleFor;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public String getGroupNameCn() {
    return groupNameCn;
  }

  public void setGroupNameCn(String groupNameCn) {
    this.groupNameCn = groupNameCn;
  }

  public String getPrinciple() {
    return principle;
  }

  public void setPrinciple(String principle) {
    this.principle = principle;
  }

  public Date getEntryTime() {
    return entryTime;
  }

  public void setEntryTime(Date entryTime) {
    this.entryTime = entryTime;
  }

  public Date getBornTime() {
    return bornTime;
  }

  public void setBornTime(Date bornTime) {
    this.bornTime = bornTime;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getGraduateSchool() {
    return graduateSchool;
  }

  public void setGraduateSchool(String graduateSchool) {
    this.graduateSchool = graduateSchool;
  }

  public String getEduBackground() {
    return eduBackground;
  }

  public void setEduBackground(String eduBackground) {
    this.eduBackground = eduBackground;
  }

  public int getWorkingYears() {
    return workingYears;
  }

  public void setWorkingYears(int workingYears) {
    this.workingYears = workingYears;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }
}