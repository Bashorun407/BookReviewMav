package com.akinnova.BookReviewMav.response;

public class EmailResponse {
    public static final String USER_CREATION_SUBJECT = "Welcome to BookReev";
    public static final String USER_CREATION_MAIL = """
            Dear %s,
            \nWelcome to BookReev app.
            \nYour registration number is: %s. Ensure to keep it safe.
            \n You can continue your registration with this link: www.bookreev.com
            \n \nSee you around!
            """;

    public static final String USER_UPDATE_NOTIFICATION = "Update Notification";
    public static final String USER_UPDATE_MAIL = """
            Dear %s,
            \nYour details have been updated on ProjectReev.
            \nYou verify on: www.bookreev.com
            """;

    public static final String PROJECT_CREATION_SUBJECT = "Project Creation";
    public static final String PROJECT_CREATION_MAIL = """
            This is to notify you that you have created a project named: %s
            \nProject id: %s";
            \nThanks
            """;

    public static final String PROJECT_UPDATE_SUBJECT = "Project Update";
    public static final String PROJECT_UPDATE_MAIL = "This is to notify you that you have updated a project named: %s.\nProject id: %s";
    public static final String PROJECT_SERVICE_UPDATE_MAIL = """
                                Dear %s,
                                \nThis is to notify you that a service provider with username: %s has accepted your project.
                                \nProject title: %s, Project id: %s.
                                \nEnsure to pay the required fee: %s $ so that project execution can commence.
                                \n
                                \nYours Sincerely.
                                """;

    public static final String PROJECT_COMPLETION_MAIL = """
            Dear %s,
            \nThis is to notify you that your project titled: %s, (project id: %s) has now been completed.
            \nEnsure to append your approval or otherwise.
            \n
            \nThank you for your patronage.
            """;

    public static final String PROJECT_COMMENCEMENT_SUBJECT = "Project Commencement Notification";
    public static final String PROJECT_COMMENCEMENT_MAIL = """
            "Dear   %s , %s ."
            \n This is to notify you that you can now commence on the task assigned to you."
            \n Payment will be upon completion and feedback by client."
            \n Best regards.
                        
            """ ;

}
