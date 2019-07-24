// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

$(function() {

	// init date tables
	var userListTable = $("#user_list").dataTable({
        "deferRender": true,
        "processing" : true,
        "serverSide": true,
        "ajax": {
            url: base_url + "/user/pageList",
            type: "post",
            data : function ( d ) {
                var obj = {};
                obj.userName = $('#userName').val();
                obj.type = $('#type').val();
                obj.start = d.start;
                obj.length = d.length;
                return obj;
            }
        },
        "searching": false,
        "ordering": false,
        //"scrollX": true,	// X轴滚动条，取消自适应
		"columns": [
			{ "data": 'id', "bSortable": false, "visible" : false},
			{ "data": 'userName', "visible" : true, "bSortable": false, 'width': '40%'},
			{
				"data": 'type',
				"visible" : true,
				"bSortable": false,
				'width': '30%',
                "render": function ( data, type, row ) {
					// 用户类型：0-外包员工、1-组长、2-经理、3-超级管理员
					var htm = '';
					if (data == 0) {
						htm = '外包员工';
					} else if (data == 1) {
						htm = '组长';
					} else if (data == 2) {
					    htm = '经理';
                    } else if (data == 3) {
					    htm = '超级管理员'
                    }
					return htm;
				}
			},
			{
				"data": '操作' ,
				"width":'30%',
				"bSortable": false,
				"render": function ( data, type, row ) {
					return function(){

                        var permissionBiz = '';
                        if (row.type != 3) {
                            permissionBiz = '<button class="btn btn-warning btn-xs permissionBiz" type="button">分配业务线权限</button>  ';
                        }

						// html
						var html = '<p id="'+ row.id +'" '+
							' userName="'+ row.userName +'" '+
							' password="'+ row.password +'" '+
							' type="'+ row.type +'" '+
                            ' permissionBiz="'+ row.permissionBiz +'" '+
                            ' userNameCn="' + row.userNameCn +'" ' +
                            ' responsibleFor="' + row.responsibleFor +'" ' +
                            ' groupName="' + row.groupName +'" ' +
                            ' groupNameCn="' + row.groupNameCn +'" ' +
                            ' principle="' + row.principle +'" ' +
                            ' entryTime="' + new Date(row.entryTime).Format("yyyy-MM-dd") +'" ' +
                            ' bornTime="' + new Date(row.bornTime).Format("yyyy-MM-dd") +'" ' +
                            ' company="' + row.company +'" ' +
                            ' graduateSchool="' + row.graduateSchool +'" ' +
                            ' eduBackground="' + row.eduBackground +'" ' +
                            ' workingYears="' + row.workingYears +'" ' +
                            ' email="' + row.email +'" ' +
                            ' mobileNumber="' + row.mobileNumber +'" ' +
                            '>'+
							'<button class="btn btn-warning btn-xs update" >编辑</button>  '+
                            permissionBiz +
							'<button class="btn btn-danger btn-xs delete" >删除</button>  '+
							'</p>';

						return html;
					};
				}
			}
		],
		"language" : {
			"sProcessing" : "处理中...",
			"sLengthMenu" : "每页 _MENU_ 条记录",
			"sZeroRecords" : "没有匹配结果",
			"sInfo" : "第 _PAGE_ 页 ( 总共 _PAGES_ 页，_TOTAL_ 条记录 )",
			"sInfoEmpty" : "无记录",
			"sInfoFiltered" : "(由 _MAX_ 项结果过滤)",
			"sInfoPostFix" : "",
			"sSearch" : "搜索:",
			"sUrl" : "",
			"sEmptyTable" : "表中数据为空",
			"sLoadingRecords" : "载入中...",
			"sInfoThousands" : ",",
			"oPaginate" : {
				"sFirst" : "首页",
				"sPrevious" : "上页",
				"sNext" : "下页",
				"sLast" : "末页"
			},
			"oAria" : {
				"sSortAscending" : ": 以升序排列此列",
				"sSortDescending" : ": 以降序排列此列"
			}
		}
	});

    $("#search").click(function(){
        userListTable.fnDraw();
    });

	// job operate
	$("#user_list").on('click', '.delete',function() {
		var id = $(this).parent('p').attr("id");

        layer.confirm( "确认删除该用户?" , {
            icon: 3,
            title: '系统提示' ,
            btn: [ '确定', '取消' ]
        }, function(index){
            layer.close(index);

			$.ajax({
				type : 'POST',
				url : base_url + "/user/delete",
				data : {
					"id" : id
				},
				dataType : "json",
				success : function(data){
					if (data.code == 200) {
                        layer.open({
                            icon: '1',
                            content: "删除成功" ,
                            end: function(layero, index){
                                userListTable.fnDraw(false);
                            }
                        });
					} else {
                        layer.open({
                            icon: '2',
                            content: (data.msg||'删除失败')
                        });
					}
				}
			});
		});
	});

    // jquery.validate 自定义校验
    jQuery.validator.addMethod("userNameValid", function(value, element) {
        var length = value.length;
        var valid = /^[a-z][a-z0-9.]*$/;
        return this.optional(element) || valid.test(value);
    }, "限制以小写字母开头，由小写字母、数字组成");

	// 新增
	$("#add").click(function(){
		$('#addModal').modal({backdrop: false, keyboard: false}).modal('show');
	});
	var addModalValidate = $("#addModal .form").validate({
		errorElement : 'span',  
        errorClass : 'help-block',
        focusInvalid : true,  
        rules : {
			userName : {
				required : true,
                rangelength: [4, 50],
				userNameValid: true
			},
			password : {
            	required : true,
                rangelength: [4, 50]
            }
        }, 
        messages : {
			userName : {
            	required :"请输入登录账号",
                rangelength : "登录账号长度限制为4~50"
            },
			password : {
            	required :"请输入密码",
                rangelength : "密码长度限制为4~50"
            }
        },
		highlight : function(element) {  
            $(element).closest('.form-group').addClass('has-error');  
        },
        success : function(label) {  
            label.closest('.form-group').removeClass('has-error');  
            label.remove();  
        },
        errorPlacement : function(error, element) {  
            element.parent('div').append(error);  
        },
        submitHandler : function(form) {
        	$.post(base_url + "/user/add",  $("#addModal .form").serialize(), function(data, status) {
    			if (data.code == "200") {
					$('#addModal').modal('hide');

                    layer.open({
                        icon: '1',
                        content: "新增成功" ,
                        end: function(layero, index){
                            userListTable.fnDraw(false);
                        }
                    });
    			} else {
                    layer.open({
                        icon: '2',
                        content: (data.msg || "新增失败")
                    });
    			}
    		});
		}
	});
	$("#addModal").on('hide.bs.modal', function () {
		$("#addModal .form")[0].reset();
		addModalValidate.resetForm();
		$("#addModal .form .form-group").removeClass("has-error");
		$(".remote_panel").show();	// remote
	});


    $("#updateModal .form input[name='passwordInput']").change(function () {
        var passwordInput = $("#updateModal .form input[name='passwordInput']").prop('checked');
        $("#updateModal .form input[name='password']").val( '' );
        if (passwordInput) {
            $("#updateModal .form input[name='password']").removeAttr("readonly");
        } else {
            $("#updateModal .form input[name='password']").attr("readonly","readonly");
        }
    });


	// 更新
	$("#user_list").on('click', '.update',function() {

		// base data
		$("#updateModal .form input[name='id']").val($(this).parent('p').attr("id"));
		$("#updateModal .form input[name='userName']").val($(this).parent('p').attr("userName"));
		$("#updateModal .form input[name='type']").eq($(this).parent('p').attr("type")).click();

        $("#updateModal .form input[name='passwordInput']").prop('checked', false);
        $("#updateModal .form input[name='password']").val( '' );
        $("#updateModal .form input[name='password']").attr("readonly","readonly");

        $("#updateModal .form input[name='permissionBiz']").val($(this).parent('p').attr("permissionBiz"));
        $("#updateModal .form input[name='userNameCn']").val($(this).parent('p').attr("userNameCn"));
        $("#updateModal .form input[name='responsibleFor']").val($(this).parent('p').attr("responsibleFor"));
        $("#updateModal .form input[name='groupName']").val($(this).parent('p').attr("groupName"));
        $("#updateModal .form input[name='groupNameCn']").val($(this).parent('p').attr("groupNameCn"));
        $("#updateModal .form input[name='principle']").val($(this).parent('p').attr("principle"));
        $("#updateModal .form input[name='entryTime']").val($(this).parent('p').attr("entryTime"));
        $("#updateModal .form input[name='bornTime']").val($(this).parent('p').attr("bornTime"));
        $("#updateModal .form input[name='company']").val($(this).parent('p').attr("company"));
        $("#updateModal .form input[name='graduateSchool']").val($(this).parent('p').attr("graduateSchool"));
        $("#updateModal .form input[name='eduBackground']").val($(this).parent('p').attr("eduBackground"));
        $("#updateModal .form input[name='workingYears']").val($(this).parent('p').attr("workingYears"));
        $("#updateModal .form input[name='email']").val($(this).parent('p').attr("email"));
        $("#updateModal .form input[name='mobileNumber']").val($(this).parent('p').attr("mobileNumber"));

		// show
		$('#updateModal').modal({backdrop: false, keyboard: false}).modal('show');
	});
	var updateModalValidate = $("#updateModal .form").validate({
		errorElement : 'span',  
        errorClass : 'help-block',
        focusInvalid : true,
        rules : {
            password : {
                required : true,
                rangelength: [4, 50]
            }
        },
        messages : {
            password : {
                required :"请输入密码",
                rangelength : "密码长度限制为4~50"
            }
        },
		highlight : function(element) {
            $(element).closest('.form-group').addClass('has-error');  
        },
        success : function(label) {  
            label.closest('.form-group').removeClass('has-error');  
            label.remove();  
        },
        errorPlacement : function(error, element) {  
            element.parent('div').append(error);  
        },
        submitHandler : function(form) {
			// post
    		$.post(base_url + "/user/update", $("#updateModal .form").serialize(), function(data, status) {
    			if (data.code == "200") {
					$('#updateModal').modal('hide');

                    layer.open({
                        icon: '1',
                        content: "更新成功" ,
                        end: function(layero, index){
                            userListTable.fnDraw(false);
                        }
                    });
    			} else {
                    layer.open({
                        icon: '2',
                        content: (data.msg || "更新失败")
                    });
    			}
    		});
		}
	});
	$("#updateModal").on('hide.bs.modal', function () {
		$("#updateModal .form")[0].reset()
	});


    // 分配项目权限
    $("#user_list").on('click', '.permissionBiz',function() {
        var id = $(this).parent('p').attr("id");
        var permissionBiz = $(this).parent('p').attr("permissionBiz");
        $("#updatePermissionBizModal .form input[name='id']").val( id );

        var permissionBizChoose;
        if (permissionBiz) {
            permissionBizChoose = $(permissionBiz.split(","));
        }
        $("#updatePermissionBizModal .form input[name='permissionBiz']").each(function () {
            if ( $.inArray($(this).val(), permissionBizChoose) > -1 ) {
                $(this).prop("checked",true);
            } else {
                $(this).prop("checked",false);
            }
        });

        $('#updatePermissionBizModal').modal('show');
    });
    $('#updatePermissionBizModal .ok').click(function () {
        $.post(base_url + "/user/updatePermissionBiz", $("#updatePermissionBizModal .form").serialize(), function(data, status) {
            if (data.code == 200) {
                layer.open({
                    icon: '1',
                    content: '操作成功' ,
                    end: function(layero, index){
                        userListTable.fnDraw(false);
                        $('#updatePermissionBizModal').modal('hide');
                    }
                });
            } else {
                layer.open({
                    icon: '2',
                    content: (data.msg||'操作失败')
                });
            }
        });
    });
    $("#updatePermissionBizModal").on('hide.bs.modal', function () {
        $("#updatePermissionBizModal .form")[0].reset()
    });

});
