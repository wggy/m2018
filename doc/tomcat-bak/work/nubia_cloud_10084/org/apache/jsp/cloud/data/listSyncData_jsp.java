/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.20
 * Generated at: 2015-12-30 02:06:48 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.cloud.data;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class listSyncData_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("jar:file:/data/wwwroot/nubia_cloud/WEB-INF/lib/jstl-1.2.jar!/META-INF/c.tld", Long.valueOf(1153356282000L));
    _jspx_dependants.put("/WEB-INF/lib/jstl-1.2.jar", Long.valueOf(1451441049000L));
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
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("<meta charset=\"utf-8\">\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
      out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n");
      out.write("<title>管理员列表</title>\n");
      out.write("<link rel=\"stylesheet\" href=\"/res/bootstrap/core/bootstrap.min.css\" />\n");
      out.write("<link rel=\"stylesheet\" href=\"/res/bootstrap/core/bootstrap-theme.min.css\" />\n");
      out.write("<link rel=\"stylesheet\" href=\"/res/bootstrap-table/bootstrap-table.min.css\" />\n");
      out.write("<link rel=\"stylesheet\" href=\"/res/common/common.css\" />\n");
      out.write("<style>\n");
      out.write("</style>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("\n");
      out.write("    <div class=\"container-fluid\">\n");
      out.write("        <h2 class=\"page-header\">同步数据</h2>\n");
      out.write("        <div>\n");
      out.write("\t        <form class=\"form-horizontal vertical-gap\">\n");
      out.write("\t            <div class=\"col-sm-3 form-group\">\n");
      out.write("\t                <label class=\"col-sm-4 control-label\" for=\"userId\">数据类型</label>\n");
      out.write("\t                <div class=\"col-sm-8\">\n");
      out.write("\t                    <select class=\"form-control\" id=\"dataType\">\n");
      out.write("\t                        <option value=\"1\">联系人</option>\n");
      out.write("\t                        <option value=\"2\">通话记录</option>\n");
      out.write("\t                    </select>\n");
      out.write("\t                </div>\n");
      out.write("\t            </div>\n");
      out.write("\t            <div class=\"col-sm-3 form-group\">\n");
      out.write("\t                <label class=\"col-sm-4 control-label\" for=\"userId\">用户ID</label>\n");
      out.write("\t                <div class=\"col-sm-8\">\n");
      out.write("\t                    <input type=\"text\" class=\"form-control\" id=\"userId\">\n");
      out.write("\t                </div>\n");
      out.write("\t            </div>\n");
      out.write("\t            <div class=\"col-sm-3 form-group\">\n");
      out.write("\t                <label class=\"col-sm-4 control-label\" for=\"versionCode\">版本号</label>\n");
      out.write("\t                <div class=\"col-sm-8\">\n");
      out.write("\t                    <input type=\"text\" class=\"form-control\" id=\"versionCode\">\n");
      out.write("\t                </div>\n");
      out.write("\t            </div>\n");
      out.write("\t            <div class=\"col-sm-3 form-group\">\n");
      out.write("                    <label class=\"col-sm-4 control-label\" for=\"status\">数据类型</label>\n");
      out.write("                    <div class=\"col-sm-5\">\n");
      out.write("                        <select class=\"form-control\" id=\"status\">\n");
      out.write("                            <option value=\"\">请选择...</option>\n");
      out.write("                            <option value=\"0\">正常</option>\n");
      out.write("                            <option value=\"1\">回收站</option>\n");
      out.write("                            <option value=\"2\">删除</option>\n");
      out.write("                        </select>\n");
      out.write("                    </div>\n");
      out.write("                   <input type=\"button\" class=\"btn btn-primary\" id=\"query-data-btn\" value=\"查询\">\n");
      out.write("\t            </div>\n");
      out.write("\t        </form>\n");
      out.write("            <table id=\"data-list\"></table>\n");
      out.write("        </div>\n");
      out.write("    </div>\n");
      out.write("    \n");
      out.write("    <!-- 详细弹出框 -->\n");
      out.write("    <div class=\"modal fade\" id=\"desc-modal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">\n");
      out.write("        <div class=\"modal-dialog\">\n");
      out.write("            <div class=\"modal-content\">\n");
      out.write("                <div class=\"modal-header\">\n");
      out.write("                    <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>\n");
      out.write("                    <h4 class=\"modal-title\" id=\"myModalLabel\">详细信息</h4>\n");
      out.write("                </div>\n");
      out.write("                <div class=\"modal-body\">\n");
      out.write("                    <table>\n");
      out.write("                        <tbody>\n");
      out.write("                            <tr><td class=\"text-right\">内容：</td><td id=\"desc-content\"></td></tr>\n");
      out.write("                            <tr><td class=\"text-right\">记录状态：</td><td id=\"desc-status\"></td></tr>\n");
      out.write("                            <tr><td class=\"text-right\">版本号：</td><td id=\"desc-version-code\"></td></tr>\n");
      out.write("                            <tr><td class=\"text-right\">新建时间：</td><td id=\"desc-create-time\"></td></tr>\n");
      out.write("                            <tr><td class=\"text-right\">删除时间：</td><td id=\"desc-delete-time\"></td></tr>\n");
      out.write("                        </tbody>\n");
      out.write("                    </table>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("    </div>\n");
      out.write("    \n");
      out.write("    <script src=\"/res/jquery.js\"></script>\n");
      out.write("    <script src=\"/res/bootstrap/core/bootstrap.min.js\"></script>\n");
      out.write("    <script src=\"/res/bootstrap-table/bootstrap-table.min.js\"></script>\n");
      out.write("    <script src=\"/res/layer/layer.js\"></script>\n");
      out.write("    <script src=\"/res/common/common.js\"></script>\n");
      out.write("    <script src=\"/cloud/data/listSyncData.js\"></script>\n");
      out.write("</body>\n");
      out.write("</html>");
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