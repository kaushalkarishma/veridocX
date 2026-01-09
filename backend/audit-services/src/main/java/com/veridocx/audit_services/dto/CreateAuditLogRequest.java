package com.veridocx.audit_services.dto;


import lombok.Data;
import java.util.UUID;

@Data
public class CreateAuditLogRequest {
    private UUID userId;
    private String action;
    private String details;
}
