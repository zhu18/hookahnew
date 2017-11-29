package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.OrderInvoice;
import com.jusfoun.hookah.core.generic.GenericDao;

public interface OrderInvoiceMapper extends GenericDao<OrderInvoice> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_invoice
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_invoice
     *
     * @mbggenerated
     */
    int insert(OrderInvoice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_invoice
     *
     * @mbggenerated
     */
    int insertSelective(OrderInvoice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_invoice
     *
     * @mbggenerated
     */
    OrderInvoice selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_invoice
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(OrderInvoice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_invoice
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(OrderInvoice record);
}