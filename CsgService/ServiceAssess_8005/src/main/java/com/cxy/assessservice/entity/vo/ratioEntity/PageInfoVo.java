package com.cxy.assessservice.entity.vo.ratioEntity;

import com.cxy.assessservice.entity.vo.TerminalScoreEntityVo;
import lombok.Data;

import java.util.List;

/**
 * @author Lishuguang
 * @create 2022-05-17-21:20
 */

@Data
public class PageInfoVo {

    // 查询的页数(查询第几页)
    private Integer pageNum;

    // 查询的每页记录数
    private Integer pageSize;

    // 当前页的数量
    private Integer curPageSize;

    // 当前页面第一个元素在数据库中的行号
    private int startRow;

    // 当前页面最后一个元素在数据库中的行号
    private int endRow;

    // 分页查询结果
    private List<TerminalScoreEntityVo> terminalScoreEntityVoPage;

    // 总记录数
    private Integer totalNum;

    // 总页数
    private Integer totalPageNum;
}
