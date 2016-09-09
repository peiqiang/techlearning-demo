package utils.poi;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * Created by changhongzi on 2016/9/9.
 */
public class WordTableParse {
    public static void main(String[] args) throws Exception {
        readDox();
    }


    public static void readDox() throws Exception {
        XWPFDocument docx = new XWPFDocument(new FileInputStream("F:\\aaa.docx"));
        XWPFWordExtractor we = new XWPFWordExtractor(docx);

        StringBuffer sb = new StringBuffer("");
        XWPFDocument document = (XWPFDocument) we.getDocument();
        List<XWPFTable> tables = document.getTables();
        for (XWPFTable table : tables) {
            List<XWPFTableRow> rows = table.getRows();
            int cellSize = rows.get(0).getTableCells().size();
            String[] cellsNewest = new String[cellSize];
            for (int i = 0; i < rows.size(); i++) {
                XWPFTableRow row = rows.get(i);
                List<XWPFTableCell> cells = row.getTableCells();
                for (int j = 0; j < cells.size(); j++) {
                    XWPFTableCell cell = cells.get(j);
                    String cellStr = cell.getText();
                    if (cellStr == null || "".equals(cellStr)) {
                        cellStr = cellsNewest[j];
                    } else {
                        cellsNewest[j] = cellStr;
                    }
                    System.out.print(cellStr + ",");
                    sb.append(cellStr).append(",");
                }
                sb.append("\n");
                System.out.println();
            }
            sb.append("\n\n");
            System.out.println("\n\n");
        }

        FileUtils.write(new File("F:\\a.csv"), sb.toString(), "UTF-8", false);
    }
}
