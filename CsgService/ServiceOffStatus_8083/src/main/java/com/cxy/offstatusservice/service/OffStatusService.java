package com.cxy.offstatusservice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.offstatusservice.entity.vo.*;
import com.cxy.offstatusservice.entity.OffTerminalStatus;
import com.cxy.offstatusservice.entity.vo.OffTerminalCountVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cxy
 * @since 2021-12-04
 */
public interface OffStatusService extends IService<OffTerminalStatus> {

    OffTerminalCountVO getOffterminalCount();

    OffTerminalManufactorVO getOffterminalManufactor();

    OffTerminalSegmentVO getOffTerminalSegment();

    OffTerminalMapVO getOffTerminalMap();

    OffTerminalCategoryVO getOffTerminalCategory();

    OffTerminalCountVO getOffTerminalSegTimeSequence(String segment);
}
