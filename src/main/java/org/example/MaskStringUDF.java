package org.example;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class MaskStringUDF extends UDF {

    // 遮掩字符串函数，保留前后各4个字符，中间用星号代替
    public Text evaluate(Text input) {
        if (input == null) {
            return null;
        }

        String str = input.toString();
        int length = str.length();
        int maskLength = length > 8 ? length - 8 : 0; // 如果字符串长度大于8，则遮掩中间部分

        StringBuilder maskedStr = new StringBuilder();
        maskedStr.append(str.substring(0, 4)); // 添加前4个字符
        for (int i = 0; i < maskLength; i++) {
            maskedStr.append("*"); // 添加星号
        }
        if (length > 4) {
            maskedStr.append(str.substring(length - 4)); // 添加后4个字符
        }

        return new Text(maskedStr.toString());
    }
}

