package com.thatday.user.modules.user.service;

import com.thatday.base.service.BaseService;
import com.thatday.user.modules.user.dao.UserDao;
import com.thatday.user.modules.user.entity.User;
import com.thatday.user.modules.user.vo.LoginPhoneVo;

public interface UserService extends BaseService<User, String, UserDao> {

    User loginByPhone(LoginPhoneVo loginPhoneVo);

    void addUser(String nickname);
}
