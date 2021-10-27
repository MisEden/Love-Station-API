package org.eden.lovestation.util.excel;

import lombok.Data;
import org.apache.poi.hssf.usermodel.*;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.eden.lovestation.dao.model.User;
import org.eden.lovestation.dao.repository.UserRepository;
import org.eden.lovestation.dto.request.ExportExcelRequest;
import org.eden.lovestation.exception.business.DownloadExportExcelException;
import org.flywaydb.core.internal.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@ConfigurationProperties(prefix = "excel")
@Data
public class ExcelUtil {

    private String exportExcelBasePath = "/storage/export-excel/";
    private String excelNamePrefix = "愛心棧匯出資料_";
    private String sheetName = "匯出資料";

    private final UserRepository userRepository;


    @Autowired
    public ExcelUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void createTitle(HSSFWorkbook workbook, HSSFSheet sheet, List<String> titleName, List<Integer> columnWidth){
        HSSFRow row = sheet.createRow(0);
        //設定列寬，setColumnWidth的第二個引數要乘以256，這個引數的單位是1/256個字元寬度
        for(int i = 0; i < titleName.size(); i++){
            sheet.setColumnWidth(i,columnWidth.get(i)*256);
        }

        //設定為居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFont(font);

        HSSFCell cell;

        for(int i = 0; i < titleName.size(); i++){
            cell = row.createCell(i);
            cell.setCellValue(titleName.get(i));
            cell.setCellStyle(style);
        }

    }

    public String getDataExcel(ExportExcelRequest request) throws DownloadExportExcelException{
        //try to delete expired files
        try{
            deleteOldFiles();
        }catch (Exception e){

        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);

        //設定Excel標頭
        createTitle(workbook,sheet, request.getTitle(), request.getColumnWidth());


        List<User> rows = userRepository.findAll();

        //設定日期格式
//        HSSFCellStyle style = workbook.createCellStyle();
//        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));

        //新增資料行，並且設定單元格資料
        for(int i = 0; i < request.getData().size(); i++){
            HSSFRow row = sheet.createRow(i+1);
            List<String> dataRow = request.getData().get(i);

            for(int j = 0; j < dataRow.size(); j++){
                row.createCell(j).setCellValue(dataRow.get(j));

//                HSSFCell cell = row.createCell(3);
//                cell.setCellValue(user.getCreatedAt());
//                cell.setCellStyle(style);
            }
        }


        String nowTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String fileName = excelNamePrefix + nowTime + ".xls";

        //生成excel檔案
        return buildExcelFile(fileName, workbook);

    }

    //生成excel檔案
    protected String buildExcelFile(String filename,HSSFWorkbook workbook) throws DownloadExportExcelException{
        try {
            String excelFilePath = String.format(".%s%s", exportExcelBasePath, filename);
            Path excelPath = Paths.get("." + exportExcelBasePath);
            Files.createDirectories(excelPath);

            FileOutputStream fos = new FileOutputStream(excelFilePath);
            workbook.write(fos);
            fos.flush();
            fos.close();

            return exportExcelBasePath + filename;
        } catch (IOException e) {
            throw new DownloadExportExcelException();
        }
    }

    public void deleteOldFiles() {
        try{
            List<String> textFiles = new ArrayList<String>();

            File dir = new File("." + exportExcelBasePath);
            for (File file : dir.listFiles()) {
                if (file.getName().endsWith((".xls"))) {
                    textFiles.add(file.getName());
                }
            }

            for(int i = 0; i < textFiles.size(); i++){
                Path file = Paths.get("." + exportExcelBasePath + textFiles.get(i));
                BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Date fileDate = sdf.parse(attr.creationTime().toString().split("T")[0]);

                Date date = new Date();
                String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
                Date todayDate = sdf.parse(modifiedDate);


                long diffInMillies = Math.abs(todayDate.getTime() - fileDate.getTime());
                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);


                //刪除產生天數大於10天的檔案
                if(diff > 10){
                    File fileDelete = new File("." + exportExcelBasePath + textFiles.get(i));
                    if(fileDelete.delete()){
                        System.out.println("[Delete Expired File - Success]" + textFiles.get(i) + " ->" + diff + "Day");
                    } else {
                        System.out.println("[Delete Expired File - Fail]" + textFiles.get(i) + " ->" + diff + "Day");
                    }
                }
            }

        } catch (Exception e) {

        }


    }

}
