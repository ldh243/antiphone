package util;

public class DateTimeUtils {
    private DateTimeUtils() {
    }

    public static String generateTime(double time) {
        int hours = (int) (time / 3600);
        time %= 3600;
        int minutes = (int) (time / 60);
        int seconds = (int) (time % 60);
        StringBuilder sb = new StringBuilder();
        if (hours > 0) {
            if (hours < 10) {
                sb.append(0);
            }
            sb.append(hours).append(":");
        }
        if (minutes < 10) {
            sb.append(0);
        }
        sb.append(minutes).append(":");
        if (seconds < 10) {
            sb.append(0);
        }
        sb.append(seconds);
        return sb.toString();
    }

    public static String generateDayHourMinutes(double time) {

        int days = (int) (time / 86400);
        time %= 86400;

        int hours = (int) (time / 3600);
        time %= 3600;

        int minutes = (int) (time / 60);
        StringBuilder sb = new StringBuilder();

        sb.append(days).append("d ");
        sb.append(hours).append("h ");
        sb.append(minutes).append("m ");
        return sb.toString();
    }
}
