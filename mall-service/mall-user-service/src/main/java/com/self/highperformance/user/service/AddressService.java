package com.self.highperformance.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.self.highperformance.user.model.Address;

import java.util.List;

public interface AddressService extends IService<Address> {

    List<Address> list(String userName);

}
