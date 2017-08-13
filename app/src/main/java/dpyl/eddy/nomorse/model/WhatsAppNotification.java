package dpyl.eddy.nomorse.model;

public class WhatsAppNotification {

    private String tag;
    private long postTime;

    public WhatsAppNotification(String tag, long postTime) {
        this.tag = tag;
        this.postTime = postTime;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getPostTime() {
        return postTime;
    }

    public void setPostTime(long postTime) {
        this.postTime = postTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WhatsAppNotification)) return false;

        WhatsAppNotification that = (WhatsAppNotification) o;

        return tag.equals(that.tag);

    }

    @Override
    public int hashCode() {
        return tag.hashCode();
    }

    @Override
    public String toString() {
        return "WhatsAppNotification{" +
                "tag='" + tag + '\'' +
                ", postTime=" + postTime +
                '}';
    }
}
