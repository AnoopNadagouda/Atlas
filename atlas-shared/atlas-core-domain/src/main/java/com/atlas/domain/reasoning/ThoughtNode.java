package com.atlas.domain.reasoning;

public class ThoughtNode {
    private String id;
    private String sessionId;
    private String content;
    private String type; // PROPOSAL, CRITIQUE, DECISION, ACTION
    private double score;
    private boolean selected;

    public ThoughtNode() {
    }

    public ThoughtNode(String id, String sessionId, String content, String type, double score, boolean selected) {
        this.id = id;
        this.sessionId = sessionId;
        this.content = content;
        this.type = type;
        this.score = score;
        this.selected = selected;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }

    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }
}
