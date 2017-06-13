//package cn.tse.pr.utils;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.util.Map;
//import java.util.TreeMap;
//
//import javax.crypto.Mac;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//
///**
// * Created by xieye on 2017/6/12.
// */
//
//public class YoukuUtils {
//    public static TreeMap get_sign(TreeMap params, String appKey, String secret)
//            throws Exception {
//        /**
//         * 用于存放与业务参数名相同的系统参数
//         */
//        Map serviceDuplicatePairs = new TreeMap<>();
//        if(params.get("client_id") != null) {
//            serviceDuplicatePairs.put("client_id", appKey);
//        } else {
//            params.put("client_id", appKey);
//        }
//        if(params.get("timestamp") != null) {
//            serviceDuplicatePairs.put("timestamp", System.currentTimeMillis() / 1000);
//        } else {
//            params.put("timestamp", System.currentTimeMillis() / 1000);
//        }
//        if(params.get("version") != null) {
//            serviceDuplicatePairs.put("version",  "3.0");
//        } else {
//            params.put("version",  "3.0");
//        }
//
//        String signMethod = params.get("sign_method") == null ? null : params.get("sign_method").toString();
//        if(signMethod == null || "".equals(signMethod)) {
//            signMethod = SignMethodEnum.MD5.getValue();
//            params.put("sign_method", signMethod);
//        } else {
//            serviceDuplicatePairs.put("sign_method", signMethod);
//        }
//
//        StringBuffer signString = new StringBuffer();
//        try {
//            /**
//             * 生成签名字符串
//             */
//            for(Map.Entry entry : params.entrySet()) {
//                /**
//                 * 同名参数，系统参数置于业务参数前
//                 */
//                if(serviceDuplicatePairs.get(entry.getKey()) != null) {
//                    signString.append(entry.getKey());
//                    /**
//                     * 对参数值进行URLEncode, 不同开发语言对特殊字符encode结果可能不同，
//                     * 以Java URLEncoder结果为准
//                     * Encode值可以参考http://tool.chinaz.com/tools/urlencode.aspx
//                     */
//                    signString.append(URLEncoder.encode(serviceDuplicatePairs.get(entry.getKey()).toString(),
//                            "UTF-8"));
//                }
//                signString.append(entry.getKey());
//                signString.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
//            }
//        } catch (UnsupportedEncodingException e) {
//            throw new Exception("ErrorCode.U8_ENCODE_ERROR.getDesc()");
//        }
////        System.out.println(signString.toString());
//        String sign = "";
//        if(SignMethodEnum.isHmac(signMethod)){
//            try {
//                sign = hmacSign(secret, signMethod, signString.toString());
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            } catch (InvalidKeyException e) {
//                e.printStackTrace();
//            }
//        } else {
//            signString.append(secret);
//            try {
//                System.out.println(signString.toString());
//                sign = md5Sign(signString.toString());
//            } catch(Exception e) {
//              //  params.put("error", ErrorCode.U8_ENCODE_ERROR.getCode());
//                return params;
//            }
//        }
//        return packageRequestParams(params, serviceDuplicatePairs, appKey, sign);
//    }
//
//    /**
//     * 拼接请求参数，返回Map方便post请求封装请求参数
//     * @param params 所有参数
//     * @param dupiicateParams 与业务参数名相同的系统参数
//     * @param appKey client_id
//     * @param sign 加密字符串
//     * @return
//     */
//    private static TreeMap packageRequestParams(TreeMap params,
//                                                Map dupiicateParams,
//                                                String appKey, String sign) {
//        StringBuffer buffer = new StringBuffer();
//        /**
//         * 拼接系统参数
//         */
//        buffer.append("{");
//        buffer.append("\"client_id\":");
//        buffer.append("\"");
//        buffer.append(appKey);
//        buffer.append("\",");
//        buffer.append("\"timestamp\":");
//        buffer.append("\"");
//        buffer.append(params.get("timestamp"));
//        buffer.append("\",");
//        buffer.append("\"version\":");
//        buffer.append("\"3.0\",");
//        buffer.append("\"sign_method\":");
//        buffer.append("\"");
//        buffer.append(params.get("sign_method"));
//        buffer.append("\",");
//        buffer.append("\"sign\":");
//        buffer.append("\"");
//        buffer.append(sign);
//        buffer.append("\",");
//        buffer.append("\"action\":");
//        buffer.append("\"");
//        buffer.append(params.get("action"));
//        buffer.append("\"");
//
//        String access_token = (String) params.get("access_token");
//        if(access_token != null && !"".equals(access_token)) {
//            buffer.append(",\"access_token\":");
//            buffer.append("\"");
//            buffer.append(access_token);
//            buffer.append("\"");
//            params.remove("access_token");
//        }
//        buffer.append("}");
//        params.put("opensysparams", buffer.toString());
//
//        /**
//         * 将Map中的系统参数移出
//         */
//        if(dupiicateParams.get("client_id") == null) {
//            params.remove("client_id");
//        }
//        if(dupiicateParams.get("timestamp") == null) {
//            params.remove("timestamp");
//        }
//        if(dupiicateParams.get("version") == null) {
//            params.remove("version");
//        }
//        if(dupiicateParams.get("sign_method") == null) {
//            params.remove("sign_method");
//        }
//        if(dupiicateParams.get("action") == null) {
//            params.remove("action");
//        }
//
//        return params;
//    }
//
//    private static String hmacSign(String secret, String signMethod, String signString)
//            throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
//        String algorithm = null;
//        if(SignMethodEnum.HMAC.getValue().equals(signMethod)){
//            algorithm = SignMethodEnum.HMACMD5.getValue();
//        }else{
//            algorithm = signMethod;
//        }
//        SecretKey secretKey = new SecretKeySpec(secret.getBytes("UTF-8"), algorithm);
//        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
//        mac.init(secretKey);
//        byte[] bytes = mac.doFinal(signString.getBytes("UTF-8"));
//        System.out.println(signString.toString());
//        return byte2hex(bytes);
//    }
//
//    private static String md5Sign(String signString)  {
//        String sign = MD5.stringToMD5(signString);
//        return sign;
//    }
//
//    public static String byte2hex(byte[] bytes) {
//        StringBuilder sign = new StringBuilder();
//        for (int i = 0; i < bytes.length; i++) {
//            String hex = Integer.toHexString(bytes[i] & 0xFF);
//            if (hex.length() == 1) {
//                sign.append("0");
//            }
//            sign.append(hex.toLowerCase());
//        }
//        return sign.toString();
//    }
//
//}
