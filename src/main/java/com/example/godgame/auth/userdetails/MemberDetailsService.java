package com.example.godgame.auth.userdetails;


import com.example.godgame.auth.utils.JwtAuthorityUtils;
import com.example.godgame.exception.BusinessLogicException;
import com.example.godgame.exception.ExceptionCode;
import com.example.godgame.member.entity.Member;
import com.example.godgame.member.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Component
@Transactional
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final JwtAuthorityUtils authorityUtils;

    public MemberDetailsService(MemberRepository memberRepository, JwtAuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.authorityUtils = authorityUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findById(username);
        Member findMember = optionalMember.orElseThrow(()->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return new MemberDetails(findMember);
    }
    private final class MemberDetails extends Member implements UserDetails{
        public MemberDetails(Member member) {
            setMemberId(member.getMemberId());
            setId(member.getId());
            setPassword(member.getPassword());
            setRoles(member.getRoles());
            setMemberStatus(member.getMemberStatus());
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorityUtils.createAuthorities(this.getRoles());
        }

        @Override
        public String getUsername() {
            return this.getId();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }


}
