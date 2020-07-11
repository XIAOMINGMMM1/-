//package cn.wtu.sj;
//
//import java.math.BigInteger;
//import java.security.MessageDigest;
//import javax.crypto.Cipher;
//import javax.crypto.SecretKey;
//import javax.crypto.SecretKeyFactory;
//import javax.crypto.spec.DESKeySpec;
//import javax.crypto.spec.IvParameterSpec;
//
///**
// * @author huiming.wang@hand-china.com
// * @date 2020/2/24 10:41
// */
//public class Des {
//    private byte[] desKey;
//
//
//    // 解密数据
//    public static String decrypt(String message, String key) throws Exception {
//        byte[] bytesrc = convertHexString(message);
//        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
//        DESKeySpec desKeySpec = new DESKeySpec(Des.getShe1Str(Des.getMD5Str(key).substring(0, 8)).substring(0, 8).getBytes("UTF-8"));
//        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
//        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
//        IvParameterSpec iv = new IvParameterSpec(Des.getMD5Str(Des.getMD5Str(key).substring(0, 8)).substring(0, 8).getBytes("UTF-8"));
//        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
//        byte[] retByte = cipher.doFinal(bytesrc);
//        return new String(retByte);
//    }
//
//
//    public static byte[] encrypt(String message, String key) throws Exception {
//        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
//        DESKeySpec desKeySpec = new DESKeySpec(Des.getShe1Str(Des.getMD5Str(key).substring(0, 8)).substring(0, 8).getBytes("UTF-8"));
//        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
//        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
//        IvParameterSpec iv = new IvParameterSpec(Des.getMD5Str(Des.getMD5Str(key).substring(0, 8)).substring(0, 8).getBytes("UTF-8"));
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
//        return cipher.doFinal(message.getBytes("UTF-8"));
//    }
//
//
//    public static byte[] convertHexString(String ss) {
//        byte digest[] = new byte[ss.length() / 2];
//        for (int i = 0; i < digest.length; i++) {
//            String byteString = ss.substring(2 * i, 2 * i + 2);
//            int byteValue = Integer.parseInt(byteString, 16);
//            digest[i] = (byte) byteValue;
//        }
//        return digest;
//    }
//
//
//    public static void main(String[] args) throws Exception {
//        String key = "Vyung";
//        String value = "hand";
//        String jiami = java.net.URLEncoder.encode(value, "utf-8").toLowerCase();
//        System.out.println("加密数据:" + jiami);
//        String a = toHexString(encrypt(jiami, key)).toUpperCase();
//        System.out.println("加密后的数据为:" + a);
//        String b = java.net.URLDecoder.decode(decrypt(a, key), "utf-8");
//        System.out.println("解密后的数据:" + b);
//    }
//
//
//    public static String toHexString(byte b[]) {
////        StringBuffer hexString = new StringBuffer();
////        for (int i = 0; i < b.length; i++) {
////            String plainText = Integer.toHexString(0xff & b[i]);
////            if (plainText.length() < 2) {
////                plainText = "0" + plainText;
////            }
////            hexString.append(plainText);
////        }
////        return hexString.toString();
//
//        StringBuffer hexString = new StringBuffer();
//        for (int i = 0; i < b.length; i++) {
//            int v = b[i] & 0xFF;
//            String hv = Integer.toHexString(v);
//            if (hv.length() < 2) {
//                hexString.append(0);
//            }
//            hexString.append(hv);
//        }
//        return hexString.toString();
//    }
//
//
//    public static String getMD5Str(String str) throws Exception {
//        try {
//            // 生成一个MD5加密计算摘
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            // 计算md5函数
//            md.update(str.getBytes());
//            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
//            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
//            return new BigInteger(1, md.digest()).toString(16);
//        } catch (Exception e) {
//            throw new Exception("MD5加密出现错误，" + e.toString());
//        }
//    }
//
//}