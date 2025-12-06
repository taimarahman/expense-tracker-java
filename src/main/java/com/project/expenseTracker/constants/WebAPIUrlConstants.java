package com.project.expenseTracker.constants;

public class WebAPIUrlConstants {
    // Base API URL
    public static final String BASE_API_URL = "/api";

    // User-related APIs
    public static final String USER_API = BASE_API_URL + "/user";
    public static final String USER_REGISTER_API = "/register";
    public static final String USER_LOGIN_API = "/login";
    public static final String USER_PROFILE_UPDATE_API = "/{username}/update";
    public static final String USER_PROFILE_INFO_API = "/{username}/profile";


    // Category-related APIs
    public static final String CATEGORY_API = BASE_API_URL + "/categories";
    public static final String CATEGORY_ALL_DETAILS_API = "/all";
    public static final String CATEGORY_ID_WISE_DETAILS_API = "/{id}";
    public static final String CATEGORY_CREATE_API = "/create";
    public static final String CATEGORY_UPDATE_API = "/update/{id}";
    public static final String CATEGORY_DELETE_API = "/DELETE/{id}";
    public static final String CATEGORY_SUB_ADD_API = "/{categoryId}/subcategories/add";
    public static final String CATEGORY_SUB_UPDATE_API = "/{categoryId}/subcategories/update/{subcategoryId}";
    public static final String CATEGORY_SUB_DELETE_API = "/{categoryId}/subcategories/delete/{subcategoryId}";



    // Expense-related APIs
    public static final String EXPENSE_API = BASE_API_URL + "/expense";
    public static final String EXPENSE_CREATE_API = "/add";
    public static final String EXPENSE_UPDATE_API = "/update";
    public static final String EXPENSE_DELETE_API = "/delete/{id}";
    public static final String EXPENSE_LIST_API = "/list";
    public static final String EXPENSE_MONTHLY_LIST_API = "/monthly-list";

//  Income-related APIs
    public static final String INCOME_API = BASE_API_URL + "/income";
    public static final String INCOME_DETAILS_API = "/{incomeId}";
    public static final String INCOME_YEARLY_DETAILS_API = "/year/{year}";

    //  Savings-related APIs
    public static final String SAVINGS_API = BASE_API_URL + "/savings";
    public static final String SAVINGS_BY_ID_API = "/{savingsId}";
    public static final String SAVINGS_YEARLY_DETAILS_API = "/year/{year}";



    // Report-related APIs
    public static final String REPORT_API = BASE_API_URL + "/report";
    public static final String REPORT_GENERATE_API = REPORT_API + "/generate";
    public static final String REPORT_DOWNLOAD_API = REPORT_API + "/download";

}
