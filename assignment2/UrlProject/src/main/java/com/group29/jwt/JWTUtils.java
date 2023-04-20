package com.group29.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ryyyyyy
 * @create 2023-04-18 2:24
 */
public class JWTUtils {

    //7 days
//    private static final long EXPIRE_TIME = 604800;
    private static final String SECRET_KEY = "123456";

    public static String getToken(Map<String, Object> payloadMap) throws JsonProcessingException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("alg", "HmacSHA256");
        headerMap.put("typ", "JWT");
        ObjectMapper mapper1 = new ObjectMapper();
        String headerJson = mapper1.writeValueAsString(headerMap);
        System.out.println(headerJson);

        byte[] headerJsonBytes = headerJson.getBytes(Charset.forName("UTF-8"));
        byte[] encodedHeaderJsonBytes = Base64.getEncoder().encode(headerJsonBytes);
        String encodedHeaderJsonString = new String(encodedHeaderJsonBytes, StandardCharsets.UTF_8);




        ObjectMapper mapper2 = new ObjectMapper();
        String BodyJson = mapper2.writeValueAsString(payloadMap);
        byte[] BodyJsonBytes = BodyJson.getBytes(Charset.forName("UTF-8"));
        byte[] encodedBodyJsonBytes = Base64.getEncoder().encode(BodyJsonBytes);
        String encodedBodyJsonString = new String(encodedBodyJsonBytes, StandardCharsets.UTF_8);

        String headerAndBody = encodedHeaderJsonString + "." + encodedBodyJsonString;
        byte[] headerAndBodyBytes = headerAndBody.getBytes(Charset.forName("UTF-8"));


        //set the private key
        byte[] keyBytes = SECRET_KEY.getBytes(Charset.forName("UTF-8"));
        //sign with HmacSHA256
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] signatureBytes = mac.doFinal(headerAndBodyBytes);

        //encode with base64
        byte[] encodedSignatureBytes = Base64.getEncoder().encode(signatureBytes);
        String signatureString = new String(encodedSignatureBytes, StandardCharsets.UTF_8);

        String token = headerAndBody + "." +signatureString;

        return token;
    }

    public static boolean verify(String jwt) throws NoSuchAlgorithmException, InvalidKeyException {
        if (jwt == null){
            return false;
        }
        String[] parts = jwt.split("\\.");
        if (parts.length != 3) {
            return false;
        }

        String header = parts[0];
        String payload = parts[1];
        String signature = parts[2];

        String signingInput = header + "." + payload;
        System.out.println(signingInput);

        byte[] keyBytes = SECRET_KEY.getBytes(Charset.forName("UTF-8"));
        //sign with HmacSHA256
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "HmacSHA256");
        mac.init(secretKeySpec);

        byte[] inputBytes = mac.doFinal(signingInput.getBytes());
        byte[] verifyBytes = Base64.getEncoder().encode(inputBytes);
        String verifyString = new String(verifyBytes, StandardCharsets.UTF_8);

        return verifyString.equals(signature);
    }

    public static Map getPayloads(String jwt){
        String[] parts = jwt.split("\\.");

        String payload = parts[1];
        byte[] decode = Base64.getDecoder().decode(payload);
        String payLoadJson = new String(decode,Charset.forName("UTF-8"));

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> map = null;
        try {
            map = objectMapper.readValue(payLoadJson, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(payLoadJson);
        return map;
    }


//    @Test
//    public void test() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
//
//        Map payloadMap = new HashMap();
//        long currentTime = System.currentTimeMillis();
//        //add time stamp
//        payloadMap.put("name", "ry");
//        payloadMap.put("pwd", "123456");
//        payloadMap.put("iat",currentTime);
//        payloadMap.put("exp",currentTime + EXPIRE_TIME);
//        String token = getToken(payloadMap);
//        System.out.println(token);
//        System.out.println(validate(token));
//    }

    @Test
    public void test1() throws JsonProcessingException {
        Map map = getPayloads("eyJ0eXAiOiJKV1QiLCJhbGciOiJIbWFjU0hBMjU2In0=.eyJuYW1lIjoicnkiLCJwd2QiOiIxMjM0NTYiLCJleHAiOjE2ODE4MjQ1NDI0NDgsImlhdCI6MTY4MTgyMzkzNzY0OH0=");
        System.out.println(map);
    }



}
