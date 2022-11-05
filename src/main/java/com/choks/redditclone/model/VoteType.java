package com.choks.redditclone.model;

public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1),
    ;

    private int direction;

    VoteType(int direction) {
//        return Arrays.stream(VoteType.values())
//                .filter((value -> value.getDirection().equals(direction)))
//                .findAny()
//                .orElseThrow(() -> new SpringRedditException("Vote not found"));
    }

    public Integer getDirection() {
        return direction;
    }
}
