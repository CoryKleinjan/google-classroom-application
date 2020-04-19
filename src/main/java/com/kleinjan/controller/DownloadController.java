package com.kleinjan.controller;

import com.kleinjan.model.ClassGroup;
import com.kleinjan.model.Grouping;
import com.kleinjan.model.Student;
import com.kleinjan.returnWrappers.GroupPackage;
import com.kleinjan.service.GroupingService;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DownloadController {

    @Autowired
    GroupingService groupingService;

    @RequestMapping("/download-grouping")
    public ResponseEntity<Resource> getRandomFile(@RequestBody GroupPackage groupPackage) {

        File file;

        try{
            file = generateGroupingFile(groupPackage);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try{
            Path path = Paths.get(file.getAbsolutePath());
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
            HttpHeaders headers = new HttpHeaders();

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("application/msword"))
                    .body(resource);

        } catch(IOException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private File generateGroupingFile(GroupPackage groupPackage){
        File file = new File("C:\\Users\\coryk\\Documents\\wordTemplate.docx");

        try {
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());

            XWPFDocument document = new XWPFDocument(fis);

            Grouping grouping = groupingService.findById(groupPackage.getGroupId());

            for(XWPFTable table : document.getTables()){

                Integer groupNumber = 0;

                XWPFTableRow lastRow = table.getRows().get(1);
                CTRow ctrow = CTRow.Factory.parse(lastRow.getCtRow().newInputStream());
                XWPFTableRow newRow = new XWPFTableRow(ctrow, table);

                for(ClassGroup group : grouping.getClassGroups()) {

                    String studentString = "";
                    for (Student student : group.getStudents()) {
                        studentString += "," + student.getName();
                    }
                    studentString = studentString.replaceFirst(",", "");

                    for(XWPFTableCell cell : newRow.getTableCells()){

                        for (XWPFParagraph para : cell.getParagraphs()) {
                            List<XWPFRun> runs = para.getRuns();

                            StringBuilder newText = new StringBuilder();

                            if (runs != null) {
                                for (XWPFRun run : runs) {
                                    String text = run.getText(0);

                                    if (text != null) {
                                        newText.append(text);
                                    }

                                    run.setText("", 0);
                                }

                                String text = newText.toString();
                                if (text != null) {
                                    if (text.equals("$groupNumber")) {
                                        groupNumber++;
                                    }

                                    text = editText(text, studentString, groupNumber);
                                }

                                if (runs.size() > 0 && text != null) {
                                    runs.get(0).setText(text, 0);
                                }
                            }
                        }
                    }

                    table.addRow(newRow);

                    lastRow = table.getRows().get(1);
                    ctrow = CTRow.Factory.parse(lastRow.getCtRow().newInputStream());
                    newRow = new XWPFTableRow(ctrow, table);
                }

                table.removeRow(1);

                fis.close();

                document.write(new FileOutputStream(new File("grouping.docx")));
                document.close();

                return new File("grouping.docx");
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        return file;
    }

    public String editText(String text, String studentString, Integer groupNumber){
        text = text.contains("$groupNumber") ? text.replace("$groupNumber", groupNumber.toString()) : text;
        text = text.contains("$studentString") ? text.replace("$studentString", studentString) : text;

        return text;
    }
}
