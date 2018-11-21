package spryrocks.com.tristimer.data;

public class Result {
    private String time;
    private String scramble;
    private String date;

    public Result(String time, String scramble, String date) {
        this.time = time;
        this.scramble = scramble;
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public String getScramble() {
        return scramble;
    }

    public String getDate() {
        return date;
    }
}
