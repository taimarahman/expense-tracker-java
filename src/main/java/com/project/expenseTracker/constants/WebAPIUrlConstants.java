package com.project.expenseTracker.constants;

public class WebAPIUrlConstants {
    // Base API URL
    public static final String BASE_API_URL = "/api";

    // User-related APIs
    public static final String USER_API = BASE_API_URL + "/user";
    public static final String USER_REGISTER_API = "/register";
    public static final String USER_LOGIN_API = "/login";
    public static final String USER_PROFILE_UPDATE_API = "/{username}/update";
    public static final String USER_PROFILE_INFO_API = "/{username}/user-info";

    // Expense-related APIs
    public static final String EXPENSE_API = BASE_API_URL + "/expense";
    public static final String EXPENSE_CREATE_API = "/create";
    public static final String EXPENSE_UPDATE_API = "/update";
    public static final String EXPENSE_LIST_API = "/list";

    // Report-related APIs
    public static final String REPORT_API = BASE_API_URL + "/report";
    public static final String REPORT_GENERATE_API = REPORT_API + "/generate";
    public static final String REPORT_DOWNLOAD_API = REPORT_API + "/download";

}
