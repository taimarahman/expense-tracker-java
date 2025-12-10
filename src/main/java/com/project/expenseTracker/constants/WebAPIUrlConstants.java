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
    public static final String CATEGORY_ID_WISE_DETAILS_API = "/{id}";
    public static final String SUBCATEGORY_LIST_API = "/{categoryId}/subcategories";
    public static final String CATEGORY_TREE_API = "/tree";
    public static final String CATEGORY_SUB_DELETE_API = "/{categoryId}/subcategories/delete/{subcategoryId}";



    // Expense-related APIs
    public static final String EXPENSE_API = BASE_API_URL + "/expense";
    public static final String EXPENSE_BY_ID_API = "/{expenseId}";
    public static final String EXPENSE_LIST_API = "/list";

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
