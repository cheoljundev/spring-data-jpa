package study.datajpa.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    // http://localhost:8080/members?page=0&size=3&sort=username,desc&sort=id,asc
    @GetMapping("/members")
//    public Page<Member> list(Pageable pageable) {
    public Page<Member> list(@PageableDefault(size = 5, sort = "username") Pageable pageable) {

        // 직접 pageRequest
        PageRequest request = PageRequest.of(1, 2);
        Page<Member> requesrPage = memberRepository.findAll(request);

        Page<Member> page = memberRepository.findAll(pageable);
//        page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));
        page.map(MemberDto::new);
        return page;
    }

    @PostConstruct
    public void init() {
         memberRepository.save(new Member("userA"));
        for (int i = 0; i < 100; i++) {
            memberRepository.save(new Member("user" + i, i));
        }
    }
}
