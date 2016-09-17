package utils.poi;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.List;

/**
 * Created by changhongzi on 2016/9/9.
 */
public class WordTableParse {
    public static void main(String[] args) throws Exception {
        parseIdDocx();
    }

    public static void parseIdDocx() throws Exception {

        //获取文件所有docx文档
        Collection<File> files = FileUtils.listFiles(new File("F:\\work\\U+大数据\\ID文档整理\\docx"), null, false);
        for (File file : files) {
            XWPFDocument docx = new XWPFDocument(new FileInputStream(file));
            //对word文档进行提取
            XWPFWordExtractor we = new XWPFWordExtractor(docx);
            //获取单个docx文件对象
            XWPFDocument document = (XWPFDocument) we.getDocument();
            //从docx文件中解析出所有表格
            List<XWPFTable> tables = document.getTables();
            for(XWPFTable table : tables){
                //解析并打印出表格内容
                parseAndPrintTable(table, file.getName());
            }
        }
    }

    public static void parseAndPrintTable(XWPFTable table, String name) throws Exception {
        //存放表格内存
        StringBuffer tableContent = new StringBuffer("");
        //获取表格的所有行
        List<XWPFTableRow> rows = table.getRows();
        //存放表格的单元格数，为后期基于列的单元格合并做准备
        int cellSize = rows.get(0).getTableCells().size();
        String[] cellsNewest = new String[cellSize];
        for (int i = 0; i < rows.size(); i++) {
            XWPFTableRow row = rows.get(i);
            //获取表格的所有单元格
            List<XWPFTableCell> cells = row.getTableCells();
            for (int j = 0; j < cells.size(); j++) {

                XWPFTableCell cell = cells.get(j);
                String cellStr = cell.getText();
                //如果该单元格为空，就取上一个单元格的内容
                if (cellStr == null || "".equals(cellStr)) {
                    cellStr = cellsNewest[j];
                } else {
                    cellsNewest[j] = cellStr;
                }
                System.out.print(cellStr + ",");
                tableContent.append(cellStr).append(",");
            }
            tableContent.append("\n");
            System.out.println();
        }
        tableContent.append("\n\n");
        System.out.println("\n\n");
        //写文件数据
        FileUtils.writeStringToFile(new File("F:\\" + name + ".csv"), tableContent.toString(), "GB18030", false);
    }


    public static void readHeadLine() throws Exception {
        XWPFDocument docx = new XWPFDocument(new FileInputStream(
            "F:\\work\\U+大数据\\ID文档整理\\分体空调_00000000000000008080000000041410_ID.docx"));
        XWPFWordExtractor we = new XWPFWordExtractor(docx);
        XWPFDocument document = (XWPFDocument) we.getDocument();
        List<XWPFHeader> headers = document.getHeaderList();
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        List<IBodyElement> documents = document.getBodyElements();

        for (XWPFParagraph paragraph : paragraphs) {
            //            System.out.println(paragraph.getParagraphText()+"\n++++++++++++++++++++++++");
            if ("1".equals(paragraph.getStyle())) {
                System.out.println(paragraph.getParagraphText() + "\n++++++++++++++++++++++++");
            }
        }


    }

    public static void readDox() throws Exception {
        XWPFDocument docx = new XWPFDocument(new FileInputStream(
            "F:\\work\\U+大数据\\ID文档整理\\分体空调_00000000000000008080000000041410_ID.docx"));
        XWPFWordExtractor we = new XWPFWordExtractor(docx);

        StringBuffer sb = new StringBuffer("");
        XWPFDocument document = (XWPFDocument) we.getDocument();
        List<XWPFTable> tables = document.getTables();
        for (XWPFTable table : tables) {
            parseAndPrintTable(table);
        }
    }

    public static void parseAndPrintTable(XWPFTable table) throws Exception {
        parseAndPrintTable(table, "test");
    }
}
