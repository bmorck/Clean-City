import java.util.HashMap;
import java.util.Arrays;
import java.io.File;
import java.lang.StringBuilder;

public class Account {

    private String first, last, email, username;
    private int id; // user id
    private String password; // hexadecimal representation of hashed bytes of String password

    // constructor
    public Account(String first, String last, String email, String username, String password,
    HashMap<Integer, Account> idToAcc) {
        this.first = first;
        this.last = last;
        this.email = email;
        this.username = username;
        this.password = password;
        this.id = idToAcc.size(); // assign unique id to this account
    }

    // creates new account
    public static void create(String f, String l, String e, String u, String p, HashMap<String, String> loginInfo,
    HashMap<String, Integer> userToID, HashMap<Integer, Account> idToAcc) {

        // check if arguments are allowed
        checkProfanity(f);
        checkProfanity(l);
        checkProfanity(u);
        checkUserValidity(u, userToID);
        checkEmailValidity(e, loginInfo);
        checkPasswordLength(p);
        String pass = Encrypt.encrypt(p);

        Account newUser = new Account(f, l, c, e, u, pass, idToAcc);
        idToAcc.put(newUser.id, newUser);
        loginInfo.put(newUser.email, newUser.password);
        userToID.put(newUser.username, newUser.id);
    }

    // throws error if username contains profanity
    private static void checkProfanity(String m) {
        if (m.contains("Fuck") || m.contains("fuck") || m.contains("Shit") || m.contains("shit") || m.contains("Bitch") || m.contains("bitch")
        || m.contains("Cunt") || m.contains("cunt") || m.contains("Nigger") || m.contains("nigger") || m.contains("Ass") || m.contains("ass")
        || m.contains("Dick") || m.contains("dick") || m.contains("Vagina") || m.contains("vagina") || m.contains("Nigga") || m.contains("nigga"))
        throw new IllegalArgumentException("Please pick an appropriate username.");
    }

    // throws error if not a valid email
    private static void checkEmailValidity(String e, HashMap<String, String> loginInfo) {
        // check if username and email are available
        if (!loginInfo.isEmpty()) {
            if (loginInfo.containsKey(e))
            throw new IllegalArgumentException("Email already taken!");
        }
    }

    private static void checkPasswordLength(String p) {
        if (p.length() < 7)
        throw new IllegalArgumentException("Password must be over 6 characters!");
    }

    // throws error if username contains non-valid characters
    private static void checkUserValidity(String m, HashMap<String, Integer> userToID) {
        if (m.contains("~") || m.contains("!") || m.contains("@") || m.contains("#") || m.contains("$") || m.contains("%")
        || m.contains("^") || m.contains("&") || m.contains("*") || m.contains("(") || m.contains(")") || m.contains("-")
        || m.contains("+") || m.contains("=") || m.contains("[") || m.contains("]") || m.contains("{") || m.contains("}")
        || m.contains("?") || m.contains("/") || m.contains(";") || m.contains(":") || m.contains("<") || m.contains(">")
        || m.contains(","))
        throw new IllegalArgumentException("Please pick an appropriate username.");

        if (m.length() >= 16)
        throw new IllegalArgumentException("Username must be under 16 characters!");

        if (m.length() <= 3)
        throw new IllegalArgumentException("Username must be at least 4 characters!");

        if (userToID.get(m) != null)
        throw new IllegalArgumentException("Username is taken!");

    }

// change email
public static void changeEmail(int accID, String newEmail, HashMap<String, String> loginInfo, HashMap<Integer, Account> idToAcc) {
    Account acc = idToAcc.get(accID);
    loginInfo.remove(acc.email);
    acc.email = newEmail;
    loginInfo.put(newEmail, acc.password);
    idToAcc.put(acc.id, acc);
}

// change username
public static void changeUsername(int accID, String newUser, HashMap<String, Integer> userToID, HashMap<Integer, Account> idToAcc) {
    Account acc = idToAcc.get(accID);
    userToID.remove(acc.username);
    acc.username = newUser;
    userToID.put(newUser, acc.id);
    idToAcc.put(acc.id, acc);
}

// change password
public static void changePassword(int accID, String newPass, HashMap<String, String> loginInfo, HashMap<Integer, Account> idToAcc) {
    Account acc = idToAcc.get(accID);
    acc.password = Encrypt.encrypt(newPass);
    loginInfo.put(acc.email, acc.password);
    idToAcc.put(acc.id, acc);
}

public static String showUser(int accID, HashMap<Integer, Account> idToAcc) {
    Account acc = idToAcc.get(accID);
    return acc.username;
}

public static String showFirstName(int accID, HashMap<Integer, Account> idToAcc) {
    Account acc = idToAcc.get(accID);
    return acc.first;
}

public static String showLastName(int accID, HashMap<Integer, Account> idToAcc) {
    Account acc = idToAcc.get(accID);
    return acc.last;
}

public static String showPass(int accID, HashMap<Integer, Account> idToAcc) {
    Account acc = idToAcc.get(accID);
    return acc.password;
}

public static String showEmail(int accID, HashMap<Integer, Account> idToAcc) {
    Account acc = idToAcc.get(accID);
    return acc.email;
}

public static int showID(int accID, HashMap<Integer, Account> idToAcc) {
    Account acc = idToAcc.get(accID);
    return acc.id;
}

}
