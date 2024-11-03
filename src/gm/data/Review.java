package gm.data;

public class Review implements Comparable<Review>{

    private String comment;
    private Rating rating;

    public Review(Rating rating,String comment ) {
        this.comment = comment;
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public Rating getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Review{" +
                "comment='" + comment + '\'' +
                ", rating=" + rating +
                '}';
    }

    @Override
    public int compareTo(Review o) {
        return o.rating.ordinal() - this.rating.ordinal();
    }
}
