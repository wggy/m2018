var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId",
            rootPId: 0
        },
        key: {
            url:"nourl"
        }
    }
};
var ztree;
var processBar;

var vm = new Vue({
    el:'#rrapp',
    data:{
        q: {
            sickId: null,
            sickName: null
        }
    },
    methods: {
        selectSick: function(){
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
                    if(!rowKey){
                        alert("请选择一条记录");
                        return ;
                    }
                    var selectedIDs = grid.getGridParam("selarrrow");
                    if(selectedIDs.length > 1){
                        alert("只能选择一条记录");
                        return ;
                    }
                    var rowData = grid.jqGrid('getRowData',rowKey);
                    vm.q.sickId = rowData.id;
                    vm.q.sickName = rowData.sickName;
                    layer.close(index);
                }
            });
        },
        reload: function () {
            OSSFile.table.refresh();
        },
        copy: function () {
            if (!vm.q.sickId) {
                alert("请选择关联的病人");
                return;
            }
            var fileId = getOssFileId();
            if (!fileId) {
                return;
            }
            $.post(baseURL + 'docker/ossfile/download/' + vm.q.sickId, {fileId: fileId}, function (r) {
                if (r.code === 0) {
                    alert('加入下载队列成功');
                    // layer.open({
                    //     type: 1,
                    //     offset: '50px',
                    //     skin: 'layui-layer-molv',
                    //     title: "下载进度",
                    //     area: ['600px', '350px'],
                    //     shade: 0,
                    //     shadeClose: true,
                    //     content: jQuery("#progressLayer")
                    // });
                    // if (!processBar) {
                    //     window.clearInterval(processBar);
                    // }
                    // processBar = window.setInterval(function () {
                    //     $.post(baseURL + 'docker/ossfile/progress/' + r.sampleId, function (r) {
                    //         console.log(r);
                    //         if (r.code === 0) {
                    //             console.log(r.msg || '加载进度成功');
                    //         } else {
                    //             window.clearInterval(processBar);
                    //             console.error(r.msg || '加载进度失败');
                    //         }
                    //     });
                    // }, 2000);
                } else {
                    alert('下载失败');
                }
            });
        }
    }
});


var OSSFile = {
    id: "fileTable",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
OSSFile.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true, align: 'center', valign: 'middle', sortable: true, width: '80px'},
        {title: '文件名称', field: 'fileName', align: 'center', valign: 'middle', sortable: true, width: '230px'},
        {title: '文件ID', field: 'id', visible: false, align: 'center', valign: 'middle', width: '80px'},
        {title: '类型', field: 'fileType', align: 'center', valign: 'middle', sortable: true, width: '80px', formatter: function(item, index){
            if(item.fileType === 'D'){
                return '<span class="label label-primary">目录</span>';
            }
            if(item.fileType === 'F'){
                return '<span class="label label-success">文件</span>';
            }
        }},
        {title: '文件大小', field: 'fileSizeFmt', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '路径', field: 'path', align: 'center', valign: 'middle', sortable: true, width: '280px'},
        {title: '层级', field: 'level', align: 'center', valign: 'middle', sortable: true, width: '80px'},
        {title: 'BUCKET', field: 'bucketName', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '最后修改时间', field: 'lastUpdateTime', align: 'center', valign: 'middle', sortable: true, width: '150px'}
    ];
    return columns;
};


function getOssFileId () {
    var selected = $('#fileTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择文件一条记录");
        return false;
    } else {
        return selected[0].id;
    }
}

function initTable() {
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
        multiboxonly:true,
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
}

$(function () {
    var colunms = OSSFile.initColumn();
    var table = new TreeTable(OSSFile.id, baseURL + "docker/ossfile/list", colunms);
    table.setExpandColumn(1);
    table.setIdField("id");
    table.setCodeField("id");
    table.setParentCodeField("parentId");
    table.setExpandAll(false);
    table.init();
    initTable();
    OSSFile.table = table;
});
