package com.wotong.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wotong.commons.result.PageInfo;
import com.wotong.commons.result.Tree;
import com.wotong.commons.utils.PropsUtil;
import com.wotong.commons.utils.StringUtils;
import com.wotong.mapper.FileMapper;
import com.wotong.mapper.RoleMapper;
import com.wotong.mapper.RoleResourceMapper;
import com.wotong.mapper.UserRoleMapper;
import com.wotong.model.FileInfo;
import com.wotong.model.Role;
import com.wotong.model.RoleResource;
import com.wotong.service.IFileService;
import com.wotong.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * Role 表数据服务层接口实现类
 *
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FileInfo> implements IFileService {


    @Autowired
    private FileMapper fileMapper;
//    private RoleMapper roleMapper;
    Properties conf = PropsUtil.loadProps("file.properties");
    private String rootPath = PropsUtil.getString(conf,"file.path");
    private int buffsize = 1024;

    public InputStream downloadFile(Map param) throws Exception {
        String fileName = "";
        String filePath = "";
        String fileid = (String) param.get("fileid");
//        TShareFile sfile = (TShareFile) dao.get(TShareFile.class, fileid);
//        fileName = sfile.getfileName();
//        filePath = sfile.getFilepath();
        System.out.println("download disc::" + fileName);
        InputStream is = null;
        File file = new File(rootPath + filePath);
        if (file.exists()) {
            param.put("fileName", fileName);
            is = new FileInputStream(file);
        }
        return is;
    }

    public Map uploadFile(InputStream is, Map param) throws Exception {
        FileInfo fileInfo = new FileInfo();
        Map rMap = new HashMap();
        String fileName = (String) param.get("fileName");
        String prefixName = fileName.substring(0,fileName.lastIndexOf("."));
        String suffixName = fileName.substring(fileName.lastIndexOf(".")+1);
        String fileid = param.get("fileid").toString();
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sf.format(date);
        File tfile = new File(rootPath );
        if (!tfile.exists()) {
            tfile.mkdirs();
        }
        rMap.put("fileid", fileid);
        String filePath = rootPath +File.separator + fileid + "_" + fileName;

        File file = new File(filePath);
        int i = 0;
        byte[] b = new byte[buffsize];
        OutputStream os = new FileOutputStream(file);
        while ((i = is.read(b)) > -1) {
            os.write(b, 0, i);
        }
        os.flush();
        os.close();
        fileInfo.setFileName(fileName);
        fileInfo.setRemark("aaa");
        fileInfo.setCreateDate(dateStr);
        fileInfo.setPrefixName(prefixName);
        fileInfo.setSuffixName(suffixName);
        fileInfo.setFilePath(filePath);
        try {
            fileMapper.insert(fileInfo);
            rMap.put("result", "success");
        } catch (Exception e) {
            rMap.put("result", "error");
            if (file.exists() && file.isFile()) {
                file.delete();
            }
            throw e;
        }
        return rMap;
    }


}