package sw.melody.common.utils;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.Map.Entry;

public class WorderToNewWordUtils {

    private static String wordTitle = "一、先证者${sampleName} SNP/Indel检测结果";
    private static List<String[]> FixedRowsPre = new ArrayList<>();
    private static final List<String[]> FixedRowsMid = new ArrayList<>();

    static {
        String[] Row_0 = {"第三部分  检测结果", "", "", "", "", "", ""};
        String[] Row_1 = {wordTitle, "", "", "", "", "", ""};
        String[] Row_2 = {"基因", "OMIM编号", "疾病名称/遗传方式", "突变信息", "转录本:外显子编号", "测序深度/突变比率/EXAC_ALL携带率", "ACMG分级"};
        String[] Row_3 = {"二、外显子拷贝数变异检测结果\n该样本未未发现明确的与疾病相关的外显子拷贝数变异", "", "", "", "", "", ""};
        String[] Row_4 = {"三、家系验证结果", "", "", "", "", "", ""};
        String[] Row_5 = {"基因", "突变信息", "先证者", "先证者之父", "先证者之母", "", ""};
        FixedRowsPre.add(Row_0);
        FixedRowsPre.add(Row_1);
        FixedRowsPre.add(Row_2);
        FixedRowsMid.add(Row_3);
        FixedRowsMid.add(Row_4);
        FixedRowsMid.add(Row_5);
    }

    /**
     * 根据模板生成新word文档
     * 判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
     *
     * @param inputUrl  模板存放地址
     * @param textMap   需要替换的信息集合
     * @param tableList 需要插入的表格信息集合
     * @return 成功返回true, 失败返回false
     */
    public static boolean changeWord(String inputUrl, String outputUrl,
                                    Map<String, String> textMap, List<String[]> tableList) {

        //模板转换默认成功
        boolean changeFlag = true;
        try {
            //获取docx解析对象
            XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(inputUrl));
            //解析替换表格对象
            WorderToNewWordUtils.changeTable(document, textMap, tableList);

            //生成新的word
            File file = new File(outputUrl);
            FileOutputStream stream = new FileOutputStream(file);
            document.write(stream);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
            changeFlag = false;
        }

        return changeFlag;
    }

    /**
     * 替换段落文本
     *
     * @param document docx解析对象
     * @param textMap  需要替换的信息集合
     */
    public static void changeText(XWPFDocument document, Map<String, String> textMap) {
        //获取段落集合
        List<XWPFParagraph> paragraphs = document.getParagraphs();

        for (XWPFParagraph paragraph : paragraphs) {
            //判断此段落时候需要进行替换
            String text = paragraph.getText();
            if (checkText(text)) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    //替换模板原来位置
                    run.setText(changeValue(run.toString(), textMap), 0);
                }
            }
        }

    }

    /**
     * 替换表格对象方法
     *
     * @param document  docx解析对象
     * @param textMap   需要替换的信息集合
     * @param tableList 需要插入的表格信息集合
     */
    public static void changeTable(XWPFDocument document, Map<String, String> textMap, List<String[]> tableList) {
        //获取表格对象集合
        List<XWPFTable> tables = document.getTables();
        for (int i = 0; i < tables.size(); i++) {
            //只处理行数大于等于2的表格，且不循环表头
            XWPFTable table = tables.get(i);
            List<XWPFTableRow> rows = table.getRows();
            if (table.getRows().size() > 1) {
                //判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
                if (checkText(table.getText())) {
                    //遍历表格,并替换模板
                    eachTable(rows, textMap);
                } else {
                    insertTable(table, tableList);
                }
            }
        }
    }


    /**
     * 遍历表格
     *
     * @param rows    表格行对象
     * @param textMap 需要替换的信息集合
     */
    public static void eachTable(List<XWPFTableRow> rows, Map<String, String> textMap) {
        if (CollectionUtils.isEmpty(textMap)) {
            return;
        }
        for (XWPFTableRow row : rows) {
            List<XWPFTableCell> cells = row.getTableCells();
            for (XWPFTableCell cell : cells) {
                //判断单元格是否需要替换
                if (checkText(cell.getText())) {
                    List<XWPFParagraph> paragraphs = cell.getParagraphs();
                    for (XWPFParagraph paragraph : paragraphs) {
                        List<XWPFRun> runs = paragraph.getRuns();
                        for (XWPFRun run : runs) {
                            run.setText(changeValue(run.toString(), textMap), 0);
                        }
                    }
                }
            }
        }
    }

    /**
     * 为表格插入数据，行数不够添加新行
     *
     * @param table     需要插入数据的表格
     * @param tableList 插入数据集合
     */
    public static void insertTable(XWPFTable table, List<String[]> tableList) {
        //创建行,根据需要插入的数据添加新行，不处理表头
        for (int i = 1; i < tableList.size(); i++) {
            XWPFTableRow row = table.createRow();
        }
        //遍历表格插入数据
        List<XWPFTableRow> rows = table.getRows();
        for (int i = 1; i < rows.size(); i++) {
            XWPFTableRow newRow = table.getRow(i);
            List<XWPFTableCell> cells = newRow.getTableCells();
            for (int j = 0; j < cells.size(); j++) {
                XWPFTableCell cell = cells.get(j);
                cell.setText(tableList.get(i - 1)[j]);
            }
        }

    }


    /**
     * 判断文本中时候包含$
     *
     * @param text 文本
     * @return 包含返回true, 不包含返回false
     */
    public static boolean checkText(String text) {
        boolean check = false;
        if (text.indexOf("$") != -1) {
            check = true;
        }
        return check;

    }

    /**
     * 匹配传入信息集合与模板
     *
     * @param value   模板需要替换的区域
     * @param textMap 传入信息集合
     * @return 模板需要替换区域信息集合对应值
     */
    public static String changeValue(String value, Map<String, String> textMap) {
        Set<Entry<String, String>> textSets = textMap.entrySet();
        for (Entry<String, String> textSet : textSets) {
            //匹配模板与替换值 格式${key}
            String key = "${" + textSet.getKey() + "}";
            if (value.indexOf(key) != -1) {
                value = value.replace(key, textSet.getValue());
            }
        }
        //模板未匹配到区域替换为空
        if (checkText(value)) {
            value = "";
        }
        return value;
    }

    private static XWPFDocument getDocment(String inputUrl, String text, List<String[]> tableList1, List<String[]> tableList2) throws IOException {
        XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(inputUrl));
        fillTable(document.getTableArray(1), text, tableList1, tableList2);
        return document;
    }

    public static boolean geneReport(String inputUrl, String outputUrl, String text, List<String[]> tableList1, List<String[]> tableList2) {
        boolean changeFlag = true;
        try {
            XWPFDocument document = getDocment(inputUrl, text, tableList1, tableList2);
            File file = new File(outputUrl);
            FileOutputStream stream = new FileOutputStream(file);
            document.write(stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            changeFlag = false;
        }
        return changeFlag;
    }

    public static boolean geneReport(String inputUrl, String fileName, HttpServletResponse response, String text, List<String[]> tableList1, List<String[]> tableList2) {
        boolean changeFlag = true;
        try {
            XWPFDocument document = getDocment(inputUrl, text, tableList1, tableList2);
            OutputStream os = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            document.write(os);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            changeFlag = false;
        }
        return changeFlag;
    }

    private static void fillTable(XWPFTable table, String sample, List<String[]> list1, List<String[]> list2) {
        if (CollectionUtils.isEmpty(list1)) {
            return;
        }
        FixedRowsPre.get(1)[0] = wordTitle.replace("${sampleName}", sample);
        int tableRowNum = list1.size() + 5;
        int list2Num = 0;
        if (!CollectionUtils.isEmpty(list2)) {
            list2Num = list2.size();
        }
        tableRowNum += list2Num;
        for (int i = 0; i < tableRowNum; i++) {
            table.createRow();
        }

        List<String[]> totalList = new ArrayList<>();
        totalList.addAll(FixedRowsPre);
        totalList.addAll(list1);
        totalList.addAll(FixedRowsMid);
        totalList.addAll(list2);
        for (int rowIndex = 0; rowIndex < totalList.size(); rowIndex++) {
            XWPFTableRow row = table.getRow(rowIndex);
            for (int colIndex = 0; colIndex < 7; colIndex++) {
                XWPFTableCell cell = row.getCell(colIndex);
                cell.setText(totalList.get(rowIndex)[colIndex]);
            }
        }

        mergeCellsHorizontal(table, 0, 0, 6);
        mergeCellsHorizontal(table, 1, 0, 6);
        mergeCellsHorizontal(table, 3 + list1.size(), 0, 6);
        mergeCellsHorizontal(table, 4 + list1.size(), 0, 6);
        mergeCellsHorizontal(table, 5 + list1.size(), 4, 6);
        for (int i = 0; i < list2Num; i++) {
            mergeCellsHorizontal(table, 6 + i + list1.size(), 4, 6);
        }
    }

    /**
     * @Description: 跨列合并
     */
    public static void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if (cellIndex == fromCell) {
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    /**
     * @Description: 跨行合并
     */
    public void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            if (rowIndex == fromRow) {
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
    }


    public static void main(String[] args) {
        //模板文件地址
        String inputUrl = "E:\\healthy_template.docx";
        //新生产的模板文件
        String outputUrl = "E:\\test.docx";

        Map<String, String> testMap = new HashMap<>();
        testMap.put("sampleName", "小明");
        testMap.put("geneRefgene", "sfdsfsdfsdfsd");
        testMap.put("Xref.refGene", "软件园");
        testMap.put("mutationInfo", "88888888");

        List<String[]> testList1 = new ArrayList<>();
        List<String[]> testList2 = new ArrayList<>();
        testList1.add(new String[]{"基因1", "", "肌肉病1", "A突变1", "aa1", "0.1a", ""});
        testList1.add(new String[]{"基因2", "", "肌肉病2", "A突变2", "aa2", "0.1b", ""});
        testList1.add(new String[]{"基因3", "", "肌肉病3", "A突变3", "aa3", "0.1c", ""});
        testList1.add(new String[]{"基因4", "", "肌肉病4", "A突变4", "aa4", "0.1d", ""});

        testList2.add(new String[]{"家系基因4", "", "肌肉病4", "A突变4", "aa4", "", ""});
        testList2.add(new String[]{"家系基因4", "", "肌肉病4", "A突变4", "aa4", "", ""});
        testList2.add(new String[]{"家系基因4", "", "肌肉病4", "A突变4", "aa4", "", ""});

        WorderToNewWordUtils.geneReport(inputUrl, outputUrl, "test1", testList1, testList2);
    }
}
