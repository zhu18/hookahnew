package com.jusfoun.hookah.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class Organization extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.org_id
     *
     * @mbggenerated
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orgId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.org_name
     *
     * @mbggenerated
     */
    private String orgName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.account_virtual
     *
     * @mbggenerated
     */
    private String accountVirtual;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.faxes
     *
     * @mbggenerated
     */
    private String faxes;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.org_address
     *
     * @mbggenerated
     */
    private String orgAddress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.industry
     *
     * @mbggenerated
     */
    private String industry;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.region
     *
     * @mbggenerated
     */
    private String region;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.status
     *
     * @mbggenerated
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.tax_code
     *
     * @mbggenerated
     */
    private String taxCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.license_code
     *
     * @mbggenerated
     */
    private String licenseCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.certificate_code
     *
     * @mbggenerated
     */
    private String certificateCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.tax_path
     *
     * @mbggenerated
     */
    private String taxPath;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.license_path
     *
     * @mbggenerated
     */
    private String licensePath;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.certificte_path
     *
     * @mbggenerated
     */
    private String certifictePath;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.is_deleted
     *
     * @mbggenerated
     */
    private Byte isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.add_time
     *
     * @mbggenerated
     */
    @JsonFormat(timezone = "GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.update_time
     *
     * @mbggenerated
     */
    @JsonFormat(timezone = "GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.law_person_name
     *
     * @mbggenerated
     */
    private String lawPersonName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.contact_address
     *
     * @mbggenerated
     */
    private String contactAddress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.org_phone
     *
     * @mbggenerated
     */
    private String orgPhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.zip_code
     *
     * @mbggenerated
     */
    private String zipCode;
    private Byte isAuth;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.website
     *
     * @mbggenerated
     */
    private String website;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.license_expiry_date
     *
     * @mbggenerated
     */
    @JsonFormat(timezone = "GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
    private Date licenseExpiryDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.tax_expiry_date
     *
     * @mbggenerated
     */
    @JsonFormat(timezone = "GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
    private Date taxExpiryDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.org_email
     *
     * @mbggenerated
     */
    private String orgEmail;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.certificte_expiry_date
     *
     * @mbggenerated
     */
    @JsonFormat(timezone = "GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
    private Date certificteExpiryDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.version
     *
     * @mbggenerated
     */
    private Integer version;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column organization.desc
     *
     * @mbggenerated
     */
    private String description;

    private Byte isSupplier;

    private Byte lawPersonCategory;

    private String lawPersonNum;

    private String lawPersonPositivePath;

    private String lawPersonNegativePath;

    private String officeAddress;

    private Long regionId;

    public Byte getIsSupplier() {
        return isSupplier;
    }

    public void setIsSupplier(Byte isSupplier) {
        this.isSupplier = isSupplier;
    }

    public Byte getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Byte isAuth) {
        this.isAuth = isAuth;
    }

    public Byte getLawPersonCategory() {
        return lawPersonCategory;
    }

    public void setLawPersonCategory(Byte lawPersonCategory) {
        this.lawPersonCategory = lawPersonCategory;
    }

    public String getLawPersonNum() {
        return lawPersonNum;
    }

    public void setLawPersonNum(String lawPersonNum) {
        this.lawPersonNum = lawPersonNum;
    }

    public String getLawPersonPositivePath() {
        return lawPersonPositivePath;
    }

    public void setLawPersonPositivePath(String lawPersonPositivePath) {
        this.lawPersonPositivePath = lawPersonPositivePath;
    }

    public String getLawPersonNegativePath() {
        return lawPersonNegativePath;
    }

    public void setLawPersonNegativePath(String lawPersonNegativePath) {
        this.lawPersonNegativePath = lawPersonNegativePath;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.org_id
     *
     * @return the value of organization.org_id
     *
     * @mbggenerated
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.org_id
     *
     * @param orgId the value for organization.org_id
     *
     * @mbggenerated
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.org_name
     *
     * @return the value of organization.org_name
     *
     * @mbggenerated
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.org_name
     *
     * @param orgName the value for organization.org_name
     *
     * @mbggenerated
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.account_virtual
     *
     * @return the value of organization.account_virtual
     *
     * @mbggenerated
     */
    public String getAccountVirtual() {
        return accountVirtual;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.account_virtual
     *
     * @param accountVirtual the value for organization.account_virtual
     *
     * @mbggenerated
     */
    public void setAccountVirtual(String accountVirtual) {
        this.accountVirtual = accountVirtual == null ? null : accountVirtual.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.faxes
     *
     * @return the value of organization.faxes
     *
     * @mbggenerated
     */
    public String getFaxes() {
        return faxes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.faxes
     *
     * @param faxes the value for organization.faxes
     *
     * @mbggenerated
     */
    public void setFaxes(String faxes) {
        this.faxes = faxes == null ? null : faxes.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.org_address
     *
     * @return the value of organization.org_address
     *
     * @mbggenerated
     */
    public String getOrgAddress() {
        return orgAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.org_address
     *
     * @param orgAddress the value for organization.org_address
     *
     * @mbggenerated
     */
    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress == null ? null : orgAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.industry
     *
     * @return the value of organization.industry
     *
     * @mbggenerated
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.industry
     *
     * @param industry the value for organization.industry
     *
     * @mbggenerated
     */
    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.region
     *
     * @return the value of organization.region
     *
     * @mbggenerated
     */
    public String getRegion() {
        return region;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.region
     *
     * @param region the value for organization.region
     *
     * @mbggenerated
     */
    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.status
     *
     * @return the value of organization.status
     *
     * @mbggenerated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.status
     *
     * @param status the value for organization.status
     *
     * @mbggenerated
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.tax_code
     *
     * @return the value of organization.tax_code
     *
     * @mbggenerated
     */
    public String getTaxCode() {
        return taxCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.tax_code
     *
     * @param taxCode the value for organization.tax_code
     *
     * @mbggenerated
     */
    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode == null ? null : taxCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.license_code
     *
     * @return the value of organization.license_code
     *
     * @mbggenerated
     */
    public String getLicenseCode() {
        return licenseCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.license_code
     *
     * @param licenseCode the value for organization.license_code
     *
     * @mbggenerated
     */
    public void setLicenseCode(String licenseCode) {
        this.licenseCode = licenseCode == null ? null : licenseCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.certificate_code
     *
     * @return the value of organization.certificate_code
     *
     * @mbggenerated
     */
    public String getCertificateCode() {
        return certificateCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.certificate_code
     *
     * @param certificateCode the value for organization.certificate_code
     *
     * @mbggenerated
     */
    public void setCertificateCode(String certificateCode) {
        this.certificateCode = certificateCode == null ? null : certificateCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.tax_path
     *
     * @return the value of organization.tax_path
     *
     * @mbggenerated
     */
    public String getTaxPath() {
        return taxPath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.tax_path
     *
     * @param taxPath the value for organization.tax_path
     *
     * @mbggenerated
     */
    public void setTaxPath(String taxPath) {
        this.taxPath = taxPath == null ? null : taxPath.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.license_path
     *
     * @return the value of organization.license_path
     *
     * @mbggenerated
     */
    public String getLicensePath() {
        return licensePath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.license_path
     *
     * @param licensePath the value for organization.license_path
     *
     * @mbggenerated
     */
    public void setLicensePath(String licensePath) {
        this.licensePath = licensePath == null ? null : licensePath.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.certificte_path
     *
     * @return the value of organization.certificte_path
     *
     * @mbggenerated
     */
    public String getCertifictePath() {
        return certifictePath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.certificte_path
     *
     * @param certifictePath the value for organization.certificte_path
     *
     * @mbggenerated
     */
    public void setCertifictePath(String certifictePath) {
        this.certifictePath = certifictePath == null ? null : certifictePath.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.is_deleted
     *
     * @return the value of organization.is_deleted
     *
     * @mbggenerated
     */
    public Byte getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.is_deleted
     *
     * @param isDeleted the value for organization.is_deleted
     *
     * @mbggenerated
     */
    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.add_time
     *
     * @return the value of organization.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.add_time
     *
     * @param addTime the value for organization.add_time
     *
     * @mbggenerated
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.update_time
     *
     * @return the value of organization.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.update_time
     *
     * @param updateTime the value for organization.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.law_person_name
     *
     * @return the value of organization.law_person_name
     *
     * @mbggenerated
     */
    public String getLawPersonName() {
        return lawPersonName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.law_person_name
     *
     * @param lawPersonName the value for organization.law_person_name
     *
     * @mbggenerated
     */
    public void setLawPersonName(String lawPersonName) {
        this.lawPersonName = lawPersonName == null ? null : lawPersonName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.contact_address
     *
     * @return the value of organization.contact_address
     *
     * @mbggenerated
     */
    public String getContactAddress() {
        return contactAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.contact_address
     *
     * @param contactAddress the value for organization.contact_address
     *
     * @mbggenerated
     */
    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress == null ? null : contactAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.org_phone
     *
     * @return the value of organization.org_phone
     *
     * @mbggenerated
     */
    public String getOrgPhone() {
        return orgPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.org_phone
     *
     * @param orgPhone the value for organization.org_phone
     *
     * @mbggenerated
     */
    public void setOrgPhone(String orgPhone) {
        this.orgPhone = orgPhone == null ? null : orgPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.zip_code
     *
     * @return the value of organization.zip_code
     *
     * @mbggenerated
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.zip_code
     *
     * @param zipCode the value for organization.zip_code
     *
     * @mbggenerated
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.website
     *
     * @return the value of organization.website
     *
     * @mbggenerated
     */
    public String getWebsite() {
        return website;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.website
     *
     * @param website the value for organization.website
     *
     * @mbggenerated
     */
    public void setWebsite(String website) {
        this.website = website == null ? null : website.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.license_expiry_date
     *
     * @return the value of organization.license_expiry_date
     *
     * @mbggenerated
     */
    public Date getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.license_expiry_date
     *
     * @param licenseExpiryDate the value for organization.license_expiry_date
     *
     * @mbggenerated
     */
    public void setLicenseExpiryDate(Date licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.tax_expiry_date
     *
     * @return the value of organization.tax_expiry_date
     *
     * @mbggenerated
     */
    public Date getTaxExpiryDate() {
        return taxExpiryDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.tax_expiry_date
     *
     * @param taxExpiryDate the value for organization.tax_expiry_date
     *
     * @mbggenerated
     */
    public void setTaxExpiryDate(Date taxExpiryDate) {
        this.taxExpiryDate = taxExpiryDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.org_email
     *
     * @return the value of organization.org_email
     *
     * @mbggenerated
     */
    public String getOrgEmail() {
        return orgEmail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.org_email
     *
     * @param orgEmail the value for organization.org_email
     *
     * @mbggenerated
     */
    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail == null ? null : orgEmail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.certificte_expiry_date
     *
     * @return the value of organization.certificte_expiry_date
     *
     * @mbggenerated
     */
    public Date getCertificteExpiryDate() {
        return certificteExpiryDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.certificte_expiry_date
     *
     * @param certificteExpiryDate the value for organization.certificte_expiry_date
     *
     * @mbggenerated
     */
    public void setCertificteExpiryDate(Date certificteExpiryDate) {
        this.certificteExpiryDate = certificteExpiryDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.version
     *
     * @return the value of organization.version
     *
     * @mbggenerated
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.version
     *
     * @param version the value for organization.version
     *
     * @mbggenerated
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column organization.description
     *
     * @return the value of organization.description
     *
     * @mbggenerated
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column organization.description
     *
     * @param description the value for organization.description
     *
     * @mbggenerated
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}