package com.phytoncide.hikinglog.domain.member.Service;

import com.phytoncide.hikinglog.base.code.ErrorCode;
import com.phytoncide.hikinglog.base.exception.MemberNotFoundException;
import com.phytoncide.hikinglog.base.exception.RegisterException;
import com.phytoncide.hikinglog.domain.member.config.AuthDetails;
import com.phytoncide.hikinglog.domain.member.dto.JoinDTO;
import com.phytoncide.hikinglog.domain.member.entity.MemberEntity;
import com.phytoncide.hikinglog.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public String join(JoinDTO joinDTO) {
        if(memberRepository.existsByEmail(joinDTO.getEmail())) {
            throw new RegisterException(ErrorCode.DUPLICATED_EMAIL);

        } else {
            MemberEntity member = MemberEntity.builder()
                    .email(joinDTO.getEmail())
                    .password(bCryptPasswordEncoder.encode(joinDTO.getPassword()))
                    .name(joinDTO.getName())
                    .birth(joinDTO.getBirth())
                    .sex(joinDTO.getSex())
                    .phone(joinDTO.getPhone())
                    .image(joinDTO.getImage())
                    .build();
            memberRepository.save(member);
            return "회원가입에 성공했습니다.";
        }
    }

    @Override
    public AuthDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println(email);
        MemberEntity memberEntity = memberRepository.findByEmail(email);
        return new AuthDetails(memberEntity);
    }

    public String findEmail(String phone) {
        Optional<MemberEntity> member;
        if(memberRepository.findByPhone(phone).isPresent()) {
            member = memberRepository.findByPhone(phone);
        } else {
            throw new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND);
        }

        return member.get().getEmail();
    }



}
