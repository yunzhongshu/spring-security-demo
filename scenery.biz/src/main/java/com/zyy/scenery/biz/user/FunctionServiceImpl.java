/**
 * }
 * Copyright 2016
 * Copyright 2016 linglingqi Group Holding Ltd. All Rights Reserved
 */
package com.zyy.scenery.biz.user;

import com.zyy.scenery.dal.user.domain.Role;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * FunctionServiceImpl.java
 * zhangyunyun      16-11-16 */
@Service
public class FunctionServiceImpl implements FunctionService {

    @Override
    public List<String> queryAllFunctions() {
        return null;
    }

    @Override
    public List<Role> queryRolesByFunctions(String functionUrl) {
        return null;
    }
}
