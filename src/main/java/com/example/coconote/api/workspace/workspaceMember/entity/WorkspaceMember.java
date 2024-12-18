package com.example.coconote.api.workspace.workspaceMember.entity;

import com.example.coconote.api.channel.channelMember.entity.ChannelMember;
import com.example.coconote.api.member.entity.Member;
import com.example.coconote.api.workspace.workspace.entity.Workspace;
import com.example.coconote.api.workspace.workspaceMember.dto.request.WorkspaceMemberUpdateReqDto;
import com.example.coconote.api.workspace.workspaceMember.dto.response.WorkspaceMemberResDto;
import com.example.coconote.common.BaseEntity;
import com.example.coconote.common.IsDeleted;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workspaceMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "workspaceMember", cascade = CascadeType.PERSIST)
    @Builder.Default
    private List<ChannelMember> channelMembers = new ArrayList<>();

    private String memberName;

    private String nickname;

    private String field;

    private String position;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private WsRole wsRole = WsRole.USER;


    public WorkspaceMemberResDto fromEntity() {
        List<Long> myChannels = new ArrayList<>();
        for(ChannelMember cm : this.channelMembers) {
            if(cm.getIsDeleted().equals(IsDeleted.N)) {
                myChannels.add(cm.getChannel().getChannelId());
            }
        }

        return WorkspaceMemberResDto.builder()
                .workspaceMemberId(this.workspaceMemberId)
                .workspaceId(this.workspace.getWorkspaceId())
                .memberId(this.member.getId())
                .memberName(this.memberName)
                .nickname(this.nickname)
                .field(this.field)
                .position(this.position)
                .profileImage(this.profileImage)
                .wsRole(this.wsRole)
                .channels(myChannels)
                .email(this.member.getEmail())
                .build();
    }

    public void updateEntity(WorkspaceMemberUpdateReqDto dto) {
        this.memberName = dto.getMemberName();
        this.nickname = dto.getNickname();
        this.field = dto.getField();
        this.position = dto.getPosition();
        this.profileImage = dto.getProfileImage();
    }

    public WorkspaceMemberResDto changeRole(WsRole wsRole) {
        this.wsRole = wsRole;
        return this.fromEntity();
    }


    public void deleteEntity() {
        this.isDeleted = IsDeleted.Y;
        this.deletedTime = LocalDateTime.now();
        if(this.channelMembers != null) {
            for (ChannelMember c : this.channelMembers) {
                c.deleteEntity();
            }
        }
    }

    public void changeProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void restoreEntity() {
        this.isDeleted = IsDeleted.N;
        this.deletedTime = null;
    }
}
