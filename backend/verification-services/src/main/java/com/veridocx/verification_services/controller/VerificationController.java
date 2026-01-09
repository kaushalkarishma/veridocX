package com.veridocx.verification_services.controller;

import com.veridocx.verification_services.dto.CreateVerificationRequest;
import com.veridocx.verification_services.dto.VerificationResponse;
import com.veridocx.verification_services.entity.Verification;
import com.veridocx.verification_services.service.VerificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/verify")
public class VerificationController {

    private final VerificationService svc;

    public VerificationController(VerificationService svc) {
        this.svc = svc;
    }

    // ✅ Create & run verification
    @PostMapping
    public ResponseEntity<VerificationResponse> verify(
            @RequestBody CreateVerificationRequest req
    ) throws Exception {
        Verification v = svc.createAndRunVerification(
                req.getDocumentId(),
                req.getUserId(),
                req.getFileUrl()
        );
        return ResponseEntity.ok(toResponse(v));
    }

    // ✅ Get verification by ID
    @GetMapping("/{id}")
    public ResponseEntity<VerificationResponse> get(@PathVariable UUID id) {
        return ResponseEntity.ok(toResponse(svc.getById(id)));
    }

    // ✅ Get verifications by document
    @GetMapping("/document/{documentId}")
    public ResponseEntity<?> byDocument(@PathVariable UUID documentId) {
        return ResponseEntity.ok(
                svc.getByDocumentId(documentId)
                        .stream()
                        .map(this::toResponse)
                        .collect(Collectors.toList())
        );
    }

    // ✅ Get verifications by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> byUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(
                svc.getByUserId(userId)
                        .stream()
                        .map(this::toResponse)
                        .collect(Collectors.toList())
        );
    }

    // ✅ Mapper method (THIS WAS MISSING)
    private VerificationResponse toResponse(Verification v) {
        VerificationResponse r = new VerificationResponse();
        r.setId(v.getId());
        r.setDocumentId(v.getDocumentId());
        r.setUserId(v.getUserId());
        r.setStatus(v.getStatus().name());
        r.setReason(v.getReason());
        r.setChecksum(v.getChecksum());
        r.setCreatedAt(v.getCreatedAt());
        r.setCompletedAt(v.getCompletedAt());
        return r;
    }
}
