package kr.vortex.familing.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by yoo2001818 on 15. 7. 21.
 */
public class Article {
    public int id;
    // public Group group;
    public int type;
    public String name;
    public String photo;
    public String description;
    public int allowed;
    public boolean solved;
    public boolean canAdd;
    public List<VoteEntry> voteEntries;
    public BaseUser author;
    public List<BaseUser> tagged;
    public List<Comment> comments;
    public String createdAt;

    public Date getCreatedAt() {
        SimpleDateFormat formatter = new
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000Z'");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date createDate = formatter.parse(createdAt);
            return createDate;
        } catch (Exception e) {
            return null;
        }
    }
}
