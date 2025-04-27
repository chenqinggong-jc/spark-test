package org.example;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Base64EncoderTest {

    @Test
    public void testEval() {
        // 创建 UDF 实例
        Base64Encoder base64Encoder = new Base64Encoder();

        // 测试空输入
//        assertEquals(null, base64Encoder.eval(null));

//        // 测试普通字符串
        assertEquals("SGVsbG8gd29ybGQh", base64Encoder.eval("Hello world!"));
//
//        // 测试空字符串
//        assertEquals("", base64Encoder.eval(""));
//
//        // 测试特殊字符
//        assertEquals("wqFhYg==", base64Encoder.eval("\u0001\u0002"));
//
//        // 测试包含非 ASCII 字符的字符串
//        assertEquals("5L2g5aW9", base64Encoder.eval("你好"));
    }
}
