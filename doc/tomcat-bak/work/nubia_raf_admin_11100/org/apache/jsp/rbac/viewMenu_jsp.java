/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.20
 * Generated at: 2016-01-18 07:51:30 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.rbac;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class viewMenu_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

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

      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("    <meta charset=\"UTF-8\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n");
      out.write("    <title></title>\r\n");
      out.write("    <link rel=\"stylesheet\" href=\"../res/css/pub.css?v=3\">\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<form>\r\n");
      out.write("\t\t<input type=\"hidden\" name=\"menuId\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${menu.menuId}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" />\r\n");
      out.write("\t\t<dl>\r\n");
      out.write("\t\t\t<b>菜单名称 <span>*</span></b>\r\n");
      out.write("\t\t\t<span><input name=\"title\" style=\"width:280px;\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${menu.title}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" maxlength=\"32\"/></span>\r\n");
      out.write("\t\t</dl>\r\n");
      out.write("\t\t<dl id=\"level\">\r\n");
      out.write("\t\t\t<b>菜单级别 <span>*</span></b>\r\n");
      out.write("\t\t\t<span>\r\n");
      out.write("\t\t\t\t<input type=\"radio\" class=\"radio\" name=\"level\" value=\"1\" id=\"r1\" onclick=\"chg(1)\"> <label for=\"r1\">一级菜单</label>\r\n");
      out.write("\t\t\t\t<input type=\"radio\" class=\"radio\" name=\"level\" value=\"2\" id=\"r2\" onclick=\"chg(2)\"> <label for=\"r2\">二级菜单</label>\r\n");
      out.write("\t\t\t</span>\r\n");
      out.write("\t\t</dl>\r\n");
      out.write("\t\t<dl>\r\n");
      out.write("\t\t\t<b>主 链 接 <span>*</span></b>\r\n");
      out.write("\t\t\t<span><input name=\"mainHref\" style=\"width:280px;\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${menu.mainHref}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" maxlength=\"64\"/></span>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<dl id=\"left\">\r\n");
      out.write("\t\t\t<b>左 链 接 <span>*</span></b>\r\n");
      out.write("\t\t\t<span><input name=\"leftHref\" style=\"width:280px;\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${menu.leftHref}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" maxlength=\"32\"/></span>\r\n");
      out.write("\t\t</dl>\r\n");
      out.write("\t</form>\r\n");
      out.write("<script src=\"../res/js/jquery.js?v=3\"></script>\r\n");
      out.write("<script>\r\n");
      out.write("$(function(){\r\n");
      out.write("\tchg(");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${menu.leftHref==''?'2':'1'}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write(");\r\n");
      out.write("\t\r\n");
      out.write("});\r\n");
      out.write("function chg(i){\r\n");
      out.write("\tif(i==1){\t\r\n");
      out.write("\t\t$('#left').show();\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\t$('#left').hide();\r\n");
      out.write("\t}\r\n");
      out.write("\t$('input[name=level]:eq('+(i-1)+')').attr('checked',true);\r\n");
      out.write("}\r\n");
      out.write("function save(){\r\n");
      out.write("\tvar frm=$('form:eq(0)'),nv=frm[0].title.value,mv=frm[0].mainHref.value;\r\n");
      out.write("\tif($.trim(nv)==''){\r\n");
      out.write("\t\ttop.tip(-1,'菜单名称不能为空');\r\n");
      out.write("\t}else if($.trim(mv)==''){\r\n");
      out.write("\t\ttop.tip(-1,'主链接不能为空');\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\t$.post('saveMenu.action',frm.serialize(),function(r){\r\n");
      out.write("\t\t\ttop.tip(1,'保存成功！',function(){\r\n");
      out.write("\t\t\t\ttop.$.fn.popup({close:true});\r\n");
      out.write("\t\t\t\ttop.frames['mainFrame'].location.reload();\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t});\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write("</body>\r\n");
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
