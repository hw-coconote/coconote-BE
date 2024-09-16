package com.example.coconote.api.drive.entity;

import com.example.coconote.api.channel.entity.Channel;
import com.example.coconote.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Folder extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String folderName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_folder_id")
    private Folder parentFolder;

//    부모가 삭제되면 자식도 삭제하기 위해 cascade 설정
//    orphanRemoval = true 설정으로 부모가 삭제되면 자식도 삭제
//    nullpointerexception 방지를 위해 ArrayList로 초기화
    @OneToMany(mappedBy = "parentFolder" ,cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Folder> childFolders = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Channel channel;


    public void changeFolderName(String folderName) {
        this.folderName = folderName;
    }
}
