package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.persistence.dao.MoocUserTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocUserT;
import com.stylefeng.guns.user.UserAPI;
import com.stylefeng.guns.user.vo.UserInfoModel;
import com.stylefeng.guns.user.vo.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Steven Lu
 * @date 2018/12/17 -20:17
 */
@Component
@Service(interfaceClass = UserAPI.class,loadbalance = "roundrobin" )
public class  UserServiceImpl implements UserAPI {

    @Autowired
    private MoocUserTMapper moocUserTMapper;

    @Override
    public boolean register(UserModel userModel) {  //从前端获取注册信息
        //将注册信息转换成数据实体性息
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUserName(userModel.getUsername());
        moocUserT.setEmail(userModel.getEmail());
        moocUserT.setUserPhone(userModel.getPhone());
        moocUserT.setAddress(userModel.getAddress());

        String md5Password = MD5Util.encrypt(userModel.getPassword());
        moocUserT.setUserPwd(md5Password);

        //将数据实体插入数据库中
        Integer insert = moocUserTMapper.insert(moocUserT);
        if (insert > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int login(String username, String password) {
        //根据登录账号获取数据库信息
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUserName(username);
        MoocUserT result = moocUserTMapper.selectOne(moocUserT);

        if (result != null && result.getUuid() > 0) {
            //获取当前结果进行密码匹配
            String md5Password = MD5Util.encrypt(password);
            if (md5Password.equals(result.getUserPwd())) {
                return result.getUuid();
            }
        }
        return 0;
    }

    @Override
    public boolean checkUsername(String username) {
        EntityWrapper<MoocUserT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_name", username);
        Integer result = moocUserTMapper.selectCount(entityWrapper);
        if (result != null && result > 0) {
            return false;     //有重复用户，不可以使用
        } else {
            return true;    //没用重复用户，可以使用
        }
    }

    @Override
    public UserInfoModel getUserInfo(int uuid) {
        //根据主键查询用户信息
        MoocUserT moocUserT = moocUserTMapper.selectById(uuid);
        //将MoocUserT转换成UserInfoModel
        UserInfoModel userInfoModel = do2UserInfo(moocUserT);
        //返回UserInfoModel
        return userInfoModel;
    }

    @Override
    public UserInfoModel updateUserInfo(UserInfoModel userInfoModel) {
        //将传入的数据转化成MoocUserT
        MoocUserT moocUserT = new MoocUserT();
       moocUserT.setUserPhone(userInfoModel.getPhone());
       moocUserT.setEmail(userInfoModel.getEmail());
       moocUserT.setAddress(userInfoModel.getAddress());
       moocUserT.setBeginTime(time2Date(userInfoModel.getBeginTime()));
       moocUserT.setBiography(userInfoModel.getBiography());
       moocUserT.setBirthday(userInfoModel.getBirthday());
       moocUserT.setHeadUrl(userInfoModel.getHeadAddress());
       moocUserT.setLifeState(Integer.parseInt(userInfoModel.getLifeState()));
       moocUserT.setUpdateTime(time2Date(userInfoModel.getUpdateTime()));
       moocUserT.setUserSex(userInfoModel.getSex());
        //将数据存入数据库
        Integer isSuccess = moocUserTMapper.updateById(moocUserT);
        if(isSuccess >0){
            //按照Id将用户信息查出来
            UserInfoModel userInfo = getUserInfo(moocUserT.getUuid());
            return userInfo;
        }else {
            //返回前端
            return userInfoModel;
        }
    }

    //获取userInfoModel(数据放入获取userInfoModel)
    private UserInfoModel do2UserInfo(MoocUserT moocUserT) {
        UserInfoModel userInfoModel = new UserInfoModel();

        userInfoModel.setUuid(moocUserT.getUuid());
        userInfoModel.setAddress(moocUserT.getAddress());
        userInfoModel.setBiography(moocUserT.getBiography());
        userInfoModel.setBirthday(moocUserT.getBirthday());
        userInfoModel.setBeginTime(moocUserT.getBeginTime().getTime());
        userInfoModel.setEmail(moocUserT.getEmail());
        userInfoModel.setHeadAddress(moocUserT.getHeadUrl());
        userInfoModel.setLifeState("" + moocUserT.getLifeState());
        userInfoModel.setNickname(moocUserT.getNickName());
        userInfoModel.setPhone(moocUserT.getUserPhone());
        userInfoModel.setSex(moocUserT.getUserSex());
        userInfoModel.setUpdateTime(moocUserT.getUpdateTime().getTime());
        userInfoModel.setUsername(moocUserT.getUserName());

        return userInfoModel;
    }

    //获取Date类型(long传入Date)
    private Date time2Date(long time){
        Date date = new Date(time);
        return date;
    }
}
