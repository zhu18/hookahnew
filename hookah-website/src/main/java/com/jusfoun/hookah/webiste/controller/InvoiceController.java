package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Invoice;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;import java.util.List;

/**
 * 发票
 * @author zhanghanqing
 * @created 2016年7月7日
 */
@RestController
@RequestMapping("/invoice")
public class InvoiceController{
	private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);
	@Resource
	private InvoiceService service;

//	@Resource
//	private UserInvoiceLatestService userInvoiceLatestService;
	/**
	 * 插入发票
	 * @param invoice
	 * @return
	 */
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public ReturnData insert(Invoice invoice, HttpSession session){
		try{
			invoice.setUserId(getUserId(session));
			invoice = service.insert(invoice);
			return ReturnData.success(invoice);
		}catch(Exception e){
			logger.error("插入错误",e);
			return ReturnData.error("系统异常:"+e.getMessage());
		}
	}

	/**
	 * 查询发票
	 * @param invoice
	 * @return
	 */
//	@RequestMapping(value="/list",method=RequestMethod.GET)
//	public ReturnData list(Map paramMap,HttpSession session){
//		try{
//			invoice.setUserId(getUserId(session));
//			List<Invoice> list = service.sele(invoice);
//			ReturnData.success(list);
//		}catch(HookahException e){
//			ReturnData.error(e.getMessage());
//		}catch(Exception e){
//			logger.error("查询错误",e);
//			ReturnData.error("系统异常");
//		}
//		return ReturnData.fail();
//	}

	/**
	 * 修改发票
	 * @param invoice
	 * @return
	 */
	@RequestMapping(value = "/update",method=RequestMethod.POST)
	public ReturnData update(Invoice invoice,HttpSession session){
		try{
			invoice.setUserId(getUserId(session));
			service.updateByIdSelective(invoice);
			return ReturnData.success();
		}catch(Exception e){
			logger.error("修改错误",e);
			ReturnData.error("系统异常:"+e.getMessage());
		}
		return ReturnData.fail();
	}

	/**
	 * 删除发票
	 * @param invoiceId
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public ReturnData delete(String invoiceId,HttpSession session){
		try{
			service.delete(invoiceId);
			return ReturnData.success();
		}catch(Exception e){
			logger.error("删除错误",e);
			ReturnData.error("系统异常"+e.getMessage());
		}
		return ReturnData.fail();
	}

	/**
	 * 修改最近选择的发票
	 * @param user
	 * @return
	 */
//	@RequestMapping(value = "/updateLatest",method=RequestMethod.POST)
//	public ReturnData updateLatest(UserInvoiceLatest userInvoiceLatest,HttpSession session){
//		try{
//			userInvoiceLatest.setUserId(getUserId(session));
//			userInvoiceLatestService.updateLatest(userInvoiceLatest);
//		}catch(HookahException e){
//			ReturnData.error(e.getMessage());
//		}catch(Exception e){
//			logger.error("修改错误",e);
//			ReturnData.error("系统异常");
//		}
//
//		return result;
//	}

	/**
	 * 详情
	 * @param user
	 * @return
	 */
//	@RequestMapping(value = "/latestdetail",method=RequestMethod.GET)
//	public ReturnData latestdetail(HttpSession session){
//		try{
//			UserInvoiceLatest userInvoiceLatest = userInvoiceLatestService.latestInvoice(getUserId(session));
//			ReturnData.success(userInvoiceLatest);
//
////			UserInvoiceLatest t = new UserInvoiceLatest();
////			t.setUserId(getUserId(session));
////			List<UserInvoiceLatest> list = userInvoiceLatestService.list(t );
////			if(list!=null&&list.size()>0){
////				ReturnData.success(list.get(0));
////			}
//		}catch(HookahException e){
//			ReturnData.error(e.getMessage());
//		}catch(Exception e){
//			logger.error("详情",e);
//			ReturnData.error("系统异常");
//		}
//
//		return result;
//	}


	/**
	 * 详情
	 * @param session
	 * @return
	 */
//	@RequestMapping(value = "/latestInvoice",method=RequestMethod.GET)
//	public ReturnData latestInvoice(HttpSession session){
//		try{
//			UserInvoiceLatest userInvoiceLatest = userInvoiceLatestService.detail(getUserId(session));
//			ReturnData.success(userInvoiceLatest);
//
////			UserInvoiceLatest t = new UserInvoiceLatest();
////			t.setUserId(getUserId(session));
////			List<UserInvoiceLatest> list = userInvoiceLatestService.list(t );
////			if(list!=null&&list.size()>0){
////				ReturnData.success(list.get(0));
////			}
//		}catch(HookahException e){
//			ReturnData.error(e.getMessage());
//		}catch(Exception e){
//			logger.error("详情",e);
//			ReturnData.error("系统异常");
//		}
//
//		return result;
//	}

	public String getUserId(HttpSession session){
		return "";
	}
}
