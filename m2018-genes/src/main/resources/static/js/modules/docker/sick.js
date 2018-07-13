$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'docker/sick/list',
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', width: 30, key: true},
            {label: '编码', name: 'sickCode', width: 60},
            {label: '病人姓名', name: 'sickName', width: 60},
            {label: '性别', name: 'sex', width: 30},
            {label: '家庭成员', name: 'family', width: 80},
            {label: '疾病基因', name: 'diseaseGeneFocused', width: 90},
            {label: '病史药史', name: 'familyHistory', width: 90},
            {label: '病情描述', name: 'panelName', width: 90},
            {label: '创建时间', name: 'createTime', width: 80},
            {label: '更新时间', name: 'updateTime', width: 80}
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

    new AjaxUpload('#upload', {
        action: null,
        name: 'file',
        autoSubmit: true,
        responseType: "json",
        onSubmit: function (file, extension) {
            var id = getSelectedRow();
            if (id == null) {
                return false;
            }
            this._settings.action = baseURL + 'docker/sample/upload/' + id +'?token=' + token;
            if (!(extension && /^(xls|jpg|png)$/.test(extension.toLowerCase()))) {
                alert('只支持jpg、png、gif格式的图片！');
                return false;
            }
        },
        onComplete: function (file, r) {
            if (r.code == 0) {
                alert("上传成功，路径：" + r.url);
                vm.reload();
            } else {
                alert(r.msg);
            }
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            key: null
        },
        showList: true,
        title: null,
        sick: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.sick = {};
        },
        update: function () {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }

            $.get(baseURL + "docker/sick/info/" + id, function (r) {
                vm.showList = false;
                vm.title = "修改";
                vm.sick = r.info;
            });
        },
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "docker/sick/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function () {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        saveOrUpdate: function () {
            var url = vm.sick.id == null ? "docker/sick/save" : "docker/sick/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.sick),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function () {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'key': vm.q.key},
                page: page
            }).trigger("reloadGrid");
        }
    }
});