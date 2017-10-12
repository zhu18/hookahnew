package com.jusfoun.hookah.core.domain;

import com.jusfoun.hookah.core.generic.GenericModel;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

public class Region extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column region.id
     *areaProvince

     * @mbggenerated
     */
    @Id
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column region.pid
     *
     * @mbggenerated
     */
    private Long pid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column region.lel
     *
     * @mbggenerated
     */
    private Byte lel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column region.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column region.merger_name
     *
     * @mbggenerated
     */
    private String mergerName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column region.code
     *
     * @mbggenerated
     */
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column region.zip_code
     *
     * @mbggenerated
     */
    private String zipCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column region.region_short
     *
     * @mbggenerated
     */
    private String regionShort;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column region.merger_short_name
     *
     * @mbggenerated
     */
    private String mergerShortName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column region.pinyin
     *
     * @mbggenerated
     */
    private String pinyin;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column region.jianpin
     *
     * @mbggenerated
     */
    private String jianpin;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column region.first
     *
     * @mbggenerated
     */
    private String first;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column region.lng
     *
     * @mbggenerated
     */
    private String lng;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column region.lat
     *
     * @mbggenerated
     */
    private String lat;

    @Transient
    private List<Region> children;


    public List<Region> getChildren() {
        return children;
    }

    public void setChildren(List<Region> children) {
        this.children = children;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column region.id
     *
     * @return the value of region.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column region.id
     *
     * @param id the value for region.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column region.pid
     *
     * @return the value of region.pid
     *
     * @mbggenerated
     */
    public Long getPid() {
        return pid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column region.pid
     *
     * @param pid the value for region.pid
     *
     * @mbggenerated
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column region.lel
     *
     * @return the value of region.lel
     *
     * @mbggenerated
     */
    public Byte getLel() {
        return lel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column region.lel
     *
     * @param lel the value for region.lel
     *
     * @mbggenerated
     */
    public void setLel(Byte lel) {
        this.lel = lel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column region.name
     *
     * @return the value of region.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column region.name
     *
     * @param name the value for region.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column region.merger_name
     *
     * @return the value of region.merger_name
     *
     * @mbggenerated
     */
    public String getMergerName() {
        return mergerName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column region.merger_name
     *
     * @param mergerName the value for region.merger_name
     *
     * @mbggenerated
     */
    public void setMergerName(String mergerName) {
        this.mergerName = mergerName == null ? null : mergerName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column region.code
     *
     * @return the value of region.code
     *
     * @mbggenerated
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column region.code
     *
     * @param code the value for region.code
     *
     * @mbggenerated
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column region.zip_code
     *
     * @return the value of region.zip_code
     *
     * @mbggenerated
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column region.zip_code
     *
     * @param zipCode the value for region.zip_code
     *
     * @mbggenerated
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column region.region_short
     *
     * @return the value of region.region_short
     *
     * @mbggenerated
     */
    public String getRegionShort() {
        return regionShort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column region.region_short
     *
     * @param regionShort the value for region.region_short
     *
     * @mbggenerated
     */
    public void setRegionShort(String regionShort) {
        this.regionShort = regionShort == null ? null : regionShort.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column region.merger_short_name
     *
     * @return the value of region.merger_short_name
     *
     * @mbggenerated
     */
    public String getMergerShortName() {
        return mergerShortName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column region.merger_short_name
     *
     * @param mergerShortName the value for region.merger_short_name
     *
     * @mbggenerated
     */
    public void setMergerShortName(String mergerShortName) {
        this.mergerShortName = mergerShortName == null ? null : mergerShortName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column region.pinyin
     *
     * @return the value of region.pinyin
     *
     * @mbggenerated
     */
    public String getPinyin() {
        return pinyin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column region.pinyin
     *
     * @param pinyin the value for region.pinyin
     *
     * @mbggenerated
     */
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin == null ? null : pinyin.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column region.jianpin
     *
     * @return the value of region.jianpin
     *
     * @mbggenerated
     */
    public String getJianpin() {
        return jianpin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column region.jianpin
     *
     * @param jianpin the value for region.jianpin
     *
     * @mbggenerated
     */
    public void setJianpin(String jianpin) {
        this.jianpin = jianpin == null ? null : jianpin.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column region.first
     *
     * @return the value of region.first
     *
     * @mbggenerated
     */
    public String getFirst() {
        return first;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column region.first
     *
     * @param first the value for region.first
     *
     * @mbggenerated
     */
    public void setFirst(String first) {
        this.first = first == null ? null : first.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column region.lng
     *
     * @return the value of region.lng
     *
     * @mbggenerated
     */
    public String getLng() {
        return lng;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column region.lng
     *
     * @param lng the value for region.lng
     *
     * @mbggenerated
     */
    public void setLng(String lng) {
        this.lng = lng == null ? null : lng.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column region.lat
     *
     * @return the value of region.lat
     *
     * @mbggenerated
     */
    public String getLat() {
        return lat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column region.lat
     *
     * @param lat the value for region.lat
     *
     * @mbggenerated
     */
    public void setLat(String lat) {
        this.lat = lat == null ? null : lat.trim();
    }
}