package com.cxy.assessservice.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.cxy.assessservice.entity.Networksegment;
import com.cxy.assessservice.entity.vo.SegScoreAllTimeVo;
import com.cxy.assessservice.entity.vo.SegScoreEntityVo;
import com.cxy.assessservice.entity.vo.TerminalScoreEntityVo;
import com.cxy.assessservice.entity.vo.ratioEntity.PageInfoVo;
import com.github.pagehelper.PageInfo;

import java.text.ParseException;
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


    SegScoreAllTimeVo getAllSegScoreDetails() throws ParseException;

    PageInfoVo getTerminalScoreDetails(SegScoreAllTimeVo segScoreEntityVoList, Integer pageNum, Integer pageSize);
}
