package factory;


import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import service.DocumentService;
import service.impl.ExcelServiceImpl;
import service.impl.PdfServiceImpl;
import service.impl.PptServiceImpl;
import service.impl.WordServiceImpl;

public class DocumentFactory {

    public DocumentService create(String filePath) {
        String suffix = FilenameUtils.getExtension(filePath);
        if (StringUtils.equals(suffix, "pdf")) {
            return new PdfServiceImpl();
        } else if (StringUtils.equals(suffix, "xlsx") || StringUtils.equals(suffix, "xls")) {
            return new ExcelServiceImpl();
        } else if (StringUtils.equals(suffix, "ppt") || StringUtils.equals(suffix, "pptx")) {
            return new PptServiceImpl();
        } else if (StringUtils.equals(suffix, "doc") || StringUtils.equals(suffix, "docx")) {
            return new WordServiceImpl();
        }
        return null;
    }

}
