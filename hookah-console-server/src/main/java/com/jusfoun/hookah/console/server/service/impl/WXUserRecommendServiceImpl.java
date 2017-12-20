package com.jusfoun.hookah.console.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.WXUserRecommendMapper;
import com.jusfoun.hookah.core.domain.WxUserRecommend;
import com.jusfoun.hookah.core.domain.jf.JfRecord;
import com.jusfoun.hookah.core.domain.jf.JfRule;
import com.jusfoun.hookah.core.domain.vo.WXUserRecommendVo;
import com.jusfoun.hookah.core.domain.vo.WithdrawVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.CacheService;
import com.jusfoun.hookah.rpc.api.JfRecordService;
import com.jusfoun.hookah.rpc.api.JfRuleService;
import com.jusfoun.hookah.rpc.api.WXUserRecommendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by admin on 2017/9/25.
 */
@Service
public class WXUserRecommendServiceImpl extends GenericServiceImpl<WxUserRecommend, String> implements WXUserRecommendService {

    //每日推荐奖励200积分封顶
    private Integer JF_UP_LIMIT=200;

    //成功邀请一位用户注册认证后，奖励推荐者20积分
    private Integer REWARD_JF_STANDARD=20;

    @Resource
    private WXUserRecommendMapper wxUserRecommendMapper;

    @Resource
    private JfRuleService jfRuleService;

    @Resource
    private JfRecordService jfRecordService;

    @Resource
    CacheService cacheService;

    @Override
    public HashMap<String, Integer> countInviteeAndReward(String userId) {
        return wxUserRecommendMapper.countInviteeAndReward(userId);
    }

    @Override
    public Pagination<WXUserRecommendVo> findRecommendListByCondition(HashMap<String, Object> paramMap) {
        Pagination<WXUserRecommendVo> pagination = new Pagination<>();
        PageHelper.startPage((int)paramMap.get("pageNum"), (int)paramMap.get("pageSize"));   //pageNum为第几页，pageSize为每页数量
        List<WXUserRecommendVo> list = wxUserRecommendMapper.getRecommendList(paramMap);
        //int count = wxUserRecommendMapper.countRecommendList(paramMap);
        PageInfo<WXUserRecommendVo> page = page = new PageInfo<WXUserRecommendVo>(list);
        pagination.setTotalItems(page.getTotal());
        pagination.setPageSize((int)paramMap.get("pageSize"));
        pagination.setCurrentPage((int)paramMap.get("pageNum"));
        pagination.setList(list);
        return pagination;
    }

    /**
     * 用户认证审核通过后，更改wx_user_recommend表对应的状态
     * @param inviteeId 被邀请者Id
     */
    @Override
    public void updateWXUserRecommendIsAuthenticate(String inviteeId) {
        WxUserRecommend recommend =new WxUserRecommend();
        recommend.setInviteeid(inviteeId);
        recommend.setIsauthenticate((byte)1);  //将被邀请人是否认证的状态更改为是
        recommend.setUpdateTime(new Date());
        WxUserRecommend wxUserRecommend = wxUserRecommendMapper.selectByInviteeId(inviteeId);
        if (wxUserRecommend!=null){
            //通过被邀请人Id查询其对应的推荐人当天的总奖励积分
            int sumRewardJfToday=wxUserRecommendMapper.selectSumRewardJfByRecommenderId(wxUserRecommend.getRecommenderid());
            List<Condition> fifters = new ArrayList<>();
            fifters.add(Condition.eq("sn", HookahConstants.JF_2));

            JfRule jfRule = jfRuleService.selectOne(fifters);
            if ( jfRule!=null && sumRewardJfToday < jfRule.getUpperLimit()){    //如果其推荐人当天的总奖励积分小于每日积分上限
                int sum = jfRule.getScore() + sumRewardJfToday;
                //溢出问题：如果当日奖励总和目前为190，再加20，则超出200上限，此时只需加200-190=10即可
                int score=(jfRule.getUpperLimit()-sum) > 0 ? jfRule.getScore() : (jfRule.getUpperLimit()-sumRewardJfToday);
                recommend.setRewardJf(score);
                JfRecord jfRecord=new JfRecord();
                jfRecord.setUserId(wxUserRecommend.getRecommenderid());
                jfRecord.setAction((byte)1);
                jfRecord.setActionDesc(jfRule.getActionDesc());
                jfRecord.setSourceId(jfRule.getSn());
                jfRecord.setScore(score);
                jfRecord.setNote("有效期至" +
                        LocalDate.now().plusYears(HookahConstants.JF_EXPIRE_YEAR)
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                jfRecord.setExpire((byte)0);
                jfRecord.setAddTime(new Date());
                jfRecord.setOperator("System");
                jfRecord.setAddDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")));
                //jfRecordService.insert(jfRecord);
                int n = cacheService.insertAndGetId(jfRecord);
            }
            wxUserRecommendMapper.updateByInviteeidSelective(recommend);
        }
    }


    @Override
    public void updateWXUserRecommendIsDeal(String inviteeId) {
        WxUserRecommend recommend=wxUserRecommendMapper.selectByInviteeId(inviteeId);
        if (recommend!=null && recommend.getIsdeal()==0){
            recommend.setIsdeal((byte)1);  //将被邀请人是否成功交易的状态更改为是
            recommend.setUpdateTime(new Date());
            //wxUserRecommendMapper.updateByPrimaryKeySelective(recommend);
            wxUserRecommendMapper.updateByInviteeidSelective(recommend);
        }
    }

    @Override
    public WXUserRecommendVo findRecommendByRecommenderId(String recommenderId) {

        return wxUserRecommendMapper.selectRecommendByRecommenderId(recommenderId);
    }

    @Override
    public Pagination<WXUserRecommendVo> findRecommendDetailsByRecommenderId(HashMap<String, Object> paramMap) {
        Pagination<WXUserRecommendVo> pagination = new Pagination<>();
        //int startIndex = ((int)paramMap.get("pageNum") - 1) * (int)paramMap.get("pageSize");
        PageHelper.startPage((int)paramMap.get("pageNum"), (int)paramMap.get("pageSize"));   //pageNum为第几页，pageSize为每页数量
        List<WXUserRecommendVo> list = wxUserRecommendMapper.findRecommendDetailsByRecommenderId(paramMap);
        //int count = wxUserRecommendMapper.countRecommendDetailsByRecommenderId(paramMap);
        PageInfo<WXUserRecommendVo> page = page = new PageInfo<WXUserRecommendVo>(list);
        pagination.setTotalItems(page.getTotal());
        pagination.setPageSize((int)paramMap.get("pageSize"));
        pagination.setCurrentPage((int)paramMap.get("pageNum"));
        pagination.setList(list);
        return pagination;
    }

    public static void main(String[] args) {
        List li=new ArrayList();
        li.add("a");
        li.add("b");
        li.add("c");
        System.out.println(li.toString());
        li.set(1,"f");
        System.out.println(li.toString());
    }

}
