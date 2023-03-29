package com.atguigu.mockito.spy;

import com.atguigu.common.http.gpt01.HttpUtils;
import com.atguigu.mockito.donothing.MyClassB;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpUtils.class, MyClassB.class})
public class MyTest01 {

    @Test
    public void syp_test() throws Exception {
        List<String> list = new LinkedList<String>();
        List<String> spy = spy(list);
        when(spy.size()).thenReturn(100);

        spy.add("one");
        spy.add("two");

        assertEquals(spy.get(0), "one");
        assertEquals(100, spy.size());
    }
    @Test
    public void mock_test() throws Exception {
        // List<String> list = new LinkedList<String>();
        List<String> listmock = mock(List.class);
        when(listmock.size()).thenReturn(100);
        listmock.add("one");
        listmock.add("two");
        String s = listmock.get(0);
        assertEquals(s, "one");
        assertEquals(100, listmock.size());
    }

}