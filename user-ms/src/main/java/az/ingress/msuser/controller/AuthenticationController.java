package az.ingress.msuser.controller;


import az.ingress.msuser.request.RequestUser;
import az.ingress.msuser.request.UserReq;
import az.ingress.msuser.response.ResponseUser;
import az.ingress.msuser.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {


private  final AuthenticationService authenticationService;



   @GetMapping("/test")
   public  String demo(){

       return "test salam";
   }

    @PostMapping("/save")
    public ResponseEntity<ResponseUser> save(@RequestBody RequestUser userDto){



         return ResponseEntity.ok(authenticationService.save(userDto));


    }


    @PostMapping("/auth")
    public ResponseEntity<ResponseUser> auth(@RequestBody UserReq userReq){

log.info("auth");
        return ResponseEntity.ok(authenticationService.auth(userReq));


    }


}
