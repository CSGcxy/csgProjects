package com.cxy.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxy.security.entity.User;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author Bing
 * @since 2022-01-05
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}
