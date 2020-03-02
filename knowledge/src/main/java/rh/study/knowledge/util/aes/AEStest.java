package rh.study.knowledge.util.aes;


import rh.study.knowledge.entity.wechat.WeixinPhoneDecryptInfo;
import rh.study.knowledge.util.aes.AESForWeixinGetPhoneNumber;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;

public class AEStest {
    public static void main(String[] args) {
        String sessionKey = "XmrbVwcRLnCSqWJs23AlEg==";
        String encryptedData="QjgqqQoWSlh+FZAjn6fHkN9YKORraRHGaZ3aTRp4l4Wpdn98QvSs+PaTQzmmNfIKCUB9WtKQi6L63yjPLssKEo1WBKUx7V7HgFeJPzuCFg4zPYyBY9cwGIbApo5wr4noZ940isOEJlfHGPiEsKm95tGfo+nqvw4pMMmnx+bYeuMIrYzfsWP48btm5GGOgH56KrV2Hq/vYe6CHAj75iZYGQ==";
        String iv="gtybVWUdLQ2TV9uYoYwq2A==";
        String appId="wx042d9b5b159808fe";
        AESForWeixinGetPhoneNumber aes=new AESForWeixinGetPhoneNumber(encryptedData,sessionKey,iv);
        WeixinPhoneDecryptInfo info=aes.decrypt();
        if (null==info){
            System.out.println("error");
        }else {
            if (!info.getWeixinWaterMark().getAppid().equals(appId)){
                System.out.println("wrong appId");
            }
            System.out.println(info.toString());
        }
    }

//    public static void main(String[] args) throws Exception {
//        byte[] encrypData = Base64.decodeBase64("QjgqqQoWSlh+FZAjn6fHkN9YKORraRHGaZ3aTRp4l4Wpdn98QvSs+PaTQzmmNfIKCUB9WtKQi6L63yjPLssKEo1WBKUx7V7HgFeJPzuCFg4zPYyBY9cwGIbApo5wr4noZ940isOEJlfHGPiEsKm95tGfo+nqvw4pMMmnx+bYeuMIrYzfsWP48btm5GGOgH56KrV2Hq/vYe6CHAj75iZYGQ==");
//        byte[] ivData = Base64.decodeBase64("gtybVWUdLQ2TV9uYoYwq2A==");
//        byte[] sessionKey = Base64.decodeBase64("XmrbVwcRLnCSqWJs23AlEg==");
//        System.out.println(decrypt(sessionKey,ivData,encrypData));
//    }

    public static String decrypt(byte[] key, byte[] iv, byte[] encData) throws Exception {
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        //解析解密后的字符串
        return new String(cipher.doFinal(encData), "UTF-8");
    }
}

