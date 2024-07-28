package com.shop.shared.enums;

public enum ErrorMessagesEnum {
    NO_PRODUCTS_FOUND("محصولی یافت نشد"),
    NO_CATEGORIES_FOUND("دسته بندی یافت نشد"),
    NO_COMMENT_FOUND("کامنت یافت نشد"),
    NO_ORDER_FOUND("سفارش یافت نشد"),
    EMAIL_ALREADY_REGISTERED("ایمیل وارد شده قبلا قبت نام کرده است"),
    PHONE_ALREADY_REGISTERED("شماره همرا وارد شده قبلا قبت نام کرده است"),
    EMAIL_OR_PHONE_INVALID("ایمیل یا شماره همراه اشتباه است"),
    PASSWORD_INVALID("پسوورد اشتباه است"),
    NO_USERS_FOUND("کاربری یافت نشد");

    ErrorMessagesEnum(String status) {
    }
}
