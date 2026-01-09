package com.veridocx.verification_services.service;

import com.veridocx.verification_services.entity.Verification;

import java.util.List;
import java.util.UUID;

public interface VerificationService {
    Verification createAndRunVerification(UUID documentId, UUID userId, String fileUrl) throws Exception;
    Verification getById(UUID id);
    List<Verification> getByUserId(UUID userId);
    List<Verification> getByDocumentId(UUID documentId);
}