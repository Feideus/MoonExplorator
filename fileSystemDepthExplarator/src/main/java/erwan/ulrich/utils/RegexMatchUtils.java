package erwan.ulrich.utils;

public class RegexMatchUtils {

    private  String ELEMENTS_AT_LEVEL = "(\\\\t){\\{0\\}}(.*?)(\\\\t){{\\{1\\}}";
    private  String DIRECT_PARENT = "{0}(?!.{1})";
    private final String ROOT_MATCHER = "\\n";
    private final String SUB_DIR_MATCHER = "\\t";

    private final String MAX_DEPTH_MATCH = "(\\t)\\1*";
}
