package com.wotong.service;

import com.baomidou.mybatisplus.service.IService;
import com.wotong.commons.result.PageInfo;
import com.wotong.model.SysLog;

/**
 *
 * SysLog 表数据服务层接口
 *
 */
public interface ISysLogService extends IService<SysLog> {

    void selectDataGrid(PageInfo pageInfo);

}