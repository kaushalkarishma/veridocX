package com.veridocx.verification_services.dto;



import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class VerificationResponse {
    private UUID id;
    private UUID documentId;
    private UUID userId;
    private String status;
    private String reason;
    private String checksum;
    private Instant createdAt;
    private Instant completedAt;
}
