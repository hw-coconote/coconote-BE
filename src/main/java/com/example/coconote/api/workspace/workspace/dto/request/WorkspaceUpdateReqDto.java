package com.example.coconote.api.workspace.workspace.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceUpdateReqDto {
    private String name;
    private String wsInfo;
}
