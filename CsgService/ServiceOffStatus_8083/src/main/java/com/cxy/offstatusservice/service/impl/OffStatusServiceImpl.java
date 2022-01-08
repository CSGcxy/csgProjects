package com.cxy.offstatusservice.service.impl;

import com.cxy.offstatusservice.entity.*;
import com.cxy.offstatusservice.entity.vo.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.offstatusservice.entity.vo.OffTerminalCountVO;
import com.cxy.offstatusservice.mapper.OffTerminalStatusMapper;
import com.cxy.offstatusservice.service.OffStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cxy
 * @since 2021-12-04
 */
@Service
public class OffStatusServiceImpl extends ServiceImpl<OffTerminalStatusMapper, OffTerminalStatus> implements OffStatusService {


    @Autowired
    private OffTerminalStatusMapper offTerminalStatusMapper;

    //查询networkSegment表中的网段名
    @Override
    public OffTerminalCountVO getOffterminalCount() {

        List<OffTerminalCount> offTerminalCounts = offTerminalStatusMapper.getOffterminalCount();
        List<String> timestampList = new ArrayList<>();
        List<Integer> offTerminalCountList = new ArrayList<>();
//        System.out.println(offTerminalCounts);
        for (OffTerminalCount otc : offTerminalCounts) {
            timestampList.add(otc.getTimestamp());
            offTerminalCountList .add(otc.getOffTerminalCount());
        }
        return new OffTerminalCountVO(timestampList, offTerminalCountList);
    }

    // 查询  不同生产厂家 的 离线终端数
    @Override
    public OffTerminalManufactorVO getOffterminalManufactor() {

        List<OffTerminalManufactor> offTerminalManufactors = offTerminalStatusMapper.getOffterminalManufactor();
        List<String> manufactorList = new ArrayList<>();
        List<Integer> offTerminalCountList = new ArrayList<>();
//        System.out.println(offTerminalManufactors);
        for (OffTerminalManufactor otm : offTerminalManufactors) {
            manufactorList.add(otm.getManufactor());
            offTerminalCountList .add(otm.getOffTerminalCount());
        }
        return new OffTerminalManufactorVO(manufactorList, offTerminalCountList);

    }

    @Override
    public OffTerminalSegmentVO getOffTerminalSegment() {
        List<OffTerminalSegment> offTerminalSegments = offTerminalStatusMapper.getOffterminalSegment();
        List<String> segmentList = new ArrayList<>();
        List<Integer> offTerminalCountList = new ArrayList<>();
//        System.out.println(offTerminalSegments);
        for (OffTerminalSegment ots : offTerminalSegments) {
            segmentList.add(ots.getNetSegment());
            offTerminalCountList .add(ots.getOffTerminalCount());
        }
        return new OffTerminalSegmentVO(segmentList, offTerminalCountList);
    }

    @Override
    public OffTerminalMapVO getOffTerminalMap() {
        List<OffTerminalMap> offTerminalMaps = offTerminalStatusMapper.getOffterminalMap();
        List<String> mapList = new ArrayList<>();
        List<Integer> offTerminalCountList = new ArrayList<>();
//        System.out.println(offTerminalMaps);
        for (OffTerminalMap otm : offTerminalMaps) {
            mapList.add(otm.getLocation());
            offTerminalCountList .add(otm.getOffTerminalCount());
        }
        return new OffTerminalMapVO(mapList, offTerminalCountList);
    }

    @Override
    public OffTerminalCategoryVO getOffTerminalCategory() {
        List<OffTerminalCategory> offTerminalCategories = offTerminalStatusMapper.getOffterminalCategory();
        List<String> categoryList = new ArrayList<>();
        List<Integer> offTerminalCountList = new ArrayList<>();
//        System.out.println(offTerminalCategories);
        for (OffTerminalCategory otc : offTerminalCategories) {
            categoryList.add(otc.getCategory());
            offTerminalCountList .add(otc.getOffTerminalCount());
        }
        return new OffTerminalCategoryVO(categoryList, offTerminalCountList);
    }

    @Override
    public OffTerminalCountVO getOffTerminalSegTimeSequence(String segment) {
        List<OffTerminalCount> offTerminalCounts = offTerminalStatusMapper.getOffTerminalSegTimeSequence(segment);
        List<String> timestampList = new ArrayList<>();
        List<Integer> offTerminalCountList = new ArrayList<>();
//        System.out.println(offTerminalCounts);
        for (OffTerminalCount otc : offTerminalCounts) {
            timestampList.add(otc.getTimestamp());
            offTerminalCountList .add(otc.getOffTerminalCount());
        }
        return new OffTerminalCountVO(timestampList, offTerminalCountList);
    }

}
