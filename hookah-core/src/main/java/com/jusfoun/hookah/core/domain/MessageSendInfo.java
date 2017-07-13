package com.jusfoun.hookah.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class MessageSendInfo extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_send_info.id
     *
     * @mbggenerated
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_send_info.template_id
     *
     * @mbggenerated
     */
    private String templateId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_send_info.send_time
     *
     * @mbggenerated
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_send_info.send_user
     *
     * @mbggenerated
     */
    private String sendUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_send_info.receive_time
     *
     * @mbggenerated
     */
    private Date receiveTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_send_info.receive_user
     *
     * @mbggenerated
     */
    private String receiveUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_send_info.receive_mail
     *
     * @mbggenerated
     */
    private String receiveMail;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_send_info.receive_tel
     *
     * @mbggenerated
     */
    private String receiveTel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_send_info.send_header
     *
     * @mbggenerated
     */
    private String sendHeader;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_send_info.send_content
     *
     * @mbggenerated
     */
    private String sendContent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_send_info.send_type
     *
     * @mbggenerated
     */
    private Byte sendType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_send_info.is_read
     *
     * @mbggenerated
     */
    private Byte isRead;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_send_info.is_success
     *
     * @mbggenerated
     */
    private Byte isSuccess;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_send_info.event_type
     *
     * @mbggenerated
     */
    private String eventType;

    private String businessId;

    private Byte isDelete;

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_send_info.id
     *
     * @return the value of message_send_info.id
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_send_info.id
     *
     * @param id the value for message_send_info.id
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_send_info.template_id
     *
     * @return the value of message_send_info.template_id
     *
     * @mbggenerated
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_send_info.template_id
     *
     * @param templateId the value for message_send_info.template_id
     *
     * @mbggenerated
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_send_info.send_time
     *
     * @return the value of message_send_info.send_time
     *
     * @mbggenerated
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_send_info.send_time
     *
     * @param sendTime the value for message_send_info.send_time
     *
     * @mbggenerated
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_send_info.send_user
     *
     * @return the value of message_send_info.send_user
     *
     * @mbggenerated
     */
    public String getSendUser() {
        return sendUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_send_info.send_user
     *
     * @param sendUser the value for message_send_info.send_user
     *
     * @mbggenerated
     */
    public void setSendUser(String sendUser) {
        this.sendUser = sendUser == null ? null : sendUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_send_info.receive_time
     *
     * @return the value of message_send_info.receive_time
     *
     * @mbggenerated
     */
    public Date getReceiveTime() {
        return receiveTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_send_info.receive_time
     *
     * @param receiveTime the value for message_send_info.receive_time
     *
     * @mbggenerated
     */
    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_send_info.receive_user
     *
     * @return the value of message_send_info.receive_user
     *
     * @mbggenerated
     */
    public String getReceiveUser() {
        return receiveUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_send_info.receive_user
     *
     * @param receiveUser the value for message_send_info.receive_user
     *
     * @mbggenerated
     */
    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser == null ? null : receiveUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_send_info.receive_mail
     *
     * @return the value of message_send_info.receive_mail
     *
     * @mbggenerated
     */
    public String getReceiveMail() {
        return receiveMail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_send_info.receive_mail
     *
     * @param receiveMail the value for message_send_info.receive_mail
     *
     * @mbggenerated
     */
    public void setReceiveMail(String receiveMail) {
        this.receiveMail = receiveMail == null ? null : receiveMail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_send_info.receive_tel
     *
     * @return the value of message_send_info.receive_tel
     *
     * @mbggenerated
     */
    public String getReceiveTel() {
        return receiveTel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_send_info.receive_tel
     *
     * @param receiveTel the value for message_send_info.receive_tel
     *
     * @mbggenerated
     */
    public void setReceiveTel(String receiveTel) {
        this.receiveTel = receiveTel == null ? null : receiveTel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_send_info.send_header
     *
     * @return the value of message_send_info.send_header
     *
     * @mbggenerated
     */
    public String getSendHeader() {
        return sendHeader;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_send_info.send_header
     *
     * @param sendHeader the value for message_send_info.send_header
     *
     * @mbggenerated
     */
    public void setSendHeader(String sendHeader) {
        this.sendHeader = sendHeader == null ? null : sendHeader.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_send_info.send_content
     *
     * @return the value of message_send_info.send_content
     *
     * @mbggenerated
     */
    public String getSendContent() {
        return sendContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_send_info.send_content
     *
     * @param sendContent the value for message_send_info.send_content
     *
     * @mbggenerated
     */
    public void setSendContent(String sendContent) {
        this.sendContent = sendContent == null ? null : sendContent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_send_info.send_type
     *
     * @return the value of message_send_info.send_type
     *
     * @mbggenerated
     */
    public Byte getSendType() {
        return sendType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_send_info.send_type
     *
     * @param sendType the value for message_send_info.send_type
     *
     * @mbggenerated
     */
    public void setSendType(Byte sendType) {
        this.sendType = sendType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_send_info.is_read
     *
     * @return the value of message_send_info.is_read
     *
     * @mbggenerated
     */
    public Byte getIsRead() {
        return isRead;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_send_info.is_read
     *
     * @param isRead the value for message_send_info.is_read
     *
     * @mbggenerated
     */
    public void setIsRead(Byte isRead) {
        this.isRead = isRead;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_send_info.is_success
     *
     * @return the value of message_send_info.is_success
     *
     * @mbggenerated
     */
    public Byte getIsSuccess() {
        return isSuccess;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_send_info.is_success
     *
     * @param isSuccess the value for message_send_info.is_success
     *
     * @mbggenerated
     */
    public void setIsSuccess(Byte isSuccess) {
        this.isSuccess = isSuccess;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_send_info.event_type
     *
     * @return the value of message_send_info.event_type
     *
     * @mbggenerated
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_send_info.event_type
     *
     * @param eventType the value for message_send_info.event_type
     *
     * @mbggenerated
     */
    public void setEventType(String eventType) {
        this.eventType = eventType == null ? null : eventType.trim();
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
}