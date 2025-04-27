package org.example;

import org.apache.flink.table.functions.ScalarFunction;

public class Base64Encoder extends ScalarFunction {

    public String eval(String input) {
        if (input == null) {
            return null;
        }
        // 使用 Java 内置的 Base64 编码器
        return java.util.Base64.getEncoder().encodeToString(input.getBytes());
    }
}
