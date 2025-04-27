package org.example;
// 导入 Flink 的相关类


import org.apache.flink.table.data.StringData;
import org.apache.flink.table.functions.ScalarFunction;
import org.apache.flink.table.annotation.DataTypeHint;


public class ReplaceString extends ScalarFunction {

    @DataTypeHint("STRING")
    public StringData eval(StringData str) {
        String inputStr = str.toString();  // Convert StringData to String
        String resultStr = inputStr.replace("acac", "bbbb");
        return StringData.fromString(resultStr);  // Convert String back to StringData
    }


}