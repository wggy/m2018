/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.20
 * Generated at: 2016-01-07 06:27:17 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.phone;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class exportActivatePhone_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(3);
    _jspx_dependants.put("/phone/../include/taglib.jsp", Long.valueOf(1452129964000L));
    _jspx_dependants.put("jar:file:/data/wwwroot/nubia_report/WEB-INF/lib/jstl-1.2.jar!/META-INF/c.tld", Long.valueOf(1153356282000L));
    _jspx_dependants.put("/WEB-INF/lib/jstl-1.2.jar", Long.valueOf(1413246458000L));
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

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;

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
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
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

      if (_jspx_meth_c_005fset_005f0(_jspx_page_context))
        return;
      if (_jspx_meth_c_005fset_005f1(_jspx_page_context))
        return;
      out.write('\n');
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("<meta charset=\"UTF-8\">\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
      out.write("<title></title>\n");
      out.write("<link rel=\"stylesheet\" href=\"../res/css/main.css?v=3\">\n");
      out.write("<link rel=\"stylesheet\" href=\"../res/css/multiple-select.css\">\n");
      out.write("<link rel=\"stylesheet\" href=\"../res/css/calendar.css\">\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("\t<div id=\"outer\">\n");
      out.write("\t\t<!-- <h1>操作资源管理 </h1> -->\n");
      out.write("\t\t\n");
      out.write("\t\t<form id=\"query_form\">\n");
      out.write("\t\t\t<div>\n");
      out.write("\t\t\t\t数据行：\n");
      out.write("\t\t\t\t<input type=\"text\" id=\"startRow\" name=\"startRow\">\n");
      out.write("\t\t\t\t~\n");
      out.write("\t\t\t\t<input type=\"text\" id=\"endRow\" name=\"endRow\">\n");
      out.write("\t\t\t\t主IMEI列：\n");
      out.write("\t\t\t\t<input type=\"text\" id=\"imeiColumn\" name=\"imeiColumn\">\n");
      out.write("\t\t\t\t<input type=\"file\" id=\"imeiFile\" onchange=\"changeFile()\" />\n");
      out.write("\t\t\t\t<input type=\"button\" value=\"导出激活数据\" onclick=\"exportPhone()\" class=\"btn_smaller\" />\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t</form>\n");
      out.write("\t</div>\n");
      out.write("<script src=\"../res/js/jquery.js\"></script>\n");
      out.write("<script src=\"../res/js/jquery.multiple.select.js\"></script>\n");
      out.write("<script src=\"../res/js/calendar.js\"></script>\n");
      out.write("<script>\n");
      out.write("$(function() {\n");
      out.write("});\n");
      out.write("\n");
      out.write("function exportPhone() {\n");
      out.write("\tvar startRow = $('#startRow').val();\n");
      out.write("\tvar endRow = $('#endRow').val();\n");
      out.write("\tif (!startRow || ! endRow) {\n");
      out.write("\t\talert('请选择起始/结束行号');\n");
      out.write("\t\treturn;\n");
      out.write("\t}\n");
      out.write("\tvar imeiColumn = $('#imeiColumn').val();\n");
      out.write("\tif (!imeiColumn) {\n");
      out.write("\t\talert('请选择主imei所在列');\n");
      out.write("\t\treturn;\n");
      out.write("\t}\n");
      out.write("\tif (!changeFlag && uploadImeiFile != '') {\n");
      out.write("\t\texportData()\n");
      out.write("\t} else {\n");
      out.write("\t\tuploadFile();\n");
      out.write("\t}\n");
      out.write("}\n");
      out.write("\n");
      out.write("var changeFlag = false;\n");
      out.write("var uploadImeiFile = '';\n");
      out.write("function changeFile() {\n");
      out.write("\tchangeFlag = true;\n");
      out.write("}\n");
      out.write("\n");
      out.write("function uploadFile() {\n");
      out.write("\tvar fd = new FormData();\n");
      out.write("\tvar files = document.getElementById(\"imeiFile\").files;\n");
      out.write("\tif (files.length == 0) {\n");
      out.write("\t\talert('请选择文件');\n");
      out.write("\t\treturn;\n");
      out.write("\t}\n");
      out.write("\t// fd.append(\"startRow\", $('#startRow').val());\n");
      out.write("\t// fd.append(\"endRow\", $('#endRow').val());\n");
      out.write("\t// fd.append(\"imeiColumn\", $('#imeiColumn').val());\n");
      out.write("\tfd.append(\"file\", files[0]);\n");
      out.write("\tvar xhr = new XMLHttpRequest();\n");
      out.write("\txhr.onload = function(e) {\n");
      out.write("\t\tuploadImeiFile = e.target.responseText;\n");
      out.write("\t\tif (uploadImeiFile && uploadImeiFile != '') {\n");
      out.write("\t\t\texportData();\n");
      out.write("\t\t} else {\n");
      out.write("\t\t\talert('上传文件失败');\n");
      out.write("\t\t\treturn;\n");
      out.write("\t\t}\n");
      out.write("\t}\n");
      out.write("\txhr.open(\"POST\", \"/phone/uploadImeiActivate.action\");\n");
      out.write("\txhr.send(fd);\n");
      out.write("\t// exportData(\"1\");\n");
      out.write("}\n");
      out.write("\n");
      out.write("function exportData() {\n");
      out.write("\tvar url = '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctx}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/phone/exportPhoneActivate.zte?uploadFile=' + uploadImeiFile\n");
      out.write("\t\t\t+ '&startRow=' + $('#startRow').val()\n");
      out.write("\t\t\t+ '&endRow=' + $('#endRow').val()\n");
      out.write("\t\t\t+ '&imeiColumn=' + $('#imeiColumn').val();\n");
      out.write("\ttop.location.href=url;\n");
      out.write("}\n");
      out.write("</script>\n");
      out.write("</body>\n");
      out.write("</html>\n");
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

  private boolean _jspx_meth_c_005fset_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_005fset_005f0 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fset_005f0.setParent(null);
    // /phone/../include/taglib.jsp(1,65) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fset_005f0.setVar("ctx");
    // /phone/../include/taglib.jsp(1,65) name = value type = javax.el.ValueExpression reqTime = true required = false fragment = false deferredValue = true expectedTypeName = java.lang.Object deferredMethod = false methodSignature = null
    _jspx_th_c_005fset_005f0.setValue(new org.apache.jasper.el.JspValueExpression("/phone/../include/taglib.jsp(1,65) ''",_el_expressionfactory.createValueExpression("",java.lang.Object.class)).getValue(_jspx_page_context.getELContext()));
    int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
    if (_jspx_th_c_005fset_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fset_005f1(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_005fset_005f1 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fset_005f1.setParent(null);
    // /phone/../include/taglib.jsp(1,92) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fset_005f1.setVar("ctxRes");
    // /phone/../include/taglib.jsp(1,92) name = value type = javax.el.ValueExpression reqTime = true required = false fragment = false deferredValue = true expectedTypeName = java.lang.Object deferredMethod = false methodSignature = null
    _jspx_th_c_005fset_005f1.setValue(new org.apache.jasper.el.JspValueExpression("/phone/../include/taglib.jsp(1,92) '${ctx}/res'",_el_expressionfactory.createValueExpression(_jspx_page_context.getELContext(),"${ctx}/res",java.lang.Object.class)).getValue(_jspx_page_context.getELContext()));
    int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
    if (_jspx_th_c_005fset_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
    return false;
  }
}