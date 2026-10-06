package lab;

import org.springframework.stereotype.Service;

/** Top-level @Service: the JPA slice must not scan it. */
@Service
public class AuditService {}
