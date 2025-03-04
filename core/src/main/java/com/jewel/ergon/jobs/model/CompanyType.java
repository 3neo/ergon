package com.jewel.ergon.jobs.model;

public enum CompanyType {
    CONSULTING("Consulting Firm"),
    BOITEDEVELOPPEMENT("Development Company"),
    STARTUP("Startup"),
    ENTERPRISE("Large Enterprise"),
    NONPROFIT("Non-Profit Organization"),
    FINTECH("Financial Technology Company"),
    ECOMMERCE("E-Commerce Business"),
    HEALTHCARE("Healthcare Provider"),
    EDUCATION("Educational Institution"),
    OTHER("Other Type");

    private final String description;

    CompanyType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static CompanyType fromDescription(String description) {
        for (CompanyType type : CompanyType.values()) {
            if (type.description.equalsIgnoreCase(description)) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name() + " (" + description + ")";
    }
}

