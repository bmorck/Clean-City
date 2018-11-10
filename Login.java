import java.util.HashMap;

public class Login {
    public static boolean login (String email, String pass, HashMap<String, String> loginInfo) {
    String hash = Encrypt.encrypt(pass);
    if (hash.equals(loginInfo.get(email)))
        return true;
    else
    return false;
    }
}
