var GeneSearch = {
    sickCode: null,
    productId: null
};

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            key: null
        },
        title: null,
        sickInfo: {}
    },
    methods: {
        query: function () {
            vm.reload();
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

GeneSearch.initSickInfo = function () {
    $.getJSON(baseURL + "docker/gene_search/sick_info", {
        sickCode: GeneSearch.sickCode,
        productId: GeneSearch.productId
    }, function (r) {
        vm.sickInfo = r.info;
    });
};

$(function () {
    var href = window.location.href;
    var params = href.replace("m=", "").substring(href.indexOf('?') + 1);
    var pArray = params.split('-');
    GeneSearch.sickCode = pArray[0];
    GeneSearch.productId = pArray[1];
    GeneSearch.initSickInfo();


    $("#jqGrid").jqGrid({
        url: baseURL + 'docker/gene_search/list',
        datatype: "json",
        colModel: [
            { label: 'ID', name: 'id', width: 30, key: true },
            { label: '基因', name: 'geneRefgene', width: 60},
            { label: 'Hpo', name: 'hop', width: 50 },
            { label: '突变信息', name: 'mutationInfo', width: 100 },
            { label: 'HGMD数据库', name: 'hgmd', width: 50 },
            { label: 'Clinical_variant ', name: 'clinicalVariant', width: 50 },
            { label: '个人专属库 ', name: 'personalDb', width: 50 },
            { label: '突变位点质量', name: 'mutationQuatity', width: 50},
            { label: '疾病信息', name: 'xrefRefgene', width: 100},
            { label: '遗传方式', name: 'hereditaryMode', width: 60},
            { label: 'HPO/代谢数据库', name: 'hpoMetabolizeDb', width: 60},
            { label: '主要致病突变类型', name: 'mainMutationMode', width: 60},
            { label: 'test1', name: 'sampleName', width: 60},
            { label: 'Allele frequency', name: 'alleleFrequency', width: 60},
            { label: 'Revel/M-CAP', name: 'revelMcap', width: 60},
            { label: 'SIFT/Polyphen', name: 'siftPolyphen', width: 60},
            { label: '单点分数', name: 'singlePointScore', width: 60},
            { label: 'Bp Score', name: 'bpScore', width: 60}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        multiselect: true,
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
});