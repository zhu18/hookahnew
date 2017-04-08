package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.PayCore;
import com.jusfoun.hookah.core.domain.vo.PayCoreVo;
import com.jusfoun.hookah.core.domain.vo.PayVo;
import com.jusfoun.hookah.core.domain.vo.RechargeVo;
import com.jusfoun.hookah.core.generic.GenericService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 支付接口
 */
public interface PayCoreService extends GenericService<PayCore,String> {

	/**根据订单Id查询支付信息
	 * @param orderId
	 * @return
	 */
	public PayCore findPayCoreByOrderId(Integer orderId);

	/**处理请求参数
	 * @param
	 * @return
	 */
	public String buildRequestParams(PayVo payVo);

	/**更新支付状态
	 * @param paied
	 * @throws Exception
	 */
	public void updatePayCore(PayCore paied) throws Exception;

	public PayCore findPayCoreByOrderSn(String orderSn);

	/**支付
	 * @param orderId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String doPay(Integer orderId, String userId) throws Exception;

	public Pagination<PayCore> findPageByVo(PayCoreVo payCoreVo, int pageNo, int pageSize);

	public List<PayCore> findListByVo(PayCoreVo payCoreVo);

	/**充值
	 * @param account
	 * @param money
	 */
	public void recharge(String account, String money);

	public Map<String, String> findUsrAccount(String userId);

	/**更新对账状态(1:已对账,0:未对账)
	 * @param pay
	 */
	public void updateCheckStatus(PayCore pay);

	public Pagination<PayCore> findRechargeList(RechargeVo vo, Integer pageNum,
												Integer pageSize);

	public boolean verifyAlipay(Map<String, String> request);

	/**开通银联支付
	 * @param orderId
	 * @param accNo
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String openUnionpay(Integer orderId, String accNo, String userId) throws Exception;

	/**银联验签
	 * @param requestParams
	 * @return
	 */
	public boolean verifyUnionpay(Map<String, String> requestParams);

	/**根据订单号查询token
	 * @param orderSn
	 * @return
	 */
	public String getTokenByOrderSn(String orderSn);

	/**银联支付时发送短信验证码
	 * @param orderSn
	 * @param accNo
	 * @param amount
	 * @param userId
	 */
	public String unionpaySendSMS(String orderSn, String accNo, BigDecimal amount, String userId);

	/**银联支付付款
	 * @param payVo
	 * @param orderId
	 * @param userId
	 * @param accNo
	 * @param smsCode
	 * @return
	 * @throws Exception
	 */
	public String unionpayConsume(PayVo payVo, Integer orderId, String userId, String accNo, String smsCode) throws Exception;

	/**解除绑定
	 * @param orderSn
	 * @param accNo
	 * @param userId
	 * @return
	 */
	public String deleteAccno(String orderSn, String accNo, String userId);


}
