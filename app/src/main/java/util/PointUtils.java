package util;

public class PointUtils {
    public static long calculatePoint(long seconds) {
        return seconds / 1200 * 20;
    }
}
