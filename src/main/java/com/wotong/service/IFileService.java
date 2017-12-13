package com.wotong.service;

import com.baomidou.mybatisplus.service.IService;
import com.wotong.commons.result.PageInfo;

import com.wotong.model.FileInfo;
import com.wotong.model.Role;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Role 表数据服务层接口
 */
public interface IFileService extends IService<FileInfo> {

    public Map uploadFile(InputStream is, Map param) throws Exception;

    public InputStream downloadFile(Map param) throws Exception;

}