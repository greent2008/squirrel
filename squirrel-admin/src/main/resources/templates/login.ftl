<!DOCTYPE html>
<html>
<head>
    <title>Squirrel人力资源管理系统</title>
    <#import "common/common.macro.ftl" as netCommon>
    <@netCommon.commonStyle />
    <link rel="stylesheet" href="${request.contextPath}/static/adminlte/plugins/iCheck/square/blue.css">
</head>
<body class="hold-transition login-page">
<div class="login-box">
    <div class="login-logo">
        <a><b>Squirrel</b></a>
    </div>
    <form id="loginForm" method="post">
        <div class="login-box-body">
            <p class="login-box-msg">人力资源管理系统</p>
            <div class="form-group has-feedback">
                <input type="text" name="userName" class="form-control" placeholder="请输入登陆账号" value="admin"
                       maxlength="50">
                <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" name="password" class="form-control" placeholder="请输入登陆密码" value="123456"
                       maxlength="50">
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <div class="row">
                <div class="col-xs-8">
                    <div class="checkbox icheck">
                        <label>
                            <input type="checkbox" name="ifRemember"> 记住我
                        </label>
                    </div>
                </div><!-- /.col -->
                <div class="col-xs-4">
                    <button type="submit" class="btn btn-primary btn-block btn-flat">登陆</button>
                </div>
            </div>
        </div>
    </form>
</div>
<@netCommon.commonScript />
<script src="${request.contextPath}/static/adminlte/plugins/iCheck/icheck.min.js"></script>
<script src="${request.contextPath}/static/js/login.1.js"></script>

</body>
</html>