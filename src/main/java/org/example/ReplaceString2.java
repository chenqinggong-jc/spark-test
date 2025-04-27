package org.example;
// 导入 Flink 的相关类


import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.data.StringData;
import org.apache.flink.table.functions.ScalarFunction;


public class ReplaceString2 extends ScalarFunction {


    public String eval(String str) {
        String resultStr = str.replace("acac", "bbbb");
        return resultStr;  // Convert String back to StringData
    }


}