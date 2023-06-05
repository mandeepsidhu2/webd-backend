package com.tabber.tabby.constants;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public interface TabbyConstants {
    String WEBSITE = "WEBSITE";
    String RESUME = "RESUME";
    String CUSTOM_LINK = "CUSTOM_LINK";
    String FRONTEND_CONFIG = "FRONTEND_CONFIG";
    String EMAIL_BY= "EMAIL_BY";
    String EMAIL_KEY= "EMAIL_KEY";
    String COUNT_EMAIL_KEY= "COUNT_EMAIL_KEY";
    Integer MAX_EMAILS_PER_KEY= 3;
    Integer RANK_WIDGET_SIZE_LIMIT = 3;
    Integer CONTEST_WIDGET_SIZE_LIMIT = 3;
    Integer PERSONAL_PROJECT_SIZE_LIMIT = 3;
    Integer EMAIL_SENDING_LIMIT = 3;
    Integer EMAIL_HISTORY_STORING_LIMIT = 4;
    String PLAN = "PLAN";
    Integer LITE_PLAN_ID = 0;
    List<String> admins = Arrays.asList("mandeep.sidhu2@gmail.com","vikrant.negi74@gmail.com","jagjeet7136@gmail.com");
}
