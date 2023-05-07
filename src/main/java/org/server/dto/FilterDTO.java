package org.server.dto;

public class FilterDTO {

    private String filterType;
    private String filterKeyword;

    public FilterDTO() {
    }

    public FilterDTO(String filterType, String filterCriteria) {
        this.filterType = filterType;
        this.filterKeyword = filterCriteria;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public String getFilterKeyword() {
        return filterKeyword;
    }

    public void setFilterKeyword(String filterCriteria) {
        this.filterKeyword = filterCriteria;
    }
}
