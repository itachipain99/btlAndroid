package com.example.sherwoodsuitesaigon.Model;

public class ErrorAuthentication {
    private String errorEmpty = "Vui lòng điền đầy đủ thông tin";
    private String errorLoginFail = "Sai thông tin đăng nhập";
    private String errorUsernameExist = "Tên tài khoản đã tồn tại";
    private String errorPasswordNotEqual = "Mật khẩu không khớp";
    private String errorConnect = "Lỗi truy cập mạng";


    public String getErrorEmpty() {
        return errorEmpty;
    }

    public String getErrorLoginFail() {
        return errorLoginFail;
    }

    public String getErrorUsernameExist() {
        return errorUsernameExist;
    }

    public String getErrorPasswordNotEqual() {
        return errorPasswordNotEqual;
    }

    public String getErrorConnect() {
        return errorConnect;
    }
}
