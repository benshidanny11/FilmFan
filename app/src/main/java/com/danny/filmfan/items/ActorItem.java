package com.danny.filmfan.items;

public class ActorItem {
    String actorName,actorRole,actorImageUrl;

    public ActorItem(String actorName, String actorRole, String actorImageUrl) {
        this.actorName = actorName;
        this.actorRole = actorRole;
        this.actorImageUrl = actorImageUrl;
    }

    public String getActorName() {
        return actorName;
    }

    public String getActorRole() {
        return actorRole;
    }

    public String getActorImageUrl() {
        return actorImageUrl;
    }
}
