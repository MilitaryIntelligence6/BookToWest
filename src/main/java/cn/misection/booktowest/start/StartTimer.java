package cn.misection.booktowest.start;

public class StartTimer {
    private int timeLeft;
    private boolean isCompleted;
    private boolean isStarted;

    public StartTimer() {
        timeLeft = 0;
        isCompleted = false;
        isStarted = false;
    }

    public void initial() {
        timeLeft = 0;
        isCompleted = false;
        isStarted = false;
    }

    public void start(int interval) {
        isStarted = true;
        this.timeLeft = interval;
    }

    public void update() {
        if (isStarted) {
            timeLeft--;
        }
        if (timeLeft == 0 && isStarted) {
            isCompleted = true;
        }
    }

    public boolean stop() {
        return isCompleted;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }
}
