package com.ychp.user.server.service;

import com.ychp.common.exception.InvalidException;
import com.ychp.common.exception.ResponseException;
import com.ychp.user.model.Address;
import com.ychp.user.server.repository.AddressRepository;
import com.ychp.user.api.service.AddressReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
* @author yingchengpeng
* @date 2018-08-09
*/
@Service
public class AddressReadServiceImpl implements AddressReadService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address findById(Long id) {
        if(id == null) {
            throw new InvalidException("address.id.empty", "id", id);
        }
        try {
            return addressRepository.findById(id);
        } catch (Exception e) {
            throw new ResponseException("address.find.fail", e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Address> findByPid(Long pid) {
        if(pid == null) {
            throw new InvalidException("address.pid.empty", "pid", pid);
        }
        try {
            return addressRepository.findByPid(pid);
        } catch (Exception e) {
            throw new ResponseException("address.find.fail", e.getMessage(), e.getCause());
        }
    }

}