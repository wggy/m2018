$(function () {
    $("#jqGrid").jqGrid({
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
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            key: null
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        execute: function () {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            $.ajax({
                type: "POST",
                url: baseURL + "docker/sample/execute/" + id,
                contentType: "application/json",
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        store: function () {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            $.ajax({
                type: "POST",
                url: baseURL + "docker/sample/store/" + id,
                contentType: "application/json",
                data: JSON.stringify(vm.config),
                success: function(r){
                    if(r.code === 0){
                        alert(r.msg || '操作成功', function(){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        merge: function () {
            var ids = getSelectedRows();
            if(ids == null || ids.length > 2){
                alert('最多选择两条记录');
                return ;
            }
            $.ajax({
                type: "POST",
                url: baseURL + "docker/sample/merge",
                contentType: "application/json",
                data: JSON.stringify(ids),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        more: function () {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            $('#sickAttrId').val(id);
            parent.layer.open({
                type: 2,
                area: ['700px', '450px'],
                fixed: false,
                maxmin: true,
                content: 'modules/docker/more_log.html'
            });
        },
        reload: function () {
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'key': vm.q.key},
                page: page
            }).trigger("reloadGrid");
        }
    }
});