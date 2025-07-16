package uz.onlineshop.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.onlineshop.config.UserPrincipal;
import uz.onlineshop.utils.GlobalVar;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        GlobalVar.clearContext();
        GlobalVar.initStartTime();

        String X_REQUESTED_ID = request.getHeader("X-Requested-ID");
        GlobalVar.setRequestId(X_REQUESTED_ID == null ? UUID.randomUUID().toString() : X_REQUESTED_ID);

        try {
            String authorization = request.getHeader("Authorization");
            if (authorization != null) {
                UserPrincipal userPrincipal = getUserFromBearerToken(authorization);
                if (userPrincipal != null) {
                    var authenticationToken = new UsernamePasswordAuthenticationToken(
                            userPrincipal, null, userPrincipal.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    GlobalVar.setUserPrincipal(userPrincipal);
                    GlobalVar.setUser(userPrincipal.user());
                }
            }
        } catch (Exception e) {
            log.error("SET_USER_PRINCIPAL_IF_ALL_OK_METHOD_ERROR", e);
        }
        filterChain.doFilter(request, response);
    }

    public UserPrincipal getUserFromBearerToken(String token) {
        token = token.trim();
        if (token.startsWith("Bearer ")) {
            token = token.substring("Bearer ".length()).trim();
            String userEmail = jwtTokenProvider.extractUsername(token);
            if (userEmail != null) {
                return (UserPrincipal) userDetailsService.loadUserByUsername(userEmail);
            }
        }
        return null;
    }
}
