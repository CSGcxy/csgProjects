package com.cxy.checkoff.service.off;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.checkoff.entity.off.OffTerminalStatus;
import com.cxy.checkoff.entity.off.vo.*;

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
