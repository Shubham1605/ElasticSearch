package com.securenow.hospitallocator.model;
import lombok.Data;


@Data
public class Hospital implements Comparable<Hospital> {
    private Integer srNo;
    private String hospitalCode;
    private String hospitalName;
    private String hospitalAddess;
    private String location;
    private String cityName;
    private String pinCode;
    private String stateName;
    private String hospitalType;
    private String contactNumber;
    private String category;
    private String source;
    private String fax;
    private String zone;
    private String email;
    private String contactPerson;
    private String designation;
    private String contactNo;
    private String providerManager;
    private String zonalHead;
    private String epcr;
    private String networkType;
    private Float score;

    @Override
    public int compareTo(Hospital hospital) {
        return this.getScore().compareTo(hospital.getScore());
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getSrNo() {
        return srNo;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalAddess() {
        return hospitalAddess;
    }

    public void setHospitalAddess(String hospitalAddess) {
        this.hospitalAddess = hospitalAddess;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getHospitalType() {
        return hospitalType;
    }

    public void setHospitalType(String hospitalType) {
        this.hospitalType = hospitalType;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getProviderManager() {
        return providerManager;
    }

    public void setProviderManager(String providerManager) {
        this.providerManager = providerManager;
    }

    public String getZonalHead() {
        return zonalHead;
    }

    public void setZonalHead(String zonalHead) {
        this.zonalHead = zonalHead;
    }

    public String getEpcr() {
        return epcr;
    }

    public void setEpcr(String epcr) {
        this.epcr = epcr;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }
}
