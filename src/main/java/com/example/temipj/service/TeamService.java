package com.example.temipj.service;

import com.example.temipj.domain.Member;
import com.example.temipj.domain.Team;
import com.example.temipj.dto.requestDto.TeamRequestDto;
import com.example.temipj.dto.responseDto.Leader.TeamResponseDto;
import com.example.temipj.dto.responseDto.ResponseDto;
import com.example.temipj.exception.ErrorCode;
import com.example.temipj.jwt.TokenProvider;
import com.example.temipj.repository.TeamRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TokenProvider tokenProvider;

    private final TeamRepository teamRepository;

    // 팀 등록
    @Transactional
    public ResponseDto<?> createTeam(TeamRequestDto requestDto, HttpServletRequest request) {

        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
        }
        Member member = (Member) tokenProvider.getMemberFromAuthentication();
        if (null == member) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND.name(), ErrorCode.MEMBER_NOT_FOUND.getMessage());
        }
        if (requestDto.getTitle().isEmpty())
            return ResponseDto.fail(ErrorCode.NOT_BLANK_TEAM.name(), ErrorCode.NOT_BLANK_TEAM.getMessage());
        Team team = Team.builder()
                .title(requestDto.getTitle())
                .member(member)
                .build();
        teamRepository.save(team);

        return ResponseDto.success(
                TeamResponseDto.builder()
                        .title(team.getTitle())
                        .build()
        );
    }

    // 특정 팀 조회
    @Transactional
    public ResponseDto<?> getTeam(Long id) {
        Team team = isPresentTeam(id);
        if (null == team) {
            return ResponseDto.fail(ErrorCode.NOT_EXIST_TEAM.name(), ErrorCode.NOT_EXIST_TEAM.getMessage());
        }

        return ResponseDto.success(TeamResponseDto.builder()
                .title(team.getTitle())
                .employeeResponseDtoList(team.getEmployee())
                .build());
    }

    // 팀 전체 조회
    @Transactional
    public ResponseDto<?> getTeamAll() {
        return ResponseDto.success(teamRepository.findAllByOrderByModifiedAtDesc());
    }

    // 팀 수정
    @Transactional
    public ResponseDto<?> updateTeam(Long id, TeamRequestDto requestDto, HttpServletRequest request) {

        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
        }
        Team team = isPresentTeam(id);
        if (null == team) {
            return ResponseDto.fail(ErrorCode.NOT_EXIST_TEAM.name(), ErrorCode.NOT_EXIST_TEAM.getMessage());
        }
        Member member = (Member) tokenProvider.getMemberFromAuthentication();
        if (team.validateMember(member)) {
            return ResponseDto.fail(ErrorCode.TEAM_UPDATE_WRONG_ACCESS.name(), ErrorCode.TEAM_UPDATE_WRONG_ACCESS.getMessage());
        }
        team.update(requestDto);
        return ResponseDto.success(team);
    }

    // 팀 삭제
    @Transactional
    public ResponseDto<?> deleteTeam(Long id, HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());

        }
        Team team = isPresentTeam(id);
        if (null == team) {
            return ResponseDto.fail(ErrorCode.NOT_EXIST_TEAM.name(), ErrorCode.NOT_EXIST_TEAM.getMessage());
        }
        Member member = (Member) tokenProvider.getMemberFromAuthentication();
        if (team.validateMember(member)) {
            return ResponseDto.fail(ErrorCode.TEAM_DELETE_WRONG_ACCESS.name(), ErrorCode.TEAM_DELETE_WRONG_ACCESS.getMessage());
        }
        teamRepository.delete(team);
        return ResponseDto.success("게시글이 삭제되었습니다.");
    }

    // 팀 유효성 검사
    @Transactional
    public Team isPresentTeam(Long id) {
        Optional<Team> Team = teamRepository.findById(id);
        return Team.orElse(null);
    }
}
