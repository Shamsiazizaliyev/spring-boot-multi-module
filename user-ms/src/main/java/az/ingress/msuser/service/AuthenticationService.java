package az.ingress.msuser.service;

import az.ingress.JwtService;
import az.ingress.model.User;
import az.ingress.msuser.request.RequestUser;
import az.ingress.msuser.request.UserReq;
import az.ingress.msuser.response.ResponseUser;
import az.ingress.repository.UserRepostory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Import({UserRepostory.class,JwtService.class})


public class AuthenticationService {

   private final UserRepostory userRepostory;

   private final AuthenticationManager authenticationManager;
   
   private final JwtService jwtService;

   private final PasswordEncoder passwordEncoder;
    public ResponseUser save(RequestUser userDto) {
        
        User user =User.builder().username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nameSurname(userDto.getNameSurname())
                .role(userDto.getRole())
                .build();
        userRepostory.save(user);

       var token = jwtService.generateToken(user);
       
        return  ResponseUser.builder().token(token).build();
    }

    public ResponseUser auth(UserReq userReq) {

      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userReq.getUsername(),userReq.getPassword()));
        User user=userRepostory.findByUsername(userReq.getUsername()).orElseThrow(()->new UsernameNotFoundException("user not found"));
        String token=jwtService.generateToken(user);
       // System.out.println("vvv"+ SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseUser.builder().token(token).build();

    }
}
