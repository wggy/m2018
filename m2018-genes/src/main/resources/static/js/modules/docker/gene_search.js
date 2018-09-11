var GeneSearch = {
    sickCode: null,
    productId: null
};

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            popfreqmax: 1,
            exacAll: 1,
            a1000gAll: 1,
            exacEas: 1,
            not_synonymous_SNV: true
        },
        title: null,
        sickInfo: {},
        noConditionCount: 0,
        totalCount: 0
    },
    methods: {
        query: function () {
            vm.reload();
        },
        report: function () {
            var ids = getSelectedRows();
            if (!ids) {
                return;
            }
            var params = {ids: ids.join(','), token: localStorage.getItem("token")};
            params.productId = GeneSearch.productId;
            params.sickCode = GeneSearch.sickCode;
            confirm('确定要生成报告？', function () {
                GeneSearch.downLoad(baseURL + "docker/gene_search/download", params, 'post');
                parent.layer.msg('下载完成', {icon: 1});
            });
        },
        isNum: function (value) {
            if (/\D/.test(value)) {
                alert('只能输入数字');
                this.value = '';
            }
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: $.extend({}, vm.q, GeneSearch),
                page: page
            }).trigger("reloadGrid");
        }
    }
});

GeneSearch.initSickInfo = function () {
    $.getJSON(baseURL + "docker/gene_search/sick_info", {
        sickCode: GeneSearch.sickCode,
        productId: GeneSearch.productId
    }, function (r) {
        vm.sickInfo = r.info;
    });
};

GeneSearch.downLoad = function (url, data, method) {

    if (url && data) {
        data = typeof data == 'string' ? data : $.param(data);
        var inputs = '';
        $.each(data.split('&'), function () {
            var pair = this.split('=');
            inputs += '<input type="hidden" name="' + pair[0] + '" value="' + pair[1] + '" />';
        });
        $('<form action="' + url + '" method="' + (method || 'post') + '">' + inputs + '</form>')
            .appendTo('body').submit().remove();
    }
};

$(function () {

    var sickParams = parent.document.getElementById('sickParams').value;
    if (!sickParams) {
        console.error("未读取到参数");
        return;
    }
    var pArray = sickParams.split('-');
    GeneSearch.sickCode = pArray[0];
    GeneSearch.productId = pArray[1];
    GeneSearch.initSickInfo();


    $("#jqGrid").jqGrid({
        url: baseURL + 'docker/gene_search/list',
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', width: 30, key: true},
            {label: '基因', name: 'geneRefgene', width: 60},
            {label: 'Hpo', name: 'hop', width: 50},
            {label: '突变信息', name: 'mutationInfo', width: 100},
            {label: 'HGMD数据库', name: 'hgmd', width: 50},
            {label: 'Clinical_variant ', name: 'clinicalVariant', width: 50},
            {label: '个人专属库 ', name: 'personalDb', width: 50},
            {label: '突变位点质量', name: 'mutationQuatity', width: 50},
            {label: '疾病信息', name: 'xrefRefgene', width: 100},
            {label: '遗传方式', name: 'hereditaryMode', width: 60},
            {label: 'HPO/代谢数据库', name: 'hpoMetabolizeDb', width: 60},
            {label: '主要致病突变类型', name: 'mainMutationMode', width: 60},
            {label: 'test1', name: 'sampleNameAttr', width: 60},
            {label: 'Allele frequency', name: 'alleleFrequency', width: 60},
            {label: 'Revel/M-CAP', name: 'revelMcap', width: 60},
            {label: 'SIFT/Polyphen', name: 'siftPolyphen', width: 60},
            {label: '单点分数', name: 'singlePointScore', width: 60},
            {label: 'Bp Score', name: 'bpScore', width: 60}
        ],
        postData: $.extend({}, vm.q, GeneSearch),
        viewrecords: true,
        height: '100%',
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
        loadComplete: function (data) {
            if (data.code == 0) {
                vm.totalCount = data.page.totalCount;
                vm.noConditionCount = data.page.noConditionCount;
            } else {
                console.error(data.msg || "服务器错误");
            }
        },
        gridComplete: function () {
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-y": "hidden"});
        }
    });
});