package com.atguigu.mockito.spy;


  
class Jerry {  
    public void goHome() {  
        doSomeThingA();  
        doSomeThingB();  
    }  
  
    // real invoke it.  
    public void doSomeThingB() {  
        System.out.println("good day");  
  
    }  
  
    // auto mock method by mockito  
    public void doSomeThingA() {  
        System.out.println("you should not see this message.");  
  
    }

}