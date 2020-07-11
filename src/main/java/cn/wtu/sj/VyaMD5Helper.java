//package cn.wtu.sj;
//
//import lombok.experimental.var;
//import org.springframework.util.StringUtils;
//
//import javax.management.modelmbean.DescriptorSupport;
//
///**
// * @author huiming.wang@hand-china.com
// * @date 2020/2/24 9:24
// */
//public class VyaMD5Helper {
//
//    public static String Encrypt(String targetValue, String key)
//    {
//        if (StringUtils.isEmpty(targetValue))
//        {
//            return null;
//        }
//
//        var returnValue = new StringBuilder();
////        var des = new DESCryptoServiceProvider();
//        byte[] inputByteArray = Encoding.Default.GetBytes(targetValue);
//        // 通过两次哈希密码设置对称算法的初始化向量
//        des.Key = Encoding.ASCII.GetBytes(FormsAuthentication.HashPasswordForStoringInConfigFile
//                (FormsAuthentication.HashPasswordForStoringInConfigFile(key, "md5").
//                        Substring(0, 8), "sha1").Substring(0, 8));
//        // 通过两次哈希密码设置算法的机密密钥
//        des.IV = Encoding.ASCII.GetBytes(FormsAuthentication.HashPasswordForStoringInConfigFile
//                (FormsAuthentication.HashPasswordForStoringInConfigFile(key, "md5")
//                        .Substring(0, 8), "md5").Substring(0, 8));
//        var ms = new MemoryStream();
//        var cs = new CryptoStream(ms, des.CreateEncryptor(), CryptoStreamMode.Write);
//        cs.Write(inputByteArray, 0, inputByteArray.Length);
//        cs.FlushFinalBlock();
//        foreach (byte b in ms.ToArray())
//        {
//            returnValue.AppendFormat("{0:X2}", b);
//        }
//        return returnValue.ToString();
//    }
//    /// <summary>
//    /// DES数据解密
//    /// </summary>
//    /// <param name="targetValue"></param>
//    /// <param name="key"></param>
//    /// <returns></returns>
//    public static string Decrypt(string targetValue, string key)
//    {
//        if (string.IsNullOrEmpty(targetValue))
//        {
//            return string.Empty;
//        }
//        // 定义DES加密对象
//        var des = new DESCryptoServiceProvider();
//        int len = targetValue.Length / 2;
//        var inputByteArray = new byte[len];
//        int x, i;
//        for (x = 0; x < len; x++)
//        {
//            i = Convert.ToInt32(targetValue.Substring(x * 2, 2), 16);
//            inputByteArray[x] = (byte)i;
//        }
//        // 通过两次哈希密码设置对称算法的初始化向量
//        des.Key = Encoding.ASCII.GetBytes(FormsAuthentication.HashPasswordForStoringInConfigFile
//                (FormsAuthentication.HashPasswordForStoringInConfigFile(key, "md5").
//                        Substring(0, 8), "sha1").Substring(0, 8));
//        // 通过两次哈希密码设置算法的机密密钥
//        des.IV = Encoding.ASCII.GetBytes(FormsAuthentication.HashPasswordForStoringInConfigFile
//                (FormsAuthentication.HashPasswordForStoringInConfigFile(key, "md5")
//                        .Substring(0, 8), "md5").Substring(0, 8));
//        // 定义内存流
//        var ms = new MemoryStream();
//        // 定义加密流
//        var cs = new CryptoStream(ms, des.CreateDecryptor(), CryptoStreamMode.Write);
//        cs.Write(inputByteArray, 0, inputByteArray.Length);
//        cs.FlushFinalBlock();
//        return Encoding.Default.GetString(ms.ToArray());
//    }
//}
