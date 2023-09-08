package com.akinnova.BookReviewMav.response;

import java.util.Random;

public class ResponseUtils {
    //The following are HTTP status codes
    public static final String CONTINUE = "100";
    public static final String CONTINUE_MESSAGE = "CONTINUE";
    public static final String SWITCHING = "101";
    public static final String SWITCHING_MESSAGE = "SWITCHING";
    public static final String CHECK_POINT = "103";
    public static final String CHECK_POINT_MESSAGE = "CHECK_POINT";
    public static final String OK = "200";
    public static final String OK_MESSAGE = "Request is valid.";
    public static final String CREATED = "201";
    public static final String CREATED_MESSAGE = "%s has been created.";
    public static final String ACCEPTED = "202";
    public static final String REQUEST_ACCEPTED = "Request has been accepted.";
    public static final String FOUND = "302";
    public static final String FOUND_MESSAGE = "%s was found!";
    public static final String SEE_OTHER = "303";
    public static final String SEE_OTHER_MESSAGE = "Check for more information at FAQ section.";
    public static final String NOT_MODIFIED = "304";
    public static final String NOT_MODIFIED_MESSAGE = "%s was not modified.";
    public static final String SWITCH_PROXY = "306";
    public static final String TEMP_REDIRECT = "307";
    public static final String TEMP_REDIRECT_MESSAGE = "Request has been temporarily redirected.";
    public static final String BAD_REQUEST = "400";
    public static final String BAD_REQUEST_MESSAGE = "Request was badly framed. Enter details in correct format.";
    public static final String UNAUTHORIZED = "401";
    public static final String UNAUTHORIZED_MESSAGE = "This request is unauthorized.";
    public static final String PAYMENT_REQUIRED = "402";
    public static final String PAYMENT_REQUIRED_MESSAGE = "This operation requires payment. Proceed to pay before continuing.";
    public static final String FORBIDDEN = "403";
    public static final String FORBIDDEN_MESSAGE = "This request is forbidden.";
    public static final String NOT_FOUND  = "404";
    public static final String NOT_FOUND_MESSAGE = "%s requested is not found.";
    public static final String NOT_ACCEPTABLE = "406";
    public static final String NOT_ACCEPTABLE_MESSAGE = "This operation is not acceptable.";
    public static final String GONE = "410";
    public static final String GONE_MESSAGE = "GONE";
    public static final String SERVER_ERROR = "500";
    public static final String SERVER_ERROR_MESSAGE = "SERVER ERROR";
    public static final String BAD_GATEWAY = "502";
    public static final String BAD_GATEWAY_MESSAGE = "BAD GATEWAY";
    public static final String TIME_OUT = "504";
    public static final String TIME_OUT_REQUEST = "REQUEST TIME OUT";

    //USER MESSAGES
    public static final String USER_EXISTS = "User with username: %s already exists";
    public static final String USER_ADDED = "New user added successfully";
    public static final String NO_USER_BY_USERNAME = "There is no user by username: %s ";
    public static final String NO_CLIENT_YET = "There are no Clients yet.";
    public static final String NO_SERVICE_PROVIDER_BY_ID = "There is no Service provider with id: ";
    public static final String NO_SERVICE_PROVIDER_YET = "There are no Service Providers yet.";
    public static final String NO_REGULAR_USER_YET = "There are no Regular Users yet.";
    public static final String NO_ADMIN_YET = "There are no Admins yet.";

    public static final String USER_UPDATE_MESSAGE = "User details has been updated successfully.";
    public static final String USER_DELETE_MESSAGE = "User has been deleted.";

    //PROJECT MESSAGES
    public static final String PROJECT_CREATION_MESSAGE = "Project added successfully";
    public static final String NO_PROJECTS = "There are no projects currently";
    public static final String NO_PROJECT_BY_ID = "There are no projects by this id: %s";
    public static final String NO_PROJECT_BY_NAME = "There are no projects by this name: %s";
    public static final String NO_PENDING_PROJECT = "There are no 'pending' projects yet.";
    public static final String NO_STARTED_PROJECT = "There are no 'started' projects yet.";
    public static final String N0_COMPLETED_PROJECT = "There are no 'completed' projects yet.";
    public static final String PROJECT_UPDATE_SUCCESSFUL = "Project updated successfully";
    public static final String PROJECT_DELETED = "Project has been deleted";

    //TRANSACTION MESSAGES
    public static final String SUCCESSFUL_TRANSACTION = "Transaction done successfully";
    public static final String NO_TRANSACTION_WITH_ID = "There are no transaction with id: ";
}
