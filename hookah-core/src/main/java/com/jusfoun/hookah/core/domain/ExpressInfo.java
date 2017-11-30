package com.jusfoun.hookah.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class ExpressInfo extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column express_info.express_id
     *
     * @mbggenerated
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String expressId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column express_info.invoice_id
     *
     * @mbggenerated
     */
    private String invoiceId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column express_info.express_name
     *
     * @mbggenerated
     */
    private String expressName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column express_info.express_no
     *
     * @mbggenerated
     */
    private String expressNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column express_info.add_time
     *
     * @mbggenerated
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column express_info.version
     *
     * @mbggenerated
     */
    private Integer version;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column express_info.express_id
     *
     * @return the value of express_info.express_id
     *
     * @mbggenerated
     */
    public String getExpressId() {
        return expressId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column express_info.express_id
     *
     * @param expressId the value for express_info.express_id
     *
     * @mbggenerated
     */
    public void setExpressId(String expressId) {
        this.expressId = expressId == null ? null : expressId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column express_info.invoice_id
     *
     * @return the value of express_info.invoice_id
     *
     * @mbggenerated
     */
    public String getInvoiceId() {
        return invoiceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column express_info.invoice_id
     *
     * @param invoiceId the value for express_info.invoice_id
     *
     * @mbggenerated
     */
    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId == null ? null : invoiceId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column express_info.express_name
     *
     * @return the value of express_info.express_name
     *
     * @mbggenerated
     */
    public String getExpressName() {
        return expressName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column express_info.express_name
     *
     * @param expressName the value for express_info.express_name
     *
     * @mbggenerated
     */
    public void setExpressName(String expressName) {
        this.expressName = expressName == null ? null : expressName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column express_info.express_no
     *
     * @return the value of express_info.express_no
     *
     * @mbggenerated
     */
    public String getExpressNo() {
        return expressNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column express_info.express_no
     *
     * @param expressNo the value for express_info.express_no
     *
     * @mbggenerated
     */
    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo == null ? null : expressNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column express_info.add_time
     *
     * @return the value of express_info.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column express_info.add_time
     *
     * @param addTime the value for express_info.add_time
     *
     * @mbggenerated
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column express_info.version
     *
     * @return the value of express_info.version
     *
     * @mbggenerated
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column express_info.version
     *
     * @param version the value for express_info.version
     *
     * @mbggenerated
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
}