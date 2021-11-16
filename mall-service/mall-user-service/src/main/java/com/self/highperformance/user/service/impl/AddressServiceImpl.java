package com.self.highperformance.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.self.highperformance.user.mapper.AddressMapper;
import com.self.highperformance.user.model.Address;
import com.self.highperformance.user.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

    @Autowired
    private AddressMapper addressMapper;


    @Override
    public List<Address> list(String userName) {
        return addressMapper.selectList(
                new QueryWrapper<Address>()
                        .eq("username", userName));
    }
}
