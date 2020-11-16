package com.dhj.demo.business.aop;

public enum TimingType {
    MS("毫秒",1000000L),S("秒",1000000000L),NS("纳秒",1L);

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getTypeRate() {
        return typeRate;
    }

    public void setTypeRate(Long typeRate) {
        this.typeRate = typeRate;
    }

    private String typeName;
    private Long typeRate;

    TimingType(String typeName, Long typeRate) {
        this.typeName = typeName;
        this.typeRate = typeRate;
    }
}
