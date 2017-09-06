package com.jusfoun.hookah.oauth2server.config;

import com.jusfoun.hookah.core.common.redis.RedisOperate;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * @author huang lei
 * @date 2017/4/5 下午2:24
 * @desc
 */
public class SessionRedisDao extends CachingSessionDAO {

//    @Autowired
//    private RedisTemplate<Serializable, Session> redisTemplate;
//
//    @Resource(name = "redisTemplate")
//    private ValueOperations<Serializable, Session> valueOps;

    @Autowired
    RedisOperate redisOperate;

    @Override
    protected void doUpdate(Session session) {
//        valueOps.set(session.getId(), session);
        redisOperate.setObject(session.getId().toString(), session, 0);
    }

    @Override
    protected void doDelete(Session session) {
        if (session == null || session.getId() == null) {
            return;
        }
//        redisTemplate.delete(session.getId());
        redisOperate.delObject(session.getId().toString());
    }

    @Override
    protected Serializable doCreate(Session session) {
        String sessionId = generateSessionId(session).toString();
        assignSessionId(session, sessionId);
//        valueOps.set(sessionId, session);
        redisOperate.setObject(sessionId, session, 0);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
//        return valueOps.get(sessionId);
        return (Session) redisOperate.getObject(sessionId.toString());
    }
}
