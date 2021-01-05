package com.zzw.mapper;

import com.zzw.pojo.User;

public interface UserMapper {
    public User findByName(String username);
}