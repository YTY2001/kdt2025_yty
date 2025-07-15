// src/boardapp/Board.java
package boardapp;

public class Board {
    private int id;
    private String title;
    private String writer;
    private String content;
    private String date;
    private int hit;

    // 기본 생성자
    public Board() {}

    // 새 글 작성용 생성자 (id, date, hit는 DB가 관리)
    public Board(String title, String writer, String content) {
        this.title   = title;
        this.writer  = writer;
        this.content = content;
    }

    // Getter & Setter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }
    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public int getHit() {
        return hit;
    }
    public void setHit(int hit) {
        this.hit = hit;
    }
}
