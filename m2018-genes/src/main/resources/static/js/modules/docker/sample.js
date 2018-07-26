$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'docker/sample/list',
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', width: 30, key: true},
            {label: '病患编码', name: 'sickCode', width: 60},
            {label: '病患姓名', name: 'sickName', width: 60},
            {label: '原始文件', name: 'originName', width: 80},
            {label: '存储文件', name: 'location', width: 120},
            {label: '上传时间', name: 'uploadTime', width: 90},
            {label: '调度时间', name: 'triggerTime', width: 90},
            {label: '执行状态', name: 'handlerStatus', width: 70},
            {label: '执行时间', name: 'handlerTime', width: 90}
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
            if (id == null) {
                return;
            }
            $.ajax({
                type: "POST",
                url: baseURL + "docker/sample/execute/" + id,
                contentType: "application/json",
                data: JSON.stringify(vm.config),
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
        reload: function () {
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'key': vm.q.key},
                page: page
            }).trigger("reloadGrid");
        }
    }
});