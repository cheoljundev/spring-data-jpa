package study.datajpa.dto;

import lombok.Data;
import study.datajpa.entity.Member;

@Data
public class MemberDto {
    private Long id;
    private String username;
    private String teamName;

    public MemberDto(Long id, String username, String teamName) {
        this.id = id;
        this.username = username;
        this.teamName = teamName;
    }

    // dto는 entity를 봐도 괜찮다.
    public MemberDto(Member member) {
        id = member.getId();
        username = member.getUsername();
    }
}
