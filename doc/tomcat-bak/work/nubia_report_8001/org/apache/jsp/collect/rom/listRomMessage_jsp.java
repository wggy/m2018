/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.20
 * Generated at: 2016-01-07 11:02:54 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.collect.rom;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class listRomMessage_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(3);
    _jspx_dependants.put("/collect/rom/../../include/taglib.jsp", Long.valueOf(1452129964000L));
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
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;

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
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
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
      out.write("<link rel=\"stylesheet\" href=\"../res/css/calendar.css\">\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("\t<div id=\"outer\">\n");
      out.write("\t\t<!-- <h1>操作资源管理 </h1> -->\n");
      out.write("\t\t\n");
      out.write("\t\t<form id=\"query_form\">\n");
      out.write("\t\t\t<div>\n");
      out.write("\t\t\t\t时段范围：\n");
      out.write("\t\t\t\t<input id=\"startDate\" type=\"text\"> ~ <input id=\"endDate\" type=\"text\">\n");
      out.write("\t\t\t\t<input type=\"button\" value=\"查询\" onclick=\"query()\" class=\"btn_smaller\" />\n");
      out.write("\t\t\t\t<input type=\"button\" value=\"导出\" onclick=\"exportData()\" class=\"btn_smaller\" />\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t</form>\n");
      out.write("\t\t<br>\n");
      out.write("\t\t<table class=\"list\">\n");
      out.write("\t\t\t<tr>\n");
      out.write("\t\t\t\t<th>ID</th>\n");
      out.write("\t\t\t\t<th>上报时间</th>\n");
      out.write("\t\t\t\t<th>消息</th>\n");
      out.write("\t\t\t</tr>\n");
      out.write("\t\t\t<tbody>\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t\t\t</tbody>\n");
      out.write("\t\t</table>\n");
      out.write("\t\t<div>");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageBean.pageNum}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("</div>\n");
      out.write("\t</div>\n");
      out.write("<script src=\"../res/js/jquery.js\"></script>\n");
      out.write("<script src=\"../res/js/calendar.js\"></script>\n");
      out.write("<script>\n");
      out.write("$(function() {\n");
      out.write("\t$('#startDate,#endDate').on('click', function() {\n");
      out.write("\t\tCalendar.show(this, '%Y-%m-%d', true);\n");
      out.write("\t});\n");
      out.write("\t$('#startDate').val('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${startDate }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("');\n");
      out.write("\t$('#endDate').val('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${endDate }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("');\n");
      out.write("});\n");
      out.write("\n");
      out.write("function query() {\n");
      out.write("\tvar startDate = $('#startDate').val();\n");
      out.write("\tvar endDate = $('#endDate').val();\n");
      out.write("\t\n");
      out.write("\tif (startDate != null && startDate != '') {\n");
      out.write("\t\tif (!checkDate(startDate)) {\n");
      out.write("\t\t\talert('日起格式不正确！');\n");
      out.write("\t\t\treturn;\n");
      out.write("\t\t}\n");
      out.write("\t}\n");
      out.write("\tif (endDate != null && endDate != '') {\n");
      out.write("\t\tif (!checkDate(endDate)) {\n");
      out.write("\t\t\talert('日起格式不正确！');\n");
      out.write("\t\t\treturn;\n");
      out.write("\t\t}\n");
      out.write("\t}\n");
      out.write("\tif (!checkEndDate(startDate, endDate)) {\n");
      out.write("\t\talert('开始范围不正确！');\n");
      out.write("\t\treturn;\n");
      out.write("\t}\n");
      out.write("\t\n");
      out.write("\tvar url = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctx}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/rom/listRomMessage.zte?startDate=\" + startDate\n");
      out.write("\t+ \"&endDate=\" + endDate;\n");
      out.write("\t\n");
      out.write("\twindow.location = url;\n");
      out.write("}\n");
      out.write("\n");
      out.write("function checkDate(d){\n");
      out.write("\tvar ds = d.match(/\\d+/g),ts=['getFullYear','getMonth','getDate'];\n");
      out.write("\tvar d = new Date(d.replace(/-/g,'/')), i=3;\n");
      out.write("\tds[1]--;\n");
      out.write("\twhile(i--)\n");
      out.write("\t\tif( ds[i]*1!=d[ts[i]]())\n");
      out.write("\t\t\treturn false;\n");
      out.write("\treturn true;\n");
      out.write("}\n");
      out.write("\n");
      out.write("function checkEndDate(d1, d2){\n");
      out.write("\tvar _d1 = new Date(d1.replace(\"-\", \"/\").replace(\"-\", \"/\"));\n");
      out.write("\tvar _d2 = new Date(d2.replace(\"-\", \"/\").replace(\"-\", \"/\"));\n");
      out.write("\tif(_d2 < _d1){\n");
      out.write("\t\treturn false;\n");
      out.write("\t}\n");
      out.write("\treturn true;\n");
      out.write("}\n");
      out.write("\n");
      out.write("function exportData() {\n");
      out.write("\tvar startDate = $('#startDate').val();\n");
      out.write("\tvar endDate = $('#endDate').val();\n");
      out.write("\t\n");
      out.write("\tvar url = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctx}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/rom/exportRomMessage.zte?&startDate=\" + startDate\n");
      out.write("\t+ \"&endDate=\" + endDate;\n");
      out.write("\t\n");
      out.write("\ttop.location.href=url;\n");
      out.write("}\n");
      out.write("</script>\n");
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

  private boolean _jspx_meth_c_005fset_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_005fset_005f0 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fset_005f0.setParent(null);
    // /collect/rom/../../include/taglib.jsp(1,65) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fset_005f0.setVar("ctx");
    // /collect/rom/../../include/taglib.jsp(1,65) name = value type = javax.el.ValueExpression reqTime = true required = false fragment = false deferredValue = true expectedTypeName = java.lang.Object deferredMethod = false methodSignature = null
    _jspx_th_c_005fset_005f0.setValue(new org.apache.jasper.el.JspValueExpression("/collect/rom/../../include/taglib.jsp(1,65) ''",_el_expressionfactory.createValueExpression("",java.lang.Object.class)).getValue(_jspx_page_context.getELContext()));
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
    // /collect/rom/../../include/taglib.jsp(1,92) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fset_005f1.setVar("ctxRes");
    // /collect/rom/../../include/taglib.jsp(1,92) name = value type = javax.el.ValueExpression reqTime = true required = false fragment = false deferredValue = true expectedTypeName = java.lang.Object deferredMethod = false methodSignature = null
    _jspx_th_c_005fset_005f1.setValue(new org.apache.jasper.el.JspValueExpression("/collect/rom/../../include/taglib.jsp(1,92) '${ctx}/res'",_el_expressionfactory.createValueExpression(_jspx_page_context.getELContext(),"${ctx}/res",java.lang.Object.class)).getValue(_jspx_page_context.getELContext()));
    int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
    if (_jspx_th_c_005fset_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
    return false;
  }

  private boolean _jspx_meth_c_005fforEach_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f0 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f0.setParent(null);
    // /collect/rom/listRomMessage.jsp(30,4) name = items type = javax.el.ValueExpression reqTime = true required = false fragment = false deferredValue = true expectedTypeName = java.lang.Object deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f0.setItems(new org.apache.jasper.el.JspValueExpression("/collect/rom/listRomMessage.jsp(30,4) '${list}'",_el_expressionfactory.createValueExpression(_jspx_page_context.getELContext(),"${list}",java.lang.Object.class)).getValue(_jspx_page_context.getELContext()));
    // /collect/rom/listRomMessage.jsp(30,4) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f0.setVar("rom");
    // /collect/rom/listRomMessage.jsp(30,4) name = varStatus type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f0.setVarStatus("status");
    int[] _jspx_push_body_count_c_005fforEach_005f0 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
      if (_jspx_eval_c_005fforEach_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n");
          out.write("\t\t\t\t\t<tr>\n");
          out.write("\t\t\t\t\t\t<td>");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${rom.recId }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
          out.write("</td>\n");
          out.write("\t\t\t\t\t\t<td>");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${rom.addTime }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
          out.write("</td>\n");
          out.write("\t\t\t\t\t\t<td>");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${rom.data }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
          out.write("</td>\n");
          out.write("\t\t\t\t\t</tr>\n");
          out.write("\t\t\t\t");
          int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fforEach_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (java.lang.Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_005fforEach_005f0[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_005fforEach_005f0.doFinally();
      _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
    }
    return false;
  }
}
