package com.zyy.scenery.dal.support.dbsec;


import com.llq.commons.security.SecTools;
import com.llq.commons.security.constants.EncryptType;

/**
 * DBSecTools.java
 *
 * @author zhouxiaofeng 5/27/15
 */
public class DBSecTools {
    private SecTools secTools;

    public String encode(String plaintext) {
        return secTools.codecTool().encode(plaintext, EncryptType.AES);
    }

    public String decode(String ciphertext) {
        return secTools.codecTool().decode(ciphertext, EncryptType.AES);
    }

    public void setSecTools(SecTools secTools) {
        this.secTools = secTools;
    }
}
