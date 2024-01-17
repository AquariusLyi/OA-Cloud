package com.atguigu.mapstruct;

public class testRun {
    public static void main(String[] args) {
        TestEndVo assginMenuVo = new TestEndVo();
        assginMenuVo.setStatusEnd("0");
        assginMenuVo.setCertTypeEnd("1");
        TestVo assginRoleVo = ApplyRecordMapStruct.INSTANCE.dOToVO(assginMenuVo);
        System.out.println();
    }
}
