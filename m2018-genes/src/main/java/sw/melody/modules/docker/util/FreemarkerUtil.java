package sw.melody.modules.docker.util;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ping
 * @create 2018-09-10 18:06
 **/

public class FreemarkerUtil {

    /****
     *
     * @param dataMap : 数据集合
     * @param request ：请求request
     * @param response ：返回response
     * @param outputFilename ： 输出文件名
     * @throws IOException
     * @throws TemplateException
     */
    public static void process(Map<String, Object> dataMap, HttpServletRequest request, HttpServletResponse response, String outputFilename) throws IOException, TemplateException {
        String filepath = FreemarkerUtil.class.getClassLoader().getResource("ftl").getPath();
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(filepath));
        cfg.setDefaultEncoding("utf-8");
        Template template = cfg.getTemplate("report.ftl");

        OutputStreamWriter osw = new OutputStreamWriter(response.getOutputStream());
        String formFileName = outputFilename;
        String userAgent = request.getHeader("User-Agent");
        // 针对IE或者以IE为内核的浏览器：
        if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
            formFileName = java.net.URLEncoder.encode(formFileName, "UTF-8");
        } else {
            // 非IE浏览器的处理：
            formFileName = new String(formFileName.getBytes("UTF-8"), "ISO-8859-1");
        }
        response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", formFileName));
        template.process(dataMap, osw);
        osw.close();
    }

    public static void main(String[] args) throws IOException, TemplateException {
        String filepath = "E:\\workspace\\m2018\\m2018\\m2018-genes\\src\\main\\resources\\ftl";
        Map<String, Object> map = new HashMap<>();
        map.put("sickName", "test1");
        map.put("birthYear", "2018");
        map.put("receiveYear", "2018");
        map.put("birthMonth", "10");
        map.put("receiveMonth", "10");
        map.put("birthDay", "1");
        map.put("receiveDay", "1");
        map.put("nation", "汉族");
        map.put("sex", "女");
        map.put("hospital", "郑大一附院");
        map.put("department", "神经内科");
        map.put("doctor", "徐洪亮");
        map.put("sampleCode", "HY20181001-01");
        map.put("tell", "头痛发烧流鼻涕");
        map.put("sickHistory", "长期腿麻");
        map.put("familyHistory", "家族病史暂缺");
        map.put("assistCheck", "无");
        map.put("diagnosis", "无");
        map.put("focusGenes", "无");
        map.put("sampleName", "test1");

        List<Map<String, Object>> list1 = new ArrayList<>();
        Map<String, Object> gene = new HashMap<>();
        gene.put("geneRefgene", "gene1");
        gene.put("xrefRefgene", "xrefRefgene1111");
        gene.put("mutationInfo", "mutationInfo11111");
        gene.put("aachangeRefgene", "aachangeRefgene11111");
        gene.put("sampleNameAttr", "sampleNameAttr11111");
        list1.add(gene);

        gene = new HashMap<>();
        gene.put("geneRefgene", "gene2222");
        gene.put("xrefRefgene", "xrefRefgene2222");
        gene.put("mutationInfo", "mutationInfo222222");
        gene.put("aachangeRefgene", "aachangeRefgene2222");
        gene.put("sampleNameAttr", "sampleNameAttr22222");
        list1.add(gene);

        gene = new HashMap<>();
        gene.put("geneRefgene", "gene3333");
        gene.put("xrefRefgene", "xrefRefgene33333");
        gene.put("mutationInfo", "mutationInfo333333");
        gene.put("aachangeRefgene", "aachangeRefgene3333");
        gene.put("sampleNameAttr", "sampleNameAttr33333");
        list1.add(gene);

        List<Map<String, Object>> list2 = new ArrayList<>();
        Map<String, Object> family = new HashMap<>();
        family.put("geneRefgene", "gene111111");
        family.put("mutationInfo", "mutationInfo1111");
        family.put("mutationType", "hom");
        list2.add(family);

        family = new HashMap<>();
        family.put("geneRefgene", "gene222222");
        family.put("mutationInfo", "mutationInfo222222");
        family.put("mutationType", "hom");
        list2.add(family);

        family = new HashMap<>();
        family.put("geneRefgene", "gene3333333");
        family.put("mutationInfo", "mutationInfo333333");
        family.put("mutationType", "hex");
        list2.add(family);

        map.put("genesList", list1);
        map.put("familyList", list2);
    }
}
