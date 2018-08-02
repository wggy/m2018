$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'docker/report/list',
        datatype: "json",
        colModel: [			
			{ label: '报告编号', name: 'sickCode', width: 60, formatter: function (cellvalue, options, rowObject) {
                    return '<a class="sickcode" href="javascript:;" attrcode="' + rowObject.sickCode + '">' + rowObject.sickCode + '</a>';
                }},
			{ label: '检测产品', name: 'productName', width: 60 },
			{ label: '送样时间', name: 'uploadTime', width: 60 },
			{ label: '调度开始时间', name: 'triggerTime', width: 60 },
			{ label: '调度完成时间', name: 'handlerTime', width: 60 },
            { label: '调度状态', name: 'handlerStatus', width: 60 },
            { label: '入库开始时间', name: 'storeTime', width: 60 },
			{ label: '入库完成时间', name: 'finishTime', width: 60 },
			{ label: '入库状态', name: 'storeStatus', width: 60 }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: false,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });

    $('#jqGrid').on('click', '.sickcode', function () {
        window.parent.location.hash = '#modules/docker/gene_search.html?m=' + $(this).attr('attrcode');
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			key: null
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		reload: function () {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'key': vm.q.key},
                page:page
            }).trigger("reloadGrid");
		}
	}
});