package com.securenow.hospitallocator.config;

public class SearchBuilder {
    private static SearchBuilder SEARCH_BUILDER_INSTANCE = null;
    private SearchBuilder() {}
    public static SearchBuilder getInstance() {
        if (SEARCH_BUILDER_INSTANCE == null) {
            SEARCH_BUILDER_INSTANCE = new SearchBuilder();
        }
        return SEARCH_BUILDER_INSTANCE;
    }
}
