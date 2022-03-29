package com.cxy.assessservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxy.assessservice.entity.Networksegment;
import com.cxy.assessservice.entity.vo.ratioEntity.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cxy
 * @since 2021-12-04
 */
@Repository
public interface NetworksegmentMapper extends BaseMapper<Networksegment> {

    // 获取1分钟内出现的网段名称
    List<String> getSegList();

    // 获取上行速率/平均速率的比值 及 速率 和 平均速率
    UpRateRatio getuprateRatio(String segment);

    // 获取下行速率/平均速率的比值 及 速率 和 平均速率
    DownRateRatio getdownrateRatio(String segment);

    // 获取在线终端数/平均在线终端数的比值 及 在线终端数 和 平均在线终端数
    OnlineCountRatio getonlineCountRatio(String segment);

    // 获取离线终端数/平均离线终端数的比值 及 离线终端数 和 平均离线终端数
    OfflineCountRatio getofflineCountRatio(String segment);

    // 获取告警流数/平均告警流数的比值 及 告警流数 和 平均告警流数
    AlertFlowCountRatio getAlertCountRatio(String segment);
}