package infinityitemeditor.util;

public class CharUtils {
    public static boolean isAllowedChatCharacter(char c) { //func_71566_a
        return (c != 167 && c >= ' ' && c != 127) || c == '\u00a7';
    }
}
