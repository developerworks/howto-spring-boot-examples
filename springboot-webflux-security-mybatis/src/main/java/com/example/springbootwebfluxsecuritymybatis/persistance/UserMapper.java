package com.example.springbootwebfluxsecuritymybatis.persistance;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {
  @Select("SELECT username, password FROM user WHERE username=#{username} LIMIT 1")
  User findByUsername(String username);
}
