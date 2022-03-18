package com.cxy.checkservice.service.impl;

import com.cxy.checkservice.entity.TestV1;
import com.cxy.checkservice.entity.vo.*;
import com.cxy.checkservice.mapper.PacketCheckMapper;
import com.cxy.checkservice.service.PacketCheckService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cxy
 * @since 2022-03-16
 */
@Service
public class PacketCheckServiceImpl extends ServiceImpl<PacketCheckMapper, TestV1> implements PacketCheckService {

    @Autowired
    private PacketCheckMapper packetCheckMapper;

    // 查询不同afn包的数目
    @Override
    public List<PacketCountVo> getPacketCount() {
        List<PacketCountVo> packetCountVoList = packetCheckMapper.getPacketCount();
        return packetCountVoList;
    }

    // 查询合格包和不合格包数目
    @Override
    public PacketUnQualifiedCount getUnqualifiedPacketCount() {
        Integer unqualifiedPacketCount = packetCheckMapper.getUnqualifiedPacketCount();
        Integer qualifiedPacketCount = packetCheckMapper.getQualifiedPacketCount();
        PacketUnQualifiedCount packetUnQualifiedCount = new PacketUnQualifiedCount();
        packetUnQualifiedCount.setUnqualifiedCount(unqualifiedPacketCount);
        packetUnQualifiedCount.setQualifiedCount(qualifiedPacketCount);
        return packetUnQualifiedCount;
    }


    // 每隔2秒查询不同的合格包数目
    @Override
    public AfnTimeVo getDiffrentAfnCount(Integer second) {

        /**
        for (int i = 4;i > 0;i--) {
            AfnVo afnVo = new AfnVo();
            List<Afn> afnLlist = packetCheckMapper.getDiffrentAfnCount(second * i,second * (i-1));
            String ts = packetCheckMapper.getTime(second * (i-1));

            afnVo.setAfnList(afnLlist);
            afnVo.setTs(ts);
            afnVoList.add(afnVo);

            timeList.add(ts);
        }
        **/

        /**
        HashMap<Integer,List<Integer>> afnMap = new HashMap<>();
        for (AfnVo afnVo : afnVoList) {
            for (Afn afn : afnVo.getAfnList()) {
                switch (afn.getAfn()) {
                    case 0:
                        if (afnMap.containsKey(0)){
                            List<Integer> tempList = new ArrayList<>();
                            for (int i = 0;i < afnMap.get(0).size();i++) {
                                tempList.add(afnMap.get(0).get(i));
                            }
                            tempList.add(afn.getCount());
                            afnMap.put(0, tempList);
                        }else{
                            List<Integer> tempList = new ArrayList<>();
                            tempList.add(afn.getCount());
                            afnMap.put(0, tempList);
                        }
                        break;
                    case 2:
                        if (afnMap.containsKey(2)){
                            List<Integer> tempList = new ArrayList<>();
                            for (int i = 0;i < afnMap.get(0).size();i++) {
                                tempList.add(afnMap.get(0).get(i));
                            }
                            tempList.add(afn.getCount());
                            afnMap.put(2, tempList);
                        }else{
                            List<Integer> tempList = new ArrayList<>();
                            tempList.add(0);
                            afnMap.put(2, tempList);
                        }
                        break;
                    default:
                        System.out.println(333);
                }
            }
        }

         **/

        /**
        HashMap<Integer,List<Integer>> afnMap = new HashMap<>();
        Integer[] afnExampleList = {0,2,10,18,19};
        for (AfnVo afnVo : afnVoList) {
            // 这一层遍历afn样值
            for (int afnexampleList : afnExampleList) {
                // 假如遍历到了 afnexampleList = 2
                for (Afn afn : afnVo.getAfnList()) {  // 逐个afn检查有没有afn=2的记录
                    if (afn.getAfn() == afnexampleList) {  // 如果当前afn对象的afn为2
                        if (afnMap.containsKey(afn.getAfn())){ // 如果哈希表中已经有了afn=2的记录
                            List<Integer> tempList = new ArrayList<>();
                            // 取出原记录
                            for (int i = 0;i < afnMap.get(afn.getAfn()).size();i++) {
                                tempList.add(afnMap.get(afn.getAfn()).get(i));
                            }
                            // 加上新记录
                            tempList.add(afn.getCount());
                            afnMap.put(afn.getAfn(), tempList);
                        }else{  // 如果哈希表中没有afn=2的记录
                            List<Integer> tempList = new ArrayList<>();
                            tempList.add(afn.getCount());
                            afnMap.put(afn.getAfn(), tempList);
                        }
                    }else {  // 如果当前afnList中没有afn对象的afn=2 只是代表本afnList 也就是本时间段没有 不代表之前没有 所以还要查哈希表中是否之前有
                        if (afnMap.containsKey(afnexampleList)){ // 如果哈希表中已经有了afn=2的记录
                            List<Integer> tempList = new ArrayList<>();
                            // 取出原记录
                            for (int i = 0;i < afnMap.get(afnexampleList).size();i++) {
                                tempList.add(afnMap.get(afnexampleList).get(i));
                            }
                            // 加上新记录0
                            tempList.add(0);
                            afnMap.put(afnexampleList, tempList);
                        }else{  // 如果哈希表中没有afn=2的记录
                            List<Integer> tempList = new ArrayList<>();
                            tempList.add(0);
                            afnMap.put(afnexampleList, tempList);
                        }
                    }
                }
            }
        }

        AfnTimeVo afnTimeVo = new AfnTimeVo();
        afnTimeVo.setTimeList(timeList);
        afnTimeVo.setAfnHashMap(afnMap);
        **/
        // 1.查出数据库中所有afn
        List<Integer> afnList = packetCheckMapper.getAfnList(second);

        // 2.查出时间列表放入响应体
        List<String> timeList = packetCheckMapper.getTimeList(second);

        // 3.查出每个afn在上述时间的值列表，放入哈希表中key为afn的value中
        HashMap<Integer, List<Integer>> afnHashMap = new HashMap<>();
        // 4.查询最近时间段内不同afn数目总体统计
        HashMap<Integer, Integer> afnTotalHashMap = new HashMap<>();
        for(Integer afnSingle : afnList) {
            List<Integer> afnCountList = packetCheckMapper.getAnfCountList(afnSingle,second);
            if (afnCountList.size() < afnList.size()) {
                afnCountList.clear();
                for (String timeSingle :timeList) {
                    int cnt = packetCheckMapper.getSpecialCountList(timeSingle,afnSingle,second);
                    afnCountList.add(cnt);
                }
            }
            afnHashMap.put(afnSingle,afnCountList);

            int afnTotalCount = packetCheckMapper.getAfnTotalCount(afnSingle,second);
            afnTotalHashMap.put(afnSingle,afnTotalCount);

        }


        // 5.将哈希表放入响应体
        AfnTimeVo afnTimeVo = new AfnTimeVo();
        afnTimeVo.setTimeList(timeList);
        afnTimeVo.setAfnHashMap(afnHashMap);
        afnTimeVo.setAfnTotalHashMap(afnTotalHashMap);

        // 返回响应体
        return afnTimeVo;
    }

    // 分页查询5s内不合规packet记录明细
    @Override
    public PageInfo<PacketDetailsVo> getUnqualifiedDetails(Integer page,Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<PacketDetailsVo> packetDetailsVoList = packetCheckMapper.getUnqualifiedDetails();
        PageInfo<PacketDetailsVo> res = new PageInfo<>(packetDetailsVoList);
        return res;
    }
}
