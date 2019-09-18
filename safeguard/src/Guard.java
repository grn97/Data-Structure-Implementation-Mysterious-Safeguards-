public class Guard {
    private int start;
    private int end;

    public Guard(int a, int b) {
        this.start = a;
        this.end = b;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public int getLength() {
        return this.end - this.start;
    }

}

