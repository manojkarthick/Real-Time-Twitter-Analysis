package twitter4j;

abstract class EntityIndex implements Comparable<EntityIndex>, java.io.Serializable {
    private static final long serialVersionUID = 3757474748266170719L;
    private int start = -1;
    private int end = -1;

    @Override
    public int compareTo(EntityIndex that) {
        long delta = this.start - that.start;
        if (delta < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        } else if (delta > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int) delta;
    }

    void setStart(int start) {
        this.start = start;
    }

    void setEnd(int end) {
        this.end = end;
    }

    int getStart() {
        return start;
    }

    int getEnd() {
        return end;
    }
}