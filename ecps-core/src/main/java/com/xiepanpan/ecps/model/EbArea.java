package com.xiepanpan.ecps.model;

public class EbArea {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EB_AREA.AREA_ID
     *
     * @mbggenerated Fri Nov 16 13:03:13 CST 2018
     */
    private Long areaId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EB_AREA.PARENT_ID
     *
     * @mbggenerated Fri Nov 16 13:03:13 CST 2018
     */
    private Long parentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EB_AREA.AREA_NAME
     *
     * @mbggenerated Fri Nov 16 13:03:13 CST 2018
     */
    private String areaName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column EB_AREA.AREA_LEVEL
     *
     * @mbggenerated Fri Nov 16 13:03:13 CST 2018
     */
    private Short areaLevel;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EB_AREA.AREA_ID
     *
     * @return the value of EB_AREA.AREA_ID
     *
     * @mbggenerated Fri Nov 16 13:03:13 CST 2018
     */
    public Long getAreaId() {
        return areaId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EB_AREA.AREA_ID
     *
     * @param areaId the value for EB_AREA.AREA_ID
     *
     * @mbggenerated Fri Nov 16 13:03:13 CST 2018
     */
    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EB_AREA.PARENT_ID
     *
     * @return the value of EB_AREA.PARENT_ID
     *
     * @mbggenerated Fri Nov 16 13:03:13 CST 2018
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EB_AREA.PARENT_ID
     *
     * @param parentId the value for EB_AREA.PARENT_ID
     *
     * @mbggenerated Fri Nov 16 13:03:13 CST 2018
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EB_AREA.AREA_NAME
     *
     * @return the value of EB_AREA.AREA_NAME
     *
     * @mbggenerated Fri Nov 16 13:03:13 CST 2018
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EB_AREA.AREA_NAME
     *
     * @param areaName the value for EB_AREA.AREA_NAME
     *
     * @mbggenerated Fri Nov 16 13:03:13 CST 2018
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column EB_AREA.AREA_LEVEL
     *
     * @return the value of EB_AREA.AREA_LEVEL
     *
     * @mbggenerated Fri Nov 16 13:03:13 CST 2018
     */
    public Short getAreaLevel() {
        return areaLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column EB_AREA.AREA_LEVEL
     *
     * @param areaLevel the value for EB_AREA.AREA_LEVEL
     *
     * @mbggenerated Fri Nov 16 13:03:13 CST 2018
     */
    public void setAreaLevel(Short areaLevel) {
        this.areaLevel = areaLevel;
    }
}