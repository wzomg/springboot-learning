package com.zzw.service.Impl;

import com.zzw.mapper.UserMapper;
import com.zzw.pojo.User;
import com.zzw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	//注入一个 UserMapper 接口
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User findByName(String username) {
		return userMapper.findByName(username);
	}
}
