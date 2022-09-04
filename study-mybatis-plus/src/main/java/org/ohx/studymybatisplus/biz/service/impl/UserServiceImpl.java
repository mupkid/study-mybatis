package org.ohx.studymybatisplus.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.ohx.studymybatisplus.dal.mapper.UserMapper;
import org.ohx.studymybatisplus.dal.model.dataobject.User;
import org.ohx.studymybatisplus.biz.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author mudkip
 * @date 2022/8/31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
