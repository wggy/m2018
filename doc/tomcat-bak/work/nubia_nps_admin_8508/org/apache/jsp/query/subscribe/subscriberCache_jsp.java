/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.20
 * Generated at: 2016-05-06 03:26:55 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.query.subscribe;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class subscriberCache_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(3);
    _jspx_dependants.put("/WEB-INF/lib/jstl-1.2.jar", Long.valueOf(1460446166000L));
    _jspx_dependants.put("jar:file:/data/wwwroot/nubia_nps_admin/WEB-INF/lib/jstl-1.2.jar!/META-INF/c.tld", Long.valueOf(1153356282000L));
    _jspx_dependants.put("/query/subscribe/../../include/head.jsp", Long.valueOf(1460338154000L));
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
      out.write("<title>订阅缓存信息</title>\r\n");
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
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<input type=\"hidden\" id=\"subscriberId\" name=\"subscriberId\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${subscriberId }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\">\r\n");
      out.write("<div class=\"container-fluid\">\r\n");
      out.write("        <div>\r\n");
      out.write("            <table id=\"table\" data-toolbar=\"#toolbar\" data-pagination=\"true\"\r\n");
      out.write("                data-id-field=\"realname\" data-show-footer=\"false\">\r\n");
      out.write("            </table>\r\n");
      out.write("        </div>\r\n");
      out.write("</div>\r\n");
      out.write("</body>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctx }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/query/subscribe/subscriberCache.js\"></script>\r\n");
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
