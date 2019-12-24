package socketsapp;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import socketsapp.models.User;
import sun.plugin2.message.Message;
import sun.security.tools.KeyStoreUtil;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.ECPrivateKey;

public class Main {
    public static void main(String[] args) throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        User user = new User();
//        user.setLogin("onsdfnawif");
//        user.setPassword("onsdfnawiasd");
//        UserInnerObject userInnerObject = new UserInnerObject();
//        userInnerObject.setUser(user);
//        String json  = objectMapper.writeValueAsString(userInnerObject);
//        System.out.println(json);
//        JsonNode jsonNode= objectMapper.readValue(json,JsonNode.class);
//        JsonNode node = jsonNode.get("user");
//        node.get("login");
        User user = User.newBuilder().setId(Long.valueOf(13)).setRole("admin").build();
        String secretKey = "gHop";
        byte[] encodedId = Base64.encodeBase64(user.getId().toString().getBytes());
        byte[] encodedRole = Base64.encodeBase64(user.getRole().getBytes());
        byte[] encodedBase64 = Base64.encodeBase64(encode(secretKey).getBytes());
        System.out.println(encode(secretKey));
        String string = new String(encodedId)+"."+new String(encodedRole)+"."+new String(encodedBase64);
        System.out.println(string);
        byte[] encodeAllWithSignature = Base64.encodeBase64(string.getBytes());
        byte[] decodedBase64Key = Base64.decodeBase64(encodedBase64);
        System.out.println(new String(decodedBase64Key));
        System.out.println(new String(encodeAllWithSignature));
        byte[] decodeAllWithSignature = Base64.decodeBase64(encodeAllWithSignature);
        System.out.println(new String(decodeAllWithSignature));
    }

    public static final String encode(String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        return Hex.encodeHexString(sha256_HMAC.doFinal(key.getBytes("UTF-8")));
    }

    public static String decode(String key, String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        return Hex.encodeHexString(sha256_HMAC.doFinal(data.getBytes("UTF-8")));
    }


}

