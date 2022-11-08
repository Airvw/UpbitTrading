package utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

public class Token {

    private static final String ROOT_PATH = System.getProperty("user.dir");
    private static final String FILE_PATH = ROOT_PATH + "/Upbit_Key.txt";
    private static final String[] keyArr = new String[2];
    private static void getKeys() throws IOException {
        FileReader fileReader = new FileReader(FILE_PATH);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String readLine;
        int idx = 0;
        while ((readLine = bufferedReader.readLine()) != null) {
            keyArr[idx++] = readLine;
        }
    }

    public static String getToken() throws IOException {
        getKeys();
        Algorithm algorithm = Algorithm.HMAC256(keyArr[1]);
        String jwtToken = JWT.create()
                .withClaim("access_key", keyArr[0])
                .withClaim("nonce", UUID.randomUUID().toString())
                .sign(algorithm);

        return "Bearer " + jwtToken;
    }
}
