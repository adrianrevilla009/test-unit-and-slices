package lab;

import org.springframework.stereotype.Service;

/** Top-level @Service (nested ones would be registered as config members): the web slice must not scan it. */
@Service
public class AuditService {}
