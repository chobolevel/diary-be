package com.daangn.api.annotation

import org.springframework.security.access.prepost.PreAuthorize

@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
annotation class HasAuthorityAdmin
