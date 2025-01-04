package com.scrimmers.api.annotation

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasRole('ADMIN')")
annotation class HasAuthorityAdmin
