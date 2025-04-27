package org.example;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UDFTest {

    @Test
    public void testEncryptField() {
        EncryptFieldUDF encryptFieldUDF = new EncryptFieldUDF();
        String encryptedData = encryptFieldUDF.eval("Hello world!");
        System.out.println("Encrypted Data: " + encryptedData);
        // 这里可以添加更多的断言来验证加密后的数据是否符合预期
    }
}
