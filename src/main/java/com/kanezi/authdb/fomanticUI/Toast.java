package com.kanezi.authdb.fomanticUI;

public record Toast(String clazz, String title, String message) {
    enum ToastClass {
        success, warning, error
    }

    public static Toast success(String title, String message) {
        return new Toast(ToastClass.success.name(), title, message);
    }

    public static Toast warning(String title, String message) {
        return new Toast(ToastClass.warning.name(), title, message);
    }

    public static Toast error(String title, String message) {
        return new Toast(ToastClass.error.name(), title, message);
    }
}
