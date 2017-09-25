package com.jusfoun.hookah.crowd.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * @author huang lei
 * @date 2017/3/27 下午15:26
 * @desc
 */
public class MySessionListener implements SessionListener {
    @Override
    public void onStart(Session session) {
        System.out.println("会话创建：" + session.getId());
    }

    @Override
    public void onStop(Session session) {
        System.out.println("会话过期：" + session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        System.out.println("会话停止：" + session.getId());
    }
}
