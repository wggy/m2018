$(function () {
    var $list = $('#fileList');

    var uploader = WebUploader.create({
        auto: false,
        swf: '../../plugins/webuploader/Uploader.swf',
        server: '/docker/upload/sample/file?token=' + localStorage.getItem("token"),
        pick: '#picker',
        chunked: true,
        chunkSize: 1 * 1024 * 1024,
        chunkRetry: 3,
        threads: 5,
        fileSizeLimit: 10000 * 1024 * 1024,
        fileSingleSizeLimit: 15000 * 1024 * 1024,
        resize: false
    });

    $("#jqGrid").jqGrid({
        url: baseURL + 'docker/sick/list',
        datatype: "local",
        colModel: [
            {label: 'ID', name: 'id', width: 50, key: true},
            {label: '编码', name: 'sickCode', width: 100},
            {label: '病人姓名', name: 'sickName', width: 100},
            {label: '性别', name: 'sex', width: 50},
            {label: '出生日期', name: 'birthday', width: 150},
            {label: '申请医生', name: 'doctor', width: 100},
            {label: '创建时间', name: 'createTime', width: 150}
        ],
        viewrecords: true,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        multiboxonly: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    $("#jqGrid1").jqGrid({
        url: baseURL + 'docker/sample/list',
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', width: 30, key: true},
            {label: '病患编码', name: 'sickCode', width: 60},
            {label: '病患姓名', name: 'sickName', width: 60},
            {label: '第一文件', name: 'originName', width: 80},
            {label: '存储路径', name: 'location', width: 120},
            {label: '上传开始时间', name: 'uploadStartTime', width: 90},
            {label: '上传状态', name: 'uploadStatus', width: 60},
            {label: '上传完成时间', name: 'uploadFinishTime', width: 90},
            {label: '调度开始时间', name: 'triggerStartTime', width: 90},
            {label: '执行状态', name: 'triggerStatus', width: 60},
            {label: '调度完成时间', name: 'triggerFinishTime', width: 90},
            {label: '入库开始时间', name: 'storeStartTime', width: 90},
            {label: '入库状态', name: 'storeStatus', width: 60},
            {label: '入库完成时间', name: 'storeFinishTime', width: 90},
            {label: '第二文件', name: 'secOriginName', width: 90},
            {label: 'MD5', name: 'md5', width: 90}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager1",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid1").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    $('#sickSelect').click(function () {
        var page = $("#jqGrid").jqGrid('getGridParam', 'page');
        $("#jqGrid").jqGrid('setGridParam', {
            datatype: 'json',
            page: page
        }).trigger("reloadGrid");

        layer.open({
            type: 1,
            offset: '50px',
            skin: 'layui-layer-molv',
            title: "关联病人",
            area: ['775px', '490px'],
            shade: 0,
            shadeClose: false,
            content: jQuery("#sickLayer"),
            btn: ['确定', '取消'],
            btn1: function (index) {
                var grid = $("#jqGrid");
                var rowKey = grid.getGridParam("selrow");
                if (!rowKey) {
                    alert("请选择一条记录");
                    return;
                }
                var selectedIDs = grid.getGridParam("selarrrow");
                if (selectedIDs.length > 1) {
                    alert("只能选择一条记录");
                    return;
                }
                var rowData = grid.jqGrid('getRowData', rowKey);
                $('#sickId').val(rowData.id);
                $('#sickSelect').val(rowData.sickName);
                layer.close(index);
            }
        });
    });

    uploader.on('uploadBeforeSend', function (block, data, headers) {

        console.log(block);
        console.log(data);
        var file = block.file;
        // var fileMd5 = file.wholeMd5;
        data.guid = file.guid;
        data.sickId = file.sickId;
    });

    uploader.on('fileQueued', function (file) {

        var sickId = $('#sickId').val();
        if (!sickId) {
            alert("未选择病人");
            return;
        }

        $list.append('<div id="' + file.id + '" class="item">' +
            '<h4 class="info">' + file.name + '<button type="button" fileId="' + file.id + '" class="btn btn-danger btn-delete"><span class="glyphicon glyphicon-trash"></span></button></h4>' +
            '</div>');

        $(".btn-delete").click(function () {
            uploader.removeFile(uploader.getFile($(this).attr("fileId"), true));
            $(this).parent().parent().fadeOut();
            $(this).parent().parent().remove();
        });
        file.guid = WebUploader.guid();
        file.sickId = sickId;
        /*

                var index = parent.layer.load(1, {shade: [0.38, '#fff']});
                uploader.md5File(file).then(function (fileMd5) {
                    parent.layer.close(index);
                    file.wholeMd5 = fileMd5;
                    console.log(file.name + "=" + fileMd5);
                    console.log(file.name + "=" + file.guid);

                    $('#' + file.id).find('p.state').text('MD5计算完毕，可以点击上传了');

                    $.ajax({
                        cache: false,
                        type: "post",
                        dataType: "json",
                        url: "/docker/upload/check_md5",
                        data: {
                            fileMd5: fileMd5,
                            fileName: file.name,
                            fileID: file.id
                        },
                        success: function (result) {
                            if (result.msg == 'this file is exist') {
                                uploader.removeFile(file, true);
                                $('#' + file.id).find('p.state').text('该文件已上传');
                                $('#' + file.id).find(".progress").find(".progress-bar").attr("class", "progress-bar progress-bar-success");
                                $('#' + file.id).find(".info").find('.btn').fadeOut('slow');
                                $("#StopBtn").fadeOut('slow');
                            } else {
                                console.log("服务器上没有同样的文件，秒传失败！");
                            }
                        }
                    });
                });
        */

    });

    uploader.on('uploadProgress', function (file, percentage) {
        var $li = $('#' + file.id),
            $percent = $li.find('.progress .progress-bar');

        // 避免重复创建
        if (!$percent.length) {
            $percent = $('<div class="progress progress-striped active">' +
                '<div class="progress-bar" role="progressbar" style="width: 0%">' +
                '</div>' +
                '</div>').appendTo($li).find('.progress-bar');
        }

        $li.find('p.state').text('上传中');
        $percent.css('width', percentage * 100 + '%');
        $percent.html(percentage * 100 + '%');
    });

    uploader.on('uploadSuccess', function (file) {
        $('#' + file.id).find('p.state').text('已上传');
        $('#' + file.id).find(".progress").find(".progress-bar").attr("class", "progress-bar progress-bar-success");
        $('#' + file.id).find(".info").find('.btn').fadeOut('slow');//上传完后删除"删除"按钮
        $('#StopBtn').fadeOut('slow');
    });

    uploader.on('uploadError', function (file) {
        $('#' + file.id).find('p.state').text('上传出错');
        //上传出错后进度条爆红
        $('#' + file.id).find(".progress").find(".progress-bar").attr("class", "progress-bar progress-bar-danger");
        //添加重试按钮
        //为了防止重复添加重试按钮，做一个判断
        if ($('#' + file.id).find(".btn-retry").length < 1) {
            var btn = $('<button type="button" fileid="' + file.id + '" class="btn btn-success btn-retry"><span class="glyphicon glyphicon-refresh"></span></button>');
            $('#' + file.id).find(".info").append(btn);//.find(".btn-danger")
        }

        $(".btn-retry").click(function () {
            uploader.retry(uploader.getFile($(this).attr("fileId")));
        });
    });

    uploader.on('uploadComplete', function (file) {//上传完成后回调
//            $('#' + file.id).find('.progress').fadeOut();//上传完删除进度条
//            $('#' + file.id + 'btn').fadeOut('slow')//上传完后删除"删除"按钮
    });

    uploader.on('uploadFinished', function () {
        reload();
    });

    $("#UploadBtn").click(function () {

        uploader.upload();//上传
    });

    $('#queryBtn').click(function () {
        reload();
    });

    $("#StopBtn").click(function () {
        console.log($('#StopBtn').attr("status"));
        var status = $('#StopBtn').attr("status");
        if (status == "suspend") {
            console.log("当前按钮是暂停，即将变为继续");
            $("#StopBtn").html("继续上传");
            $("#StopBtn").attr("status", "continuous");

            console.log("__________________当前所有的文件_______________________");
            console.log(uploader.getFiles());
            console.log("__________________暂停上传_____________________________");
            uploader.stop(true);
            console.log("__________________所有当前暂停的文件___________________");
            console.log(uploader.getFiles("interrupt"));
        } else {
            console.log("当前按钮是继续，即将变为暂停");
            $("#StopBtn").html("暂停上传");
            $("#StopBtn").attr("status", "suspend");

            console.log("__________________所有当前暂停的文件___________________");
            console.log(uploader.getFiles("interrupt"));
            uploader.upload(uploader.getFiles("interrupt"));
        }
    });

    uploader.on('uploadAccept', function (file, response) {
        if (response._raw === '{"error":true}') {
            return false;
        }
    });

    function reload() {
        var page = $("#jqGrid1").jqGrid('getGridParam', 'page');
        $("#jqGrid1").jqGrid('setGridParam', {
            postData: {'key': $('#keyVal').val()},
            page: page
        }).trigger("reloadGrid");
    }
});