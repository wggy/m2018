/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.20
 * Generated at: 2016-05-05 09:00:38 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.push;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class edit_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(3);
    _jspx_dependants.put("/push/../include/head.jsp", Long.valueOf(1460338154000L));
    _jspx_dependants.put("/WEB-INF/lib/jstl-1.2.jar", Long.valueOf(1460446166000L));
    _jspx_dependants.put("jar:file:/data/wwwroot/nubia_nps_admin/WEB-INF/lib/jstl-1.2.jar!/META-INF/c.tld", Long.valueOf(1153356282000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

final java.lang.String _jspx_method = request.getMethod();
if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
return;
}

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<title>应用管理</title>\r\n");
      out.write("\r\n");
      out.write("\r\n");

	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
	request.setAttribute("ctx", basePath);

      out.write(" \r\n");
      out.write("<script>\r\n");
      out.write("var ctx =\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctx }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\";\r\n");
      out.write("</script>\r\n");
      out.write("<link rel=\"stylesheet\" href=\"/res/bootstrap/core/bootstrap.min.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" href=\"/res/bootstrap/core/bootstrap-theme.min.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" href=\"/res/bootstrap-table/bootstrap-table.min.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" href=\"/res/common/common.css\" />\r\n");
      out.write("\r\n");
      out.write("<script src=\"/res/jquery.js\"></script>\r\n");
      out.write("<script src=\"/res/jquery.json-2.2.min.js\"></script>\r\n");
      out.write("<script src=\"/res/bootstrap/core/bootstrap.min.js\"></script>\r\n");
      out.write("<script src=\"/res/bootstrap-table/bootstrap-table.min.js\"></script>\r\n");
      out.write("<script src=\"/res/layer/layer.js\"></script>\r\n");
      out.write("<script src=\"/res/common/common.js\"></script>\r\n");
      out.write("<script src=\"/res/My97DatePicker/WdatePicker.js\"></script>\r\n");
      out.write("<script src=\"/res/DateUtil.js\"></script>");
      out.write("\r\n");
      out.write("<style>\r\n");
      out.write(".form-control{height: 30px;}\r\n");
      out.write("input[type=radio],input[type=checkbox]{width: 16px; height: 16px; margin-right: 4px; vertical-align: text-bottom;}\r\n");
      out.write("input[type=file]{display: inline-block;}\r\n");
      out.write(".Wdate{\r\n");
      out.write("    height: 24px;\r\n");
      out.write("\tpadding: 6px 12px;\r\n");
      out.write("\tfont-size: 14px;\r\n");
      out.write("\tline-height: 1.42857143;\r\n");
      out.write("\tcolor: #555;\r\n");
      out.write("\tbackground-color: #fff;\r\n");
      out.write("\tborder: 1px solid #ccc;\r\n");
      out.write("\tborder-radius: 4px;\r\n");
      out.write("\t-webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);\r\n");
      out.write("\tbox-shadow: inset 0 1px 1px rgba(0,0,0,.075);\r\n");
      out.write("\t-webkit-transition: border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;\r\n");
      out.write("\t-o-transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;\r\n");
      out.write("\ttransition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;\r\n");
      out.write("}\r\n");
      out.write("</style>\r\n");
      out.write("</head>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctx}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/push/edit.js\" ></script>\r\n");
      out.write("<body onload=\"clear()\">\r\n");
      out.write("<div class=\"container-fluid\">\r\n");
      out.write("    <h2 class=\"page-header\">应用添加</h2>\r\n");
      out.write("          <label>基本设定</label>\r\n");
      out.write("\t  <form id=\"uploadForm\" class=\"form-horizontal\" enctype=\"multipart/form-data\" action=\"/adminpush/send.do\" method=\"post\" name=\"form1\" id=\"form1\">\r\n");
      out.write("\t      <input id=\"appId\" name=\"appId\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${id}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" type=\"hidden\"/>\r\n");
      out.write("\t      <div class=\"form-group\">\r\n");
      out.write("\t            <label for=\"pushStyle\" class=\"col-sm-2 control-label\">样式</label>\r\n");
      out.write("\t             <div class=\"col-sm-8\">\r\n");
      out.write("\t\t      \t\t<input type=\"radio\" id=\"pushStyle\" name=\"pushStyle\" value=\"1\" checked=\"checked\" onclick=\"showStyle()\">Android默认通知</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\r\n");
      out.write("\t\t      \t\t<input type=\"radio\" id=\"pushStyle\" name=\"pushStyle\" value=\"2\" onclick=\"hideStyle()\">透传</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\r\n");
      out.write("\t          \t</div>\r\n");
      out.write("\t            <div class=\"col-sm-2 error-msg\"></div>\r\n");
      out.write("\t      </div>\r\n");
      out.write("\t      <div id=\"div1\">\r\n");
      out.write("\t\t      <div class=\"form-group\">\r\n");
      out.write("\t\t          <label for=\"title\" class=\"col-sm-2 control-label\">标题</label>\r\n");
      out.write("\t\t          <div class=\"col-sm-4\">\r\n");
      out.write("\t\t              <input type=\"text\" class=\"form-control\" id=\"title\" name=\"title\" placeholder=\"不可超过15字符\"/>\r\n");
      out.write("\t\t          </div>\r\n");
      out.write("\t\t          <div class=\"col-sm-2 error-msg\"></div>\r\n");
      out.write("\t\t      </div>\r\n");
      out.write("\t\t      <div class=\"form-group\">\r\n");
      out.write("\t\t          <label for=\"description\" class=\"col-sm-2 control-label\">描述</label>\r\n");
      out.write("\t\t          <div class=\"col-sm-4\">\r\n");
      out.write("\t\t              <textarea rows=\"5\" cols=\"50\" id=\"description\" name=\"description\" placeholder=\"不可超过40字符\">\r\n");
      out.write("\t\t              </textarea>\r\n");
      out.write("\t\t          </div>\r\n");
      out.write("\t\t          <div class=\"col-sm-2 error-msg\"></div>\r\n");
      out.write("\t\t      </div>\r\n");
      out.write("\t      </div>\r\n");
      out.write("\t      <div class=\"form-group\">\r\n");
      out.write("\t          <label for=\"content\" class=\"col-sm-2 control-label\">内容</label>\r\n");
      out.write("\t          <div class=\"col-sm-4\">\r\n");
      out.write("\t              <textarea rows=\"5\" cols=\"50\" id=\"content\" name=\"content\" placeholder=\"不可超过1000字符\">\r\n");
      out.write("\t              </textarea>\r\n");
      out.write("\t          </div>\r\n");
      out.write("\t          <div class=\"col-sm-2 error-msg\"></div>\r\n");
      out.write("\t      </div>\r\n");
      out.write("\t       <div class=\"form-group\" id=\"notifyIdModule\">\r\n");
      out.write("              <label for=\"notifyId\" class=\"col-sm-2 control-label\">notifyId</label>\r\n");
      out.write("              <div class=\"col-sm-4\">\r\n");
      out.write("                  <select id=\"notifyId\" name=\"notifyId\">\r\n");
      out.write("                     <option value=\"0\">0</option>\r\n");
      out.write("                     <option value=\"1\">1</option>\r\n");
      out.write("                     <option value=\"2\">2</option>\r\n");
      out.write("                     <option value=\"3\">3</option>\r\n");
      out.write("                     <option value=\"4\">4</option>\r\n");
      out.write("                  </select>\r\n");
      out.write("              </div>\r\n");
      out.write("              <div class=\"col-sm-2 error-msg\"></div>\r\n");
      out.write("          </div>\r\n");
      out.write("\t      <div class=\"form-group\" id=\"alertType\">\r\n");
      out.write("\t          <label for=\"alertType1\" class=\"col-sm-2 control-label\">提醒方式</label>\r\n");
      out.write("\t          <div class=\"col-sm-4\">\r\n");
      out.write("\t              <input type=\"checkbox\" value=\"1\" id=\"alertType1\" name=\"alertType\">呼吸灯</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\r\n");
      out.write("\t          \t  <input type=\"checkbox\" value=\"2\" id=\"alertType1\" name=\"alertType\">声音</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\r\n");
      out.write("\t          \t  <input type=\"checkbox\" value=\"3\" id=\"alertType1\" name=\"alertType\">振动</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\r\n");
      out.write("\t          \t  <input type=\"checkbox\" value=\"4\" id=\"alertType1\" name=\"alertType\">亮屏</input>\r\n");
      out.write("\t          </div>\r\n");
      out.write("\t          <div class=\"col-sm-2 error-msg\"></div>\r\n");
      out.write("\t      </div>\r\n");
      out.write("\t      <hr>\r\n");
      out.write("\t      <label>推送设定</label>\r\n");
      out.write("\t      <div class=\"form-group\">\r\n");
      out.write("\t            <label for=\"pushObj\" class=\"col-sm-2 control-label\">推送对象</label>\r\n");
      out.write("\t             <div class=\"col-sm-8\">\r\n");
      out.write("\t             \t<div class=\"form-group\">\r\n");
      out.write("\t\t      \t\t\t<input type=\"radio\" id=\"pushObj\" name=\"pushObj\" value=\"1\" checked=\"checked\">所有用户</input>\r\n");
      out.write("\t\t      \t\t</div>\r\n");
      out.write("\t             \t<div class=\"form-group  form-inline\">\r\n");
      out.write("\t\t      \t\t\t<input type=\"radio\" id=\"pushObj\" name=\"pushObj\" value=\"2\" >指定标签</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" class=\"form-control\" id=\"pushObjTag\" name=\"pushObjTag\" placeholder=\"添加标签\"></input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;eg: taga,tagb,tagc\r\n");
      out.write("\t\t      \t\t</div>\r\n");
      out.write("\t\t      \t\t<div class=\"form-group  form-inline\">\r\n");
      out.write("\t\t      \t\t\t<input type=\"radio\" id=\"pushObj\" name=\"pushObj\" value=\"3\" >指定IMEI</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"file\" class=\"file\" name=\"imeiFile\" value=\"imeiFile\" style=\"width:175px;\" accept=\".txt\"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color=\"red\">注意：只支持.txt 记事本文件</font>\r\n");
      out.write("\t\t      \t\t</div>\r\n");
      out.write("\t\t      \t\t<div class=\"form-group form-inline\">\r\n");
      out.write("\t\t      \t\t\t<input type=\"radio\" id=\"pushObj\" name=\"pushObj\" value=\"4\" >指定别名</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" class=\"form-control\" id=\"pushObjAlias\" name=\"pushObjAlias\" placeholder=\"添加别名\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;eg: alia,alib,alic\r\n");
      out.write("\t\t      \t\t</div>\r\n");
      out.write("\t          \t</div>\r\n");
      out.write("\t            <div class=\"col-sm-2 error-msg\"></div>\r\n");
      out.write("\t      </div>\r\n");
      out.write("\t      <hr>\r\n");
      out.write("\t      <div id=\"div2\">\r\n");
      out.write("\t\t      <div class=\"form-group\">\r\n");
      out.write("\t\t            <label for=\"oper\" class=\"col-sm-2 control-label\">操作类型</label>\r\n");
      out.write("\t\t             <div class=\"col-sm-8\">\r\n");
      out.write("\t\t             \t<div class=\"form-group\">\r\n");
      out.write("\t\t\t      \t\t\t<input type=\"radio\" id=\"oper\" name=\"oper\" value=\"1\" checked=\"checked\">点击操作</input><br>\r\n");
      out.write("\t\t\t      \t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t      \t\t<input type=\"radio\" id=\"oper\" name=\"oper\" value=\"2\">按钮操作</input><br>\r\n");
      out.write("\t\t\t      \t\t</div>\t\t      \t\t\r\n");
      out.write("\t\t          \t</div>\r\n");
      out.write("\t\t            <div class=\"col-sm-2 error-msg\"></div>\r\n");
      out.write("\t\t      </div>\t  \r\n");
      out.write("\t<!-- \t      <div class=\"form-group\">\r\n");
      out.write("\t\t            <label for=\"buttonName\" class=\"col-sm-2 control-label\">按钮名称</label>\r\n");
      out.write("\t\t             <div class=\"col-sm-8\">\r\n");
      out.write("\t\t\t      \t\t<input type=\"text\" id=\"buttonName\" name=\"buttonName\"></input>\r\n");
      out.write("\t\t          \t</div>\r\n");
      out.write("\t\t            <div class=\"col-sm-2 error-msg\"></div>\r\n");
      out.write("\t\t      </div> -->\r\n");
      out.write("\t\t      <div class=\"form-group\">\r\n");
      out.write("\t\t            <label for=\"operResult\" class=\"col-sm-2 control-label\">操作效果</label>\r\n");
      out.write("\t\t             <div class=\"col-sm-8\">\r\n");
      out.write("\t\t             \t<div class=\"form-group\">\r\n");
      out.write("\t\t\t      \t\t\t<input type=\"radio\" id=\"operResult\" name=\"operResult\" value=\"1\" checked=\"checked\">打开应用</input><br>\r\n");
      out.write("\t\t\t      \t\t</div>\r\n");
      out.write("\t\t\t      \t\t<div class=\"form-group form-inline\">\r\n");
      out.write("\t\t\t      \t\t\t<input type=\"radio\" id=\"operResult\" name=\"operResult\" value=\"2\" >打开网页</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" id=\"operResultUrl\" class=\"form-control\" name=\"operResultUrl\" placeholder=\"输入地址\"></input><br>\r\n");
      out.write("\t\t\t\t\t\t</div>\t\r\n");
      out.write("\t\t\t      \t\t<div class=\"form-group form-inline\">\r\n");
      out.write("\t\t\t      \t\t\t<input type=\"radio\" id=\"operResult\" name=\"operResult\" value=\"3\" >下载应用</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" id=\"operResultDownloadUrl\" class=\"form-control\" name=\"operResultDownloadUrl\" placeholder=\"输入地址\"></input><br>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t      \t\t<div class=\"form-group form-inline\">\r\n");
      out.write("\t\t\t      \t\t\t<input type=\"radio\" id=\"operResult\" name=\"operResult\" value=\"4\" >指定页面</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" id=\"operResultIntentUrl\" class=\"form-control\" name=\"operResultIntentUrl\" placeholder=\"输入地址\"></input><br>\r\n");
      out.write("\t\t\t\t\t\t</div>\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t          \t</div>\r\n");
      out.write("\t\t            <div class=\"col-sm-2 error-msg\"></div>\r\n");
      out.write("\t\t      </div>\r\n");
      out.write("\t      </div>\r\n");
      out.write("\t      \r\n");
      out.write("\t      <hr>\r\n");
      out.write("\t      <label>时间与网络设定</label>    \r\n");
      out.write("\t      <div class=\"form-group\">\r\n");
      out.write("\t            <label for=\"pushTimeType\" class=\"col-sm-2 control-label\">推送时间</label>\r\n");
      out.write("\t             <div class=\"col-sm-8\">\r\n");
      out.write("\t             \t<div class=\"form-group\">\r\n");
      out.write("\t\t\t      \t\t<input type=\"radio\" id=\"pushTimeType\" name=\"pushTimeType\" value=\"1\" checked=\"checked\">即时推送</input><br>\r\n");
      out.write("\t\t      \t\t</div>\r\n");
      out.write("\t\t      \t\t<div class=\"form-group form-inline\">\r\n");
      out.write("\t\t\t      \t\t<input type=\"radio\" id=\"pushTimeType\" name=\"pushTimeType\" value=\"2\" >定时推送</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input placeholder=\"添加推送时间\" type=\"text\" id=\"pushTime\" name=\"pushTime\" class=\"Wdate\" onclick=\"WdatePicker({dateFmt : 'yyyy-MM-dd HH:mm:ss'})\"></input><br>\r\n");
      out.write("\t\t\t\t\t</div>\t             \r\n");
      out.write("\t          \t</div>\r\n");
      out.write("\t            <div class=\"col-sm-2 error-msg\"></div>\r\n");
      out.write("\t      </div>\r\n");
      out.write("\t      <div class=\"form-group\">\r\n");
      out.write("\t            <label for=\"wlan\" class=\"col-sm-2 control-label\">WLAN设置</label>\r\n");
      out.write("\t            <div class=\"col-sm-8\">\r\n");
      out.write("\t              <input type=\"checkbox\" value=\"1\" id=\"wlan\" name=\"wlan\">当设备处于WLAN状态下推送</input>\r\n");
      out.write("\t          \t</div>\r\n");
      out.write("\t            <div class=\"col-sm-2 error-msg\"></div>\r\n");
      out.write("\t      </div>\r\n");
      out.write("<!-- \t      <div class=\"form-group\">\r\n");
      out.write("\t            <label for=\"receiveTimeType\" class=\"col-sm-2 control-label\">用户接收时间</label>\r\n");
      out.write("\t             <div class=\"col-sm-8\">\r\n");
      out.write("\t\t      \t\t<input type=\"radio\" id=\"receiveTimeType\" name=\"receiveTimeType\" value=\"1\" checked=\"checked\">即时接收</input><br>\r\n");
      out.write("\t\t      \t\t<input type=\"radio\" id=\"receiveTimeType\" name=\"receiveTimeType\" value=\"2\" >定时推送</input><input type=\"text\" id=\"receiveTime\" name=\"receiveTime\" class=\"Wdate\" onclick=\"WdatePicker({dateFmt : 'yyyy-MM-dd HH:mm:ss'})\"></input><br>\r\n");
      out.write("\t          \t</div>\r\n");
      out.write("\t            <div class=\"col-sm-2 error-msg\"></div>\r\n");
      out.write("\t      </div> -->\r\n");
      out.write("\t      <div class=\"form-group\">\r\n");
      out.write("\t            <label for=\"msgOverTimeType\" class=\"col-sm-2 control-label\">消息过期时间</label>\r\n");
      out.write("\t             <div class=\"col-sm-8\">\r\n");
      out.write("\t             \t<div class=\"form-group\">\r\n");
      out.write("\t\t\t      \t\t<input type=\"radio\" id=\"msgOverTimeType\" name=\"msgOverTimeType\" value=\"1\" checked=\"checked\">自推送后</input>\r\n");
      out.write("\t\t\t      \t\t<select id=\"msgTimeSelected\" name=\"msgTimeSelected\">\r\n");
      out.write("\t\t\t      \t\t     <option value=\"1\" selected>1天</option>\r\n");
      out.write("\t\t\t      \t\t     <option value=\"3\">3天</option>\r\n");
      out.write("\t\t\t      \t\t     <option value=\"5\">5天</option>\r\n");
      out.write("\t\t\t      \t\t     <option value=\"7\">7天</option>\r\n");
      out.write("\t\t\t      \t\t</select>\r\n");
      out.write("\t\t      \t\t</div>\r\n");
      out.write("\t\t      \t\t<div class=\"form-group form-inline\">\r\n");
      out.write("\t\t\t      \t\t<input type=\"radio\" id=\"msgOverTimeType\" name=\"msgOverTimeType\" value=\"2\" >指定时间</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input placeholder=\"添加过期时间\" type=\"text\" id=\"msgOverTime\" name=\"msgOverTime\" class=\"Wdate\" onclick=\"WdatePicker({dateFmt : 'yyyy-MM-dd HH:mm:ss'})\"></input><br>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t          \t</div>\r\n");
      out.write("\t            <div class=\"col-sm-2 error-msg\"></div>\r\n");
      out.write("\t      </div>\r\n");
      out.write("\t\t  <div class=\"form-group\">\r\n");
      out.write("\t            <label for=\"privatePush\" class=\"col-sm-2 control-label\">测试效果</label>\r\n");
      out.write("\t             <div class=\"col-sm-8\">\r\n");
      out.write("\t\t      \t\t<input type=\"text\" id=\"privatePush\" name=\"privatePush\" placeholder=\"手动输入设备IMEI号\"></input>&nbsp;&nbsp;&nbsp;<button id=\"test-btn\" type=\"button\" class=\"btn btn-primary\">测试推送</button><br>\r\n");
      out.write("\t          \t</div>\r\n");
      out.write("\t            <div class=\"col-sm-2 error-msg\"></div>\r\n");
      out.write("\t      </div>\r\n");
      out.write("\t      \r\n");
      out.write("\t       <div class=\"form-group\">\r\n");
      out.write("\t           <div class=\"col-sm-offset-2 col-sm-8\">\r\n");
      out.write("\t               <button id=\"save-btn\" type=\"button\" class=\"btn btn-primary\" onclick=\"return check();\">提交</button>&nbsp;&nbsp;&nbsp;\r\n");
      out.write("\t               <button id=\"back-btn\" type=\"button\" class=\"btn btn-default\">返回</button>\r\n");
      out.write("\t           </div>\r\n");
      out.write("\t       </div>\r\n");
      out.write("\t   </form>\r\n");
      out.write("</div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("      \r\n");
      out.write("      function clear() {\r\n");
      out.write("    \t  document.getElementById(\"title\").value= \"\";\r\n");
      out.write("    \t  document.getElementById(\"content\").value = \"\";\r\n");
      out.write("    \t  document.getElementById(\"description\").value = \"\";\r\n");
      out.write("    \t  $(\"#notifyId\").val(\"0\");\r\n");
      out.write("    \t  $(\"[name = alertType]:checkbox\").attr(\"checked\",false);\r\n");
      out.write("      }\r\n");
      out.write("      \r\n");
      out.write("      function showStyle() {\r\n");
      out.write("    \t  clear();\r\n");
      out.write("    \t  document.getElementById(\"div1\").style.display=\"\";\r\n");
      out.write("    \t  document.getElementById(\"div2\").style.display=\"\";\r\n");
      out.write("    \t  document.getElementById(\"alertType\").style.display=\"\";\r\n");
      out.write("    \t  document.getElementById(\"notifyIdModule\").style.display=\"\";\r\n");
      out.write("    \t  $(\"#content\").attr(\"placeholder\", \"不可超过1000字符\");\r\n");
      out.write("      }\r\n");
      out.write("      \r\n");
      out.write("      function hideStyle() {\r\n");
      out.write("    \t  clear();\r\n");
      out.write("    \t  document.getElementById(\"div1\").style.display=\"none\";\r\n");
      out.write("    \t  document.getElementById(\"div2\").style.display=\"none\"; \r\n");
      out.write("    \t  document.getElementById(\"alertType\").style.display=\"none\";\r\n");
      out.write("    \t  document.getElementById(\"notifyIdModule\").style.display=\"none\";\r\n");
      out.write("    \t  $(\"#content\").attr(\"placeholder\", \"请输入1到1000个字\");\r\n");
      out.write("      }\r\n");
      out.write("\r\n");
      out.write("\n");
      out.write("\t  function check(){\r\n");
      out.write("\t\t    var chkObjs = document.getElementsByName(\"pushStyle\"); \r\n");
      out.write("\t\t    var pushStyleVal = null;\r\n");
      out.write("\t\t    for(var i=0;i<chkObjs.length;i++){\r\n");
      out.write("                if(chkObjs[i].checked){\r\n");
      out.write("                \tpushStyleVal = chkObjs[i].value;\r\n");
      out.write("                    break;\r\n");
      out.write("                }\r\n");
      out.write("            }\r\n");
      out.write("\t\t    if(pushStyleVal == 1) {\r\n");
      out.write("\t\t\t\tif($(\"#title\").val().trim()==\"\"){\r\n");
      out.write("\t\t\t\t\tlayer.msg(\"标题不能为空\");\r\n");
      out.write("\t\t\t\t\treturn false;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\tif($(\"#description\").val().trim()==\"\"){\r\n");
      out.write("\t\t\t\t\tlayer.msg(\"描述不能为空\");\r\n");
      out.write("\t\t\t\t\treturn false;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\tif($(\"#title\").val().length > 15){\r\n");
      out.write("\t\t\t\t\tlayer.msg(\"标题长度不可超过15字符\");\r\n");
      out.write("\t\t\t\t\treturn false;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\tif($(\"#description\").val().length > 40){\r\n");
      out.write("\t\t\t\t\tlayer.msg(\"描述长度不可超过40字符\");\r\n");
      out.write("\t\t\t\t\treturn false;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t    }\r\n");
      out.write("\t\t    \r\n");
      out.write("\t\t\tif(getRadioValue(\"pushObj\")==2){\r\n");
      out.write("\t\t\t\tif($(\"#pushObjTag\").val().trim()==\"\"){\r\n");
      out.write("\t\t\t\t\tlayer.msg(\"指定标签内容不能为空\");\r\n");
      out.write("\t\t\t\t\treturn false;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\tif(getRadioValue(\"pushObj\")==4){\r\n");
      out.write("\t\t\t\tif($(\"#pushObjAlias\").val().trim()==\"\"){\r\n");
      out.write("\t\t\t\t\tlayer.msg(\"指定别名内容不能为空\");\r\n");
      out.write("\t\t\t\t\treturn false;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\tif(getRadioValue(\"pushTimeType\")==2){\r\n");
      out.write("\t\t\t\tif($(\"#pushTime\").val().trim()==\"\"){\r\n");
      out.write("\t\t\t\t\tlayer.msg(\"定时推送时间不能为空\");\r\n");
      out.write("\t\t\t\t\treturn false;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\tif(getRadioValue(\"msgOverTimeType\")==2){\r\n");
      out.write("\t\t\t\tif($(\"#msgOverTime\").val().trim()==\"\"){\r\n");
      out.write("\t\t\t\t\tlayer.msg(\"消息过期时间不能为空\");\r\n");
      out.write("\t\t\t\t\treturn false;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t    function getRadioValue(name) {\r\n");
      out.write("\t    \tvar value = null;\r\n");
      out.write("\t    \tvar chkObjs = document.getElementsByName(name);\r\n");
      out.write("\t        for(var i=0;i<chkObjs.length;i++){\r\n");
      out.write("\t            if(chkObjs[i].checked){\r\n");
      out.write("\t                value = chkObjs[i].value;\r\n");
      out.write("\t                break;\r\n");
      out.write("\t            }\r\n");
      out.write("\t        }\r\n");
      out.write("\t        return value;\r\n");
      out.write("\t    }\r\n");
      out.write("\t  \r\n");
      out.write("</script>\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
