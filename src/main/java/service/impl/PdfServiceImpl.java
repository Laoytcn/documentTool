package service.impl;

import com.aspose.pdf.Document;
import com.aspose.pdf.HtmlSaveOptions;
import com.aspose.pdf.SaveFormat;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import service.DocumentService;
import utils.PathUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * PDF操作类
 */
public class PdfServiceImpl implements DocumentService {

    /**
     * PDF 转其他文件
     *
     * @param filePath
     * @param type
     * @return
     */
    @Override
    public String convertFile(String filePath, String type) {
        List<String> res = new ArrayList<>();
        String checkType = FilenameUtils.getExtension(filePath);
        if (!"pdf".equals(checkType)) {
            throw new RuntimeException("输入文件不是PDF文件！");
        }
        try {
            switch (type.toUpperCase()) {
                case "WORD": {
                    return switchFile(filePath, com.aspose.pdf.SaveFormat.DocX, "docx");
                }
                case "XML": {
                    return switchFile(filePath, SaveFormat.PdfXml, "xml");
                }
                case "EXCEL": {
                    return switchFile(filePath, com.aspose.pdf.SaveFormat.Excel, "xlsx");
                }
                case "PPT": {
                    return switchFile(filePath, com.aspose.pdf.SaveFormat.Pptx, "pptx");
                }
                case "HTML": {

                    int startIndex = StringUtils.lastIndexOf(filePath, ".");
                    String fileName = StringUtils.substring(filePath, startIndex + 1, filePath.length());
                    // 输出路径
                    String outFileName = fileName + "." + ".html";
                    FileOutputStream os = new FileOutputStream(outFileName);

                    FileInputStream is = new FileInputStream(filePath);
                    Document doc = new Document(is);

                    HtmlSaveOptions saveOptions = new HtmlSaveOptions();
                    saveOptions.setFixedLayout(true);
                    saveOptions.setSplitIntoPages(false);
                    saveOptions.setRasterImagesSavingMode(HtmlSaveOptions.RasterImagesSavingModes.AsExternalPngFilesReferencedViaSvg);
                    doc.save(filePath, saveOptions);
                    doc.close();
                    return fileName;
                }
                default: {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private String switchFile(String filePath, SaveFormat saveFormat, String suffix) {
        try {
            String fileName = PathUtils.getFilePath(filePath, suffix);
            FileOutputStream os = new FileOutputStream(fileName);
            FileInputStream fileInputStream = new FileInputStream(filePath);
            Document doc = new Document(fileInputStream);
            doc.save(os, saveFormat);
            os.close();
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
