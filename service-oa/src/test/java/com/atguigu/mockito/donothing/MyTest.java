package com.atguigu.mockito.donothing;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class MyTest {

    @Test
    public void testMockNestedMethod() throws Exception {
        MyClass myClass = new MyClass();

        // 创建模拟对象并屏蔽 B1 和 B2 方法调用
        MyDependency myDependencyMock = Mockito.mock(MyDependency.class);
        MyDependency.BClass bClassMock = Mockito.mock(MyDependency.BClass.class);
        Mockito.doNothing().when(bClassMock).B1();
        Mockito.doNothing().when(bClassMock).B2();
        Mockito.when(myDependencyMock.getB()).thenReturn(bClassMock);

        // 将模拟对象注入到 MyClass 对象中
        myClass.setMyDependency(myDependencyMock);

        // 调用 myMethod 方法并验证返回值
        String result = myClass.myMethod("input");
        Assert.assertEquals("actual result A actual result C", result);
    }

    private static class MyClass {
        private MyDependency myDependency;

        public String myMethod(String input) {
            // 调用 myDependency 对象的方法
            String resultA = myDependency.A();
            String resultC = myDependency.C();
            return resultA + " " + resultC;
        }

        public void setMyDependency(MyDependency myDependency) {
            this.myDependency = myDependency;
        }
    }

    private static class MyDependency {
        public String A() {
            // 调用 BClass 对象的方法
            BClass b = getB();
            b.B1();
            b.B2();
            return "actual result A";
        }

        public String C() {
            // 实际方法体
            return "actual result C";
        }

        public BClass getB() {
            return new BClass();
        }

        public static class BClass {
            public void B1() {
                // 实际方法体
                return;
            }

            public void B2() {
                // 实际方法体
                return;
            }
        }
    }
}
