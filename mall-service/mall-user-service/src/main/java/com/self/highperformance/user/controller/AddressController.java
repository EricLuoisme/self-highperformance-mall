package com.self.highperformance.user.controller;

import com.self.highperformance.user.model.Address;
import com.self.highperformance.user.service.AddressService;
import com.self.highperformance.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
@CrossOrigin
public class AddressController {

    @Autowired
    private AddressService addressService;


    @GetMapping("/list")
    public RespResult<List<Address>> list() {
        String userName = "testUser";
        List<Address> list = addressService.list(userName);
        return RespResult.ok(list);
    }


}
