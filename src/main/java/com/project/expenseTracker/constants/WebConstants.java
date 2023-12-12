package com.project.expenseTracker.constants;

public class WebConstants {
    // Base API URL
    public static final String BASE_API_URL = "https://localhost/api/";

    // User-related APIs
    public static final String USER_API = BASE_API_URL + "user";
    public static final String USER_REGISTER_API = USER_API + "/register";
    public static final String USER_LOGIN_API = USER_API + "/login";

    // Expense-related APIs
    public static final String EXPENSE_API = BASE_API_URL + "expense";
    public static final String EXPENSE_CREATE_API = EXPENSE_API + "/create";
    public static final String EXPENSE_LIST_API = EXPENSE_API + "/list";

    // Report-related APIs
    public static final String REPORT_API = BASE_API_URL + "report";
    public static final String REPORT_GENERATE_API = REPORT_API + "/generate";
    public static final String REPORT_DOWNLOAD_API = REPORT_API + "/download";

}
