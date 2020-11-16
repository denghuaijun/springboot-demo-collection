package com.dhj.demo.mp.service.impl;

import com.dhj.demo.mp.entity.User;
import com.dhj.demo.mp.mapper.UserMapper;
import com.dhj.demo.mp.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author 邓怀俊
 * @since 2020-11-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
