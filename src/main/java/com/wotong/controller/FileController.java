package com.wotong.controller;

import com.wotong.commons.base.BaseController;
import com.wotong.service.IFileService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * 文件上传
 *
 * @author XuJijun
 */
@Controller
@RequestMapping("/file")
public class FileController extends BaseController {
    private static final String SAVE_DIR = "uploadFiles";
    @Autowired
    private IFileService fileService;
//    private File filedata;
    private String fileName="";

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadFile(@RequestParam("file") MultipartFile filedata){
        String type="1";
        String msg ="";

//        if(filedata.getOriginalFilename().length()==0){
            System.out.println(filedata.getOriginalFilename());
            fileName = filedata.getOriginalFilename();
//        }
        Map param = new HashMap();
        param.put("fileName", fileName);
        param.put("fileid", "");
        InputStream is = null;
        try {
            is = filedata.getInputStream();
            Map fMap = fileService.uploadFile(is, param);
        } catch (Exception e) {
            e.printStackTrace();
            return renderError("文件上传失败!");

        }finally{
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return renderSuccess();
    }


    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public String downloadFile(@RequestParam("file") MultipartFile filedata){
        String type="1";
        String msg ="";
        String bodyXml="";
//        if(filename.length()==0){

        System.out.println(filedata.getOriginalFilename());
        fileName = filedata.getOriginalFilename();
//        }
        Map param = new HashMap();
        param.put("fileName", fileName);
        param.put("fileid", "");
        InputStream is = null;
        try {
            is = filedata.getInputStream();
//            is = new FileInputStream(filedata.getInputStream());
            Map fMap = fileService.uploadFile(is, param);
//            Map infoMap = CTools.map2Xml(fMap);
//            bodyXml = (String)infoMap.get("value");
        } catch (Exception e) {
            e.printStackTrace();
            type="0";
            msg = e.getMessage();
        }finally{
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
//            returnXml = BusinessReturnXml.createReturnXml(type, msg, bodyXml);
//            request.setAttribute("returnXml", returnXml);
        }
        return "success";
    }

}