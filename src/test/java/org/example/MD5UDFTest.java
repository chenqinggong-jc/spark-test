package org.example;


import org.apache.hadoop.hive.ql.exec.UDF;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MD5UDFTest {

    @Test
    public void testEvaluate() {
        MD5UDF md5UDF = new MD5UDF();

        // 测试空字符串
        assertEquals(null, md5UDF.evaluate(null));

        // 测试普通字符串
        assertEquals("5d41402abc4b2a76b9719d911017c592", md5UDF.evaluate("hello"));

//        // 测试另一个字符串
//        assertEquals("c93d3bf7a7c4afe94b64e30c2ce39f4f", md5UDF.evaluate("world"));
//
//        // 测试数字字符串
//        assertEquals("5994471abb01112afcc18159f6cc7f13", md5UDF.evaluate("123456"));
    }
}
