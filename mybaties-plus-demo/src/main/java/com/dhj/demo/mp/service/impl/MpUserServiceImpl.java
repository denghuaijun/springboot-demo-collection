package com.dhj.demo.mp.service.impl;

import com.dhj.demo.mp.entity.MpUser;
import com.dhj.demo.mp.mapper.MpUserMapper;
import com.dhj.demo.mp.service.IMpUserService;
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
public class MpUserServiceImpl extends ServiceImpl<MpUserMapper, MpUser> implements IMpUserService {

}
