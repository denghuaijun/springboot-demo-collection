package com.dhj.demo.mp.service.impl;

import com.dhj.demo.mp.entity.Items;
import com.dhj.demo.mp.mapper.ItemsMapper;
import com.dhj.demo.mp.service.IItemsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author 邓怀俊
 * @since 2020-11-11
 */
@Service
public class ItemsServiceImpl extends ServiceImpl<ItemsMapper, Items> implements IItemsService {

}
