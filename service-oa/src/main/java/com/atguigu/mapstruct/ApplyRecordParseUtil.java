package com.atguigu.mapstruct;

public class ApplyRecordParseUtil {
    public static String parseCertType(String certType) {
        switch (certType) {
            case "1":
                return "身份证";
            case "2":
                return "港澳台居民居住证";
            default:
                return null;
        }
    }
 
    public static String parseStatus(String status) {
        switch (status) {
            case "0":
                return "待审核";
            case "1":
                return "通过";
            case "2":
                return "驳回";
            default:
                return null;
        }
    }
}