package com.boredat.boredatdroid.network.oauth;

import org.scribe.services.Base64Encoder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class BuildOauthAuthorize {

    public static final String EMPTY_STRING = "";
    public static final String CARRIAGE_RETURN = "\r\n";
    public static final String UTF8 = "UTF-8";
    public static final String HMAC_SHA1 = "HmacSHA1";
    public static final String METHOD = "HMAC-SHA1";

    public static final String PARAM_CONSUMER="oauth_consumer_key";
    public static final String PARAM_SIGN="oauth_signature";
    public static final String PARAM_CALLBACK="oauth_callback";
    public static final String PARAM_VERSION="oauth_version";
    public static final String PARAM_METHOD="oauth_signature_method";
    public static final String PARAM_TIMESTAMP="oauth_timestamp";
    public static final String PARAM_NONCE="oauth_nonce";

    public static final String PARAM_TOKEN="oauth_token";

    public static final String CONSUMER_KEY="d81a0dc62eaa717532d134db065270cb04fab84c1";
    public static final String CONSUMER_SECRET="f7a543570d6a6efdd38d7a9e3a51a2bd";

    private static String token;
    private static String tokenSecret;

    // build oauth header for a boredat request given the url and access token/token secret
    /*
    public static HashMap<String, String>buildOAuthHeader(String url, String oauthToken, String oauthSecret) {
        HashMap<String, String> params = new HashMap<String, String>();

        token=oauthToken;
        tokenSecret=oauthSecret;
    }
    */

    public static HashMap<String, String> build(String url,String oauthToken, String oauthSecret){
        HashMap<String, String> params = new HashMap<String, String>();

        token=oauthToken;
        tokenSecret=oauthSecret;

        params.put(PARAM_CONSUMER, CONSUMER_KEY);
        params.put(PARAM_CALLBACK, encode("http://authenticate.callback"));
        params.put(PARAM_VERSION,"1.0");
        params.put(PARAM_METHOD,METHOD);
        String timestamp= String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
        params.put(PARAM_TIMESTAMP,timestamp);
       // String uuid= Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
      //  String nonce= Base64.encodeToString(uuid.getBytes(), Base64.DEFAULT);
        params.put(PARAM_NONCE,/*nonce*/"DB4D3918672B4138A66F5B7875ADBCD7");
        if (token!=null){
           params.put(PARAM_TOKEN, token);
        }

        String singparam="";
        singparam+=PARAM_CALLBACK+"="+params.get(PARAM_CALLBACK)+"&";
        singparam+=PARAM_CONSUMER+"="+params.get(PARAM_CONSUMER)+"&";
        singparam+=PARAM_NONCE+"="+params.get(PARAM_NONCE)+"&";
        singparam+=PARAM_METHOD+"="+params.get(PARAM_METHOD)+"&";
        singparam+=PARAM_TIMESTAMP+"="+params.get(PARAM_TIMESTAMP)+"&";
        if (token!=null){
            singparam+=PARAM_TOKEN+"="+params.get(PARAM_TOKEN)+"&";
        }
        singparam+=PARAM_VERSION+"="+params.get(PARAM_VERSION);
//        Log.d("", "singparam " + singparam);
        String singparamEncoded = encode(singparam);
//        Log.d("","singparamEncoded "+singparamEncoded);

        String sing="POST&"+encode(url)+"&"+singparamEncoded;
//        Log.d("","sing "+sing);
        String singEncoded= sing;
//        Log.d("","singEncoded "+singEncoded);
        try {
            String singSH1= doSign(singEncoded, CONSUMER_SECRET + "&"+(tokenSecret!=null?tokenSecret:""));
//            Log.d("","singSH1 "+singSH1);
            String singBase64= singSH1;
//            Log.d("","singBase64 "+singBase64);
            String singFinal = encode(singBase64);
//            Log.d("","singFinal "+singFinal);
            params.put(PARAM_SIGN,singFinal);

            String auth="OAuth ";
            auth+=PARAM_SIGN+"="+params.get(PARAM_SIGN)+", ";
            auth+=PARAM_CALLBACK+"="+params.get(PARAM_CALLBACK)+", ";
            auth+=PARAM_TIMESTAMP+"="+params.get(PARAM_TIMESTAMP)+", ";
            auth+=PARAM_NONCE+"="+params.get(PARAM_NONCE)+", ";
            auth+=PARAM_CONSUMER+"="+params.get(PARAM_CONSUMER)+", ";
            auth+=PARAM_VERSION+"="+params.get(PARAM_VERSION)+", ";
            if (token!=null) {
                auth += PARAM_TOKEN + "=" + params.get(PARAM_TOKEN) + ", ";
            }
            auth+=PARAM_METHOD+"="+params.get(PARAM_METHOD);

//            Log.d("","auth "+auth);
            HashMap<String, String> oauthparams = new HashMap<>();
            oauthparams.put("Authorization",auth);
            return oauthparams;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final Map<String, String> ENCODING_RULES;
    static
    {
        Map<String, String> rules = new HashMap<String, String>();
        rules.put("*", "%2A");
        rules.put("+", "%20");
        rules.put("%7E", "~");
        ENCODING_RULES = Collections.unmodifiableMap(rules);
    }

    public static String encode(String plain)
    {
        String encoded = "";
        try
        {
            encoded = URLEncoder.encode(plain, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for(Map.Entry<String, String> rule : ENCODING_RULES.entrySet())
        {
            encoded = applyRule(encoded, rule.getKey(), rule.getValue());
        }
        return encoded;
    }
    public static String applyRule(String encoded, String toReplace, String replacement)
    {
        return encoded.replaceAll(Pattern.quote(toReplace), replacement);
    }

    public static String doSign(String toSign, String keyString) throws Exception
    {
        SecretKeySpec key = new SecretKeySpec((keyString).getBytes(UTF8), HMAC_SHA1);
        Mac mac = Mac.getInstance(HMAC_SHA1);
        mac.init(key);
        byte[] bytes = mac.doFinal(toSign.getBytes(UTF8));
        return bytesToBase64String(bytes).replace(CARRIAGE_RETURN, EMPTY_STRING);
    }

    public static String bytesToBase64String(byte[] bytes)
    {
        return Base64Encoder.getInstance().encode(bytes);
    }

}
