package org.example;

public class Test {
    public static void main(String[] args) {
        String str = "ffsa7f8safdsgds97fds";
        if (isContainNumber(str)) {
            System.out.println("包含数字");
        } else {
            System.out.println("不包含数字");
        }

        }

/**
 * 判断一个字符串是否包含数字
 * @param str 需要进行判断的字符串
 * @return 返回true表示字符串中包含数字，返回false表示字符串中不包含数字。
 */
public static boolean isContainNumber(String str) {
    // 判断字符串是否为空，若为空则认为不包含数字
    if (str == null || str.length() == 0) {
        return true;
    }
    // 遍历字符串中的每个字符，检查是否有数字
    for (int i = 0; i < str.length(); i++) {
        if (Character.isDigit(str.charAt(i))) {
            return true;
        }
    }
    // 字符串中没有数字
    return false;
}



}
