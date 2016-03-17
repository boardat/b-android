package com.boredat.boredatdroid.network.oauth;

import android.util.Log;

import com.boredat.boredatdroid.util.Constants;

import org.scribe.services.Base64Encoder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Liz on 7/4/2015.
 */
public class OauthHeader {

    public static final String EMPTY_STRING = "";
    public static final String CARRIAGE_RETURN = "\r\n";
    public static final String UTF8 = "UTF-8";
    public static final String HMAC_SHA1 = "HmacSHA1";

    /* OAuth Protocol Parameter names*/
    public static final String PARAM_METHOD="oauth_signature_method";
    public static final String PARAM_SIGNATURE="oauth_signature";
    public static final String PARAM_NONCE="oauth_nonce";
    public static final String PARAM_TIMESTAMP="oauth_timestamp";
    public static final String PARAM_TOKEN="oauth_token";
    public static final String PARAM_CONSUMER="oauth_consumer_key";
    public static final String PARAM_VERSION="oauth_version";

    /* OAuth Protocol Parameters */
    private static String METHOD = "HMAC-SHA1";
    private static String signature;
    private static String nonce;
    private static String timeStamp;
    private static String token;
    private static String VERSION = "1.0";

    // should store this in a properties file once we get the signature working
    public static final String CONSUMER_KEY="d81a0dc62eaa717532d134db065270cb04fab84c1";
    public static final String CONSUMER_SECRET="f7a543570d6a6efdd38d7a9e3a51a2bd";
    private static String tokenSecret;

    private static String requestMethod;
    private static String requestUrl;
    private static Map<String,String> queryParams;


    private static char[] VALID_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456879".toCharArray();

    public static HashMap<String, String> getOauthHeader(String httpRequestMethod, String httpRequestUrl, Map<String,String> httpQueryParams, String oauthToken, String oauthSecret) {
        nonce = getNonce();
        timeStamp = getTimestamp();
        token = oauthToken;
        tokenSecret = oauthSecret;
        requestMethod = httpRequestMethod;
        requestUrl = httpRequestUrl;
        queryParams = httpQueryParams;
        return build();
    }

    /* Generate the Signature Base String to sign */
    private static HashMap<String,String> build() {
        Log.d(Constants.TAG, "Build the Signature Base String to sign");
        /* Collect all the unsigned parameters */
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(PARAM_CONSUMER, CONSUMER_KEY);
        params.put(PARAM_VERSION, VERSION);
        params.put(PARAM_METHOD, METHOD);
        params.put(PARAM_TIMESTAMP, timeStamp);
        params.put(PARAM_NONCE, nonce);
        params.put(PARAM_TOKEN, token);

        // collect query params
        String qParam = "";
        String qVal = "";
        if (queryParams != null) {
            Log.d(Constants.TAG,"=> Query Params:");
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                // encode each key and value
                qParam = encode(entry.getKey());
                qVal = encode(entry.getValue());
                params.put(qParam, qVal);
                Log.d(Constants.TAG, "\t\tplain: " + entry.getKey() + "=" + entry.getValue());
                Log.d(Constants.TAG, "\t\tencoded: " + qParam + "=" + qVal);
            }
        }

        // sort all params
        Map<String,String> sortedParams = new TreeMap<>(params);
        String sParamString = "";

        // build base param string
        if (sortedParams != null) {
            boolean firstParam = true;
            for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
                if (!firstParam) {
                    sParamString+="&";
                }
                sParamString+=entry.getKey()+"="+entry.getValue();
                firstParam = false;
            }
        }

        Log.d(Constants.TAG, "Oauth Signature Base String parameters => " + sParamString);
        // encode url
        String encodedRequestUrl = encode(requestUrl);
        // encode param string
        String encodedParams = encode(sParamString);
        Log.d("", "Encoded Oauth Signature Base String parameters => " + sParamString);
        // generate base string
        String sBaseString = requestMethod+"&"+encodedRequestUrl+"&"+encodedParams;
        Log.d(Constants.TAG, "Oauth Signature Base String => " + sBaseString);

        try {
            /* Sign the base string */
            String sigSH1Base64 = doSign(sBaseString, CONSUMER_SECRET + "&"+tokenSecret);
            Log.d("", "sigSH1Base64  " + sigSH1Base64);
            signature = encode(sigSH1Base64);
            Log.d("", "signature: " + signature);
            params.put(PARAM_SIGNATURE, signature);

            /* Build the final OAuth header */
            String auth = "OAuth ";
            auth+=PARAM_SIGNATURE+"="+params.get(PARAM_SIGNATURE)+", ";
            auth+=PARAM_TIMESTAMP+"="+params.get(PARAM_TIMESTAMP)+", ";
            auth+=PARAM_NONCE+"="+params.get(PARAM_NONCE)+", ";
            auth+=PARAM_CONSUMER+"="+params.get(PARAM_CONSUMER)+", ";
            auth+=PARAM_VERSION+"="+params.get(PARAM_VERSION)+", ";
            auth+=PARAM_TOKEN+"="+params.get(PARAM_TOKEN)+", ";
            auth+=PARAM_METHOD+"="+params.get(PARAM_METHOD)+", ";
            Log.d("", "auth " + auth);

            HashMap<String, String> header = new HashMap<String, String>();
            header.put("Authorization", auth);
            return header;

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

    private static String getTimestamp() {
        return String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
    }
    private static String getNonce() {
        RandomString rand = new RandomString(32);
        return rand.nextString();
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
