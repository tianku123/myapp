package rh.study.knowledge.util.aes;

import com.alibaba.fastjson.JSON;
import rh.study.knowledge.entity.wechat.WeixinPhoneDecryptInfo;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;

public class AESForWeixinGetPhoneNumber {

    //待解密的数据
    private String encrypData;
    //会话密钥sessionKey
    private String session_key;
    //加密算法的初始向量
    private String iv;

    public AESForWeixinGetPhoneNumber(String encrypData, String session_key, String iv) {
        this.encrypData = encrypData;
        this.session_key = session_key;
        this.iv = iv;
    }

    /**
     * AES解密
     * 填充模式AES/CBC/PKCS7Padding
     * 解密模式128
     *
     * @return 解密后的信息对象
     */
    public WeixinPhoneDecryptInfo decrypt() {
        try {
            byte[] encrypData = org.apache.commons.codec.binary.Base64.decodeBase64(this.encrypData);
            byte[] ivData = org.apache.commons.codec.binary.Base64.decodeBase64(this.iv);
            byte[] sessionKey = org.apache.commons.codec.binary.Base64.decodeBase64(this.session_key);
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivData);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(sessionKey, "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            //解析解密后的字符串
            String datastr = new String(cipher.doFinal(encrypData), "UTF-8");

            return JSON.toJavaObject(JSON.parseObject(datastr),WeixinPhoneDecryptInfo.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}

