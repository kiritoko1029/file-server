package com.cxc.fileservice.controller;

import cn.hutool.core.date.DateUtil;
import com.cxc.fileservice.constant.ReqParam;
import com.cxc.fileservice.domain.FSFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * @author chenxiangcai
 */
@Controller
@RequestMapping("/")
public class FileServerController {

    @GetMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("index");
        String matchUri = (String) request.getAttribute(ReqParam.REQ_URI);
        String fsPath = (String) request.getAttribute(ReqParam.FILE_SERVER_RES_PATH);
        File file = new File(fsPath);
        File[] fileList =file.listFiles();
        ArrayList<FSFile> fsFiles = new ArrayList<>();
        if(fileList != null){
            for(File fileItem :fileList){
                FSFile fsFile = new FSFile();
                String fileName = fileItem.getName();
                if(fileItem.isDirectory()){
                    fileName = fileItem.getName()+"/";
                }else {
                    fileName = fileItem.getName();
                }
                fsFile.setName(fileName);
                fsFile.setSize(fileItem.length()+" bytes");
                if(!matchUri.endsWith("/")){
                    matchUri = matchUri+"/";
                }
                fsFile.setRelatePath(matchUri+fileItem.getName());
                Date date = DateUtil.date(fileItem.lastModified());
                fsFile.setModifyTime(DateUtil.formatDateTime(date));
                fsFiles.add(fsFile);
            }
        }
        if(!"/".equals(matchUri)){
            String[] dirArray = matchUri.split("/");
            String preUri = "/";
            if (dirArray.length > 1) {
                dirArray = Arrays.copyOf(dirArray, dirArray.length - 1);
                if(dirArray.length>1){
                    preUri = String.join("/", dirArray);
                }
            }
            mv.addObject("preUri", preUri);
        }
        mv.addObject("dirs",fsFiles);
        return mv;
    }

    @GetMapping("/test")
    public ModelAndView showFile(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }
}
