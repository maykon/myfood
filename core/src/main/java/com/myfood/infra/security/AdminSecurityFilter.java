package com.myfood.infra.security;

import com.myfood.domain.user.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.regex.Pattern;

@Component
public class AdminSecurityFilter extends OncePerRequestFilter {

    private boolean checkUserIsAdmin(HttpServletRequest request, HttpServletResponse response) {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return true;
        }
        var companyPattern = Pattern.compile("/api/v1/admin/.*$");
        var matcher = companyPattern.matcher(request.getRequestURI());
        if (matcher.find()) {
            var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user != null && user.getAuthorities().stream().noneMatch(grant -> grant.getAuthority().equals("ROLE_ADMIN"))) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return false;
            }
        }
        return true;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (this.checkUserIsAdmin(request, response)) {
            filterChain.doFilter(request, response);
        }
    }
}
