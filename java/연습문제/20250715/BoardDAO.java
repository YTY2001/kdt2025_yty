// src/boardapp/BoardDAO.java
package boardapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {

    /** 전체 글 목록 조회 (최신순) */
    public List<Board> findAll() {
        List<Board> list = new ArrayList<>();
        String sql = "SELECT id, title, writer, content, " +
                     "DATE_FORMAT(date,'%Y-%m-%d %H:%i') AS date, hit " +
                     "FROM board ORDER BY id DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Board b = new Board();
                b.setId(rs.getInt("id"));
                b.setTitle(rs.getString("title"));
                b.setWriter(rs.getString("writer"));
                b.setContent(rs.getString("content"));
                b.setDate(rs.getString("date"));
                b.setHit(rs.getInt("hit"));
                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /** 새 글 저장 */
    public void insert(Board b) {
        String sql = "INSERT INTO board(title, writer, content) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getWriter());
            ps.setString(3, b.getContent());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** ID로 글 조회 + 조회수 증가 */
    public Board findById(int id) {
        String selectSql = "SELECT id, title, writer, content, " +
                           "DATE_FORMAT(date,'%Y-%m-%d %H:%i') AS date, hit " +
                           "FROM board WHERE id = ?";
        String updateSql = "UPDATE board SET hit = hit + 1 WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement psSel = conn.prepareStatement(selectSql)) {
            psSel.setInt(1, id);
            try (ResultSet rs = psSel.executeQuery()) {
                if (rs.next()) {
                    Board b = new Board();
                    b.setId(rs.getInt("id"));
                    b.setTitle(rs.getString("title"));
                    b.setWriter(rs.getString("writer"));
                    b.setContent(rs.getString("content"));
                    b.setDate(rs.getString("date"));
                    b.setHit(rs.getInt("hit"));
                    // 조회수 증가
                    try (PreparedStatement psUpd = conn.prepareStatement(updateSql)) {
                        psUpd.setInt(1, id);
                        psUpd.executeUpdate();
                        b.setHit(b.getHit() + 1);
                    }
                    return b;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 글 삭제 */
    public void delete(int id) {
        String sql = "DELETE FROM board WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** 글 수정 */
    public void update(Board b) {
        String sql = "UPDATE board SET title = ?, writer = ?, content = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getWriter());
            ps.setString(3, b.getContent());
            ps.setInt(4, b.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
