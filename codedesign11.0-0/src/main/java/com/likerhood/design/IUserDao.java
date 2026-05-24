package com.likerhood.design;


import com.likerhood.design.agent.Select;

public interface IUserDao {

    @Select("select userName from user where id = #{uId}")
    String queryUserInfo(String uId);



}
