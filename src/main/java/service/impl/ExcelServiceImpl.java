package service.impl;


import com.aspose.cells.Workbook;
import org.apache.commons.io.FilenameUtils;
import service.DocumentService;
import utils.PathUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * Excel操作类
 */
public class ExcelServiceImpl implements DocumentService {

    /**
     * Excel转其他文件
     *
     * @param filePath
     * @param type
     * @return
     */
    @Override
    public String convertFile(String filePath, String type) {
        String checkType = FilenameUtils.getExtension(filePath);
        if (!"xlsx".equals(checkType) && !"xls".equals(checkType)) {
            throw new RuntimeException("输入文件不是Excel文件！");
        }
        try {
            switch (type.toUpperCase()) {
                /******************** 文档类型 ***************/
                case "WORD": {
                    return SwitchFile(filePath, com.aspose.cells.SaveFormat.DOCX, "docx");
                }
                case "PDF": {
                    return SwitchFile(filePath, com.aspose.cells.SaveFormat.PDF, "pdf");
                }
                case "PPT": {
                    return SwitchFile(filePath, com.aspose.cells.SaveFormat.PPTX, "pptx");
                }
                case "HTML": {
                    return SwitchFile(filePath, com.aspose.cells.SaveFormat.HTML, "html");
                }
                case "JSON": {
                    return SwitchFile(filePath, com.aspose.cells.SaveFormat.JSON, ".json");
                }
                case "MARKDOWN": {
                    return SwitchFile(filePath, com.aspose.cells.SaveFormat.MARKDOWN, "md");
                }
                default: {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String SwitchFile(String filePath, int saveFormat, String suffix) {
        String url = "";
        try {
            String fileName = PathUtils.getFilePath(filePath, suffix);
            FileOutputStream os = new FileOutputStream(fileName);
            FileInputStream is = new FileInputStream(filePath);
            //加载源文件数据
            Workbook excel = new Workbook(is);
            //设置转换文件类型并转换
            excel.save(os, saveFormat);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }


}
