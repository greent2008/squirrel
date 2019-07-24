<!DOCTYPE html>
<html>
<head>
    <title>Squirrel人力资源管理系统</title>
    <#import "../common/common.macro.ftl" as netCommon>
    <link rel="stylesheet" href="${request.contextPath}/static/adminlte/plugins/datatables/dataTables.bootstrap.css">
    <@netCommon.commonStyle />
    <!-- DataTables -->


</head>
<body class="hold-transition skin-blue sidebar-mini <#if cookieMap?exists && cookieMap["adminlte_settings"]?exists && "off" == cookieMap["adminlte_settings"].value >sidebar-collapse</#if>">
<div class="wrapper">
    <!-- header -->
    <@netCommon.commonHeader />
    <!-- left -->
    <@netCommon.commonLeft "userList" />

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>用户管理</h1>
        </section>

        <!-- Main content -->
        <section class="content">

            <div class="row">

                <div class="col-xs-4">
                    <div class="input-group">
                        <span class="input-group-addon">用户类型</span>
                        <select class="form-control" id="type">
                            <option value="-1">默认</option>
                            <option value="0">外包员工</option>
                            <option value="1">组长</option>
                            <option value="2">经理</option>
                            <option value="3">超级管理员</option>
                        </select>
                    </div>
                </div>

                <div class="col-xs-4">
                    <div class="input-group">
                        <span class="input-group-addon">登录账号</span>
                        <input type="text" class="form-control" id="userName" autocomplete="on">
                    </div>
                </div>
                <div class="col-xs-2">
                    <button class="btn btn-block btn-info" id="search">搜索</button>
                </div>
                <#if SQUIRREL_LOGIN_IDENTITY.type == 3>
                <div class="col-xs-2 pull-right">
                    <button class="btn btn-block btn-success" type="button" id="add">+新增用户</button>
                </div>
                </#if>
            </div>

            <div class="row">
                <div class="col-xs-12">

                    <div class="box">
                        <!-- /.box-header -->
                        <div class="box-body">
                            <table id="user_list" class="table table-bordered table-striped" width="100%">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>账号</th>
                                    <th>类型</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->

                </div>
            </div>
        </section>
    </div>

    <!-- footer -->
    <@netCommon.commonFooter />
</div>

<!-- 新增.模态框 -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">新增用户</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal form" role="form">
<#--                    <div class="form-group">-->
<#--                        <label for="username" class="col-sm-2 control-label">登录账号<font color="red">*</font></label>-->
<#--                        <div class="col-sm-10"><input type="text" class="form-control" name="userName"-->
<#--                                                      placeholder="请输入“登录账号”" maxlength="50"></div>-->
<#--                    </div>-->
<#--                    <div class="form-group">-->
<#--                        <label for="lastname" class="col-sm-2 control-label">登录密码<font color="red">*</font></label>-->
<#--                        <div class="col-sm-10"><input type="text" class="form-control" name="password"-->
<#--                                                      placeholder="请输入“登录密码”" maxlength="50" value="123456"></div>-->
<#--                    </div>-->
<#--                    <div class="form-group">-->
<#--                        <label for="lastname" class="col-sm-2 control-label">用户类型<font color="red">*</font></label>-->
<#--                        <div class="col-sm-10">-->
<#--                            <input type="radio" name="type" value="0" checked>普通用户-->
<#--                            <input type="radio" name="type" value="1">管理员-->
<#--                        </div>-->
<#--                    </div>-->

                    <div class="form-group">
                        <label for="username" class="col-sm-2 control-label">登录账号<font color="red">*</font></label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="userName"
                                                      placeholder="请输入“登录账号”" maxlength="50" readonly></div>
                    </div>

                    <div class="form-group">
                        <label for="lastname" class="col-sm-2 control-label">登录密码<font color="red">*</font></label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="password"
                                                      placeholder="请输入“登录密码”" maxlength="50" value="123456"></div>
                    </div>

                    <div class="form-group">
                        <label for="type" class="col-sm-2 control-label">用户类型<font color="red">*</font></label>
                        <div class="col-sm-10">
                            <input type="radio" name="type" value="0">外包员工
                            <input type="radio" name="type" value="1">组长
                            <input type="radio" name="type" value="2">经理
                            <input type="radio" name="type" value="3">超级管理员
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="permission_biz" class="col-sm-2 control-label">权限</label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="permissionBiz"
                                                      placeholder="请输入“权限,多个逗号分隔”" maxlength="200"/></div>
                    </div>

                    <div class="form-group">
                        <label for="username_cn" class="col-sm-2 control-label">中文名<font color="red">*</font></label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="userNameCn"
                                                      placeholder="请输入“中文名”" maxlength="50"/></div>
                    </div>

                    <div class="form-group">
                        <label for="responsible_for" class="col-sm-2 control-label">负责内容<font
                                    color="red">*</font></label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="responsibleFor"
                                                      placeholder="请输入“负责内容”"/></div>
                    </div>

                    <div class="form-group">
                        <label for="groupname" class="col-sm-2 control-label">所属小组<font color="red">*</font></label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="groupName"
                                                      placeholder="请输入“所属小组”" maxlength="50"/></div>
                    </div>

                    <div class="form-group">
                        <label for="groupname_cn" class="col-sm-2 control-label">所属小组中文名<font
                                    color="red">*</font></label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="groupNameCn"
                                                      placeholder="请输入“所属小组中文名”" maxlength="50"/></div>
                    </div>

                    <div class="form-group">
                        <label for="principle" class="col-sm-2 control-label">汇报人</label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="principle"
                                                      placeholder="请输入“汇报人”" maxlength="50"/></div>
                    </div>

                    <div class="form-group">
                        <label for="entry_time" class="col-sm-2 control-label">入职时间</label>
                        <div class="col-sm-10"><input type="date" class="form-control" name="entryTime"
                                                      placeholder="请输入“入职时间”"/></div>
                    </div>

                    <div class="form-group">
                        <label for="born_time" class="col-sm-2 control-label">出生年月</label>
                        <div class="col-sm-10"><input type="date" class="form-control" name="bornTime"
                                                      placeholder="请输入“出生年月”"/></div>
                    </div>

                    <div class="form-group">
                        <label for="company" class="col-sm-2 control-label">所属公司</label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="company"
                                                      placeholder="请输入“所属公司”" maxlength="200"/></div>
                    </div>

                    <div class="form-group">
                        <label for="graduate_school" class="col-sm-2 control-label">毕业院校</label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="graduateSchool"
                                                      placeholder="请输入“毕业院校”" maxlength="200"/></div>
                    </div>

                    <div class="form-group">
                        <label for="edu_background" class="col-sm-2 control-label">学历</label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="eduBackground"
                                                      placeholder="请输入“学历”" maxlength="50"/></div>
                    </div>

                    <div class="form-group">
                        <label for="working_years" class="col-sm-2 control-label">工作年限</label>
                        <div class="col-sm-10"><input type="number" class="form-control" name="workingYears"
                                                      min="0" placeholder="请输入“工作年限”" maxlength="11"/></div>
                    </div>

                    <div class="form-group">
                        <label for="eamil" class="col-sm-2 control-label">工作邮箱<font color="red">*</font></label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="email"
                                                      placeholder="请输入“工作邮箱”" maxlength="200"/></div>
                    </div>

                    <div class="form-group">
                        <label for="mobile_number" class="col-sm-2 control-label">移动电话</label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="mobileNumber"
                                                      placeholder="请输入“移动电话”" maxlength="200"/></div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-offset-3 col-sm-6">
                            <button type="submit" class="btn btn-primary">保存</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- 更新.模态框 -->
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">更新用户基础信息</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal form" role="form">
                    <div class="form-group">
                        <label for="username" class="col-sm-2 control-label">登录账号<font color="red">*</font></label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="userName"
                                                      placeholder="请输入“登录账号”" maxlength="50" readonly></div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="col-sm-2 control-label">登录密码<font color="black">*</font></label>
                        <div class="col-sm-10">
                            <div class="input-group">
									<span class="input-group-addon">
										重置密码<input type="checkbox" name="passwordInput">
									</span>
                                <input type="text" class="form-control" name="password" placeholder="为空则不更新密码"
                                       maxlength="50" readonly>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="type" class="col-sm-2 control-label">用户类型<font color="red">*</font></label>
                        <div class="col-sm-10">
                            <input type="radio" name="type" value="0">外包员工
                            <input type="radio" name="type" value="1">组长
                            <input type="radio" name="type" value="2">经理
                            <input type="radio" name="type" value="3">超级管理员
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="permission_biz" class="col-sm-2 control-label">权限</label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="permissionBiz"
                                                      placeholder="请输入“权限,多个逗号分隔”" maxlength="200"/></div>
                    </div>

                    <div class="form-group">
                        <label for="username_cn" class="col-sm-2 control-label">中文名<font color="red">*</font></label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="userNameCn"
                                                      placeholder="请输入“中文名”" maxlength="50"/></div>
                    </div>

                    <div class="form-group">
                        <label for="responsible_for" class="col-sm-2 control-label">负责内容<font
                                    color="red">*</font></label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="responsibleFor"
                                                      placeholder="请输入“负责内容”"/></div>
                    </div>

                    <div class="form-group">
                        <label for="groupname" class="col-sm-2 control-label">所属小组<font color="red">*</font></label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="groupName"
                                                      placeholder="请输入“所属小组”" maxlength="50"/></div>
                    </div>

                    <div class="form-group">
                        <label for="groupname_cn" class="col-sm-2 control-label">所属小组中文名<font
                                    color="red">*</font></label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="groupNameCn"
                                                      placeholder="请输入“所属小组中文名”" maxlength="50"/></div>
                    </div>

                    <div class="form-group">
                        <label for="principle" class="col-sm-2 control-label">汇报人</label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="principle"
                                                      placeholder="请输入“汇报人”" maxlength="50"/></div>
                    </div>

                    <div class="form-group">
                        <label for="entry_time" class="col-sm-2 control-label">入职时间</label>
                        <div class="col-sm-10"><input type="date" class="form-control" name="entryTime"
                                                      placeholder="请输入“入职时间”"/></div>
                    </div>

                    <div class="form-group">
                        <label for="born_time" class="col-sm-2 control-label">出生年月</label>
                        <div class="col-sm-10"><input type="date" class="form-control" name="bornTime"
                                                      placeholder="请输入“出生年月”"/></div>
                    </div>

                    <div class="form-group">
                        <label for="company" class="col-sm-2 control-label">所属公司</label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="company"
                                                      placeholder="请输入“所属公司”" maxlength="200"/></div>
                    </div>

                    <div class="form-group">
                        <label for="graduate_school" class="col-sm-2 control-label">毕业院校</label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="graduateSchool"
                                                      placeholder="请输入“毕业院校”" maxlength="200"/></div>
                    </div>

                    <div class="form-group">
                        <label for="edu_background" class="col-sm-2 control-label">学历</label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="eduBackground"
                                                      placeholder="请输入“学历”" maxlength="50"/></div>
                    </div>

                    <div class="form-group">
                        <label for="working_years" class="col-sm-2 control-label">工作年限</label>
                        <div class="col-sm-10"><input type="number" class="form-control" name="workingYears"
                                                      min="0" placeholder="请输入“工作年限”" maxlength="11"/></div>
                    </div>

                    <div class="form-group">
                        <label for="eamil" class="col-sm-2 control-label">工作邮箱<font color="red">*</font></label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="email"
                                                      placeholder="请输入“工作邮箱”" maxlength="200"/></div>
                    </div>

                    <div class="form-group">
                        <label for="mobile_number" class="col-sm-2 control-label">移动电话</label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="mobileNumber"
                                                      placeholder="请输入“移动电话”" maxlength="200"/></div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-offset-3 col-sm-6">
                            <button type="submit" class="btn btn-primary">保存</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>

                            <input type="hidden" name="id">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- 分配业务线权限.模态框 -->
<div class="modal fade" id="updatePermissionBizModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">分配项目权限</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal form" role="form">
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-8">
                            <div class="form-group">
                                <#list bizList as biz>
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" name="permissionBiz" value="${biz.id}">${biz.bizName}
                                        </label>
                                    </div>
                                </#list>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="button" class="btn btn-primary ok">保存</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>

                            <input type="hidden" name="id">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<@netCommon.commonScript />
<!-- DataTables -->
<script src="${request.contextPath}/static/adminlte/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="${request.contextPath}/static/adminlte/plugins/datatables/dataTables.bootstrap.min.js"></script>
<!-- moment -->
<script src="${request.contextPath}/static/adminlte/plugins/daterangepicker/moment.min.js"></script>

<script src="${request.contextPath}/static/js/user.list.1.js"></script>
</body>
</html>
