package com.diary.api.annotation

import org.springframework.security.access.prepost.PreAuthorize

@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasAnyRole('ROLE_USER')")
annotation class HasAuthorityUser
