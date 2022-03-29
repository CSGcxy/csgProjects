package com.cxy.assessservice.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.cxy.assessservice.entity.Networksegment;
import com.cxy.assessservice.entity.vo.SegScoreEntityVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cxy
 * @since 2021-12-04
 */
public interface NetworksegmentService extends IService<Networksegment> {


    List<SegScoreEntityVo> getAllSegScoreDetails();
}
