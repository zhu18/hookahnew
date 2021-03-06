package com.jusfoun.hookah.core.domain;

import com.jusfoun.hookah.core.generic.GenericModel;
import com.sun.javafx.beans.IDProperty;

import javax.persistence.Id;
import java.util.Date;

public class UserDetail extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_detail.user_id
     *
     * @mbggenerated
     */
    @Id
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_detail.real_name
     *
     * @mbggenerated
     */
    private String realName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_detail.gender
     *
     * @mbggenerated
     */
    private String gender;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_detail.birthday
     *
     * @mbggenerated
     */
    private Date birthday;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_detail.card_num
     *
     * @mbggenerated
     */
    private String cardNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_detail.address
     *
     * @mbggenerated
     */
    private String address;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_detail.zipcode
     *
     * @mbggenerated
     */
    private String zipcode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_detail.fax
     *
     * @mbggenerated
     */
    private String fax;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_detail.education
     *
     * @mbggenerated
     */
    private String education;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_detail.mobile
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_detail.email
     *
     * @mbggenerated
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_detail.tel_num
     *
     * @mbggenerated
     */
    private String telNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_detail.nation
     *
     * @mbggenerated
     */
    private String nation;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_detail.company
     *
     * @mbggenerated
     */
    private String company;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_detail.c_adress
     *
     * @mbggenerated
     */
    private String cAdress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_detail.c_tel
     *
     * @mbggenerated
     */
    private String cTel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_detail.c_zip
     *
     * @mbggenerated
     */
    private String cZip;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_detail.family
     *
     * @mbggenerated
     */
    private String family;
    private String identityCardFrontPath;
    private String identityCardReversePath;
    private Byte isAuth;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_detail.add_time
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_detail.last_updated_time
     *
     * @mbggenerated
     */
    private Date lastUpdatedTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_detail.version
     *
     * @mbggenerated
     */
    private Integer version;


    public String getIdentityCardFrontPath() {
        return identityCardFrontPath;
    }

    public void setIdentityCardFrontPath(String identityCardFrontPath) {
        this.identityCardFrontPath = identityCardFrontPath;
    }

    public String getIdentityCardReversePath() {
        return identityCardReversePath;
    }

    public void setIdentityCardReversePath(String identityCardReversePath) {
        this.identityCardReversePath = identityCardReversePath;
    }

    public Byte getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Byte isAuth) {
        this.isAuth = isAuth;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_detail.user_id
     *
     * @return the value of user_detail.user_id
     *
     * @mbggenerated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_detail.user_id
     *
     * @param userId the value for user_detail.user_id
     *
     * @mbggenerated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_detail.real_name
     *
     * @return the value of user_detail.real_name
     *
     * @mbggenerated
     */
    public String getRealName() {
        return realName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_detail.real_name
     *
     * @param realName the value for user_detail.real_name
     *
     * @mbggenerated
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_detail.gender
     *
     * @return the value of user_detail.gender
     *
     * @mbggenerated
     */
    public String getGender() {
        return gender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_detail.gender
     *
     * @param gender the value for user_detail.gender
     *
     * @mbggenerated
     */
    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_detail.birthday
     *
     * @return the value of user_detail.birthday
     *
     * @mbggenerated
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_detail.birthday
     *
     * @param birthday the value for user_detail.birthday
     *
     * @mbggenerated
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_detail.card_num
     *
     * @return the value of user_detail.card_num
     *
     * @mbggenerated
     */
    public String getCardNum() {
        return cardNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_detail.card_num
     *
     * @param cardNum the value for user_detail.card_num
     *
     * @mbggenerated
     */
    public void setCardNum(String cardNum) {
        this.cardNum = cardNum == null ? null : cardNum.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_detail.address
     *
     * @return the value of user_detail.address
     *
     * @mbggenerated
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_detail.address
     *
     * @param address the value for user_detail.address
     *
     * @mbggenerated
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_detail.zipcode
     *
     * @return the value of user_detail.zipcode
     *
     * @mbggenerated
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_detail.zipcode
     *
     * @param zipcode the value for user_detail.zipcode
     *
     * @mbggenerated
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode == null ? null : zipcode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_detail.fax
     *
     * @return the value of user_detail.fax
     *
     * @mbggenerated
     */
    public String getFax() {
        return fax;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_detail.fax
     *
     * @param fax the value for user_detail.fax
     *
     * @mbggenerated
     */
    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_detail.education
     *
     * @return the value of user_detail.education
     *
     * @mbggenerated
     */
    public String getEducation() {
        return education;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_detail.education
     *
     * @param education the value for user_detail.education
     *
     * @mbggenerated
     */
    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_detail.mobile
     *
     * @return the value of user_detail.mobile
     *
     * @mbggenerated
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_detail.mobile
     *
     * @param mobile the value for user_detail.mobile
     *
     * @mbggenerated
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_detail.email
     *
     * @return the value of user_detail.email
     *
     * @mbggenerated
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_detail.email
     *
     * @param email the value for user_detail.email
     *
     * @mbggenerated
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_detail.tel_num
     *
     * @return the value of user_detail.tel_num
     *
     * @mbggenerated
     */
    public String getTelNum() {
        return telNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_detail.tel_num
     *
     * @param telNum the value for user_detail.tel_num
     *
     * @mbggenerated
     */
    public void setTelNum(String telNum) {
        this.telNum = telNum == null ? null : telNum.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_detail.nation
     *
     * @return the value of user_detail.nation
     *
     * @mbggenerated
     */
    public String getNation() {
        return nation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_detail.nation
     *
     * @param nation the value for user_detail.nation
     *
     * @mbggenerated
     */
    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_detail.company
     *
     * @return the value of user_detail.company
     *
     * @mbggenerated
     */
    public String getCompany() {
        return company;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_detail.company
     *
     * @param company the value for user_detail.company
     *
     * @mbggenerated
     */
    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_detail.c_adress
     *
     * @return the value of user_detail.c_adress
     *
     * @mbggenerated
     */
    public String getcAdress() {
        return cAdress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_detail.c_adress
     *
     * @param cAdress the value for user_detail.c_adress
     *
     * @mbggenerated
     */
    public void setcAdress(String cAdress) {
        this.cAdress = cAdress == null ? null : cAdress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_detail.c_tel
     *
     * @return the value of user_detail.c_tel
     *
     * @mbggenerated
     */
    public String getcTel() {
        return cTel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_detail.c_tel
     *
     * @param cTel the value for user_detail.c_tel
     *
     * @mbggenerated
     */
    public void setcTel(String cTel) {
        this.cTel = cTel == null ? null : cTel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_detail.c_zip
     *
     * @return the value of user_detail.c_zip
     *
     * @mbggenerated
     */
    public String getcZip() {
        return cZip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_detail.c_zip
     *
     * @param cZip the value for user_detail.c_zip
     *
     * @mbggenerated
     */
    public void setcZip(String cZip) {
        this.cZip = cZip == null ? null : cZip.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_detail.family
     *
     * @return the value of user_detail.family
     *
     * @mbggenerated
     */
    public String getFamily() {
        return family;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_detail.family
     *
     * @param family the value for user_detail.family
     *
     * @mbggenerated
     */
    public void setFamily(String family) {
        this.family = family == null ? null : family.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_detail.add_time
     *
     * @return the value of user_detail.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_detail.add_time
     *
     * @param addTime the value for user_detail.add_time
     *
     * @mbggenerated
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_detail.last_updated_time
     *
     * @return the value of user_detail.last_updated_time
     *
     * @mbggenerated
     */
    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_detail.last_updated_time
     *
     * @param lastUpdatedTime the value for user_detail.last_updated_time
     *
     * @mbggenerated
     */
    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_detail.version
     *
     * @return the value of user_detail.version
     *
     * @mbggenerated
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_detail.version
     *
     * @param version the value for user_detail.version
     *
     * @mbggenerated
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
}